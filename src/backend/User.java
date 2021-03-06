package backend;

import java.util.ArrayList;

import org.json.JSONObject;

/**
 * Holds User info
 * @author Matthew Williams
 */
public class User {
	
	private final String id;
	private ArrayList<Review> reviews;
	
	/**
	 * Constructor
	 * @param id user id
	 * @param reviewCount count of users reviews
	 */
	public User(String id) {
		this.id = id;
		this.reviews = new ArrayList<Review>();
	}
	
	/**
	 * get users id
	 * @return user id
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Add a review to this user
	 * @param review The review to add
	 */
	public void addReview(Review review) {
		reviews.add(review);
	}
	
	/**
	 * Get all this user's reviews as an ArrayList
	 * @return An ArrayList of the user's reviews
	 */
	public ArrayList<Review> getReviews() {
		return reviews;
	}
	
	/**
	 * Make a User from a JSON String
	 * @param jsonString The JSON string to read from
	 * @return The User object
	 */
	public static User fromJSON(String jsonString) {
		JSONObject currObj = new JSONObject(jsonString);
		return new User((String) currObj.get("user_id"));
	}
	
}
