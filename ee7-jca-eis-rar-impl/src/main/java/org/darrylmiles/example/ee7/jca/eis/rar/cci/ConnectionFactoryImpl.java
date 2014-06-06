package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionSpec;
import javax.resource.cci.RecordFactory;
import javax.resource.cci.ResourceAdapterMetaData;
import javax.resource.spi.ConnectionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionFactoryImpl implements EisConnectionFactory {

	private static final Logger log = LoggerFactory.getLogger(ConnectionFactoryImpl.class);

	private Reference reference;
	private Queue<ConnectionImpl> connectionQueue;
	private RecordFactoryImpl recordFactory;
	private ResourceAdapterMetaDataImpl resourceAdapterMetaData;
	private ConnectionManager connectionManager;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4831445754387987698L;

	private void init() {
		connectionQueue = new ConcurrentLinkedQueue<ConnectionImpl>();
		recordFactory = new RecordFactoryImpl();
		resourceAdapterMetaData = new ResourceAdapterMetaDataImpl();
	}

	public ConnectionFactoryImpl() {
		init();
	}

	public ConnectionFactoryImpl(ConnectionManager cxManager) {
		init();
		log.debug("cxManager={}", cxManager);
		this.connectionManager = cxManager;
	}

	@Override
	public void setReference(Reference reference) {
		log.debug("reference={}", reference);
		this.reference = reference;
	}

	@Override
	public Reference getReference() throws NamingException {
		log.debug("reference={}", reference);
		return reference;
	}

	@Override
	public Connection getConnection() throws ResourceException {
		return getConnection(null);	// logged in here
	}

	@Override
	public Connection getConnection(ConnectionSpec properties) throws ResourceException {
		ConnectionImpl connection = new ConnectionImpl(this, properties);
		registerConnection(connection);
		log.debug("connection={}", connection);
		return connection;
	}

	@Override
	public RecordFactory getRecordFactory() throws ResourceException {
		log.debug("recordFactory={}", recordFactory);
		return recordFactory;
	}

	@Override
	public ResourceAdapterMetaData getMetaData() throws ResourceException {
		log.debug("resourceAdapterMetaData={}", resourceAdapterMetaData);
		return resourceAdapterMetaData;
	}

	/**
	 * Not EE API but provided for force a closure from the factory.
	 * @throws ResourceException
	 */
	public void close() throws ResourceException {
		log.debug("connectionQueue.size()={}", connectionQueue.size());
		Set<ConnectionImpl> set = new HashSet<ConnectionImpl>();
		synchronized (connectionQueue) {
			set.addAll(connectionQueue);
		}

		for(ConnectionImpl connection : set) {
			connection.close();
		}
	}

	/*package*/ void unregisterConnection(/*@Nonnull*/ ConnectionImpl connection) {
		connectionQueue.remove(connection);
	}

	/*package*/ void registerConnection(/*@Nonnull*/ ConnectionImpl connection) {
		connectionQueue.add(connection);
	}

	/**
	 * @see EisConnectionFactory
	 */
	public Object methodConnectionFactory(Object obj) {
		return String.valueOf(obj) + "-methodConnectionFactory";
	}

}
