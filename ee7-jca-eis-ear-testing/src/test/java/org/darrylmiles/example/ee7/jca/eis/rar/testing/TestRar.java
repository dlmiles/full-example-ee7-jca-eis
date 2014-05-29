package org.darrylmiles.example.ee7.jca.eis.rar.testing;

import static org.junit.Assert.*;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.resource.cci.ConnectionFactory;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.ConnectionFactoryImpl;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.ConnectionImpl;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.EisConnection;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.EisManagedConnectionFactory;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.EisManagedConnectionMetaData;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.ManagedConnectionFactoryImpl;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.ManagedConnectionImpl;
import org.darrylmiles.example.ee7.jca.eis.rar.spi.ResourceAdapterImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.ResourceAdapterArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TestRar {
	private static Logger log = Logger.getLogger(TestRar.class.getCanonicalName());

	private static String deploymentName = "TestRar";

	/**
	 * Define the deployment
	 *
	 * @return The deployment archive
	 */
	@Deployment
	public static ResourceAdapterArchive createDeployment() {
		ResourceAdapterArchive raa = ShrinkWrap.create(ResourceAdapterArchive.class, deploymentName + ".rar");
		JavaArchive ja = ShrinkWrap.create(JavaArchive.class, UUID.randomUUID().toString() + ".jar");
		ja.addClasses(
				ResourceAdapterImpl.class,
				ConnectionFactoryImpl.class,
				ManagedConnectionImpl.class,
				EisManagedConnectionMetaData.class,
				EisManagedConnectionFactory.class,
				ManagedConnectionFactoryImpl.class,
				EisConnection.class,
				ConnectionImpl.class);
		raa.addAsLibrary(ja);
		//raa.addAsManifestResource("META-INF/ironjacamar.xml", "ironjacamar.xml");
		File file = new File("src/main/rar/META-INF/ironjacamar.xml");
		assertTrue(file.isFile());
		raa.addAsManifestResource(file, file.getName());		// "ironjacamar.xml"

		return raa;
	}

	/** resource */
	@Resource(mappedName = "java:/eis/ExampleMCF")
	private ConnectionFactory connectionFactory;

	/**
	 * Test helloWorld
	 *
	 * @exception Throwable
	 *                Thrown if case of an error
	 */
	@Test
	public void testHelloWorldNoArgs() throws Throwable {
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
	public void testHelloWorldNameString() throws Throwable {
		assertNotNull(connectionFactory);
		EisConnection connection = (EisConnection) connectionFactory.getConnection();
		assertNotNull(connection);
		String result = connection.customStringMethod(null);
		assertEquals("customerStringMethod-null", result);
		connection.close();
	}
}
