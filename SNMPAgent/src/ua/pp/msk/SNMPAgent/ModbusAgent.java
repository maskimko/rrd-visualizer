package ua.pp.msk.SNMPAgent;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.apache.log4j.BasicConfigurator;
import org.snmp4j.TransportMapping;
import org.snmp4j.agent.BaseAgent;
import org.snmp4j.agent.CommandProcessor;
import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOAccess;
import org.snmp4j.agent.ManagedObject;
import org.snmp4j.agent.io.ImportModes;
import org.snmp4j.agent.mo.MOAccessImpl;
import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.agent.mo.MOTableRow;
import org.snmp4j.agent.mo.snmp.RowStatus;
import org.snmp4j.agent.mo.snmp.SnmpCommunityMIB;
import org.snmp4j.agent.mo.snmp.SnmpNotificationMIB;
import org.snmp4j.agent.mo.snmp.SnmpTargetMIB;
import org.snmp4j.agent.mo.snmp.StorageType;
import org.snmp4j.agent.mo.snmp.VacmMIB;
import org.snmp4j.agent.security.MutableVACM;
import org.snmp4j.log.Log4jLogFactory;
import org.snmp4j.log.LogFactory;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModel;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UnsignedInteger32;
import org.snmp4j.smi.Variable;
import org.snmp4j.transport.TransportMappings;
import org.snmp4j.util.ThreadPool;


public class ModbusAgent extends BaseAgent {

	static {
		LogFactory.setLogFactory(new Log4jLogFactory());
	}
	
	protected String address;
	
	public ModbusAgent(File bootCounterFile, File configFile) throws IOException{
		super(bootCounterFile, configFile, new CommandProcessor(new OctetString(MPv3.createLocalEngineID())));
		agent.setWorkerPool(ThreadPool.create("Working pool", 4));
	} 
	
	
	public ModbusAgent(String serverAddress) throws IOException{
		this(new File("bootCounter.agent"), new File("config.agent"));
		this.address = serverAddress;
	}
	
	public void registerManagedObject(ManagedObject mo){
		try {
			server.register(mo, null);	
		} catch (DuplicateRegistrationException de){
			throw new RuntimeException(de);
		}
	}
	
	@Override
	protected void addCommunities(SnmpCommunityMIB arg0) {
		Variable[] communityToSecurity = new Variable[] {
				new OctetString("public"),
				new OctetString("cpublic"),
				getAgent().getContextEngineID(),
				new OctetString("public"),
				new OctetString(),
				new Integer32(StorageType.nonVolatile),
				new Integer32(RowStatus.active)
		};
		MOTableRow tableRow = arg0.getSnmpCommunityEntry().createRow(new OctetString("public2public").toSubIndex(true), communityToSecurity);
		arg0.getSnmpCommunityEntry().addRow(tableRow);

	}

	@Override
	protected void addNotificationTargets(SnmpTargetMIB arg0,
			SnmpNotificationMIB arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void addUsmUser(USM arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void addViews(VacmMIB arg0) {
		arg0.addGroup(SecurityModel.SECURITY_MODEL_SNMPv1, new OctetString("cpublic"), new OctetString("v1v2group"), StorageType.nonVolatile);
		arg0.addAccess(new OctetString("v1v2group"), new OctetString("public"), SecurityModel.SECURITY_MODEL_ANY, SecurityLevel.NOAUTH_NOPRIV, MutableVACM.VACM_MATCH_EXACT, new OctetString("fullReadView"), new OctetString("fullWriteView"), new OctetString("fullNotifyView"), StorageType.nonVolatile);
		arg0.addViewTreeFamily(new OctetString("fullReadView"), new OID(".1.3"), new OctetString(), VacmMIB.vacmViewIncluded, StorageType.nonVolatile);

	}
	
	protected void initTransportMappings() throws IOException {
		transportMappings = new TransportMapping[1];
		Address  servaddress = GenericAddress.parse(address);
		TransportMapping tm = TransportMappings.getInstance().createTransportMapping(servaddress);
		transportMappings[0] = tm;
	}

	@Override
	protected void registerManagedObjects() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void unregisterManagedObjects() {
		// TODO Auto-generated method stub

	}
	
	public void start() throws IOException{
		init();
		loadConfig(ImportModes.REPLACE_CREATE);
		addShutdownHook();
		getServer().addContext(new OctetString("public"));
		finishInit();
		run();
		sendColdStartNotification();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String address;
		address = "udp:0.0.0.0/2013";
		BasicConfigurator.configure();
		try {
			ModbusAgent ma = new ModbusAgent(new File("bc"), new File("cnf"));
			ma.address = address;
			ma.init();
			//ma.loadConfig(ImportModes.REPLACE_CREATE);
			ma.addShutdownHook();
			ma.getServer().addContext(new OctetString("public"));
			ma.finishInit();
			ma.run();
			ma.sendColdStartNotification();
			ma.registerManagedObject(MOCreator.createReadOnly(new OID(".1.3.6.1.4.1.2006.1.1"), "This server works!"));
			ma.registerManagedObject(new CurrentValue(new OID(".1.3.6.1.4.1.2006.2.1"), MOAccessImpl.ACCESS_READ_WRITE, new UnsignedInteger32(1000)));
			try {
				Thread.sleep(30000);
			} catch (InterruptedException ex){
				
			}
			ma.stop();
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
		
	}

}

class CurrentValue extends MOScalar {
	
	

	public CurrentValue(OID id, MOAccess access, Variable value) {
		super(id, access, value);
		// TODO Auto-generated constructor stub
	}

	/*
	@Override
	public void get(SubRequest subr){
		long curtime = Calendar.getInstance().getTimeInMillis() / 1000;
		setValue(new UnsignedInteger32(curtime ));
		super.get(subr);
	}
	*/
	
	@Override
	public Variable getValue(){
		long curtime = Calendar.getInstance().getTimeInMillis() / 1000;
		return new UnsignedInteger32(curtime);
	}
	
	
}


