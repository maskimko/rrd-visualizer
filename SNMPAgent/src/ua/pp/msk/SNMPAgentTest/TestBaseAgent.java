package ua.pp.msk.SNMPAgentTest;
/*_############################################################################
  _## 
  _##  SNMP4J-Agent 2 - TestAgent.java  
  _## 
  _##  Copyright (C) 2005-2012  Frank Fock (SNMP4J.org)
  _##  
  _##  Licensed under the Apache License, Version 2.0 (the "License");
  _##  you may not use this file except in compliance with the License.
  _##  You may obtain a copy of the License at
  _##  
  _##      http://www.apache.org/licenses/LICENSE-2.0
  _##  
  _##  Unless required by applicable law or agreed to in writing, software
  _##  distributed under the License is distributed on an "AS IS" BASIS,
  _##  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  _##  See the License for the specific language governing permissions and
  _##  limitations under the License.
  _##  
  _##########################################################################*/

import java.io.File;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.*;
import org.snmp4j.TransportMapping;
import org.snmp4j.agent.BaseAgent;
import org.snmp4j.agent.CommandProcessor;
import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOGroup;
import org.snmp4j.agent.ManagedObject;
import org.snmp4j.agent.io.ImportModes;
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
import org.snmp4j.smi.Variable;
import org.snmp4j.transport.TransportMappings;
import org.snmp4j.util.ThreadPool;

import ua.pp.msk.SNMPAgent.MOCreator;

/**
 * The <code>TestAgent</code> is a sample SNMP agent implementation of all
 * features (MIB implementations) provided by the SNMP4J-Agent framework. The
 * <code>TestAgent</code> extends the <code>BaseAgent</code> which provides a
 * framework for custom agent implementations through hook methods. Those
 * abstract hook methods need to be implemented by extending the
 * <code>BaseAgent</code>.
 * <p>
 * This IF-MIB implementation part of this test agent, is instrumentation as a
 * simulation MIB. Thus, by changing the agentppSimMode
 * (1.3.6.1.4.1.4976.2.1.1.0) from 'oper(1)' to 'config(2)' any object of the
 * IF-MIB is writable and even creatable (columnar objects) via SNMP. Check it
 * out!
 * 
 * @author Frank Fock
 * @version 1.0
 */
public class TestBaseAgent extends BaseAgent {

	// initialize Log4J logging
	static {
		LogFactory.setLogFactory(new Log4jLogFactory());
	}

	protected String address;

	// private Snmp4jHeartbeatMib heartbeatMIB;
	// private AgentppSimulationMib agentppSimulationMIB;

	/**
	 * Creates the test agent with a file to read and store the boot counter and
	 * a file to read and store its configuration.
	 * 
	 * @param bootCounterFile
	 *            a file containing the boot counter in serialized form (as
	 *            expected by BaseAgent).
	 * @param configFile
	 *            a configuration file with serialized management information.
	 * @throws IOException
	 *             if the boot counter or config file cannot be read properly.
	 */
	public TestBaseAgent(File bootCounterFile, File configFile)
			throws IOException {
		super(bootCounterFile, configFile, new CommandProcessor(
				new OctetString(MPv3.createLocalEngineID())));
		// Alternatively: OctetString.fromHexString("00:00:00:00:00:00:02",
		// ':');
		agent.setWorkerPool(ThreadPool.create("RequestPool", 4));
	}

	protected void registerManagedObject(ManagedObject mo) {
		try {
			server.register(mo, null);
		} catch (DuplicateRegistrationException ex) {
			throw new RuntimeException(ex);
		}
	}

	protected void registerManagedObjects() {
		/*
		 * try { // server.register(createStaticIfTable(), null);
		 * agentppSimulationMIB.registerMOs(server, null);
		 * heartbeatMIB.registerMOs(server, null); } catch
		 * (DuplicateRegistrationException ex) { ex.printStackTrace(); }
		 */
	}

	protected void addNotificationTargets(SnmpTargetMIB targetMIB,
			SnmpNotificationMIB notificationMIB) {

	}

