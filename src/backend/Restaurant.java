package backend;

public class Restaurant implements Comparable<Restaurant> {

	private String name;  // The name of the Restaurant

	private String id;  // The unique identifier for the Restaurant from the file
	private Location loc;  // The address of the Restaurant
	private double stars;
	private int price;
	private int reviewCount;
	private LinkedList<Review> reviews;
	
	public Restaurant(String name,
			String id,
			Location loc,
			double stars,
			int price,
			int reviewCount) {
		
		this.name = name;
		this.id = id;
		this.loc = loc;
		this.stars = stars;
		this.price = price;
		this.reviewCount = reviewCount;
		this.reviews = new LinkedList<Review>();
		
	}
	
	public Restaurant(String id) {
		this.id = id;
	}

	public String getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public double getStars() {
		return stars;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getReviewCount() {
		return this.reviewCount;
	}
	
	public Location getLocation() {
		return this.loc;
	}
	
	public int compareTo(Restaurant that) {
		return this.id.compareTo(that.id);
	}
	
	public String toString() {
		return name + " at " + loc;
	}

	public void addReview(Review review) {
		reviews.insert(review);
	}
	
	public LinkedList<Review> getReviews() {
		return reviews;
	}

}