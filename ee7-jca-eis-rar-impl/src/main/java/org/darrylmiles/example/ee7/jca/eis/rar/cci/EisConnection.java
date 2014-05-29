package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import javax.resource.cci.Connection;

public interface EisConnection extends Connection {

	void customMethod();

	String customStringMethod();
	String customStringMethod(String arg);

	boolean isValid();

}
