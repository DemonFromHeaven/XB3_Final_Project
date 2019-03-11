package backend.tests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.Location;
import backend.Restaurant;

/**
 * For testing <code>Restaurant.java</code>
 * Notes to self: 
 *  * Don't retest Location in this class, you are already testing it
 * @author David Thompson
 */
public class RestaurantTest {

	Restaurant r1;
	
	@Before
	public void setUp() {
		r1 = new Restaurant("The Wooly", "y7y7y7yu8u8u8u",
				new Location(null, null, null, 0, 0), 4.0, 2, 69);
	}
	
	@After
	public void tearDown() {
		r1 = null;
	}
	
	/**
	 * Make sure name getter works
	 */
	@Test
	public void testGetName() {
		assertEquals(r1.getName(), "The Wooly");
	}
	
	@Test
	public void testGetLocation() {
		assertEquals(r1.getLocation(), new Location(null, null, null, 0, 0));
	}

}