	/*
	 * 
	 * protected void addNotificationTargets(SnmpTargetMIB targetMIB,
	 * SnmpNotificationMIB notificationMIB) { targetMIB.addDefaultTDomains();
	 * 
	 * targetMIB.addTargetAddress(new OctetString("notificationV2c"),
	 * TransportDomains.transportDomainUdpIpv4, new OctetString(new
	 * UdpAddress("127.0.0.1/162").getValue()), 200, 1, new
	 * OctetString("notify"), new OctetString("v2c"), StorageType.permanent);
	 * targetMIB.addTargetAddress(new OctetString("notificationV3"),
	 * TransportDomains.transportDomainUdpIpv4, new OctetString(new
	 * UdpAddress("127.0.0.1/1162").getValue()), 200, 1, new
	 * OctetString("notify"), new OctetString("v3notify"),
	 * StorageType.permanent); targetMIB.addTargetParams(new OctetString("v2c"),
	 * MessageProcessingModel.MPv2c, SecurityModel.SECURITY_MODEL_SNMPv2c, new
	 * OctetString("cpublic"), SecurityLevel.AUTH_PRIV, StorageType.permanent);
	 * targetMIB.addTargetParams(new OctetString("v3notify"),
	 * MessageProcessingModel.MPv3, SecurityModel.SECURITY_MODEL_USM, new
	 * OctetString("v3notify"), SecurityLevel.NOAUTH_NOPRIV,
	 * StorageType.permanent); notificationMIB.addNotifyEntry(new
	 * OctetString("default"), new OctetString("notify"),
	 * SnmpNotificationMIB.SnmpNotifyTypeEnum.inform, StorageType.permanent); }
	 */

	protected void addViews(VacmMIB vacm) {
		vacm.addGroup(SecurityModel.SECURITY_MODEL_SNMPv1, new OctetString(
				"cpublic"), new OctetString("v1v2group"),
				StorageType.nonVolatile);
		vacm.addAccess(new OctetString("v1v2group"), new OctetString("public"),
				SecurityModel.SECURITY_MODEL_ANY, SecurityLevel.NOAUTH_NOPRIV,
				MutableVACM.VACM_MATCH_EXACT, new OctetString("fullReadView"),
				new OctetString("fullWriteView"), new OctetString(
						"fullNotifyView"), StorageType.nonVolatile);
		vacm.addViewTreeFamily(new OctetString("fullReadView"),
				new OID(".1.3"), new OctetString(), VacmMIB.vacmViewIncluded,
				StorageType.nonVolatile);
	}

