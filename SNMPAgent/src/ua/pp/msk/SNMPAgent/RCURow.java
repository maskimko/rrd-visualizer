package ua.pp.msk.SNMPAgent;

import org.snmp4j.agent.mo.DefaultMOMutableRow2PC;
import org.snmp4j.smi.Gauge32;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;

import ua.pp.msk.ModbusAnalyzer.RCUAnalyzer;
import ua.pp.msk.ModbusAnalyzer.RCUPacket;

public class RCURow extends DefaultMOMutableRow2PC {
	
	private boolean canUpdate = true;
	private RCUAnalyzer rcuAnalyzer = null;
	private RCUDevice rcuDev = null;
	private Variable[] rowValues = new Variable[RCUPacket.units + 3];
	private String ipAddress = null;
	private int port = 0;
	private short modbusDevice = 0;
	
	public RCURow(RCUDevice rcudev){
		super(makeOID(rcudev), makeStatsArray(rcudev));
		rcuDev = rcudev;
		rowValues = makeStatsArray(rcuDev);
		ipAddress = rcuDev.getIpAddress();
		port = rcuDev.getPort();
		modbusDevice =(short) rcuDev.getModbusDeviceNumber();
		rcuAnalyzer = new RCUAnalyzer(ipAddress, port, modbusDevice);
	}
	
	private static OID makeOID(RCUDevice rcud){
		String oid = "" + rcud.getCity();
		OID rowOID = new OID(oid).append(rcud.getModbusDeviceNumber());
		return rowOID;
	}
	
	private static Variable[] makeStatsArray(RCUDevice rcud){
		int counter = 0;
		int[] stats = rcud.getRCUStats();
		Variable[] vars = new Variable[3 + stats.length];
		vars[counter++] = new OctetString(rcud.getDescription());
		vars[counter++] = new IpAddress(rcud.getIpAddress());
		vars[counter++]	= new Gauge32(rcud.getPort());
		while (counter < vars.length) {
			vars[counter] = new Gauge32(stats[counter++ -3]);
		}
		return vars;
	}

	
	
	/*
	 * int counter = 0;
		int[] stats = rcud.getRCUStats();
		Variable[] vars = new Variable[3 + RCUPacket.units]; 
		vars[counter++] = new OctetString(rcud.getDescription());
		vars[counter++] = new IpAddress(rcud.getIpAddress());
		vars[counter++]	= new Gauge32(rcud.getPort());
		while (counter < vars.length) {
			vars[counter] = new Gauge32(stats[counter++ - 3]);
		}
		OID devOID = new OID("" + rcud.getCity()).append(rcud.getModbusDeviceNumber());
	 */
}
