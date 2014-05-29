package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.resource.cci.ConnectionSpec;

public class EisConnectionSpec implements ConnectionSpec, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2768247843206856605L;

	private String hostname;
	private Short port;

	private String bindAddress;
	private Short bindPort;

	private Long paramLongOne;

	private Map<String,? extends Serializable> options;


	public EisConnectionSpec() {
		options = new HashMap<String,Serializable>();
	}


	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Short getPort() {
		return port;
	}
	public void setPort(Short port) {
		this.port = port;
	}

	public String getBindAddress() {
		return bindAddress;
	}
	public void setBindAddress(String bindAddress) {
		this.bindAddress = bindAddress;
	}

	public Short getBindPort() {
		return bindPort;
	}
	public void setBindPort(Short bindPort) {
		this.bindPort = bindPort;
	}

	public Long getParamLongOne() {
		return paramLongOne;
	}
	public void setParamLongOne(Long paramLongOne) {
		this.paramLongOne = paramLongOne;
	}

	public Map<String, ? extends Serializable> getOptions() {
		return options;
	}
	public void setOptions(Map<String, ? extends Serializable> options) {
		this.options = options;
	}

}
