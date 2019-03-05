package backend;

public class Stopwatch {

	private long begin, end;
	
	/**
	 * Make a Stopwatch
	 */
	public Stopwatch() {
		begin = -1;
		end = -1;
	}
	
	/**
	 * Start the Stopwatch
	 */
	public void tick() {
		begin = System.currentTimeMillis();
		end = begin - 1;
	}
	
	/**
	 * Stop the Stopwatch
	 */
	public void tock() {
		end = System.currentTimeMillis();		
	}
	
	/**
	 * Returns the recorded time, or zero if the clock hasn't been started,
	 * or the currently elapsed time if the timer hasn't been stopped yet
	 * @return The time elapsed in mills
	 */
	public long elapsedMillis() {
		if (begin == -1) {
			return 0;
		} if (end == -1) {
			return System.currentTimeMillis()  - begin;
		} else {
			return end - begin;
		}
	}
	
}
