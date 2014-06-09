package org.darrylmiles.example.ee7.jca.eis.rar.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;

import org.darrylmiles.example.ee7.jca.eis.rar.cci.ConnectionFactoryImpl;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.ConnectionImpl;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.EisConnection;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.EisConnectionFactory;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.EisManagedConnectionFactory;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.EisManagedConnectionMetaData;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.ManagedConnectionFactoryImpl;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.ManagedConnectionImpl;
import org.darrylmiles.example.ee7.jca.eis.rar.driver.PerMinuteTimerTask;
import org.darrylmiles.example.ee7.jca.eis.rar.spi.ResourceAdapterImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.ResourceAdapterArchive;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TestRar {
	private static Logger log = Logger.getLogger(TestRar.class.getName());

	private static String deploymentName = "TestRar";

	/**
	 * Tried to use to fix missing interface classes issues.
	 * But we need to test both EAR deployment (all inside) and
	 *   separate RAR and EAR/SAR.
	 * @return
	 */
	//@Deployment(name="EAR", testable = false, order = 1)
	public static EnterpriseArchive createEnterpriseArchive() {
		EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, deploymentName + ".ear");
		ear.addAsModule(createConnector());
		ear.addAsModule(createClient());
		//ear.addAsManifestResource(, "jboss-deployment-structure.xml");
		return ear;
	}

	/**
	 * Define the deployment
	 *
	 * @return The deployment archive
	 */
	@Deployment(name= "RAR", testable = false, order = 1)
	public static ResourceAdapterArchive createConnector() {
		ResourceAdapterArchive raa = ShrinkWrap.create(ResourceAdapterArchive.class, deploymentName + ".rar");
		JavaArchive ja = ShrinkWrap.create(JavaArchive.class, UUID.randomUUID().toString() + ".jar");
//		ja.addClasses(
//				ResourceAdapterImpl.class,
//				ConnectionFactoryImpl.class,
//				ManagedConnectionImpl.class,
//				EisManagedConnectionMetaData.class,
//				EisManagedConnectionFactory.class,
//				ManagedConnectionFactoryImpl.class,
//				EisConnection.class,
//				ConnectionImpl.class,
//				PerMinuteTimerTask.class);
		// package private classes
//		ja.addClass("org.darrylmiles.example.ee7.jca.eis.rar.spi.EndpointKey");
		ja.addPackages(true, org.darrylmiles.example.ee7.jca.eis.rar.Constants.class.getPackage());
		raa.addAsLibrary(ja);
		raa.addAsManifestResource(new File("../ee7-jca-eis-rar/src/main/rar/META-INF/ironjacamar.xml"), "ironjacamar.xml");
		raa.addAsManifestResource(new File("../ee7-jca-eis-rar/src/main/rar/META-INF/jboss-deployment-structure.xml"), "jboss-deployment-structure.xml");
		//File file = new File("src/main/rar/META-INF/ironjacamar.xml");
		//assertTrue(file.isFile());
		//raa.addAsManifestResource(file, file.getName());		// "ironjacamar.xml"

		return raa;
	}

	@Deployment(order = 2)
	public static JavaArchive createClient() {
		JavaArchive ja = ShrinkWrap.create(JavaArchive.class);
	//	ja.addClass(EisConnection.class);
	//	ja.addClass(EisConnectionFactory.class);
		// This ends up inwide JAR inside WEB-INF/lib inside WAR
		//ja.addAsManifestResource(new File("src/test/resources/jboss-deployment-structure.xml"), "jboss-deployment-structure.xml");
		//ja.addPackages(true, org.darrylmiles.example.ee7.jca.eis.rar.Constants.class.getPackage());
		return ja;
	}

	@AfterClass
	public static void afterClass() {
		log.fine("");
	}

	/** resource */
	// FIXME should be also have shareable=true ?  what are the rules on when to and when not-to do this ?
	@Resource(mappedName = "java:/eis/ExampleMCF")
	private ConnectionFactory connectionFactory;

	/**
	 * Test @Resource injection and it is of expected type.
	 */
	@Test
	public void testConnectionFactoryInjection() throws Throwable {
		assertNotNull(connectionFactory);
		assertTrue(connectionFactory instanceof ConnectionFactory);
		assertTrue(connectionFactory instanceof EisConnectionFactory);
		EisConnectionFactory eisConnectionFactory = (EisConnectionFactory) connectionFactory;
		Object obj = Long.valueOf(42);
		Object rv = eisConnectionFactory.methodConnectionFactory(obj);
		assertEquals("42-methodConnectionFactory", rv);
	}

	/**
	 * Test helloWorld
	 *
	 * @exception Throwable
	 *                Thrown if case of an error
	 */
	@Test
	public void testCustomStringMethodNoArg() throws Throwable {
		assertNotNull(connectionFactory);
		EisConnection connection = (EisConnection) connectionFactory.getConnection();
		assertNotNull(connection);
		String result = connection.customStringMethod();
		assertEquals("customerStringMethod-NO-ARG", result);
		connection.close();
	}

	/**
	 * Test helloWorld
	 *
	 * @exception Throwable
	 *                Thrown if case of an error
	 */
	@Test
	public void testCustomStringMethodArg() throws Throwable {
		assertNotNull(connectionFactory);
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
		EisConnection connection = (EisConnection) o;
		assertNotNull(connection);

		String result = connection.customStringMethod(null);
		assertEquals("customerStringMethod-null", result);
		result = connection.customStringMethod("123456");
		assertEquals("customerStringMethod-123456", result);

		connection.close();
	}
}
