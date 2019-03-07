package backend.tests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import backend.RedBlackBST;
import backend.RestRead;
import backend.Restaurant;
import backend.Stopwatch;

/**
 * Potential test cases to try:
 *  * Numerical fields in attributes (int and double)
 * @author david
 *
 */
public class RestReadTest {

	static RedBlackBST<String, Restaurant> restRBBST;
	
	// Arizona Biltmore Golf Club at 2818 E Camino Acequia Drive, Phoenix, AZ
	static final String ARIZONA_GOLF = "1SWheh84yJXfytovILXOAQ";
	
	// Hilton Toronto at 145 Richmond Street W, Toronto, ON
	static final String HILTON_RICHMOND_TO = "i6hWP3si97eKQl_JyK8L3w";
	
	/**
	 * Read the data into the RedBlackBST, and time how long it takes to read
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		Stopwatch sw = new Stopwatch();
		sw.tick();
		restRBBST = RestRead.readRestaurants();
		sw.tock();
		System.out.println("Building the RBBST took "
				+ sw.elapsedMillis()
				+ "ms");
	}
	
	/**
	 * Let the RBBST fall into the GC so that resources are freed
	 */
	@AfterClass
	public static void tearDownAfterClass() {
		restRBBST = null;
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Check if the BST contains the first business in the business.json file,
	 * Arizona Biltmore Golf Club
	 */
	@Test
	public void idTestNormal1() {
		assert restRBBST.contains(ARIZONA_GOLF);
	}
	
	/**
	 * Check if the BST contains the Hilton on Richmond street in Toronto
	 */
	@Test
	public void idTestNormal2() {
		assert restRBBST.contains(HILTON_RICHMOND_TO);
	}
	
	/**
	 * Verify that the Arizona Biltmore Golf Club Object has the attribute field
	 * 'GoodForKids'
	 */
	@Test
	public void hasAttributeNormal1() {
		assert restRBBST.get(ARIZONA_GOLF).hasAttribute("GoodForKids");
	}
	
	/**
	 * Verify that the correct value was read in for the attribute
	 */
	@Test
	public void correctAttributeNormal1() {
		assertEquals(restRBBST.get(ARIZONA_GOLF).getAttribute("GoodForKids"), "False");		
	}
	
	/**
	 * That's the name of the game. We are playing the name game. Name something
	 * something fame.
	 */
	@Test
	public void name() {
		System.out.println(restRBBST.get(ARIZONA_GOLF).toString());
		System.out.println(restRBBST.get(HILTON_RICHMOND_TO).toString());
	}

	/**
	 * Benchmarking the time it takes to search the RBBST
	 */
	public void searchSpeedTest() {
		// TODO: Perform a test and output the search time
	}
	
}
