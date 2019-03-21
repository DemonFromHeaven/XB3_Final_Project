package backend;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Rate {
	public static void main(String[] args) {
		new ReadData();
		Scanner in = new Scanner(System.in);
		ArrayList<Restaurant> userRestaurants = new ArrayList<Restaurant>();
		ArrayList<Double> userStars = new ArrayList<Double>();
		ArrayList<RankPair> recommendation = new ArrayList<RankPair>();
		
		// cHdJXLlKNWixBXpDwEGb_A is a good test string
		while(true) {
			System.out.println("Enter the id of the restaurant you tried - or c to continue");
			String id = in.nextLine();
			Restaurant restaurant = ReadData.restaurantData.get(id);

			if (restaurant != null) userRestaurants.add(restaurant);
			else if(id.equals("c")) break; 
			else {
				System.out.println("This id doesnt not correspond to a restaurant");
				continue;
			}
			
			System.out.println("Enter your rating out of 5");
			userStars.add(Double.parseDouble(in.nextLine()));
			
		} in.close();
		
		int i = 0;
		for (Restaurant r: userRestaurants) {
			for (Review review: r.getReviews()) {
				for (Review extended: ReadData.userData.get(review.getUserID()).getReviews()) {
					double congruence = 1/Math.abs(review.getStars() - userStars.get(i));
					double hardToPlease = 1/(review.getStars());
					Restaurant toTry = ReadData.restaurantData.get(extended.getBusinessID());
					double rating = congruence * hardToPlease * extended.getStars() * toTry.getStars();
					recommendation.add(new RankPair(rating, toTry));
				}
			}
			i++;
		}
		Comparator<RankPair> byRating = new Comparator<RankPair>() {
			public int compare(RankPair r1, RankPair r2) {
				if (r1.rating < r2.rating) return 1;
				else if (r1.rating > r2.rating) return -1;
				return 0;
			}
		};
		
		recommendation.sort(byRating);
		System.out.println(recommendation);
	}
}