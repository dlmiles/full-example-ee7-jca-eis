package org.darrylmiles.example.ee7.jca.eis.rar.driver;

/**
 * Convenience method that allows easier idiom in java.
 * @author Darryl
 *
 * @param <T>  The result type you choose to use.
 * @see ElapsedTimerTemplate
 */
public abstract class ElapsedTimerSimple<T> extends ElapsedTimerTemplate<T> {

	/**
	 * This is the only method you should override.
	 * @return
	 * @throws InterruptedException
	 */
	abstract public T check() throws InterruptedException;

	/**
	 * @deprecated DO NOT USE DIRECTLY SEE DOCUMENTATION
	 */
	@Override
	@Deprecated
	public T preCheck() throws InterruptedException {
		return check();
	}

	/**
	 * @deprecated DO NOT USE DIRECTLY SEE DOCUMENTATION
	 */
	@Override
	@Deprecated
	public T loopCheck() throws InterruptedException {
		return check();
	}

}
