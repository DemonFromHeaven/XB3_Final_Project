package test;

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
 * Test to make sure that the data from the JSON file is correct
 * @author David Thompson
 */
public class RestReadTest {

	private static final String BUSINESS_FILEPATH_UNFILTERED = "data/business.json";
	private static RedBlackBST<String, Restaurant> restRBBST;
	
	// Arizona Biltmore Golf Club at 2818 E Camino Acequia Drive, Phoenix, AZ
	private static final String ARIZONA_GOLF = "1SWheh84yJXfytovILXOAQ";
	
	// Hilton Toronto at 145 Richmond Street W, Toronto, ON
	private static final String HILTON_RICHMOND_TO = "i6hWP3si97eKQl_JyK8L3w";
	
	/**
	 * Read the data into the RedBlackBST, and time how long it takes to read
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		Stopwatch sw = new Stopwatch();
		sw.tick();
		restRBBST = RestRead.readRestaurants(BUSINESS_FILEPATH_UNFILTERED);
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
	 * Arizona Biltmore Golf Club. It shouldn't because it is not a restaurant
	 */
	@Test
	public void idTestNormal1() {
		assert !restRBBST.contains(ARIZONA_GOLF);
	}
	
	/**
	 * Check if the BST contains the Hilton on Richmond street in Toronto
	 */
	@Test
	public void idTestNormal2() {
		assert restRBBST.contains(HILTON_RICHMOND_TO);
	}
	
}
