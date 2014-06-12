package org.darrylmiles.example.ee7.jca.eis.rar.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;

import org.darrylmiles.example.ee7.jca.eis.rar.cci.EisConnection;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.EisConnectionFactory;

/**
 * This is as close to a real EJB client as it gets, but it calls junit assertions
 *  to perform tested on state.
 * @author Darryl
 *
 */
@Stateless
public class EjbClient implements EjbClientInterface {

	private static Logger log = Logger.getLogger(EjbClient.class.getName());

	/** resource */
	// FIXME should be also have shareable=true ?  what are the rules on when to and when not-to do this ?
	@Resource(mappedName = "java:/eis/ExampleMCF")
	private ConnectionFactory connectionFactory;

	/**
	 * Test @Resource injection and it is of expected type.
	 */
	public void testConnectionFactoryInjection() throws Throwable {
		assertNotNull(connectionFactory);
		assertTrue(connectionFactory instanceof ConnectionFactory);
		assertTrue(connectionFactory instanceof EisConnectionFactory);
		EisConnectionFactory eisConnectionFactory = (EisConnectionFactory) connectionFactory;
		Object obj = Long.valueOf(42);
		Object rv = eisConnectionFactory.methodConnectionFactory(obj);
		assertEquals("methodConnectionFactory-42", rv);
	}

	/**
	 * Test helloWorld
	 *
	 * @exception Throwable
	 *                Thrown if case of an error
	 */
	public void testCustomStringMethodNoArg() throws Throwable {
		assertNotNull(connectionFactory);
		EisConnection connection = (EisConnection) connectionFactory.getConnection();
		assertNotNull(connection);
		String result = connection.customStringMethod();
		assertEquals("customStringMethod-NO-ARG", result);
		connection.close();
	}

	/**
	 * Test helloWorld
	 *
	 * @exception Throwable
	 *                Thrown if case of an error
	 */
	public void testCustomStringMethodArg() throws Throwable {
		assertNotNull(connectionFactory);
		EisConnection connection = null;
		try {
			Object o = connectionFactory.getConnection();
			assertNotNull(o);
			assertTrue(o instanceof Connection);
			assertNotNull(o.getClass().asSubclass(Connection.class));
			Connection conn = (Connection) o;
	
			ClassLoader cl1 = o.getClass().getClassLoader();		// ModuleClassLoader for Module "deployment.TestRar.rar:main" from Service Module Loader
			ClassLoader cl2 = Connection.class.getClassLoader();	// ModuleClassLoader for Module "javax.resource.api:main" from local module loader @59906517 (finder: local module finder @5bfbf16f (roots: F:\Devel\deps\wildfly-8.1.0.Final\modules,F:\Devel\deps\wildfly-8.1.0.Final\modules\system\layers\base,F:\Devel\deps\wildfly-8.1.0.Final\modules\system\add-ons\gravia,F:\Devel\deps\wildfly-8.1.0.Final\modules\system\add-ons\snowdrop))
			ClassLoader cl3 = EisConnection.class.getClassLoader();	// ModuleClassLoader for Module "deployment.test.war:main" from Service Module Loader
			// ah... we need EisConnection.class from inside "deployment.TestRar.rar:main"  or we need a proxy!
			// surely when the @Resource of connectionFactoryis injected, that is a proxy ?
			// so then the invocation of connectionFactory.getConnection() returns a proxied instance too ?
	
			assertNotNull(o.getClass().asSubclass(EisConnection.class));
			assertTrue(o instanceof EisConnection);
			connection = (EisConnection) o;
			assertNotNull(connection);
	
			String result = connection.customStringMethod(null);
			assertEquals("customStringMethod-null", result);
			result = connection.customStringMethod("123456");
			assertEquals("customStringMethod-123456", result);
	
			connection.close();
			connection = null;
		} finally {
			if(connection != null) {
				try {
					connection.close();	// try to be good citizen
				} catch(Exception eat) {
				}
				connection = null;
			}
		}
	}

}
