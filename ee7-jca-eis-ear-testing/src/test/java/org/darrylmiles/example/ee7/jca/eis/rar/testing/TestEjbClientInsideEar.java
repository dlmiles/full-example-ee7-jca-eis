package org.darrylmiles.example.ee7.jca.eis.rar.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;

import org.darrylmiles.example.ee7.jca.eis.rar.cci.EisConnection;
import org.darrylmiles.example.ee7.jca.eis.rar.cci.EisConnectionFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.ResourceAdapterArchive;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@Stateless
@RunWith(Arquillian.class)
public class TestEjbClientInsideEar {

	private static Logger log = Logger.getLogger(TestEjbClientInsideEar.class.getName());

	private static String deploymentName = TestEjbClientInsideEar.class.getSimpleName();

	/**
	 * Tried to use to fix missing interface classes issues.
	 * But we need to test both EAR deployment (all inside) and
	 *   separate RAR and EAR/SAR.
	 * @return
	 */
	@Deployment(name="EAR", testable = true, order = 1)
	public static EnterpriseArchive createEnterpriseArchive() {
		EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, deploymentName + "_EAR.ear");
		ear.addAsModule(createConnector());
		ear.addAsModule(createEjb());
		//ear.addAsManifestResource(, "jboss-deployment-structure.xml");
		return ear;
	}

	public static JavaArchive createEjb() {
		return ShrinkWrap.create(JavaArchive.class, deploymentName + "_EJB.jar")
				.addClasses(EjbClientInterface.class, EjbClient.class)
				.addClasses(TestEjbClientInsideEar.class)		// myself
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	/**
	 * Define the deployment
	 *
	 * @return The deployment archive
	 */
	public static ResourceAdapterArchive createConnector() {
		ResourceAdapterArchive raa = ShrinkWrap.create(ResourceAdapterArchive.class, deploymentName + "_RAR.rar");

		JavaArchive ja = ShrinkWrap.create(JavaArchive.class, UUID.randomUUID().toString() + "_RARimpl.jar");
		// Just the code packages (not the testing ones)
		ja.addPackages(false, org.darrylmiles.example.ee7.jca.eis.rar.Constants.class.getPackage());
		ja.addPackages(false, org.darrylmiles.example.ee7.jca.eis.rar.cci.EisConnection.class.getPackage());
		ja.addPackages(false, org.darrylmiles.example.ee7.jca.eis.rar.driver.EisConnectionWorker.class.getPackage());
		ja.addPackages(false, org.darrylmiles.example.ee7.jca.eis.rar.spi.ResourceAdapterCustom.class.getPackage());
		raa.addAsLibrary(ja);

		JavaArchive jaEis = ShrinkWrap.create(JavaArchive.class, UUID.randomUUID().toString() + "_EISimpl.jar");
		jaEis.addPackages(false, org.darrylmiles.example.eis.EisImpl.class.getPackage());
		raa.addAsLibraries(jaEis);

		raa.addAsManifestResource(new File("../ee7-jca-eis-rar/src/main/rar/META-INF/ironjacamar.xml"), "ironjacamar.xml");
		raa.addAsManifestResource(new File("../ee7-jca-eis-rar/src/main/rar/META-INF/jboss-deployment-structure.xml"), "jboss-deployment-structure.xml");
		//File file = new File("src/main/rar/META-INF/ironjacamar.xml");
		//assertTrue(file.isFile());
		//raa.addAsManifestResource(file, file.getName());		// "ironjacamar.xml"

		return raa;
	}

	//@Deployment(testable = false)
	public static JavaArchive createClient() {
		JavaArchive ja = ShrinkWrap.create(JavaArchive.class);
	//	ja.addClass(EisConnection.class);
	//	ja.addClass(EisConnectionFactory.class);
		// add EJB client
		// This ends up inwide JAR inside WEB-INF/lib inside WAR
		//ja.addAsManifestResource(new File("src/test/resources/jboss-deployment-structure.xml"), "jboss-deployment-structure.xml");
		//ja.addPackages(true, org.darrylmiles.example.ee7.jca.eis.rar.Constants.class.getPackage());
		return ja;
	}

	@AfterClass
	public static void afterClass() {
		log.fine("");
	}



	@EJB
	private EjbClientInterface ejbClientInterface;

	/**
	 * Test @EJB injection and it is of expected type.
	 */
	@Test
	@InSequence(1)
	public void testEjbClientInterfaceInjection() throws Throwable {
		assertNotNull(ejbClientInterface);
	}

	/**
	 * Test @Resource injection and it is of expected type.
	 */
	@Test
	public void testConnectionFactoryInjection() throws Throwable {
		assertNotNull(ejbClientInterface);
		ejbClientInterface.testConnectionFactoryInjection();
	}

	/**
	 * Test helloWorld
	 *
	 * @exception Throwable
	 *                Thrown if case of an error
	 */
	@Test
	public void testCustomStringMethodNoArg() throws Throwable {
		assertNotNull(ejbClientInterface);
		ejbClientInterface.testCustomStringMethodNoArg();
	}

	/**
	 * Test helloWorld
	 *
	 * @exception Throwable
	 *                Thrown if case of an error
	 */
	@Test
	public void testCustomStringMethodArg() throws Throwable {
		assertNotNull(ejbClientInterface);
		ejbClientInterface.testCustomStringMethodArg();
	}

}
