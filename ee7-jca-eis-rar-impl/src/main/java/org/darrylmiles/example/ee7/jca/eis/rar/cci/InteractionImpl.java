package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.Interaction;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.Record;
import javax.resource.cci.ResourceWarning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InteractionImpl implements Interaction {

	private static final Logger log = LoggerFactory.getLogger(InteractionImpl.class);

	private ConnectionImpl connection;
	private ResourceWarning resourceWarning;

	public InteractionImpl(ConnectionImpl connectionImpl) {
		this.connection = connectionImpl;
	}

	@Override
	public void close() throws ResourceException {
		log.debug("");
		connection.unregisterInteraction(this);
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public boolean execute(InteractionSpec ispec, Record input, Record output) throws ResourceException {
		log.debug("ispec={}, input={}, output={}", ispec, input, output);
		return false;
	}

	@Override
	public Record execute(InteractionSpec ispec, Record input) throws ResourceException {
		log.debug("ispec={}, input={}", ispec, input);
		return null;
	}

	@Override
	public ResourceWarning getWarnings() throws ResourceException {
		return resourceWarning;
	}

	@Override
	public void clearWarnings() throws ResourceException {
		resourceWarning = null;
	}

}
