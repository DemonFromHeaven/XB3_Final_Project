package backend;
/**
 * Stores review information
 * @author Matthew Williams L03 willim36
 */
public class Review{
	private final String USER_ID;
	private final String BUSINESS_ID;
	private final double STARS;	
	/**
	 * Review constructor
	 * @param userID ID associatied with user
	 * @param businessID ID associatied with restaurant
	 * @param stars The rating of the restaurant  /5
	 */
	public Review(String userID, String businessID, double stars) {
		this.USER_ID = userID;
		this.BUSINESS_ID = businessID;
		this.STARS = stars;
	}
	/**
	 * Get the user id
	 * @return The user id
	 */
	public String getUserID() {
		return USER_ID;
	}
	/**
	 * Get the business id
	 * @return The business id
	 */
	public String getBusinessID() {
		return BUSINESS_ID;
	}
	/**
	 * Get the star rating
	 * @return The star rating
	 */
	public double getStars() {
		return STARS;
	}
	/**
	 * Get the id based on entered type
	 * @param type The type of id to get
	 * @return The id
	 */
	public String getID(String type) {
		if (type.equals("user")) 		return getUserID();
		if (type.equals("restaurant"))	return getBusinessID();
		return null;
	}
}
