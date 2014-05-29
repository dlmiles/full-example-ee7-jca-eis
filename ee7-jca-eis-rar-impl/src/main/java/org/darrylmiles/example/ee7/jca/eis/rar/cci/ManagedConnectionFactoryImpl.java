package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import java.io.PrintWriter;
import java.util.Queue;
import java.util.Set;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;
import javax.security.auth.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ConnectionDefinition(
	connectionFactory = ConnectionFactory.class,
	connectionFactoryImpl = ConnectionFactoryImpl.class,
	connection = Connection.class,
	connectionImpl = ConnectionImpl.class)
public class ManagedConnectionFactoryImpl implements ManagedConnectionFactory, ResourceAdapterAssociation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8361541362715040672L;

	private static final Logger log = LoggerFactory.getLogger(ManagedConnectionFactoryImpl.class);

	private transient Queue<ManagedConnectionImpl> connectionQueue;

	private transient PrintWriter logWriter;

	@ConfigProperty()
	private String testConfigProperty;

	@Override
	public Object createConnectionFactory(ConnectionManager cxManager) throws ResourceException {
		log.debug("cxManager={}", cxManager);
		return null;
	}

	@Override
	public Object createConnectionFactory() throws ResourceException {
		log.debug("");
		return createConnectionFactory(null);
	}

	@Override
	public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
		ManagedConnection mc = new ManagedConnectionImpl(this);
		log.debug("subject={}, cxRequestInfo={}", subject, cxRequestInfo);
		return mc;
	}

	@Override
	public ManagedConnection matchManagedConnections(@SuppressWarnings("rawtypes") Set connectionSet, Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
		ManagedConnection mc = null;
		log.debug("connectionSet={}, subject={}, cxRequestInfo={}", connectionSet, subject, cxRequestInfo);
		return mc;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws ResourceException {
		log.debug("out={}", out);
		this.logWriter = out;
	}

	@Override
	public PrintWriter getLogWriter() throws ResourceException {
		return logWriter;
	}

	/*package*/ void unregisterConnection(/*@Nonnull*/ ManagedConnectionImpl managedConnection) {
		connectionQueue.remove(managedConnection);
	}

	/*package*/ void registerConnection(/*@Nonnull*/ ManagedConnectionImpl managedConnection) {
		connectionQueue.add(managedConnection);
	}

	// Required EE contract to implement #equals(Object)
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(o == this)
			return true;
		if((o instanceof ManagedConnectionFactoryImpl) == false)
			return false;
		return false;
	}

	// Required EE contract to implement #hashCode()
	public int hashCode() {
		return super.hashCode();
	}

	/////////////////////////////////////////////////////////////////////////

	private ResourceAdapter resourceAdapter;

	/**
	 * @see javax.resource.spi.ResourceAdapterAssociation
	 */
	@Override
	public ResourceAdapter getResourceAdapter() {
		return resourceAdapter;
	}

	/**
	 * @see javax.resource.spi.ResourceAdapterAssociation
	 */
	@Override
	public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
		this.resourceAdapter = ra;
	}

}
