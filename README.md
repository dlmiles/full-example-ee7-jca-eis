

This is a full API example of a Java EE7, JCA.

Although the project currently uses EE6 dependencies, until I understand and indentify new features for EE7, so they can be added and documented as such.

Project License: GPL v2


ee7-jca-eis-impl		This is the EIS implementation.
				This mimics an external resource (that the Application Server) does not natively understand.
				This resource is capable of two-way messaging and transactions.

ee7-jca-eis-rar-impl		This is the JCA/RAR implementation.
				This contains the various Java *.class libraries needed to adapt and convert the APIs of the
				 ee7-jca-eis-impl into the JCA API so that the application server can manage/deploy this
				 information source.

ee7-jca-eis-rar			This is the packaging and deployment into a RAR of:
					ee7-jca-eis-impl
					ee7-jca-eis-rar-impl
				This project produces a JAR that has everything an application server needs to deploy this
				 EIS type.

ee7-jca-eis-ejb			This is the EJB MDB consumer project.
				This project is an example of your business logic consumer of the EIS.

ee7-jca-eis-ear			This is the EAR packaging of both the JCA and EJB to put the two halves together to allow.





The questions that this project tries to resolve:

1) How to package up a JCA implementation, EJB consumer, EAR to deploy into JBoss/WildFly AppServer.

   The implementation attempts to exercise many of the new features of recent EE specifications.

   The EIS should be allowed to manage its own threads, these threads are both
   short running (performing some operation against the AppServer) and long running
   (such as persistent reusable/pooled network connections).

   The EIS should be able to fire messages from EIS to AppServer, when processing these messages
   they may simply be consumed, or there maybe a callback.

   The AppServer should be able to locate/resolve the EIS and then fire messages at the EIS to
   process.

   The EIS should be able to 

   Some customer JBoss code should be able to access jboss-threads custom APIs
   to do even more exotic stuff.  Where to hook into JCA lifecycle to ensure a
   proper startup and shutdown sequence for those implementation detail threads.

2) Is it possible to deploy the JCA implementation separately (outside of EAR) but inside the same Application Server JVM ?
  So that is is consumed entirely via JNDI and Proxies.  With the container managing it ?

  The question here is what are all the possible deployment modes ?
  And what changes are required in the project to accomodate each method ?

   *) Deploy JCA/RAR as a standalone JAR that is globally accessible and can be
      activated and consumed by any other EAR/WAR (or other deployment) ?

   *) Deploy JCA/RAR inside EAR ?


3) How to configure META-INF/jboss-deployment-structure.xml of the various projects ?

   How to manage module naming best inside this XML.  It really is a pain to
   see "0.0.1-SNAPSHOT" in the name, OSGi has Bundle-Symbolic-Name and Bundle-Version
   to provide arbitrary naming and version control of other modules.  It also has
   tooling to manage and verify itself.

   How can this be made easier to manage in a project so it takes care of itself,
   is not error prone, always does the correct thing or fails as early as possible
   (ideally during Maven building, not during deployment testing).


4) How to configure META-INF/jboss-ejb3.xml inside the EJB project.

   A goal of the project is that it should not use any JBoss specific java code.  So that porting
   it to run on another compliant EE Application Server requires minimal work.  This means not
   using any custom JBoss annotations.
   It is allowed that additional files can be in the deployment, since a different application
   server will simply ignore those files.

   In this project the META-INF/jboss-ejb3.xml is used to describe to the AS which JCA
   implementation is expected for a particular EJB consumer.


5) The EIS is a simplistic file based store of data.

   The EIS should capable of participating within AppServer transactions.

   Some EIS APIs might be transaction-less, meaning they will never cause an error
   during the 2 stage commit, but if they must be part of the transaction they will
   go through the motions, but the API consumer should not expect REPEATIBLE-READ
   behaviour.
   So maybe there are some optimizations to inform the AppServer transaction layer
   of this.


6) Is there anything special the EIS needs to manage to participate in XA transactions ?


7) What changes are needed to project to demonstrate deployment on at least one
   other Application Server implementation ?

?) Document/explain relevant usage of META-INF/MANIFEST.MF and "Dependencies:" header ?

?) Document/explain relevant usage of META-INF/ironjacamar.xml ?


