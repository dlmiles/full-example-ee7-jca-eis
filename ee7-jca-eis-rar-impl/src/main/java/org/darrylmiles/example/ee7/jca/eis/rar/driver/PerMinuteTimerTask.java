package org.darrylmiles.example.ee7.jca.eis.rar.driver;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a placeholder timer task verifying it gets executed.
 * 
 * @author Darryl
 *
 */
public class PerMinuteTimerTask extends TimerTask {

	private static final Logger log = LoggerFactory.getLogger(PerMinuteTimerTask.class);

	private static final long delay = 45000;
	private static final long period = 60000;

	public void init() {
	}

	public long getDelay() {
		return delay;
	}

	public long getPeriod() {
		return period;
	}

	@Override
	public void run() {
		log.debug("threadName={}", Thread.currentThread().getName());
	}

}
