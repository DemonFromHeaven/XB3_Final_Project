
package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import algorithms.LinearProbingHashST;
import algorithms.TST;
import data.Data;

/**
 * A version of ReadData specifically for the server
 * 
 * @author David Thompson
 *
 */
public class ReadDataServer {

	// Stores the Restaurants by ID
	private LinearProbingHashST<String, Restaurant> restaurantData;

	// Stores the user's data
	private LinearProbingHashST<String, User> userData;

	// Stores the Restaurant IDs by name
	private TST<String> restaurantName;

	/**
	 * Read the restaurants and user data from file into Linear Probing Hash Tables
	 * 
	 * @return The Linear Probing Hash Table of the Restaurants
	 */
	public ReadDataServer() {
		restaurantData = new LinearProbingHashST<String, Restaurant>(400000);
		userData = new LinearProbingHashST<String, User>(400000);
		restaurantName = new TST<>();
		BufferedReader businessReader;
		BufferedReader reviewReader;
		BufferedReader userReader;
		String line;

		try {

			// Check if the filtered restaurant file is created. If not, create it
			try {
				Data.class.getResource(Filepaths.BUSINESS_FILEPATH_FILTERED_SERVER).getFile();
			} catch (NullPointerException e) {
				System.out.println("Creating the filtered business file...");
				FileFilter.filterRestServer();
				System.out.println("Done!");
			}

			// Read businesses to hash table
			businessReader = new BufferedReader(
				new FileReader(Data.class.getResource(Filepaths.BUSINESS_FILEPATH_FILTERED_SERVER).getFile()));
			while ((line = businessReader.readLine()) != null) {
				Restaurant restaurant = Restaurant.fromJSON(line);
				if (restaurant != null) {
					restaurantData.put(restaurant.getID(), restaurant);
					restaurantName.put(restaurant.getName(), restaurant.getID());
				}
			}
			businessReader.close();

			// Read users to hash table
			userReader = new BufferedReader(new FileReader(Data.class.getResource(Filepaths.USER_FILEPATH_SERVER).getFile()));
			while ((line = userReader.readLine()) != null) {
				User user = User.fromJSON(line);
				if (user != null)
					userData.put(user.getID(), user);
			}
			userReader.close();

			// Read Reviews into linked list for each restaurant
			reviewReader = new BufferedReader(
					new FileReader(Data.class.getResource(Filepaths.REVIEW_FILEPATH_SERVER).getFile()));
			while ((line = reviewReader.readLine()) != null) {
				Review review = Review.fromJSON(line);
				Restaurant restaurant = restaurantData.get(review.getBusinessID());
				User user = userData.get(review.getUserID());
				if (restaurant != null && user != null) {
					restaurant.addReview(review);
					user.addReview(review);
				}
			}
			reviewReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the Restaurant object using the restaurant ID
	 * 
	 * @param id The ID of the restaurant
	 * @return The Restaurant data associated with the ID
	 */
	public Restaurant getRestaurant(String id) {
		return restaurantData.get(id);
	}

	/**
	 * Get the User object using the user ID
	 * 
	 * @param id The ID of the user
	 * @return The User data associated with the ID
	 */
	public User getUser(String id) {
		return userData.get(id);
	}

	/**
	 * Search for restaurants by name
	 * 
	 * @param name       The name search query
	 * @param maxResults The maximum number of results to return. A non-positive
	 *                   value yields all items.
	 * @return An ArrayList of potential matching Restaurants
	 */
	public ArrayList<Restaurant> searchByName(String name, int maxResults) {

		ArrayList<Restaurant> results = new ArrayList<>();
		int numAdded = 0;

		// For all the restaurant names, add the corresponding
		for (String matchingName : restaurantName.keysWithPrefix(name)) {
			results.add(restaurantData.get(restaurantName.get(matchingName)));
			numAdded++;
			if (numAdded == maxResults) {
				break;
			}
		}

		return results;

	}

}
