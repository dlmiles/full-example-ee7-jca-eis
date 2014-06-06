package org.darrylmiles.example.ee7.jca.eis.rar.driver;

import javax.resource.spi.work.Work;

import org.darrylmiles.example.eis.EisImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EisConnectionWorker implements Work {

	private static final Logger log = LoggerFactory.getLogger(EisConnectionWorker.class);

	private EisImpl eisImpl;

	private Thread thread;
	private boolean shutdownFlag;
	private boolean shutdownCompletedFlag;
	private boolean startupReadyFlag;
	private long statupMillis;
	private long lastLoopMillis;
	private long loopCounter;

	private void runInternal() {
		eisImpl = new EisImpl();

		while(true) {
			synchronized (this) {
				if(shutdownFlag)
					break;
				// do other stuff ???
				lastLoopMillis = System.currentTimeMillis();
				loopCounter++;
			}
	
			try {
				log.debug("statupMillis={}, lastLoopMillis={}", statupMillis, lastLoopMillis);
				//Thread.sleep(10000);
				synchronized (this) {
					if(shutdownFlag)
						break;
					wait(10000);
				}
			} catch(InterruptedException t) {
				log.debug("", t);
				setShutdownFlag();
			}
		}

		try {
			log.debug("loopCounter={}", loopCounter);
		} catch(Exception e) {
			log.debug("", e);
		}

		setShutdownCompletedFlag();
	}

	public boolean waitForStartupReady(/*@Nonnegative*/ long timeout) throws InterruptedException {
		boolean first = true;
		ElapsedTimer elapsedTimer = new ElapsedTimer();
		elapsedTimer.start();
		while(true) {
			long left = elapsedTimer.leftSinceEpoc(timeout, timeout);
			if(left <= 0) {
				if(first && timeout > 0)		// wait at least one time, when timeout>0
					left = 1;
				else
					break;
			}
			synchronized (this) {
				if(isStartupReadyFlag())
					return true;
				wait(left);
			}
			first = false;
		}
		return false;
	}

	public boolean waitForShutdown(/*@Nonnegative*/ long timeout) throws InterruptedException {
		boolean first = true;
		ElapsedTimer elapsedTimer = new ElapsedTimer();
		elapsedTimer.start();
		while(true) {
			long left = elapsedTimer.leftSinceEpoc(timeout, timeout);
			if(left <= 0) {
				if(first && timeout > 0)		// wait at least one time, when timeout>0
					left = 1;
				else
					break;
			}
			synchronized (this) {
				if(isShutdownFlag())
					return true;
				wait(left);
			}
			first = false;
		}
		return false;
	}

	public synchronized boolean isStartupReadyFlag() {
		return startupReadyFlag;
	}
	public synchronized void setStartupReadyFlag() {
		startupReadyFlag = true;
		notifyAll();
	}
	public synchronized boolean isShutdownFlag() {
		return shutdownFlag;
	}
	public synchronized void setShutdownFlag() {
		shutdownFlag = true;
		// FIXME notify thread/network-socket to shutdown
		notifyAll();
	}

	public synchronized void wakeUp() {
		notifyAll();
	}

	@Override
	public void run() {
		synchronized (this) {
			statupMillis = System.currentTimeMillis();
			thread = Thread.currentThread();
			setStartupReadyFlag();
		}

		try {
			runInternal();
		} catch(Throwable t) {
			log.error("", t);
		}
	}

	@Override
	public void release() {
		setShutdownFlag();
		// FIXME is this sync or async ?   we presume optimistic-sync and try and wait a small time for termination.
		try {
			waitForShutdown(10000);
		} catch (InterruptedException e) {
			log.warn("", e);
		}
	}

	private synchronized void setShutdownCompletedFlag() {
		this.shutdownCompletedFlag = true;
	}

	public synchronized boolean isShutdownCompleted() {
		return shutdownCompletedFlag;
	}

}
