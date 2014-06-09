package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import javax.resource.spi.ManagedConnectionFactory;

/**
 * This forms our bespoke public interface to the managed connection factory.
 * 
 * This allows the handle to the managed connection factory to be proxied across
 * JVMs and ClassLoaders.
 * 
 * We can add our bespoke APIs to configure the ManagedConnectionFactory just here.
 * 
 * @author Darryl
 *
 */
public interface EisManagedConnectionFactory extends ManagedConnectionFactory {

	Object methodManagedConnectionFactory(Object obj);

}
