package org.darrylmiles.example.ee7.jca.eis.rar.driver;

/**
 * epocwait - Fixed milliseconds time interval since the 'epoc'
 * maxwait - Fixed milliseconds time interval based on the current wait
 * Usage is like:
 *  ElapsedTimer elapsed = new ElapsedTimer();
 *  elapsed.start();
 *  while(elapsed.timeoutSinceEpoc(timeout)) {
 *    // do something
 *    elapsed.sleepSinceEpoc(timeout, timeout); // or waitSinceEpoc()
 *  }
 * @author Darryl
 * FIXME make a Generics/Template class with a preCheck() check() abstracts for testing done
 *
 */
public class ElapsedTimer {
	private long epoc;

	/**
	 * 
	 */
	public ElapsedTimer() {
		
	}

	public void start() {
		if(epoc == 0)
			reset();
		// FIXME else ???
	}
	public void reset() {
		epoc = System.currentTimeMillis();
	}

	/**
	 * This recomputes the interval distance between epoc and the current time.
	 * @return Maybe +ve or -ve or 0.  +ve value indicate not yes expired.
	 */
	public long sinceEpoc() {
		if(epoc == 0)
			throw new RuntimeException();
		long now = System.currentTimeMillis();
		long since = now - epoc;
		return since;
	}

	/**
	 * This recomputes if a timeout/expiry occurred based on the current time.
	 * @param epocwait The absolute limit of total time, should be 0 or positive value.
	 * @return true when we expired the epocwait already.
	 */
	public boolean timeoutSinceEpoc(long epocwait) {
		assert(epocwait >= 0);
		long since = sinceEpoc();
		if(epocwait >= since)
			return true;
		return false;
	}

	/**
	 * Used for computing the shortest interval remaining of the values between. 
	 * @param epocwait The absolute limit of total time, should be 0 or positive value.
	 * @param maxwait The current limit we would wait, should be 0 or positive.
	 * @return Return can be negative.  Negative indicates expired.
	 */
	public long leftSinceEpoc(long epocwait, long maxwait) {
		assert(epocwait >= 0);
		assert(maxwait >= 0);
		long since = sinceEpoc();
		long min = minLong(epocwait, maxwait);
		if(since <= 0)
			return min;			// FIXME return Min of
		long left = epocwait - since;		// left is total absolute left
		return minLong(left, maxwait);		// clap to maxwait
	}

	private static long minLong(long a, long b) {
		return a < b ? a : b;
	}

	/**
	 * Invokes {@link Thread#sleep(long)} for the smallest period of time that is either
	 * the expiry of epocwait or the maxwait for this time.  If expiry has already occurred
	 * it does not call {@link Thread#sleep(long)} and returns false.  maxwait is an interval
	 * between the current time.
	 * 
	 * @param epocwait The absolute limit of total time, should be 0 or positive value.
	 * @param maxwait The current limit we would wait, should be 0 or positive.
	 * @return true when we waited, implied false when we didn't due to expiry
	 * @throws InterruptedException
	 */
	public boolean sleepSinceEpoc(long epocwait, long maxwait) throws InterruptedException {
		assert(epocwait >= 0);
		assert(maxwait >= 0);
		long left = leftSinceEpoc(epocwait, maxwait);
		if(left <= 0)
			return false;
		Thread.sleep(left);
		return true;
	}

	/**
	 * Invokes {@link Object#wait(long)} for the smallest period of time that is either
	 * the expiry of epocwait or the maxwait for this time.  If expiry has already occurred
	 * it does not call {@link Object#wait(long)} and returns false.  maxwait is an interval
	 * between the current time.
	 * 
	 * @param epocwait The absolute limit of total time, should be 0 or positive value.
	 * @param maxwait The current limit we would wait, should be 0 or positive.
	 * @return true when we waited, implied false when we didn't due to expiry
	 * @throws InterruptedException
	 */
	public boolean waitSinceEpoc(Object lockedObject, long epocwait, long maxwait) throws InterruptedException {
		assert(lockedObject != null);
		assert(epocwait >= 0);
		assert(maxwait >= 0);
		long left = leftSinceEpoc(epocwait, maxwait);
		if(left <= 0)
			return false;
		lockedObject.wait(left);
		return true;
	}

	public boolean isStarted() {
		return (epoc != 0);
	}
}
