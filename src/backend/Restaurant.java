package backend;

import java.util.Map;

public class Restaurant implements Comparable<Restaurant> {

	private String name;  // The name of the Restaurant
	private String id;  // The unique identifier for the Restaurant from the file
	private Location loc;  // The address of the Restaurant
	private Map<String, String> attributes;
	
	public Restaurant(String name, String id, Location loc) {
		this.name = name;
		this.id = id;
		this.loc = loc;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAttribute(String attribute) {
		return attributes.get(attribute);
	}
	
	public int compareTo(Restaurant that) {
		return this.id.compareTo(that.id);
	}
	
}