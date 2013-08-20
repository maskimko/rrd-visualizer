package ua.pp.msk.SNMPAgentTest;

import org.snmp4j.agent.mo.DefaultMOMutableRow2PC;
import org.snmp4j.agent.mo.DefaultMOTable;
import org.snmp4j.agent.mo.MOAccessImpl;
import org.snmp4j.agent.mo.MOColumn;
import org.snmp4j.agent.mo.MOMutableTableModel;
import org.snmp4j.agent.mo.MOTableIndex;
import org.snmp4j.agent.mo.MOTableSubIndex;
import org.snmp4j.smi.Gauge32;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.SMIConstants;
import org.snmp4j.smi.Variable;


public class DeviceTable extends DefaultMOTable {
	
	public DeviceTable(OID oid, MOTableIndex mti, MOColumn[] cols){
		super(oid, mti, cols);
	}
	
	protected static DeviceTable createDefaultTable(){
		MOTableSubIndex[] subIndx = new MOTableSubIndex[] {
				new MOTableSubIndex(SMIConstants.SYNTAX_GAUGE32)
		};
		MOTableIndex indx = new MOTableIndex(subIndx, false);
		MOColumn[] columns = new MOColumn[4];
		int counter = 0;
		columns[counter++] = new MOColumn(counter, SMIConstants.SYNTAX_OCTET_STRING, MOAccessImpl.ACCESS_READ_ONLY); //Description
		columns[counter++] = new MOColumn(counter, SMIConstants.SYNTAX_IPADDRESS, MOAccessImpl.ACCESS_READ_ONLY); //IP address of a device
		columns[counter++] = new MOColumn(counter, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY); // IP port of a device
		columns[counter++] = new MOColumn(counter, SMIConstants.SYNTAX_OBJECT_IDENTIFIER, MOAccessImpl.ACCESS_READ_ONLY); //OID to device table 
		OID devTableOid = new OID(".1.3.6.1.4.1.2006.5");
		DeviceTable devs = new DeviceTable(devTableOid, indx, columns);
		MOMutableTableModel model = (MOMutableTableModel) devs.getModel();
		Variable[] varsKyiv = new Variable[] {
				new OctetString("Kyiv power input"),
				new IpAddress("10.1.20.122"),
				new Gauge32(502),
				devTableOid.append(new OID("1"))
		};
		Variable[] varsDnepr = new Variable[] {
				new OctetString("Kyiv power input"),
				new IpAddress("10.144.20.122"),
				new Gauge32(502),
				devTableOid.append(new OID("2"))
		};
		model.addRow(new DefaultMOMutableRow2PC(new OID("1"), varsKyiv));
		model.addRow(new DefaultMOMutableRow2PC(new OID("1.2.3"), varsDnepr));
		devs.setVolatile(true);
		return devs;
	}
	

}
