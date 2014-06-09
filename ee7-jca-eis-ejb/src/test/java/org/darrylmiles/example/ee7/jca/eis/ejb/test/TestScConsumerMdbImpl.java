package org.darrylmiles.example.ee7.jca.eis.ejb.test;

import static org.junit.Assert.*;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.darrylmiles.example.ee7.jca.eis.ejb.ScConsumerMdb;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Taken from Arquallian blog references
// FIXME WIP not got it working yet, convert to TestNG
public class TestScConsumerMdbImpl {

	private static final Logger log = LoggerFactory.getLogger(TestScConsumerMdbImpl.class);

	private static EJBContainer ejbContainer;

	private static Context ctx;

	public TestScConsumerMdbImpl() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		ejbContainer = EJBContainer.createEJBContainer();

		ctx = ejbContainer.getContext();
	}

	@AfterClass
	public static void teadDownClass() throws Exception {
		ejbContainer.close();
	}

	@Test
	public void testOne() throws NamingException {
		ScConsumerMdb scConsumerMdb = (ScConsumerMdb) ctx.lookup("java:global/classes/ScConsumerMdb");

		assertNotNull(scConsumerMdb);
		long id = scConsumerMdb.methodOne();
		assertEquals(1, id);
	}

}
