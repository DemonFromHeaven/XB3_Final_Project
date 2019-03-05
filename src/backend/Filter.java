package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class Filter {

	public static void filterRest() {
		File businessIn = new File("data/business.json");
		File businessOut = new File("data/businessFiltered.json");
		
		/**
		 * Filter businesses to just restaurants
		 */
		try {
			BufferedReader bf = new BufferedReader(new FileReader(businessIn));
			FileWriter fw = new FileWriter(businessOut);
			
			String line;
			while((line = bf.readLine()) != null) { if (line.contains("RestaurantsPrice")) fw.write(line + "\n"); }
			
			bf.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void filterData(RedBlackBST <String, Restaurant> rbRest) {
		
		JSONParser parser = new JSONParser();
		
		try {
			JSONArray jarrayCheckin = (JSONArray) parser.parse(new FileReader("data/checkin.json"));
			JSONArray jarrayTip = (JSONArray) parser.parse(new FileReader("data/tip.json"));
			JSONArray jarrayReview = (JSONArray) parser.parse(new FileReader("data/review.json"));
			
			FileWriter fwcheckin = new FileWriter(new File("data/checkinFiltered.json"));
			FileWriter fwtip = new FileWriter(new File("data/tipFiltered.json"));
			FileWriter fwreview = new FileWriter(new File("data/reviewFiltered.json"));
			
			for (Object o : jarrayCheckin) {
				JSONObject checkin = (JSONObject) o;
				
				String id = (String) checkin.get("business_id");
				if (rbRest.contains(id)) fwcheckin.write(o.toString());
			}
			
			for (Object o : jarrayTip) {
				JSONObject tip = (JSONObject) o;
				
				String id = (String) tip.get("business_id");
				if (rbRest.contains(id)) fwtip.write(o.toString());
			}
			
			for (Object o : jarrayReview) {
				JSONObject review = (JSONObject) o;
				
				String id = (String) review.get("business_id");
				if (rbRest.contains(id)) fwreview.write(o.toString());
			}
			
			fwcheckin.close();
			fwtip.close();
			fwreview.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