	/*
	 * protected void addViews(VacmMIB vacm) {
	 * vacm.addGroup(SecurityModel.SECURITY_MODEL_SNMPv1, new
	 * OctetString("cpublic"), new OctetString("v1v2group"),
	 * StorageType.nonVolatile);
	 * vacm.addGroup(SecurityModel.SECURITY_MODEL_SNMPv2c, new
	 * OctetString("cpublic"), new OctetString("v1v2group"),
	 * StorageType.nonVolatile); vacm.addGroup(SecurityModel.SECURITY_MODEL_USM,
	 * new OctetString("SHADES"), new OctetString("v3group"),
	 * StorageType.nonVolatile); vacm.addGroup(SecurityModel.SECURITY_MODEL_USM,
	 * new OctetString("MD5DES"), new OctetString("v3group"),
	 * StorageType.nonVolatile); vacm.addGroup(SecurityModel.SECURITY_MODEL_USM,
	 * new OctetString("TEST"), new OctetString("v3test"),
	 * StorageType.nonVolatile); vacm.addGroup(SecurityModel.SECURITY_MODEL_USM,
	 * new OctetString("SHA"), new OctetString("v3restricted"),
	 * StorageType.nonVolatile); vacm.addGroup(SecurityModel.SECURITY_MODEL_USM,
	 * new OctetString("SHAAES128"), new OctetString("v3group"),
	 * StorageType.nonVolatile); vacm.addGroup(SecurityModel.SECURITY_MODEL_USM,
	 * new OctetString("SHAAES192"), new OctetString("v3group"),
	 * StorageType.nonVolatile); vacm.addGroup(SecurityModel.SECURITY_MODEL_USM,
	 * new OctetString("SHAAES256"), new OctetString("v3group"),
	 * StorageType.nonVolatile); vacm.addGroup(SecurityModel.SECURITY_MODEL_USM,
	 * new OctetString("MD5AES128"), new OctetString("v3group"),
	 * StorageType.nonVolatile); vacm.addGroup(SecurityModel.SECURITY_MODEL_USM,
	 * new OctetString("MD5AES192"), new OctetString("v3group"),
	 * StorageType.nonVolatile); vacm.addGroup(SecurityModel.SECURITY_MODEL_USM,
	 * new OctetString("MD5AES256"), new OctetString("v3group"),
	 * StorageType.nonVolatile); vacm.addGroup(SecurityModel.SECURITY_MODEL_USM,
	 * new OctetString("v3notify"), new OctetString("v3group"),
	 * StorageType.nonVolatile);
	 * 
	 * vacm.addAccess(new OctetString("v1v2group"), new OctetString("public"),
	 * SecurityModel.SECURITY_MODEL_ANY, SecurityLevel.NOAUTH_NOPRIV,
	 * MutableVACM.VACM_MATCH_EXACT, new OctetString("fullReadView"), new
	 * OctetString("fullWriteView"), new OctetString("fullNotifyView"),
	 * StorageType.nonVolatile); vacm.addAccess(new OctetString("v3group"), new
	 * OctetString(), SecurityModel.SECURITY_MODEL_USM, SecurityLevel.AUTH_PRIV,
	 * MutableVACM.VACM_MATCH_EXACT, new OctetString("fullReadView"), new
	 * OctetString("fullWriteView"), new OctetString("fullNotifyView"),
	 * StorageType.nonVolatile); vacm.addAccess(new OctetString("v3restricted"),
	 * new OctetString(), SecurityModel.SECURITY_MODEL_USM,
	 * SecurityLevel.NOAUTH_NOPRIV, MutableVACM.VACM_MATCH_EXACT, new
	 * OctetString("restrictedReadView"), new
	 * OctetString("restrictedWriteView"), new
	 * OctetString("restrictedNotifyView"), StorageType.nonVolatile);
	 * vacm.addAccess(new OctetString("v3test"), new OctetString(),
	 * SecurityModel.SECURITY_MODEL_USM, SecurityLevel.AUTH_PRIV,
	 * MutableVACM.VACM_MATCH_EXACT, new OctetString("testReadView"), new
	 * OctetString("testWriteView"), new OctetString("testNotifyView"),
	 * StorageType.nonVolatile);
	 * 
	 * vacm.addViewTreeFamily(new OctetString("fullReadView"), new OID("1.3"),
	 * new OctetString(), VacmMIB.vacmViewIncluded, StorageType.nonVolatile);
	 * vacm.addViewTreeFamily(new OctetString("fullWriteView"), new OID("1.3"),
	 * new OctetString(), VacmMIB.vacmViewIncluded, StorageType.nonVolatile);
	 * vacm.addViewTreeFamily(new OctetString("fullNotifyView"), new OID("1.3"),
	 * new OctetString(), VacmMIB.vacmViewIncluded, StorageType.nonVolatile);
	 * 
	 * vacm.addViewTreeFamily(new OctetString("restrictedReadView"), new
	 * OID("1.3.6.1.2"), new OctetString(), VacmMIB.vacmViewIncluded,
	 * StorageType.nonVolatile); vacm.addViewTreeFamily(new
	 * OctetString("restrictedWriteView"), new OID("1.3.6.1.2.1"), new
	 * OctetString(), VacmMIB.vacmViewIncluded, StorageType.nonVolatile);
	 * vacm.addViewTreeFamily(new OctetString("restrictedNotifyView"), new
	 * OID("1.3.6.1.2"), new OctetString(), VacmMIB.vacmViewIncluded,
	 * StorageType.nonVolatile); vacm.addViewTreeFamily(new
	 * OctetString("restrictedNotifyView"), new OID("1.3.6.1.6.3.1"), new
	 * OctetString(), VacmMIB.vacmViewIncluded, StorageType.nonVolatile);
	 * 
	 * vacm.addViewTreeFamily(new OctetString("testReadView"), new
	 * OID("1.3.6.1.2"), new OctetString(), VacmMIB.vacmViewIncluded,
	 * StorageType.nonVolatile); vacm.addViewTreeFamily(new
	 * OctetString("testReadView"), new OID("1.3.6.1.2.1.1"), new OctetString(),
	 * VacmMIB.vacmViewExcluded, StorageType.nonVolatile);
	 * vacm.addViewTreeFamily(new OctetString("testWriteView"), new
	 * OID("1.3.6.1.2.1"), new OctetString(), VacmMIB.vacmViewIncluded,
	 * StorageType.nonVolatile); vacm.addViewTreeFamily(new
	 * OctetString("testNotifyView"), new OID("1.3.6.1.2"), new OctetString(),
	 * VacmMIB.vacmViewIncluded, StorageType.nonVolatile);
	 * 
	 * }
	 */

	protected void addUsmUser(USM usm) {

	}

