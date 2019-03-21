package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class ReadData {
	private static final String BUSINESS_FILEPATH = "data/business.json";
	private static final String REVIEW_FILEPATH = "data/review_subset.json";
	
	/**
	 * Read the restaurants from the data into a RedBlackBST of Restaurants
	 * @return The RedBlackBST of the Restaurants
	 */
	public static LinearProbingHashST<String, Restaurant> read(String business_filepath, String review_filepath) {
		LinearProbingHashST<String, Restaurant> restaurantData = new LinearProbingHashST<String, Restaurant>(400000);
		BufferedReader businessReader;
		BufferedReader reviewReader;
		String line;
		
		try {
			//Read businesses to hash table
			businessReader = new BufferedReader(new FileReader(business_filepath));
			while ((line = businessReader.readLine()) != null) {
				Restaurant restaurant = RestaurantRead(line);
				if (restaurant != null)
					restaurantData.put(restaurant.getID(), restaurant);
			} businessReader.close();
			
			//Read Reviews into linked list for each restaurant
			reviewReader = new BufferedReader(new FileReader(review_filepath));
			while ((line = reviewReader.readLine()) != null) {
				Review review = ReviewRead(line);
				Restaurant restaurant = restaurantData.get(review.getBusinessID());
				if (restaurant != null)
					restaurant.addReview(review);
					
			} reviewReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return restaurantData;
	}
	public static LinearProbingHashST<String, Restaurant> read() {
		return read(BUSINESS_FILEPATH, REVIEW_FILEPATH);
	}
	
	// Copied from David's restRead.java
	private static Restaurant RestaurantRead(String line) {
		// Make a JSON object from the current line
		JSONObject currObj = new JSONObject(line);
		Restaurant restaurant;
		// Extract the desired parameters from the object
		String id = (String)currObj.get("business_id");
		String name = (String)currObj.get("name");
		Location loc = new Location(
				(String)currObj.get("address"),
				(String)currObj.get("city"),
				(String)currObj.get("state"),
				currObj.getInt("latitude"),
				currObj.getInt("longitude"));
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
	
	private static Review ReviewRead(String line) {
		JSONObject currObj = new JSONObject(line);
		return new Review(
						(String) currObj.get("user_id"),
						(String) currObj.get("business_id"),
						(double) currObj.get("stars")
		);
	}
	
	public static void main(String[] args) {
		LinearProbingHashST<String, Restaurant> restaurantData = read();
		System.out.println(restaurantData.contains("Gyrez6K8f1AyR7dzW9fvAw")
				);
		for (Review r : restaurantData.get("Gyrez6K8f1AyR7dzW9fvAw").getReviews())
			System.out.println(r);
	}
}
