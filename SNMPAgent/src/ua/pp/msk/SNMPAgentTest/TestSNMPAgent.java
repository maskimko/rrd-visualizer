package ua.pp.msk.SNMPAgentTest;


import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.BasicConfigurator;
import org.snmp4j.smi.OID;

import ua.pp.msk.SNMPAgent.MOCreator;
import ua.pp.msk.SNMPAgent.ModbusAgent;



public class TestSNMPAgent {

	static final OID sysDescr = new OID(".1.3.6.1.2.1.1.1.0");

	public static void main(String[] args) throws IOException {
		TestSNMPAgent client = new TestSNMPAgent("udp:127.0.0.1/161");
		client.init();
	}

	ModbusAgent agent = null;
	//SNMP2Agent agent = null;
	//SimpleAgent agent = null;
	/**
	 * This is the client which we have created earlier
	 */
	SNMPManager client = null;

	String address = null;

	/**
	 * Constructor
	 *
	 * @param add
	 */
	public TestSNMPAgent(String add) {
		address = add;
	}

	private void init() throws IOException {
		
		BasicConfigurator.configure();
		agent = new ModbusAgent("0.0.0.0/2013");
		//agent = new SNMP2Agent("127.0.0.1/2001");
		//agent = new SimpleAgent("udp:0.0.0.0/2013");
		agent.start();

		// Since BaseAgent registers some MIBs by default we need to unregister
		// one before we register our own sysDescr. Normally you would
		// override that method and register the MIBs that you need
		//agent.unregisterManagedObject(agent.getSnmpv2MIB());

		// Register a system description, use one from you product environment
		// to test with
		//agent.registerManagedObject(MOCreator.createReadOnly(sysDescr, "This Description is not null"));

		agent.registerManagedObject(MOCreator.createReadOnly(new OID(".1.3.6.1.4.1.2006.1.1"), "Hello world!"));
		// Setup the client to use our newly started agent
		client = new SNMPManager("udp:127.0.0.1/2013");
		client.start();
		System.out.println(client.getAsString(sysDescr));
		System.out.println(client.getAsString(new OID(".1.3.6.1.4.1.2006.1.1")));
		try {
		Thread.sleep(100 * 1000);
		} catch (InterruptedException ie) {
			
		}
		// Get back Value which is set
		//System.out.println(client.getAsString(sysDescr));
	}

}
