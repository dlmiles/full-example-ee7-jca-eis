package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.Referenceable;
import javax.resource.ResourceException;
import javax.resource.cci.ConnectionFactory;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;
import javax.security.auth.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ConnectionDefinition(
	// It is important this is the widest remote interface java type
	//  that RAR clients can use.
	connectionFactory = EisConnectionFactory.class,
	connectionFactoryImpl = ConnectionFactoryImpl.class,
	// It is important this is the widest remote interface java type
	//  that RAR clients can use (by obtaining it from the ConnectionFactory)
	connection = EisConnection.class,
	connectionImpl = ConnectionImpl.class)
public class ManagedConnectionFactoryImpl implements EisManagedConnectionFactory, Referenceable, ResourceAdapterAssociation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8361541362715040672L;

	private static final Logger log = LoggerFactory.getLogger(ManagedConnectionFactoryImpl.class);

	private transient Queue<ManagedConnectionImpl> connectionQueue;

	private transient PrintWriter logWriter;

	@ConfigProperty()
	private String testConfigProperty;

	public ManagedConnectionFactoryImpl() {
		connectionQueue = new LinkedList<ManagedConnectionImpl>();
	}

	@Override
	public Object createConnectionFactory(ConnectionManager cxManager) throws ResourceException {
		log.debug("cxManager={}", cxManager);
		ConnectionFactory cf = new ConnectionFactoryImpl(cxManager);
		return cf;
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

	/////////////////////////////////////////////////////////////////////////

	// Required EE contract to implement #equals(Object)
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if((obj instanceof ManagedConnectionFactoryImpl) == false)
			return false;
		// FIXME what are the rules on this?
		return super.equals(obj);
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
		if(this.resourceAdapter != null) {		// FIXME can this be set twice for same instance?
			throw new ResourceException("resourceAdapter is already set old=" +
				((this.resourceAdapter == null) ? this.resourceAdapter : (this.resourceAdapter.getClass().getName() + "@" + Integer.toHexString(this.resourceAdapter.hashCode()))) +
				"; new=" +
				((ra == null) ? ra : (ra.getClass().getName() + "@" + Integer.toHexString(ra.hashCode()))));
		}
		this.resourceAdapter = ra;
		log.debug("ra={}", ra);
	}

	/////////////////////////////////////////////////////////////////////////

	private Reference reference;

	/**
	 * @see javax.resource.Referenceable
	 */
	// @Nonnull
	@Override
	public Reference getReference() throws NamingException {
		if(reference == null)	// API contract says we can not return null
			throw new NamingException("reference has not been set");
		return reference;
	}

	/**
	 * @see javax.resource.Referenceable
	 */
	@Override
	public void setReference(/*@Nonnull*/ Reference reference) {
		this.reference = reference;
		log.debug("reference={}", reference);
	}

	/**
	 * @see EisManagedConnectionFactory
	 */
	@Override
	public Object methodManagedConnectionFactory(Object obj) {
		return String.valueOf(obj) + "-methodManagedConnectionFactory";
	}

}
