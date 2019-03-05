package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class RestRead {
	
	private static final String BUSINESS_FILEPATH = "data/businessFiltered.json";
	private static final String BUSINESS_FILEPATH_UNFILTERED = "data/business.json";
	
	public static RedBlackBST<String, Restaurant> initRestaurants() {
		
		Filter.filterRest();
		
		JSONParser parser = new JSONParser();
		
		RedBlackBST<String, Restaurant> rbRest = new RedBlackBST<String, Restaurant>();
		
		try {
			JSONArray jarray = (JSONArray) parser.parse(new FileReader(BUSINESS_FILEPATH));
			
			for (Object o : jarray) {
				JSONObject restaurant = (JSONObject) o;
				
				String id = (String) restaurant.get("business_id");
				
				//TODO: Fill in placeholders when Restaurant Type is complete.
				Restaurant r = new Restaurant("placeholder name", id, new Location("placeholder address",
																					"placeholder city",
																					"placeholder province"));
				
				rbRest.put(id, r);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Filter.filterData(rbRest);
		return rbRest;
	}
	
	/**
	 * Read the restaurants from the data into a RedBlackBST of Restaurants
	 * @return The RedBlackBST of the Restaurants
	 */
	public static RedBlackBST<String, Restaurant> readRestaurants() {
		
		RedBlackBST<String, Restaurant> restaurantData = new RedBlackBST<String, Restaurant>();
		
		try {
			
			// Make a BufferedReader to read the file
			BufferedReader br = new BufferedReader(new FileReader(BUSINESS_FILEPATH_UNFILTERED));
			String line;
			
			// While the current line isn't null
			while((line = br.readLine()) != null) {
				
				// Make a JSON object from the current line
				JSONObject currObj = new JSONObject(line);
				
				// Extract the desired parameters from the object
				String id = (String)currObj.get("business_id");
				String name = (String)currObj.get("name");
				Location loc = new Location(
						(String)currObj.get("address"),
						(String)currObj.get("city"),
						(String)currObj.get("state"));
				
				restaurantData.put(id, new Restaurant(name, id, loc));
				
			}
			
			br.close();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return restaurantData;
		
	}
	
	public static void main(String args[]) {
		initRestaurants();
//		RedBlackBST<String, Restaurant> rbbst = readRestaurants();
	}
}

