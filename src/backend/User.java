package backend;
/**
 * Holds User info
 * @author Matthew Williams
 *
 */
public class User {
	private final String id;	
	/**
	 * Constructor
	 * @param id user id
	 * @param reviewCount count of users reviews
	 */
	public User(String id) {
		this.id = id;
	}
	/**
	 * get users id
	 * @return user id
	 */
	public String getID() {
		return id;
	}
}
