package org.darrylmiles.example.ee7.jca.eis.ejb.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.Remote;

import org.darrylmiles.example.ee7.jca.eis.ejb.ScConsumerMdb;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.InboundMessageListener;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.ManagedConnectionFactoryImpl;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(
	messageListenerInterface = InboundMessageListener.class,
	activationConfig = {
		@javax.ejb.ActivationConfigProperty(propertyName = "stringProperty", propertyValue = "stringData")
	}
)
@Remote(ScConsumerMdb.class)
public class ScConsumerMdbImpl implements ScConsumerMdb, InboundMessageListener {

	private static final Logger log = LoggerFactory.getLogger(ScConsumerMdbImpl.class);

	//@Resource(mappedName = "java:/eis/ExampleMCF")
	private ManagedConnectionFactoryImpl connectionFactory;

	@Resource
	private MessageDrivenContext mdctx;

	@PostConstruct
	public void postConstruct() {
		log.debug("mdctx={}", mdctx);
		log.debug("connectionFactory={}", connectionFactory);
	}

	@PreDestroy
	public void preDestroy() {
		log.debug("mdctx={}", mdctx);
	}

	@Override
	public void onMessage(Message message) {
		log.debug("message={}", message);
		mdctx.setRollbackOnly();		// don't consume anything
	}

	private long id = 1;

	@Override
	public long methodOne() {
		return id;
	}

}
