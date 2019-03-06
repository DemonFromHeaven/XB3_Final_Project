package backend;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LocationTest {

	private Location l1, l2, l3, l4, l5;
	
	@Before
	public void setUp() {
		// The Wooly Pub in Guelph
		l1 = new Location("176 Woolwich St", "Guelph", "ON", 43.548245, -80.252036);
		// Basilique in Westdale
		l2 = new Location("1065 King St W", "Hamilton", "ON", 43.261169, -79.906957);
		// Webers burgers in Orillia
		l3 = new Location("8825 ON-11", "Orillia", "ON", 44.694138, -79.398080);
		// Edge case
		l4 = new Location("", "", "", 0, 0);
	}

	@After
	public void tearDown() {
		l1 = null;
		l2 = null;
		l3 = null;
		l4 = null;
	}

	/**
	 * Make sure that the distance from a location to that same location
	 * is around 0.
	 */
	@Test
	public void edgeCase1Distance() {
		// Epsilon is set to 1 meter
		assertEquals(l4.distanceTo(l4), 0, 0.001);
	}
	
	/**
	 * Distance from the Wooly to Basilique
	 * Epsilon is set to 10 meters, the precision
	 * of the online calculator I am using
	 */
	@Test
	public void normalCase1Distance() {
		// 
		assertEquals(Math.abs(l1.distanceTo(l2)), 42.38, 0.01);
	}
	
	/**
	 * Distance from the Wooly to Webers
	 * Epsilon is set to 100 meters, the precision
	 * given by the online claculator I am using
	 */
	@Test
	public void normalCase2Distance() {
		assertEquals(Math.abs(l1.distanceTo(l3)), 144.5, 0.1);
	}
	
	/**
	 * Distance from Basilique to Webers
	 * Epsilon is set to 100 meters, the precision
	 * given by the online calculator I am using
	 */
	@Test
	public void normalCase3Distance() {
		assertEquals(Math.abs(l2.distanceTo(l3)), 164.5, 0.1);
	}
	
	@Test
	public void edgeCase1ToString() {
		assertEquals(l4.toString(), "");
	}
	
	@Test
	public void normalCase1ToString() {
		assertEquals(l1.toString(), "176 Woolwich St, Guelph, ON");
	}
	
	@Test
	public void normalCase2ToString() {
		assertEquals(l2.toString(), "1065 King St W, Hamilton, ON");
	}

}
