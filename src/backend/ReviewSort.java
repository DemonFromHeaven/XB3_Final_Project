package backend;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Load sorted review arrays from json
 * @author Matthew Williams L03 willim36
 */
public class ReviewSort {
	private static final String REVIEW_FILEPATH = "data/reviewFiltered.json";
	
	private static Review[] reviewsByBusiness;
	private static Review[] reviewsByUser;
	
	/**
	 * Constructor loads the arrays from json
	 */
	ReviewSort() {
		// Read reviews into arraylist from file
		BufferedReader br = null;
		ArrayList<Review> a = new ArrayList<Review>();
		String line;
		try {
			br = new BufferedReader(new FileReader(REVIEW_FILEPATH));
			while((line = br.readLine()) != null) {
				JSONObject currObj = new JSONObject(line);
				a.add(	new Review(
						(String) currObj.get("user_id"),
						(String) currObj.get("business_id"),
						(double) currObj.get("stars")
						)
				);
			}
			br.close();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Sort by user then business
		Comparator<Review> byUser = new Comparator<Review>() {
			public int compare(Review r1, Review r2) {
				return r1.getUserID().compareTo(r2.getUserID());
			}
		};
		a.sort(byUser);
		ReviewSort.reviewsByUser = (Review[]) a.toArray();
		
		Comparator<Review> byBusiness = new Comparator<Review>() {
			public int compare(Review r1, Review r2) {
				return r1.getBusinessID().compareTo(r2.getBusinessID());
			}
		};
		a.sort(byBusiness);
		ReviewSort.reviewsByBusiness = (Review[]) a.toArray();
	}
	/**
	 * Access the sorted reviews
	 * @param type Must be user or restaurant
	 * @return The array of sorted reviews
	 */
	public static Review[] getReviews(String type) {
		if (type.equals("user")) return reviewsByUser;
		if (type.equals("restaurant"))return reviewsByBusiness;
		return null;
	}
	/**
	 * Access the reviews sorted by user
	 * @return The array of sorted reviews
	 */
	public static Review[] getReviewsByUser() {
		return reviewsByUser;
	}
	/**
	 * Access the reviews sorted by restaurant
	 * @return The array of sorted reviews
	 */
	public static Review[] getReviewsByBusiness() {
		return reviewsByBusiness;
	}
}
