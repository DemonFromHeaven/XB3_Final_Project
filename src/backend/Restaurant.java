package backend;

import org.json.JSONObject;

public class Restaurant implements Comparable<Restaurant> {

	private String name;  // The name of the Restaurant
	private String id;  // The unique identifier for the Restaurant from the file
	private Location loc;  // The address of the Restaurant
	private double stars;
	private JSONObject attributes; // A map object that maps an attribute name
											// to an attribute file
	
	// TODO: accept the attribute mapping and store it, finish API
	public Restaurant(String name, String id, Location loc, double stars, JSONObject attributes) {
		this.name = name;
		this.id = id;
		this.loc = loc;
		this.stars = stars;
		this.attributes = attributes;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAttribute(String attribute) {
		return attributes.getString(attribute);
	}
	
	public boolean hasAttribute(String attribute) {
		return attributes.has(attribute);
	}
	
	public int compareTo(Restaurant that) {
		return this.id.compareTo(that.id);
	}
	
	public String toString() {
		return name + " at " + loc;
	}

	public double getStars() {
		return stars;
	}
	
}