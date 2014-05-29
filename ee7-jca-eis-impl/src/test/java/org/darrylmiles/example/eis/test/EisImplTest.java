package org.darrylmiles.example.eis.test;

import static org.junit.Assert.*;

import org.darrylmiles.example.eis.EisImpl;
import org.junit.Test;

public class EisImplTest {

	@Test
	public void test() {
		EisImpl eisImpl = new EisImpl();
		long l = eisImpl.methodOne();
		assertEquals(0L, l);
	}

}
