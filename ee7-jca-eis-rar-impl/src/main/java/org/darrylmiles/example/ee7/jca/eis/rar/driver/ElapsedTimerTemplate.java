package org.darrylmiles.example.ee7.jca.eis.rar.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the full monty class, you implement preCheck() and check() yourself.
 * @author Darryl
 *
 * @param <T>  The result type you choose to use.
 */
public abstract class ElapsedTimerTemplate<T> {

	private static final Logger log = LoggerFactory.getLogger(ElapsedTimerTemplate.class);		// FIXME remove this!

	/**
	 * This is executed with the lockObject held.
	 * @return A non-null return is a final result, returning null means keep waiting.
	 * @throws InterruptedException we allow this, since it can also happen for all use cases.
	 */
	public abstract T preCheck() throws InterruptedException;

	/**
	 * This is executed with the lockObject held.
	 * @return A non-null return is a final result, returning null means keep waiting.
	 * @throws InterruptedException we allow this, since it can also happen for all use cases.
	 */
	public abstract T loopCheck() throws InterruptedException;


	/**
	 * 
	 * @param lockObject
	 * @param maxWaitMillis null = forever, <0 = only pre-check (never wait()), 0
	 * @return
	 * @throws InterruptedException 
	 */
	public T run(/*@Nonnull*/ Object lockObject, /*@Nullable*/ Long maxWaitMillis) throws InterruptedException {
		T result = null;
		synchronized(lockObject) {
			result = preCheck();
		}
		if(result != null) {
			log.debug("preCheck() with result, lockObject={} maxWaitMillis={} result={}", lockObject, maxWaitMillis, result);
			return result;
		}

		ElapsedTimer elapsedTimer = new ElapsedTimer();
		elapsedTimer.start();
		long left = Long.MAX_VALUE;
		int loopCount = 0;
		while(true) {
			if(maxWaitMillis != null) {
				left = elapsedTimer.leftSinceEpoc(maxWaitMillis, maxWaitMillis);
				if(left <= 0)
					break;
				// never left left==0 as this also means forever to the API
			}

			synchronized(lockObject) {
				result = loopCheck();
				if(result != null) {
					log.debug("loopCheck({}, left={}) with result, lockObject={} maxWaitMillis={} result={}", loopCount, left, lockObject, maxWaitMillis, result);
					break;
				}
				log.debug("loopCheck({}, left={}) wait, lockObject={} maxWaitMillis={} result={}", loopCount, left, lockObject, maxWaitMillis, result);
				if(maxWaitMillis != null)
					lockObject.wait(left);
				else
					lockObject.wait();		// forever
			}
			loopCount++;
		}

		return result;
	}

}
