package org.darrylmiles.example.ee7.jca.eis.rar.spi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Placed into JNDI to provide custom controls.
 * @author Darryl
 *
 */
public class ResourceAdapterCustomImpl implements ResourceAdapterCustom {

	private static final Logger log = LoggerFactory.getLogger(ResourceAdapterCustomImpl.class);

	private ResourceAdapterImpl resourceAdapter;

	public ResourceAdapterCustomImpl(ResourceAdapterImpl resourceAdapter) {
		this.resourceAdapter = resourceAdapter;
	}

	public void test(int valueOne) {
		log.debug("resourceAdapter={}, valueOne={}", resourceAdapter, valueOne);
	}

}
