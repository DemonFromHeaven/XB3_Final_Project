package backend;

import java.util.ArrayList;

/**
 * I started writing this before I realized everything that I needed was in Rate.java
 * @author David Thompson
 *
 */
@Deprecated
public class Suggest {
	
	// The radius, in KM, of the restaurants to put in the filter
	private static double RADIUS = 30;
	
	public ReadData data;
	
	/**
	 * Make a new suggestion item given the data object.
	 * @param data The data object
	 */
	public Suggest(ReadData data) {
		this.data = data;
	}
	
	public ArrayList<Restaurant> getSuggestions(Experience exp, Location loc) {
		
		ArrayList<Restaurant> suggestions = new ArrayList<>(); 
		for (String s: exp.getExperience()) {
			suggestions.addAll(getSuggestions(s));
		}
		filterLoc(suggestions, loc);
		return suggestions;
		
	}
	
	private ArrayList<Restaurant> getSuggestions(String restID) {
		ArrayList<Restaurant> suggestions = new ArrayList<>();
		
		for (Review r : data.getRestaurant(restID).getReviews())
			for (Review p: data.getUser(r.getUserID()).getReviews())
				if (p != r)
					System.out.println(p);
		
		return suggestions;
	}
	
	private void filterLoc(ArrayList<Restaurant> contenders, Location loc) {
		int i = 0;
		while(i < contenders.size()) {
			// If this restaurant is out of radius, remove it from the list
			if (contenders.get(i).getLocation().distanceTo(loc) > RADIUS) {
				contenders.remove(i);
				// Move on to checking the next value in the list, which
				// is at the same index as this was
				continue;
			}
			// If this is okay, advance to the next element
			i++;
		}
	}
	
}