	/*
	 * protected void addUsmUser(USM usm) { UsmUser user = new UsmUser(new
	 * OctetString("SHADES"), AuthSHA.ID, new OctetString("SHADESAuthPassword"),
	 * PrivDES.ID, new OctetString("SHADESPrivPassword")); //
	 * usm.addUser(user.getSecurityName(), usm.getLocalEngineID(), user);
	 * usm.addUser(user.getSecurityName(), null, user); user = new UsmUser(new
	 * OctetString("TEST"), AuthSHA.ID, new OctetString("maplesyrup"),
	 * PrivDES.ID, new OctetString("maplesyrup"));
	 * usm.addUser(user.getSecurityName(), usm.getLocalEngineID(), user); user =
	 * new UsmUser(new OctetString("SHA"), AuthSHA.ID, new
	 * OctetString("SHAAuthPassword"), null, null);
	 * usm.addUser(user.getSecurityName(), usm.getLocalEngineID(), user); user =
	 * new UsmUser(new OctetString("SHADES"), AuthSHA.ID, new
	 * OctetString("SHADESAuthPassword"), PrivDES.ID, new
	 * OctetString("SHADESPrivPassword")); usm.addUser(user.getSecurityName(),
	 * usm.getLocalEngineID(), user); user = new UsmUser(new
	 * OctetString("MD5DES"), AuthMD5.ID, new OctetString("MD5DESAuthPassword"),
	 * PrivDES.ID, new OctetString("MD5DESPrivPassword"));
	 * usm.addUser(user.getSecurityName(), usm.getLocalEngineID(), user); user =
	 * new UsmUser(new OctetString("SHAAES128"), AuthSHA.ID, new
	 * OctetString("SHAAES128AuthPassword"), PrivAES128.ID, new
	 * OctetString("SHAAES128PrivPassword"));
	 * usm.addUser(user.getSecurityName(), usm.getLocalEngineID(), user); user =
	 * new UsmUser(new OctetString("SHAAES192"), AuthSHA.ID, new
	 * OctetString("SHAAES192AuthPassword"), PrivAES192.ID, new
	 * OctetString("SHAAES192PrivPassword"));
	 * usm.addUser(user.getSecurityName(), usm.getLocalEngineID(), user); user =
	 * new UsmUser(new OctetString("SHAAES256"), AuthSHA.ID, new
	 * OctetString("SHAAES256AuthPassword"), PrivAES256.ID, new
	 * OctetString("SHAAES256PrivPassword"));
	 * usm.addUser(user.getSecurityName(), usm.getLocalEngineID(), user);
	 * 
	 * user = new UsmUser(new OctetString("MD5AES128"), AuthMD5.ID, new
	 * OctetString("MD5AES128AuthPassword"), PrivAES128.ID, new
	 * OctetString("MD5AES128PrivPassword"));
	 * usm.addUser(user.getSecurityName(), usm.getLocalEngineID(), user); user =
	 * new UsmUser(new OctetString("MD5AES192"), AuthMD5.ID, new
	 * OctetString("MD5AES192AuthPassword"), PrivAES192.ID, new
	 * OctetString("MD5AES192PrivPassword"));
	 * usm.addUser(user.getSecurityName(), usm.getLocalEngineID(), user); user =
	 * new UsmUser(new OctetString("MD5AES256"), AuthMD5.ID, new
	 * OctetString("MD5AES256AuthPassword"), PrivAES256.ID, new
	 * OctetString("MD5AES256PrivPassword"));
	 * usm.addUser(user.getSecurityName(), usm.getLocalEngineID(), user); user =
	 * new UsmUser(new OctetString("MD5AES256"), AuthMD5.ID, new
	 * OctetString("MD5AES256AuthPassword"), PrivAES256.ID, new
	 * OctetString("MD5AES256PrivPassword"));
	 * usm.addUser(user.getSecurityName(), usm.getLocalEngineID(), user); user =
	 * new UsmUser(new OctetString("v3notify"), null, null, null, null);
	 * usm.addUser(user.getSecurityName(), null, user); }
	 */
	/*
	 * This code illustrates how a table can be created and filled with static
	 * data:
	 * 
	 * private static DefaultMOTable createStaticIfTable() { MOTableSubIndex[]
	 * subIndexes = new MOTableSubIndex[] { new
	 * MOTableSubIndex(SMIConstants.SYNTAX_INTEGER) }; MOTableIndex indexDef =
	 * new MOTableIndex(subIndexes, false); MOColumn[] columns = new
	 * MOColumn[8]; int c = 0; columns[c++] = new MOColumn(c,
	 * SMIConstants.SYNTAX_INTEGER, MOAccessImpl.ACCESS_READ_ONLY); // ifIndex
	 * columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_OCTET_STRING,
	 * MOAccessImpl.ACCESS_READ_ONLY);// ifDescr columns[c++] = new MOColumn(c,
	 * SMIConstants.SYNTAX_INTEGER, MOAccessImpl.ACCESS_READ_ONLY); // ifType
	 * columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_INTEGER,
	 * MOAccessImpl.ACCESS_READ_ONLY); // ifMtu columns[c++] = new MOColumn(c,
	 * SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY); // ifSpeed
	 * columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_OCTET_STRING,
	 * MOAccessImpl.ACCESS_READ_ONLY);// ifPhysAddress columns[c++] = new
	 * MOMutableColumn(c, SMIConstants.SYNTAX_INTEGER, // ifAdminStatus
	 * MOAccessImpl.ACCESS_READ_WRITE, null); columns[c++] = new MOColumn(c,
	 * SMIConstants.SYNTAX_INTEGER, MOAccessImpl.ACCESS_READ_ONLY); //
	 * ifOperStatus
	 * 
	 * DefaultMOTable ifTable = new DefaultMOTable(new OID("1.3.6.1.2.1.2.2.1"),
	 * indexDef, columns); MOMutableTableModel model = (MOMutableTableModel)
	 * ifTable.getModel(); Variable[] rowValues1 = new Variable[] { new
	 * Integer32(1), new OctetString("eth0"), new Integer32(6), new
	 * Integer32(1500), new Gauge32(100000000), new
	 * OctetString("00:00:00:00:01"), new Integer32(1), new Integer32(1) };
	 * Variable[] rowValues2 = new Variable[] { new Integer32(2), new
	 * OctetString("loopback"), new Integer32(24), new Integer32(1500), new
	 * Gauge32(10000000), new OctetString("00:00:00:00:02"), new Integer32(1),
	 * new Integer32(1) }; model.addRow(new DefaultMOMutableRow2PC(new OID("1"),
	 * rowValues1)); model.addRow(new DefaultMOMutableRow2PC(new OID("2"),
	 * rowValues2)); ifTable.setVolatile(true); return ifTable; }
	 */
	protected void initTransportMappings() throws IOException {
		transportMappings = new TransportMapping[1];
		Address addr = GenericAddress.parse(address);
		TransportMapping tm = TransportMappings.getInstance()
				.createTransportMapping(addr);
		transportMappings[0] = tm;
	}

