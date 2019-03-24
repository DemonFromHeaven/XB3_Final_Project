package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class ReadData {
	
	// I have these constants in the Filepaths.java file now
//	private static final String BUSINESS_FILEPATH = "data/business.json";
//	private static final String REVIEW_FILEPATH = "data/reviewFiltered.json";
//	private static final String USER_FILEPATH = "data/user.json";
	
	static LinearProbingHashST<String, Restaurant> restaurantData;
	static LinearProbingHashST<String, User> userData;
	
	/**
	 * Read the restaurants and user data from file into Linear
	 * Probing Hash Tables
	 * @return The Linear Probing Hash Table of the Restaurants
	 */
	public ReadData() {
		LinearProbingHashST<String, Restaurant> restaurantData = new LinearProbingHashST<String, Restaurant>(400000);
		LinearProbingHashST<String, User> userData = new LinearProbingHashST<String, User>(400000);
		BufferedReader businessReader;
		BufferedReader reviewReader;
		BufferedReader userReader;
		String line;
		
		try {
			
			// Check if the filtered restaurant file is created. If not, create it
			File restaurantsFiltered = new File(Filepaths.BUSINESS_FILEPATH_FILTERED);
			if (!restaurantsFiltered.exists()) {
				System.out.println("Creating the filtered business file...");
				FileFilter.filterRest();
				System.out.println("Done!");
			}
			
			// Read businesses to hash table
			businessReader = new BufferedReader(new FileReader(Filepaths.BUSINESS_FILEPATH_FILTERED));
			while ((line = businessReader.readLine()) != null) {
				Restaurant restaurant = RestaurantRead(line);
				if (restaurant != null)
					restaurantData.put(restaurant.getID(), restaurant);
			} businessReader.close();
			
			// Read users to hash table
			userReader = new BufferedReader(new FileReader(Filepaths.USER_FILEPATH));
			while ((line = userReader.readLine()) != null) {
				User user = UserRead(line);
				if (user != null)
					userData.put(user.getID(), user);
			} userReader.close();
			
			//Read Reviews into linked list for each restaurant
			reviewReader = new BufferedReader(new FileReader(Filepaths.REVIEW_FILEPATH));
			while ((line = reviewReader.readLine()) != null) {
				Review review = ReviewRead(line);
				Restaurant restaurant = restaurantData.get(review.getBusinessID());
				User user = userData.get(review.getUserID());
				if (restaurant != null && user!= null) {
					restaurant.addReview(review);
					user.addReview(review);
				}
			} reviewReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		ReadData.restaurantData = restaurantData;
		ReadData.userData = userData;
	}
	
	private User UserRead(String line) {
		JSONObject currObj = new JSONObject(line);
		return new User((String) currObj.get("user_id"));
	}
	
	// Copied from David's restRead.java
	/**
	 * Read a restaurant JSON String into a Restaurant object
	 * @param line The input String
	 * @return The Restaurant object
	 */
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
	
	/**
	 * Read a review from a JSON String into a review object.
	 * @param line The JSON String representing the Review
	 * @return The Review object
	 */
	private static Review ReviewRead(String line) {
		JSONObject currObj = new JSONObject(line);
		return new Review(
						(String) currObj.get("user_id"),
						(String) currObj.get("business_id"),
						(double) currObj.get("stars")
		);
	}
	
	public static void main(String[] args) {
		ReadData data = new ReadData();
//		for (Review r : data.restaurantData.get("Gyrez6K8f1AyR7dzW9fvAw").getReviews())
//			for (Review p: data.userData.get(r.getUserID()).getReviews())
//				if (p != r) System.out.println(p);
	}
	
}
