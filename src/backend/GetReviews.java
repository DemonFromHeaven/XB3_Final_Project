package backend;
import java.util.ArrayList;
/**
 * Uses binary sort to retrieve reviews from a given user or restaurant
 * @author Matthew Williams L03 willim36
 */
public class GetReviews {
	/**
	 * Retrieve reviews from this user
	 * @param u The user to get reviews from
	 * @return ArrayList containing the reviews
	 */
	public static ArrayList<Review> from(User u) {
		String id = u.getID();
		return from(id, "user");
	}
	/**
	 * Retrieve reviews from this restaurant
	 * @param r The restaurant to get reviews from
	 * @return ArrayList containing the reviews
	 */
	public static ArrayList<Review> from(Restaurant r) {
		String id = r.getID();
		return from(id, "restaurant");
	}
	
	// Returns list of reviews match user/business id
	private static ArrayList<Review> from(String id, String type) {
		Review[] reviewArray = ReviewSort.getReviews(type);
		ArrayList<Review> reviewList = new ArrayList<Review>();
		int i = indexOf(reviewArray, id, type);
		
		//Find first matching review
		while(reviewArray[i - 1].getID(type).equals(id)) i--;
		//Add all matching reviews
		while(reviewArray[i].getID(type).equals(id))
			reviewList.add(reviewArray[i++]);
		
		return reviewList;
	}
	// binary search for to find review with given user/business id
	private static int indexOf(Review[] reviews, String id, String type) {
        int lo = 0, hi = reviews.length - 1, cmp;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            cmp = id.compareTo(reviews[mid].getID(type));
            if (cmp == 0) return mid;
            hi = mid + cmp;
        }
        return -1;
    }
}
