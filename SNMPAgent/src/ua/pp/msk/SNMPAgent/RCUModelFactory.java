package ua.pp.msk.SNMPAgent;

import org.snmp4j.agent.mo.DefaultMOMutableRow2PC;
import org.snmp4j.agent.mo.MOMutableTableModel;
import org.snmp4j.smi.Gauge32;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;

import ua.pp.msk.ModbusAnalyzer.RCUPacket;

public class RCUModelFactory {
	
	public static void addRCUDeviceToModel(MOMutableTableModel mmtm, RCUDevice rcud) {
		
		mmtm.addRow(new RCURow(rcud));
	}
	
	public static void fillRCUDeviceModel(RCUTable table, RCUDevice[] rcudevs) {
		MOMutableTableModel model = (MOMutableTableModel) table.getModel();
		for (int i = 0; i < rcudevs.length; i++){
			addRCUDeviceToModel(model, rcudevs[i]);
		}
	}

}
