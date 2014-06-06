package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionMetaData;
import javax.resource.cci.ConnectionSpec;
import javax.resource.cci.Interaction;
import javax.resource.cci.LocalTransaction;
import javax.resource.cci.ResultSetInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.darrylmiles.example.ee7.jca.eis.rar.Constants;

public class ConnectionImpl implements EisConnection {

	private static final Logger log = LoggerFactory.getLogger(ConnectionImpl.class);

	private ConnectionFactoryImpl connectionFactory;
	@SuppressWarnings("unused")
	private ConnectionSpec connectionSpec;
	private Queue<InteractionImpl> interactionQueue;
	private ConnectionMetaDataImpl connectionMetaData;

	public ConnectionImpl(ConnectionFactoryImpl connectionFactoryImpl, ConnectionSpec properties) {
		this.connectionFactory = connectionFactoryImpl;
		this.connectionSpec = properties;
		init();

		processConnectionSpec(properties);		// we probably need to extract userName from properties
		connectionMetaData = new ConnectionMetaDataImpl(Constants.PRODUCT_NAME, Constants.PRODUCT_VERSION, null);

		connectionFactoryImpl.registerConnection(this);
		log.debug("properties={}", properties);
	}

	private void processConnectionSpec(ConnectionSpec properties) {
		if(properties == null)
			return;
		if((properties instanceof EisConnectionSpec) == false)
			throw new IllegalArgumentException();
		EisConnectionSpec connectionSpec = (EisConnectionSpec) properties;
		log.debug("connectionSpec={}", connectionSpec);
	}

	private void init() {
		interactionQueue = new ConcurrentLinkedQueue<InteractionImpl>();
	}

	@Override
	public Interaction createInteraction() throws ResourceException {
		init();
		InteractionImpl interaction = new InteractionImpl(this);
		registerInteraction(interaction);
		log.debug("interaction={}", interaction);
		return interaction;
	}

	@Override
	public LocalTransaction getLocalTransaction() throws ResourceException {
		LocalTransaction localTransaction = null;
		log.debug("localTransaction={}", localTransaction);
		return localTransaction;
	}

	@Override
	public ConnectionMetaData getMetaData() throws ResourceException {
		log.debug("connectionMetaData={}", connectionMetaData);
		return connectionMetaData;
	}

	@Override
	public ResultSetInfo getResultSetInfo() throws ResourceException {
		ResultSetInfo resultSetInfo = null;
		log.debug("resultSetInfo={}", resultSetInfo);
		return resultSetInfo;
	}

	@Override
	public void close() throws ResourceException {
		log.debug("interactionQueue.size()={}", interactionQueue.size());
		Set<InteractionImpl> set = new HashSet<InteractionImpl>();
		synchronized (interactionQueue) {
			set.addAll(interactionQueue);
		}

		for(InteractionImpl interaction : set) {
			interaction.close();
		}

		connectionFactory.unregisterConnection(this);
	}

	/*package*/ void unregisterInteraction(/*@Nonnull*/ InteractionImpl interaction) {
		interactionQueue.remove(interaction);
	}

	/*package*/ void registerInteraction(/*@Nonnull*/ InteractionImpl interaction) {
		interactionQueue.add(interaction);
	}

	@Override
	public void customMethod() {
		log.debug("");
	}

	@Override
	public boolean isValid() {
		log.debug("");
		return false;
	}

	@Override
	public String customStringMethod() {
		log.debug("");
		return "customerStringMethod-NO-ARG";
	}

	@Override
	public String customStringMethod(String arg) {
		log.debug("arg={}", arg);
		return "customerStringMethod-" + arg;
	}
}
