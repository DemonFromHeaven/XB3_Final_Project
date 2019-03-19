package backend;

/**
 * Holds User info
 * @author Matthew Williams
 *
 */
public class User {
	private final String id;
	private final int reviewCount;
	
	/**
	 * Constructor
	 * @param id user id
	 * @param reviewCount count of users reviews
	 */
	public User(String id, int reviewCount) {
		this.id = id;
		this.reviewCount = reviewCount;
	}
	/**
	 * get users id
	 * @return user id
	 */
	public String getID() {
		return id;
	}

	/**
	 * get users number of reviews
	 * @return review count
	 */
	public int getReviewCount() {
		return reviewCount;
	}
	
	
}
