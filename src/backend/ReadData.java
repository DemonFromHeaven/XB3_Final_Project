package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class ReadData {
	
	// Stores the Restaurants by ID
	static LinearProbingHashST<String, Restaurant> restaurantData;
	
	// Stores the user's data
	static LinearProbingHashST<String, User> userData;
	
	// Stores the Restaurant IDs by name
	static RedBlackBST<String, String> restaurantName;
	
	/**
	 * Read the restaurants and user data from file into Linear
	 * Probing Hash Tables
	 * @return The Linear Probing Hash Table of the Restaurants
	 */
	public ReadData() {
		LinearProbingHashST<String, Restaurant> restaurantData = new LinearProbingHashST<String, Restaurant>(400000);
		LinearProbingHashST<String, User> userData = new LinearProbingHashST<String, User>(400000);
		RedBlackBST<String, String> restaurantName = new RedBlackBST<>();
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
				Restaurant restaurant = Restaurant.fromJSON(line);
				if (restaurant != null)
					restaurantData.put(restaurant.getID(), restaurant);
					restaurantName.put(restaurant.getName(), restaurant.getID());
			} businessReader.close();
			
			// Read users to hash table
			userReader = new BufferedReader(new FileReader(Filepaths.USER_FILEPATH));
			while ((line = userReader.readLine()) != null) {
				User user = User.fromJSON(line);
				if (user != null)
					userData.put(user.getID(), user);
			} userReader.close();
			
			//Read Reviews into linked list for each restaurant
			reviewReader = new BufferedReader(new FileReader(Filepaths.REVIEW_FILEPATH));
			while ((line = reviewReader.readLine()) != null) {
				Review review = Review.fromJSON(line);
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
		ReadData.restaurantName = restaurantName;
	}
	
	public static void main(String[] args) {
		ReadData data = new ReadData();
		for (Review r : data.restaurantData.get("Gyrez6K8f1AyR7dzW9fvAw").getReviews())
			for (Review p: data.userData.get(r.getUserID()).getReviews())
				if (p != r) System.out.println(p);
	}
	
}
