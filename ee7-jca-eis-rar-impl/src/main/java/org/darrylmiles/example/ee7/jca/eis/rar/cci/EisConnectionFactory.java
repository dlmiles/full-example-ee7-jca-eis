package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import javax.resource.cci.ConnectionFactory;

/**
 * This forms our bespoke public interface to the connection factory.
 * 
 * This allows the handle to the connection factory to be proxied across
 * JVMs and ClassLoaders.
 * 
 * We can add our bespoke APIs to configure the ConnectionFactory just here.
 * 
 * @author Darryl
 *
 */
public interface EisConnectionFactory extends ConnectionFactory {

	Object methodConnectionFactory(Object obj);

}
