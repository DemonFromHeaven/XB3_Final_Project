package backend;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * Restaurant ADT
 * @author David Thompson
 * @author Matthew Williams
 *
 */
public class Restaurant implements Comparable<Restaurant> {

	private String name;  // The name of the Restaurant

	private String id;  // The unique identifier for the Restaurant from the file
	private Location loc;  // The address of the Restaurant
	private double stars;
	private int price;
	private int reviewCount;
	private ArrayList<Review> reviews;
	
	/**
	 * Review Constructor
	 * @param name The restaurant name
	 * @param id The restaurant id
	 * @param loc The coordinates
	 * @param stars The star rating
	 * @param price The p
	 * @param reviewCount
	 */
	public Restaurant(String name,
			String id,
			Location loc,
			double stars,
			int price,
			int reviewCount) {
		
		this.name = name;
		this.id = id;
		this.loc = loc;
		this.stars = stars;
		this.price = price;
		this.reviewCount = reviewCount;
		this.reviews = new ArrayList<Review>();
		
	}
	
	public Restaurant(String id) {
		this.id = id;
	}

	/**
	 * ID getter
	 * @return The restaurant ID
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Name getter
	 * @return The restuarant name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Stars getter
	 * @return The star rating
	 */
	public double getStars() {
		return stars;
	}
	
	/**
	 * Price getter
	 * @return The restaurant price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Review count getter
	 * @return The number of reviews
	 */
	public int getReviewCount() {
		return this.reviewCount;
	}
	
	/**
	 * Location getter
	 * @return The coordinates of the restaurant
	 */
	public Location getLocation() {
		return this.loc;
	}
	
	@Override
	public int compareTo(Restaurant that) {
		return this.id.compareTo(that.id);
	}
	
	@Override
	public boolean equals(Object that) {
		try {
			Restaurant r = (Restaurant) that;
			return this.id.equals(r.id);
		} catch (ClassCastException e) {
			return false;
		}
	}
	
	/**
	 * String with name and location
	 * @return The string representation
	 */
	public String toString() {
		return name + " at " + loc;
	}
	
	/**
	 * Add a review to the review list
	 * @param review The review to add
	 */
	public void addReview(Review review) {
		reviews.add(review);
	}
	
	/**
	 * Read a restaurant from a JSON String into a Restaurant object.
	 * @param jsonString The JSON string to read from
	 * @return The Restaurant object
	 */
	public static Restaurant fromJSON(String jsonString) {
		// Make a JSON object from the current line
		JSONObject currObj = new JSONObject(jsonString);
		Restaurant restaurant;
		// Extract the desired parameters from the object
		String id = (String)currObj.get("business_id");
		String name = (String)currObj.get("name");
		Location loc = new Location(
				(String)currObj.get("address"),
				(String)currObj.get("city"),
				(String)currObj.get("state"),
				currObj.getDouble("latitude"),
				currObj.getDouble("longitude"));
		double stars = currObj.getDouble("stars");
		int reviewCount = currObj.getInt("review_count");
		
		// Extract the string data of the attributes parameter
		String attributesString = currObj.get("attributes").toString();
		
		// Try to convert this string data into another JSON object
		// If this succeeds, get the price field
		// If it fails, then this isn't a restaurant
		try {
			JSONObject attributes = new JSONObject(attributesString);
			int price = attributes.getInt("RestaurantsPriceRange2");
			restaurant = new Restaurant(name, id, loc, stars, price, reviewCount);
		} catch (JSONException jsone) {
			restaurant = null;
		}
		return restaurant;
	}
	
	/**
	 * Get a JSON String representation of this Restaurant
	 * 
	 * @return A JSON String representation of this Restaurant
	 */
	public String toJSON() {

		JSONObject attr = new JSONObject().put("RestaurantsPriceRange2", this.price);

		String rep = new JSONStringer()
			.object()
				.key("business_id").value(this.id)
				.key("name").value(this.name)
				.key("stars").value(this.stars)
				.key("review_count").value(this.reviewCount)
				.key("address").value(this.getLocation().getAddress())
				.key("city").value(this.getLocation().getCity())
				.key("state").value(this.getLocation().getState())
				.key("latitude").value(this.getLocation().getLatitude())
				.key("longitude").value(this.getLocation().getLongitude())
				.key("attributes").value(attr)
			.endObject()
			.toString();

		return rep;

	}
	
	/**
	 * Getter for review list
	 * @return The list of reviews
	 */
	public ArrayList<Review> getReviews() {
		return reviews;
	}

}