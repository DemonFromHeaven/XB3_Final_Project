package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import data.Data;

/**
 * Library of functions for creating versions of the data files without
 * non-restaurant data.
 * 
 * @author Rohan Thakral
 */
public class FileFilter {

	/**
	 * Generate the filtered restaurant data file. This filters the businesses to
	 * just restaurants.
	 */
	public static void filterRest() {
		String outFile = Filepaths.BUSINESS_FILEPATH_FILTERED;
		File businessIn = new File(Filepaths.BUSINESS_FILEPATH);
		File businessOut = new File(outFile);

		/**
		 * Filter businesses to just restaurants
		 */
		try {
			BufferedReader bf = new BufferedReader(new FileReader(businessIn));
			FileWriter fw = new FileWriter(businessOut);

			String line;
			while ((line = bf.readLine()) != null) {
				if (line.contains("RestaurantsPrice"))
					fw.write(line + "\n");
			}

			bf.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generate the filtered restaurant data file. This filters the businesses to
	 * just restaurants.
	 */
	public static void filterRestServer() {
		File businessIn = new File(Data.class.getResource(Filepaths.BUSINESS_FILEPATH_SERVER).getFile());

		String outFile = businessIn.getAbsolutePath().substring(0, businessIn.getAbsolutePath().lastIndexOf("/") + 1)
				+ Filepaths.BUSINESS_FILEPATH_FILTERED_SERVER;
		File businessOut = new File(outFile);

		/**
		 * Filter businesses to just restaurants
		 */
		try {
			BufferedReader bf = new BufferedReader(new FileReader(businessIn));
			FileWriter fw = new FileWriter(businessOut);

			String line;
			while ((line = bf.readLine()) != null) {
				if (line.contains("RestaurantsPrice"))
					fw.write(line + "\n");
			}

			bf.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
