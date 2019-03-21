package backend;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Rate {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		ArrayList<ArrayList<Review>> mutualReviews = new ArrayList<ArrayList<Review>>();
		ArrayList<Restaurant> userRestaurants = new ArrayList<Restaurant>();
		ArrayList<RankPair> potentialRestaurants = new ArrayList<RankPair>();
		ArrayList<Double> userStars = new ArrayList<Double>();
		String tempid;
		
		// cHdJXLlKNWixBXpDwEGb_A is a good test string
		while(true) {
			System.out.println("Enter the id of the restaurant you tried - or 1 to exit");
			tempid = in.nextLine();
			if (tempid.equals("1")) break;
			userRestaurants.add(new Restaurant(tempid));
			System.out.println("Enter your rating out of 5");
			userStars.add(Double.parseDouble(in.nextLine()));
		} in.close();
		
		
		for (Restaurant r: userRestaurants)
//			System.out.println(GetReviews.from(r));
			mutualReviews.add(GetReviews.from(r));
		
		int i = 0;
		for (ArrayList<Review> reviews: mutualReviews) {
			for (Review r: reviews) {
				double weight = userStars.get(i);
				User u = new User(r.getUserID());
				ArrayList<Review> extendedReviews = GetReviews.from(u);
				
				for (Review e: extendedReviews)
					if (e.getBusinessID() != r.getBusinessID())
						potentialRestaurants.add( new RankPair(e.getStars()*weight,
												  new Restaurant(e.getBusinessID())));
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
		
		potentialRestaurants.sort(byRating);
		System.out.println(potentialRestaurants);
	}
}