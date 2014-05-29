package org.darrylmiles.example.ee7.jca.eis.ejb.impl;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.darrylmiles.example.ee7.jca.eis.ejb.ScConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Stateless
@Startup
@Singleton
@Remote(ScConsumer.class)
public class ScConsumerImpl implements ScConsumer {

	private static final Logger log = LoggerFactory.getLogger(ScConsumerImpl.class);

	@PostConstruct
	public void afterPropertiesSet() {
		log.debug("");
		timedEvent();
	}

	@Schedule(second = "0/30", minute = "*", hour = "*", persistent = false)
	@Lock(LockType.READ)
	public void timedEvent() {
		// ensure RA is restarted
		log.debug("");
	}
}
