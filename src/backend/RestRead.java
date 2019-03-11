package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class RestRead {
	
	private static final String BUSINESS_FILEPATH = "data/businessFiltered.json";
	private static final String BUSINESS_FILEPATH_UNFILTERED = "data/business.json";
	
	// This one hasn't been updated to the new interface of the Restaurant class,
	// so I left it commented out.
//	public static RedBlackBST<String, Restaurant> initRestaurants() {
//		
//		Filter.filterRest();
//		
//		JSONParser parser = new JSONParser();
//		
//		RedBlackBST<String, Restaurant> rbRest = new RedBlackBST<String, Restaurant>();
//		
//		try {
//			JSONArray jarray = (JSONArray) parser.parse(new FileReader(BUSINESS_FILEPATH));
//			
//			for (Object o : jarray) {
//				JSONObject restaurant = (JSONObject) o;
//				
//				String id = (String) restaurant.get("business_id");
//				
//				//TODO: Fill in placeholders when Restaurant Type is complete.
//				Restaurant r = new Restaurant("placeholder name",
//						id,
//						new Location("placeholder address",
//								"placeholder city",
//								"placeholder province",0,0),
//						0.0,
//						null);
//				
//				rbRest.put(id, r);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		Filter.filterData(rbRest);
//		return rbRest;
//	}
	
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
					restaurantData.put(id, new Restaurant(name, id, loc, stars, price, reviewCount));
				} catch (JSONException jsone) {
					// Then this isn't a restaurant, don't add it to the RBBST
				}
				
			}
			
			br.close();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(restaurantData.size());
		
		return restaurantData;
		
	}
	
	
}

