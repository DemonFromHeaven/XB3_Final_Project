package backend;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RestRead {
	
	public static RedBlackBST<String, Restaurant> initRestaurants() {
		
		Filter.filterRest();
		
		JSONParser parser = new JSONParser();
		
		RedBlackBST<String, Restaurant> rbRest = new RedBlackBST<String, Restaurant>();
		
		try {
			JSONArray jarray = (JSONArray) parser.parse(new FileReader("data/businessFiltered.json"));
			
			for (Object o : jarray) {
				JSONObject restaurant = (JSONObject) o;
				
				String id = (String) restaurant.get("business_id");
				
				//TODO: Fill in placeholders when Restaurant Type is complete.
				Restaurant r = new Restaurant("placeholder name", id, new Location("placeholder address",
																					"placeholder city",
																					"placeholder province",
																					"placeholder country"));
				
				rbRest.put(id, r);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Filter.filterData(rbRest);
		return rbRest;
	}
	
	public static void main(String args[]) {
		initRestaurants();
	}
}

