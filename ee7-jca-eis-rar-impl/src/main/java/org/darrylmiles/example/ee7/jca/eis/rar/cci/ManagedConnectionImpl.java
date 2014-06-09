package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.security.auth.Subject;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManagedConnectionImpl implements ManagedConnection {

	private static final Logger log = LoggerFactory.getLogger(ManagedConnectionImpl.class);

	private ManagedConnectionFactoryImpl managedConnectionFactory;
	private PrintWriter logWriter;
	private Queue<ConnectionEventListener> listenerQueue;

	public ManagedConnectionImpl(ManagedConnectionFactoryImpl managedConnectionFactoryImpl) {
		this.managedConnectionFactory = managedConnectionFactoryImpl;
		init();
		managedConnectionFactory.registerConnection(this);
	}

	private void init() {
		listenerQueue = new ConcurrentLinkedQueue<ConnectionEventListener>();
	}

	@Override
	public Object getConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
		log.debug("subject={}, cxRequestInfo={}", subject, cxRequestInfo);
		return null;
	}

	@Override
	public void destroy() throws ResourceException {
		log.debug("");
	}

	@Override
	public void cleanup() throws ResourceException {
		log.debug("");
		managedConnectionFactory.unregisterConnection(this);
	}

	@Override
	public void associateConnection(Object connection) throws ResourceException {
		log.debug("connection={}", connection);
		if(connection == null)
			return;
		if((connection instanceof ManagedConnectionImpl) == false)
			return;
		ManagedConnectionImpl connectionImpl = (ManagedConnectionImpl) connection;
	}

	@Override
	public void addConnectionEventListener(ConnectionEventListener listener) {
		log.debug("listener={}", listener);
		synchronized (listenerQueue) {
			if(listenerQueue.contains(listener))
				listenerQueue.remove(listener);
			listenerQueue.add(listener);
		}
	}

	@Override
	public void removeConnectionEventListener(ConnectionEventListener listener) {
		log.debug("listener={}", listener);
		listenerQueue.remove(listener);
	}

	@Override
	public XAResource getXAResource() throws ResourceException {
		XAResource xaResource = null;
		log.debug("xaResource={}", xaResource);
		return xaResource;
	}

	@Override
	public LocalTransaction getLocalTransaction() throws ResourceException {
		LocalTransaction localTransaction = null;
		log.debug("localTransaction={}", localTransaction);
		return localTransaction;
	}

	@Override
	public ManagedConnectionMetaData getMetaData() throws ResourceException {
		ManagedConnectionMetaData mcmd = null;
		log.debug("managedConnectionMetaData={}", mcmd);
		return mcmd;
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

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		return sb.toString();
	}
}
