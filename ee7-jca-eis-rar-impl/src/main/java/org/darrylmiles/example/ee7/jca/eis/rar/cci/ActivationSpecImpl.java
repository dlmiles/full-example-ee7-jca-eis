package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import java.io.Serializable;

import javax.resource.ResourceException;
import javax.resource.spi.Activation;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class handling Inbound async invocation on EE from EIS.
 * @author Darryl
 *
 */
@Activation(messageListeners={InboundMessageListener.class})
public class ActivationSpecImpl implements ActivationSpec, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4756221177574476234L;

	private static final Logger log = LoggerFactory.getLogger(ConnectionFactoryImpl.class);

	private transient ResourceAdapter resourceAdapter;

	@ConfigProperty()
	private String stringProperty;
	@ConfigProperty()
	private Integer integerProperty;
	@ConfigProperty()
	private Boolean booleanProperty;

	@Override
	public ResourceAdapter getResourceAdapter() {
		return resourceAdapter;
	}

	@Override
	public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
		this.resourceAdapter = ra;
		log.debug("ra={}", ra);
	}

	@Override
	public void validate() throws InvalidPropertyException {
		log.debug("stringProperty={}, integerProperty={}, booleanProperty={}", stringProperty, integerProperty, booleanProperty);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("stringProperty=").append(stringProperty);
		sb.append(";integerProperty").append(integerProperty);
		sb.append(";booleanProperty").append(booleanProperty);
		sb.append("}");
		return sb.toString();
	}
}
