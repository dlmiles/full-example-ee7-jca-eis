package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import javax.resource.spi.AdministeredObject;

@AdministeredObject(adminObjectInterfaces={AdministeredObjectInterface.class})
public class AdministeredObjectImpl implements AdministeredObjectInterface {

	private Integer value;

	@Override
	public Integer testMethodOne() {
		Integer rv;
		synchronized (this) {
			rv = value;
			int i = 0;
			if(value != null)
				i = value.intValue() + 1;
			value = Integer.valueOf(i);
		}
		return rv;
	}

	@Override
	public void testMethodTwo() {
		synchronized (this) {
			value = null;
		}
	}

}
