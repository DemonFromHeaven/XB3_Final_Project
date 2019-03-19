package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

/**
 * Removing data that is not needed
 * @author Matthew Williams
 */
public class ReviewFiltering {
	private static final String REVIEW_FILEPATH = "data/reviewFiltered.json";
	private static final String REVIEW_FILEPATH_UNFILTERED = "data/review.json";
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(REVIEW_FILEPATH_UNFILTERED));
		BufferedWriter bw = new BufferedWriter(new FileWriter(REVIEW_FILEPATH));
		
		String line, newLine;
		
		while((line = br.readLine()) != null) {
			JSONObject currObj = new JSONObject(line);
			newLine = "{";
			newLine += ("\"user_id\":" + (String)currObj.get("user_id") + ",");
			newLine += ("\"business_id\":" + (String)currObj.get("business_id") + ",");
			newLine += ("\"stars\":" + currObj.get("stars"));
			newLine += ("}\n");
			bw.write(newLine);
		}
		
		bw.close();
		br.close();
	}
}
