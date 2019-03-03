package backend;

public class Restaurant {

	private String name;  // The name of the Restaurant
	private String id;  // The unique identifier for the Restaurant from the file
	
	private Address addr;  // The address of the Restaurant
	
	public Restaurant(String name, String id, Address addr) {
		this.name = name;
		this.id = id;
		this.addr = addr;
	}
	
	
	
}
