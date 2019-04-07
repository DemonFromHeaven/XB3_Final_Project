package backend;
/**
 * Helps keep restaurants ordered by their rating from the rating algorithms
 * @author Matthew Williams
 * 
 */
public class RankPair implements Comparable<RankPair> {
	
	public final double rating; 
	public final Restaurant restaurant; 
	
	/**
	 * Constructor
	 * @param rating The rating for the restaurant
	 * @param restaurant The Restaurant
	 */
	public RankPair(double rating, Restaurant restaurant) { 
	    this.rating = rating; 
	    this.restaurant = restaurant; 
	}
	
	/**
	 * A string representation of the rank pair
	 * @return The string representation
	 */
	@Override
	public String toString() {
		return "Weighted Rating: "
				+ rating
				+ "Restaurant id: "
				+ restaurant.getID()
				+ "\n"
				+ restaurant
				+ "\n";
	}
	
	/**
	 * Compare two rank pairs
	 * @return an integer that shows if one rating is greater, lesser or equal
	 */
	@Override
	public int compareTo(RankPair that) {
		if (this.rating < that.rating)
			return 1;
		else if (this.rating > that.rating)
			return -1;
		return 0;
	}
	
	/**
	 * Check if two rank pairs are equal
	 * @return true if equal
	 */
	@Override
	public boolean equals(Object that) {
		try {
			RankPair pair = (RankPair) that;
			return this.restaurant.equals(pair.restaurant);
		} catch (ClassCastException e) {
			return false;
		}
	}
	
}
