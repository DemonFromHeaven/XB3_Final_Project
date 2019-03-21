package backend;

public class RankPair {
	public final double rating; 
	public final Restaurant restaurant; 
	public RankPair(double rating, Restaurant restaurant) { 
	    this.rating = rating; 
	    this.restaurant = restaurant; 
	}
	public String toString() {
		return "Weighted Rating: " + rating + "Restaurant id: " + restaurant.getID() + "\n";
	}
}
