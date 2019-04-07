package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

// TODO: merge into or include in RestRead, fix the call to RestRead here

/**
 * Includes only restaurants and removes unneccessary data
 * @author Matthew Williams
 */
@Deprecated
public class ReviewFiltering {
	private static final String REVIEW_FILEPATH_FILTERED = "data/reviewFiltered.json";
	private static final String REVIEW_FILEPATH = "data/review.json";
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(REVIEW_FILEPATH));
		BufferedWriter bw = new BufferedWriter(new FileWriter(REVIEW_FILEPATH_FILTERED));
		RedBlackBST<String, Restaurant> rb = RestRead.readRestaurants(Filepaths.BUSINESS_FILEPATH);
		
		String line, newLine, restaurantID;
		
		while((line = br.readLine()) != null) {
			JSONObject currObj = new JSONObject(line);
			restaurantID = (String)currObj.get("business_id");
			if (!rb.contains(restaurantID)) continue;
			newLine = "{";
			newLine += ("\"user_id\":" + (String)currObj.get("user_id") + ",");
			newLine += ("\"business_id\":" + restaurantID + ",");
			newLine += ("\"stars\":" + currObj.get("stars"));
			newLine += ("}\n");
			bw.write(newLine);
		}
		
		bw.close();
		br.close();
	}
}
