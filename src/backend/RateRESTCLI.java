package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * CLI for RateRest
 * @author David Thompson
 *
 */
public class RateRESTCLI {

	public static String IP;
	
	public static void main(String[] args) throws Exception {

		Scanner in = new Scanner(System.in);
		ArrayList<Restaurant> userRestaurants = new ArrayList<Restaurant>();
		ArrayList<Double> userStars = new ArrayList<Double>();
		ArrayList<RankPair> recommendation = new ArrayList<RankPair>();

		// Read in the IP
		BufferedReader br = new BufferedReader(new FileReader(Filepaths.IP_FILEPATH));
		IP = br.readLine();
		IP = IP.trim();
		br.close();
		
		// Related to the user's position
		Location loc;

		// Get the user's location
		// Toronto is 43.657716 -79.383931
		for (;;) {
			System.out.println("Please input your latitude and longitude, in that order, space seperated:");
			try {
				double latitude = in.nextDouble();
				double longitude = in.nextDouble();
				loc = new Location("", "", "", latitude, longitude);
				break;
			} catch (Exception e) {
				System.out.println("That is not in the correct format.");
				in.nextLine();
			}
		}
		in.nextLine();

		// GET USER EXPERIENCE

		for (;;) {

			// Prompt for restaurant search
			System.out.println("Please enter the name restaurant you tried,\nor c to (c)ontinue to recommendations:");
			String name = in.nextLine();

			// Break if user flags they are done entering
			if (name.equals("c"))
				break;
			
			// Return to prompt if it is null
			if (name.equals(""))
				continue;

			// A bit of cleaning
			name = name.replaceAll(" ", "+");
			
			// TODO: cleanse input so that extra information isn't accidently added
			// GET the query results from the REST API
			URL searchURL = new URL("http", IP, 8080, "/recommend/search?query=" + name);
			HttpURLConnection con = (HttpURLConnection) searchURL.openConnection();
			con.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			ArrayList<Restaurant> results = new ArrayList<>();
			String line;
			while ((line=br.readLine()) != null) {
				results.add(Restaurant.fromJSON(line));
			}
			br.close();
			
			if (results.size() == 0) {
				System.out.println("No restaurants were found");
				in.nextLine();
				continue;
			}

			// Print out the list of results
			for (int i = 1; i < results.size() + 1; i++) {
				System.out.println("(" + i + ") " + results.get(i - 1));
			}

			// Get the index of the restaurant they went to
			int selection = -1;
			while (selection < 0 || selection > results.size()) {
				System.out.println("Please enter a number between 1 and " + results.size()
						+ " to indicate which restaurant you visited");
				System.out.println("Enter 0 if none of these match where you went");
				try {
					selection = in.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("That is not a valid number!");
				}
			}

			// None of the results matched, try searching again
			if (selection == 0) {
				in.nextLine();
				continue;
			}

			// Otherwise they have input their selection; add it
			userRestaurants.add(results.get(selection - 1));

			in.nextLine();
			System.out.println("Enter your rating out of 5");
			userStars.add(Double.parseDouble(in.nextLine()));

		}
		in.close();

		// GET suggestions

		String suggestURLStr = "/recommend/suggest?";
		
		for (int i = 0; i < userRestaurants.size(); i++) {
			suggestURLStr += "visited=" + userRestaurants.get(i).getID()
					+ "&stars=" + userStars.get(i) + "&";
		}
		
		suggestURLStr += "lat=" + loc.getLatitude() + "&long=" + loc.getLongitude();
		
		URL searchURL = new URL("http", IP, 8080, suggestURLStr);
		HttpURLConnection con = (HttpURLConnection) searchURL.openConnection();
		con.setRequestMethod("GET");
		br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		ArrayList<Restaurant> suggestions = new ArrayList<>();
		String line;
		while ((line=br.readLine()) != null) {
			suggestions.add(Restaurant.fromJSON(line));
		}
		br.close();

		for (Restaurant r: suggestions) {
			System.out.println(r);
		}

	}
}