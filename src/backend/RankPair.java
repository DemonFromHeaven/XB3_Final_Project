package backend;

public class RankPair implements Comparable<RankPair> {
	
	public final double rating; 
	public final Restaurant restaurant; 
	
	public RankPair(double rating, Restaurant restaurant) { 
	    this.rating = rating; 
	    this.restaurant = restaurant; 
	}
	
	public String toString() {
		return "Weighted Rating: "
				+ rating
				+ "Restaurant id: "
				+ restaurant.getID()
				+ "\n"
				+ restaurant
				+ "\n";
	}

	@Override
	public int compareTo(RankPair that) {
		if (this.rating < that.rating)
			return 1;
		else if (this.rating > that.rating)
			return -1;
		return 0;
	}
	
	@Override
	public boolean equals(Object that) {
		try {
			RankPair other = (RankPair) that;
			return this.restaurant.equals(other.restaurant);
		} catch (ClassCastException e) {
			return false;
		}
	}
	
}
