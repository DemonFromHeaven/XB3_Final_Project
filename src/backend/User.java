package backend;

import java.util.ArrayList;

/**
 * Holds User info
 * @author Matthew Williams
 *
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
	public void addReview(Review review) {
		reviews.add(review);
	}
	public ArrayList<Review> getReviews() {
		return reviews;
	}
}
