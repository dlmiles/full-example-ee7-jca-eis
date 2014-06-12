package org.darrylmiles.example.ee7.jca.eis.rar.testing;

import javax.ejb.Remote;

//@Local
@Remote
public interface EjbClientInterface {

	void testConnectionFactoryInjection() throws Throwable;

	void testCustomStringMethodNoArg() throws Throwable;

	void testCustomStringMethodArg() throws Throwable;

}