	public static void main(String[] args) {
		String address;
		if (args.length > 0) {
			address = args[0];
		} else {
			address = "0.0.0.0/2013";
		}
		BasicConfigurator.configure();
		try {
			TestBaseAgent testAgent1 = new TestBaseAgent(new File(
					"SNMP4JTestAgentBC.cfg"), new File(
					"SNMP4JTestAgentConfig.cfg"));
			testAgent1.address = address;
			testAgent1.init();
			testAgent1.loadConfig(ImportModes.REPLACE_CREATE);
			testAgent1.addShutdownHook();
			testAgent1.getServer().addContext(new OctetString("public"));
			testAgent1.finishInit();
			testAgent1.run();
			testAgent1.sendColdStartNotification();
			testAgent1.registerManagedObject(MOCreator.createReadOnly(new OID(
					".1.3.6.1.4.1.2006.1.1"), "Good morning, Maksym!"));
			/*
			 * Try to rerun agent: try { Thread.sleep(30000); } catch
			 * (InterruptedException ex2) { } testAgent1.stop();
			 * testAgent1.run();
			 */
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex1) {
					break;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	protected void unregisterManagedObjects() {
		// here we should unregister those objects previously registered...
	}

	protected void unregisterManagedObject(MOGroup moGroup) {
		// here we should unregister those objects previously registered...
		moGroup.unregisterMOs(server, getContext(moGroup));
	}

	protected void addCommunities(SnmpCommunityMIB communityMIB) {
		Variable[] com2sec = new Variable[] { new OctetString("public"), // community
																			// name
				new OctetString("cpublic"), // security name
				getAgent().getContextEngineID(), // local engine ID
				new OctetString("public"), // default context name
				new OctetString(), // transport tag
				new Integer32(StorageType.nonVolatile), // storage type
				new Integer32(RowStatus.active) // row status
		};
		MOTableRow row = communityMIB.getSnmpCommunityEntry().createRow(
				new OctetString("public2public").toSubIndex(true), com2sec);
		communityMIB.getSnmpCommunityEntry().addRow(row);
		// snmpCommunityMIB.setSourceAddressFiltering(true);
	}

	/*
	 * protected void registerSnmpMIBs() { heartbeatMIB = new
	 * Snmp4jHeartbeatMib(super.getNotificationOriginator(), new OctetString(),
	 * super.snmpv2MIB.getSysUpTime()); agentppSimulationMIB = new
	 * AgentppSimulationMib(); super.registerSnmpMIBs(); }
	 */
}
