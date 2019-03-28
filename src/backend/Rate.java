package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Rate {
	
	// The radius in which to recommend restaurants in KM
	public static final double RADIUS = 100;
	
	public static void main(String[] args) {
		ReadData data = new ReadData();
		Scanner in = new Scanner(System.in);
		ArrayList<Restaurant> userRestaurants = new ArrayList<Restaurant>();
		ArrayList<Double> userStars = new ArrayList<Double>();
		ArrayList<RankPair> recommendation = new ArrayList<RankPair>();
		
		// Related to the user's position
		Location loc;
		
		for(;;) {
			System.out.println("Please input your latitude and longitude, in that order, space seperated:");
			try {
				double latitude = in.nextDouble();
				double longitude = in.nextDouble();
				loc = new Location("", "", "", latitude, longitude);
				break;
			} catch (Exception e) {
				System.out.println("That is not in the correct format");
				in.nextLine();
			}
		}
		
		// Get input restaurants
		
		// cHdJXLlKNWixBXpDwEGb_A is a good test string
		// mlHC2XcU9Bows6cnYEmRgg is in Toronto --> should yield results
		while(true) {
			// TODO: select restaurant by name instead of ID
			System.out.println("Enter the id of the restaurant you tried - or c to continue");
			String id = in.nextLine();
			Restaurant restaurant = data.getRestaurant(id);

			if (restaurant != null) userRestaurants.add(restaurant);
			else if(id.equals("c")) break; 
			else {
				System.out.println("This ID doesn't not correspond to a restaurant");
				continue;
			}
			
			System.out.println("Enter your rating out of 5");
			userStars.add(Double.parseDouble(in.nextLine()));
			
		} in.close();
		
		// Build suggestions
		
		// TODO: I think we still need to filter out duplicates
		
		int i = 0;
		for (Restaurant r: userRestaurants) {
			for (Review review: r.getReviews()) {
				for (Review extended: data.getUser(review.getUserID()).getReviews()) {
					double congruence = 1/Math.abs(review.getStars() - userStars.get(i));
					double hardToPlease = 1/(review.getStars());
					Restaurant toTry = data.getRestaurant(extended.getBusinessID());
					double rating = congruence * hardToPlease * extended.getStars() * toTry.getStars();
					recommendation.add(new RankPair(rating, toTry));
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
		
		System.out.println(recommendation);
	}
}