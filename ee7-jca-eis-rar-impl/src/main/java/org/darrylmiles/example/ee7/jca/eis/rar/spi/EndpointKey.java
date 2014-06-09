package org.darrylmiles.example.ee7.jca.eis.rar.spi;

import javax.resource.spi.ActivationSpec;
import javax.resource.spi.endpoint.MessageEndpointFactory;

/* package */ class EndpointKey {

	private ResourceAdapterImpl resourceAdapter;
	private MessageEndpointFactory endpointFactory;
	private ActivationSpec activationSpec;

	/* package */ EndpointKey(ResourceAdapterImpl resourceAdapter, MessageEndpointFactory endpointFactory, ActivationSpec spec) {
		this.resourceAdapter = resourceAdapter;
		this.endpointFactory = endpointFactory;
		this.activationSpec = spec;
	}

	public ResourceAdapterImpl getResourceAdapter() {
		return resourceAdapter;
	}

	public MessageEndpointFactory getEndpointFactory() {
		return endpointFactory;
	}

	public ActivationSpec getActivationSpec() {
		return activationSpec;
	}

	public boolean match(ResourceAdapterImpl resourceAdapter2, MessageEndpointFactory endpointFactory2, ActivationSpec activationSpec2) {
		if(getResourceAdapter().equals(resourceAdapter2) == false)
			return false;
		if(getEndpointFactory().equals(endpointFactory2) == false)
			return false;
		if(getActivationSpec().equals(activationSpec2) == false)
			return false;
		return true;
	}

}