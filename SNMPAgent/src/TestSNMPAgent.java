import java.io.IOException;

import org.snmp4j.agent.mo.MOAccessImpl;
import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;


public class TestSNMPAgent {

	static final OID sysDescr = new OID(".1.3.6.1.2.1.1.1.0");
	
	public static void main(String[] args) throws IOException, InterruptedException{
		TestSNMPAgent client = new TestSNMPAgent("udp:127.0.0.1/161");
		client.init();
	}
	
	SNMPAgent agent = null;
	//SNMPManager client = null;
	String address = null;
	
	public TestSNMPAgent(String add){
		address = add;
	}
	
	private void init() throws IOException, InterruptedException{
		agent = new SNMPAgent("0.0.0.0/2013");
		agent.start();
		agent.unregisterManagedObject(agent.getSnmpv2MIB());
		
		agent.registerManagedObject(new MOScalar(new OID(".1.3.6.1.4.1.2006.1.1"), MOAccessImpl.ACCESS_READ_ONLY, new Integer32(1989)));
		agent.registerManagedObject(MOCreator.createReadOnly(sysDescr, "You've got this descrition"));
		//client = new SNMPManager("udp:127.0.0.1/2013");
		while (true) {
			System.out.println("Agent is running...");
			Thread.sleep(5000);
		}
	}
	
			
			
}
