package backend;

/**
 * The point of this class is to represent an address.
 * This class should interface with the Google API to
 * determine how far away a particular address is and
 * the Google Maps URL for how to get to a particular
 * restaurant.
 * @author David Thompson
 *
 */
public class Location {
	
	// Holds the read value of the address
	private String addr;
	private String city;
	private String province;
	private String country;
	
	/**
	 * Make a new address object
	 * @param addr
	 */
	public Location(String addr, String city, String province, String country) {
		this.addr = addr;
		this.city = city;
		this.province = province;
		this.country = country;
	}
	
	/**
	 * Get the address of this Location
	 * @return The address of the location as a human legible string
	 */
	public String getAddress() {
		return addr + ", " + city + ", " + province + ", " + country;
	}
	
	/**
	 * Generates the Google Maps URL that will give directions
	 * to the Location.
	 * @return
	 */
	public String getDirectionsURL() {
		//throw new UnsupportedOperationException("Not yet capable to interface with Google Maps");
		String cleaned = getAddress().replaceAll(",", "%2C");
		cleaned = cleaned.replaceAll(" ", "+");
		return "https://www.google.com/maps/dir/?api=1&destination=" + cleaned;
	}
	
	/**
	 * Returns the distance to another location in kilometers.
	 * Uses the Google Maps API to accomplish this.
	 * @param that The other location
	 * @return The distance between the locations in kilometers
	 */
	public int distanceTo(Location that) {
		throw new UnsupportedOperationException("Not yet capable to interface with Google Maps");
	}
	
	/**
	 * For unit testing the Location class
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {
		Location there = new Location("The Woolwich in Arms Pub", "Guelph", "Ontario", "Canada");
		System.out.println(there.getDirectionsURL());
		
	}
	
}
