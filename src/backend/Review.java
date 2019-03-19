package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Stores review information
 * @author Matthew Williams
 */
public class Review {
	private final String USER_ID;
	private final String BUSINESS_ID;
	private final int STARS;
	private static final String FILEPATH = "data/reviewFiltered.json";
	
	/**
	 * Constructor
	 * @param id the user or business ID
	 * @param isBusiness true if the id is a business id
	 */
	public Review(String userID, String businessID, int stars) {
		this.USER_ID = userID;
		this.BUSINESS_ID = businessID;
		this.STARS = stars;
	}

	public String getUserID() {
		return USER_ID;
	}

	public String getBusinessID() {
		return BUSINESS_ID;
	}

	public int getStars() {
		return STARS;
	}
	
	/**
	 * Returns list of reviews under a user
	 * @param r The restaurant object
	 * @return ArrayList of reviews
	 */
	public static ArrayList<Review> getReviewsFrom(Restaurant r)
	{ return getReviewsFrom(r, FILEPATH); }
	
	public static ArrayList<Review> getReviewsFrom(Restaurant r, String path) {
		ArrayList<Review> reviews = null;
		String[] ids = { r.getID() };
		try {
			reviews = fromID("business_id", ids, r.getReviewCount(), path);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return reviews;
	}
	
	/**
	 * Returns list of reviews under a user
	 * @param u The user object
	 * @return ArrayList of reviews
	 */
	public static ArrayList<Review> getReviewsFrom(User u)
	{ return getReviewsFrom(u, FILEPATH); }
	
	public static ArrayList<Review> getReviewsFrom(User u, String path) {
		ArrayList<Review> reviews = null;
		String[] ids = { u.getID() };
		try {
			reviews = fromID("user_id", ids, u.getReviewCount(), path);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return reviews;
	}
	/**
	 * Returns all reviews from a list of users
	 * @param users ArrayList of users
	 * @return ArrayList of reviews
	 */
	public static ArrayList<Review> getReviewsFrom(ArrayList<User> users)
	{ return getReviewsFrom(users, FILEPATH); }
	
	public static ArrayList<Review> getReviewsFrom(ArrayList<User> users, String path) {
		ArrayList<Review> reviews = null;
		int n = users.size(), reviewCount = 0, i = 0;
		String[] ids = new String[n];
		
		for (User u: users) {
			ids[i++] = u.getID();
			reviewCount += u.getReviewCount();
		}
		
		try {
			reviews = fromID("user_id", ids, reviewCount, path);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return reviews;
	}
	
	private static ArrayList<Review> fromID(String type, String[] ids, int count, String path) throws JSONException, IOException {
		ArrayList<Review> reviews = new ArrayList<Review>();
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line;
		int i = 0;
		boolean idMatch = false;
		String id = "";
		
		while((line = br.readLine()) != null) {
			JSONObject currObj = new JSONObject(line);
			
			for (int j = 0; j < ids.length; j++) {
				id = (String)currObj.get(type);
				if ( id.equals(ids[j]))
					idMatch = true;
			}
			if (idMatch) {
				reviews.add(new Review(
						(String)currObj.get("user_id"),
						(String)currObj.get("business_id"),
						(int)currObj.get("stars")
						));
				if (++i == count) break;
			}
			idMatch = false;
		}
		br.close();
		return reviews;
	}
}
