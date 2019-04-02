package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Rate {

	// The radius in which to recommend restaurants in KM
	public static final double RADIUS = 100;
	public static final int SEARCH_LIMIT = 10;

	public static void main(String[] args) throws Exception {
		ReadData data = new ReadData();
		Scanner in = new Scanner(System.in);
		ArrayList<Restaurant> userRestaurants = new ArrayList<Restaurant>();
		ArrayList<Double> userStars = new ArrayList<Double>();
		ArrayList<RankPair> recommendation = new ArrayList<RankPair>();

		// Related to the user's position
		Location loc;

		// Get the user's location
		// Toronto is 43.657716 -79.383931
		for (;;) {
			System.out.println("Please input your latitude and longitude, in that order, space seperated:");
			try {
				double latitude = in.nextDouble();
				double longitude = in.nextDouble();
				loc = new Location("", "", "", latitude, longitude);
				break;
			} catch (Exception e) {
				System.out.println("That is not in the correct format.");
				in.nextLine();
			}
		}
		in.nextLine();

		// GET USER EXPERIENCE

		for (;;) {

			// Prompt for restaurant search
			System.out.println("Please enter the name restaurant you tried,\nor c to (c)ontinue to recommendations:");
			String name = in.nextLine();

			// Break if user flags they are done entering
			if (name.equals("c"))
				break;

			ArrayList<Restaurant> results = data.searchByName(name, 10);

			if (results.size() == 0) {
				System.out.println("No restaurants were found");
				in.nextLine();
				continue;
			}

			// Print out the list of results
			for (int i = 1; i < results.size() + 1; i++) {
				System.out.println("(" + i + ") " + results.get(i - 1));
			}

			// Get the index of the restaurant they went to
			int selection = -1;
			while (selection < 0 || selection > results.size()) {
				System.out.println("Please enter a number between 1 and" + results.size()
						+ " to indicate which restaurant you visited");
				System.out.println("Enter 0 if none of these match where you went");
				try {
					selection = in.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("That is not a valid number!");
				}
			}

			// None of the results matched, try searching again
			if (selection == 0) {
				in.nextLine();
				continue;
			}

			// Otherwise they have input their selection; add it
			userRestaurants.add(results.get(selection - 1));

			in.nextLine();
			System.out.println("Enter your rating out of 5");
			userStars.add(Double.parseDouble(in.nextLine()));

		}
		in.close();

		// Build suggestions

		// TODO: Filter out duplicates properly

		//
		int i = 0;
		for (Restaurant r : userRestaurants) {
			for (Review review : r.getReviews()) {
				for (Review extended : data.getUser(review.getUserID()).getReviews()) {
					// Assign rating based of many factors
					double congruence = 1 / (Math.abs(review.getStars() - userStars.get(i)) + 1);
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
			i++;
		}

		// Filter by distance:
		// If distance is greater than RADIUS, remove it
		// No need to recommend far away restaurants
		i = 0;
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

		// Print out the recommendations
		System.out.println("Recommendations:");
		for (RankPair rp : recommendation) {
			System.out.println(rp.restaurant);
		}

	}
}