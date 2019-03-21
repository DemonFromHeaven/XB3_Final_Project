package test;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({LocationTest.class, RestReadTest.class, RestaurantTest.class})
public class AllTest {
	
	@BeforeClass
	public static void setUp() {
		System.out.println("Running tests...");
	}
	
}
