package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import javax.resource.ResourceException;
import javax.resource.spi.ManagedConnectionMetaData;

import org.darrylmiles.example.ee7.jca.eis.rar.Constants;


public class EisManagedConnectionMetaData implements ManagedConnectionMetaData {

	/**
	 * Default constructor
	 */
	public EisManagedConnectionMetaData() {
	}

	/**
	 * Returns Product name of the underlying EIS instance connected through the
	 * ManagedConnection.
	 *
	 * @return Product name of the EIS instance
	 * @throws ResourceException
	 *             Thrown if an error occurs
	 */
	@Override
	public String getEISProductName() throws ResourceException {
		return Constants.PRODUCT_NAME;
	}

	/**
	 * Returns Product version of the underlying EIS instance connected through
	 * the ManagedConnection.
	 *
	 * @return Product version of the EIS instance
	 * @throws ResourceException
	 *             Thrown if an error occurs
	 */
	@Override
	public String getEISProductVersion() throws ResourceException {
		return Constants.PRODUCT_VERSION;
	}

	/**
	 * Returns maximum limit on number of active concurrent connections
	 *
	 * @return Maximum limit for number of active concurrent connections
	 * @throws ResourceException
	 *             Thrown if an error occurs
	 */
	@Override
	public int getMaxConnections() throws ResourceException {
		return 0;
	}

	/**
	 * Returns name of the user associated with the ManagedConnection instance
	 *
	 * @return Name of the user
	 * @throws ResourceException
	 *             Thrown if an error occurs
	 */
	@Override
	public String getUserName() throws ResourceException {
		return null;
	}

}
