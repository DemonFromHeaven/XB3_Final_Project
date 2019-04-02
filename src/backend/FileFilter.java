package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Library of functions for creating versions of the data files without
 * non-restaurant data.
 * @author Rohan Thakral
 */
public class FileFilter {

	private static ClassLoader cload = ClassLoader.getSystemClassLoader();
	
	/**
	 * Generate the filtered restaurant data file.
	 * This filters the businesses to just restaurants.
	 */
	public static void filterRest() {
		String outFile = cload.getResource(Filepaths.BUSINESS_FILEPATH_FILTERED).getFile();
		File businessIn = new File(cload.getResource(Filepaths.BUSINESS_FILEPATH).getFile());
		File businessOut = new File(outFile);
		
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
	
	// TODO: use cload here
	public static void filterData(RedBlackBST <String, Restaurant> rbRest) {
		
		JSONParser parser = new JSONParser();
		
		try {
			JSONArray jarrayCheckin = (JSONArray) parser.parse(new FileReader(Filepaths.CHECKIN_FILEPATH));
			JSONArray jarrayTip = (JSONArray) parser.parse(new FileReader(Filepaths.TIP_FILEPATH));
			JSONArray jarrayReview = (JSONArray) parser.parse(new FileReader(Filepaths.REVIEW_FILEPATH));
			
			FileWriter fwcheckin = new FileWriter(new File(Filepaths.CHECKIN_FILEPATH_FILTERED));
			FileWriter fwtip = new FileWriter(new File(Filepaths.TIP_FILEPATH_FILTERED));
			FileWriter fwreview = new FileWriter(new File(Filepaths.REVIEW_FILEPATH_FILTERED));
			
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
