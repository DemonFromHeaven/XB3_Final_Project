package rest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.RankPair;
import backend.ReadData;
import backend.Restaurant;
import backend.Review;

public class GetSuggestion extends HttpServlet {

    // Default search radius
	private final int MAX_RESULTS = 10;
	private final double RADIUS = 100;

    ReadData data;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

		// Format of the response is UTF-8
		response.setCharacterEncoding("utf-8");

        data = (ReadData) getServletContext().getAttribute("data");
		
		// Default values for non-mandatory parameters
		double radius = RADIUS;
		int max = MAX_RESULTS;

		// Try to read in their values, update if they can be read
		try {
			radius = Double.parseDouble(request.getParameter("rad"));
		} catch (NumberFormatException e){
		}
        try {
			max = Integer.parseInt(request.getParameter("max"));
        } catch (NumberFormatException e) {
		}
		
		// Everything else is required, so error if we don't get it
		try {
			
			// Get the mandatory passed parameters
			double latitude = Double.parseDouble(request.getParameter("lat"));
			double longitude = Double.parseDouble(request.getParameter("long"));
			String[] visited = request.getParameterValues("visited");
			String[] stars = request.getParameterValues("stars");

			// Allocate an ArrayList for holding the suggestions
			ArrayList<RankPair> recommendation = new ArrayList<>();

			// For all the submitted restaurant/star rating pairs
			for (int i = 0; i < visited.length; i++) {
				Restaurant userRestaurant = data.getRestaurant(visited[i]);
				double userStars = Double.parseDouble(stars[i]);

				if (userRestaurant == null) {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					return;
				}

				// Get the reviews for the restaurant
				for (Review review : userRestaurant.getReviews()) {

					// Get all reviews of the reviewer
					for (Review extended : data.getUser(review.getUserID()).getReviews()) {

						// Assign rating based off many factors
						double congruence = 1 / (Math.abs(review.getStars() - userStars) + 1);
						double hardToPlease = 1 / (review.getStars());
						Restaurant toTry = data.getRestaurant(extended.getBusinessID());
						double rating = congruence * hardToPlease * extended.getStars() * toTry.getStars();
						RankPair candidateRest = new RankPair(rating, toTry);

						// If this isn't already recommended, recommend it
						if (!recommendation.contains(candidateRest)) {
							recommendation.add(candidateRest);
						} else {
							// If this is already recommended, update the recommendation with the
							// new rating if it is higher (otherwise just ignore it)
							RankPair rp = recommendation.get(recommendation.indexOf(candidateRest));
							if (rp.rating < rating) {
								recommendation.remove(rp);
								recommendation.add(candidateRest);
							}
						}
					}
				}
			}

			// Filter by distance:
			// If distance is greater than RADIUS, remove it
			// No need to recommend far away restaurants
			int i = 0;
			while (i < recommendation.size()) {
				// If this restaurant is out of radius, remove it from the list
				if (recommendation.get(i).restaurant.getLocation().distanceTo(loc) > RADIUS) {
					recommendation.remove(i);
					// Move on to checking the next value in the list, which
					// is at the same index as this was
					continue;
				}
				// If this is okay, advance to the next element
				i++;
			}

			// Sort by rating
			Collections.sort(recommendation);


			// Construct the JSON response
			BufferedWriter bw = new BufferedWriter(response.getWriter());
			for (RankPair rp: recommendation) {
				bw.write(rp.restaurant.toString());
				bw.write("\n");
			}
			bw.close();

		} catch (Exception e) {
			// If an error occurs while reading the parameters, give a
			// "bad syntax" response
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

    }

}