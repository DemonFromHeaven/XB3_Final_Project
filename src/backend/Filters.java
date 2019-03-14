package backend;

public class Filters {
	
	public RedBlackBST<String, Restaurant> lexico(LinkedList<Restaurant> rests) {
		
		RedBlackBST<String, Restaurant> rbrest = new RedBlackBST <String, Restaurant>();
		for (Restaurant r : rests) {
			//TODO: Pending implementation
			rbrest.put(r.getID(), r);
		}
		return rbrest;
	}
	
}
