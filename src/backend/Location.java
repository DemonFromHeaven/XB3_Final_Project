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
	
	// Mean Volumetric Radius, taken from https://nssdc.gsfc.nasa.gov/planetary/factsheet/earthfact.html
	private static final double EARTH_RADIUS = 6371.000;
	
	// Holds the read value of the address
	private String addr;
	private String city;
	private String state;
	
	private double lattitude;
	private double longitude;
	
	/**
	 * Make a new address object
	 * @param addr
	 */
	public Location(String addr, String city, String state, double lattitude, double longitude) {
		this.addr = addr;
		this.city = city;
		this.state = state;
		this.lattitude = lattitude;
		this.longitude = longitude;
	}
	
	/**
	 * Generates the Google Maps URL that will give directions
	 * to the Location.
	 * @return A String that represents the URL that when opened
	 * gives directions to the location.
	 */
	public String getDirectionsURL() {
		
		// If one of the words fields is missing, generate the URL based off
		// lattitude/longitude
		if (addr == "" || city == "" || state == "" ) {
			String dest = lattitude + "%2C" + longitude;
			return "https://www.google.com/maps/dir/?api=1&destination=" + dest;
		}
		
		// Otherwise, generate based on Location string
		String cleaned = toString().replaceAll(",", "%2C");
		cleaned = cleaned.replaceAll(" ", "+");
		return "https://www.google.com/maps/dir/?api=1&destination=" + cleaned;
	}
	
	/**
	 * Returns the distance to another location in kilometers.
	 * Uses the latitude and longitude in order to do this.
	 * The formula for calculating the distance was found on these websites:
	 * https://www.movable-type.co.uk/scripts/latlong.html
	 * https://stackoverflow.com/questions/1502590/calculate-distance-between-two-points-in-google-maps-v3
	 * </detail>
	 * @param that The other location
	 * @return The distance between the locations in kilometers
	 */
	public double distanceTo(Location that) {
		
		double deltaLatHalf = (Math.toRadians(that.lattitude) - Math.toRadians(this.lattitude))/2;
		double deltaLongHalf = (Math.toRadians(that.longitude) - Math.toRadians(this.longitude))/2;
		
		double latSinePart = Math.sin(deltaLatHalf);
		latSinePart *= latSinePart;
		
		double longSinePart = Math.sin(deltaLongHalf);
		longSinePart *= longSinePart;
		
		double a = latSinePart
				+ Math.cos(Math.toRadians(this.lattitude))
				* Math.cos(Math.toRadians(that.lattitude))
				* longSinePart;
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		
		return c * EARTH_RADIUS;
		
	}
	
	/**
	 * Latitude getter
	 * @return The latitude
	 */
	public double getLatitude() {
		return lattitude;
	}
	
	/**
	 * Longitude getter
	 * @return The latitude
	 */
	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * Checks if this location is the same as another.
	 * This value is based solely off lattitude and longitude.
	 * @param l The other location
	 * @return True if the locations are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object that) {
		try {
			Location l = (Location)that;
			boolean latSame = (this.lattitude - l.lattitude) < 0.0001;
			boolean longSame = (this.longitude - l.longitude) < 0.0001;
			return latSame && longSame;
		} catch(ClassCastException cce) {
			return false;
		}
	}
	
	/**
	 * Get the human readable address of this Location.
	 * This function omits parts of the address that weren't provided.
	 * @return The address of the location as a human readable String
	 */
	public String toString() {
		
		// The string that holds the address representation
		String rep = "";
		
		if (addr != "") {
			
			rep += addr;
			if (city != "") {
				rep += ", " + city;
			}
			
			if (state != "") {
				rep += ", " + state;
			}
			
		} else {
			
			if (city !="") {
				rep += city;
				if (state != "") {
					rep += ", " + state;
				}
			} else if (state != "") {
				rep += state;
			}
			
		}
		return rep;
	}
	
}
