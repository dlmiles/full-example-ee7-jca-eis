package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import javax.resource.ResourceException;
import javax.resource.cci.ConnectionMetaData;

public class ConnectionMetaDataImpl implements ConnectionMetaData {

	private String eisProductName;
	private String eisProductVersion;
	private String userName;

	public ConnectionMetaDataImpl(String eisProductName, String eisProductVersion, String userName) {
		this.eisProductName = eisProductName;
		this.eisProductVersion = eisProductVersion;
		this.userName = userName;
	}

	@Override
	public String getEISProductName() throws ResourceException {
		return eisProductName;
	}

	@Override
	public String getEISProductVersion() throws ResourceException {
		return eisProductVersion;
	}

	@Override
	public String getUserName() throws ResourceException {
		return userName;
	}

}
