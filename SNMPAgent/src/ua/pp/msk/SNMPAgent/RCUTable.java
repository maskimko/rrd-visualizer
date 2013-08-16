package ua.pp.msk.SNMPAgent;
import org.snmp4j.agent.mo.DefaultMOMutableRow2PC;
import org.snmp4j.agent.mo.DefaultMOTable;
import org.snmp4j.agent.mo.MOAccessImpl;
import org.snmp4j.agent.mo.MOColumn;
import org.snmp4j.agent.mo.MOMutableTableModel;
import org.snmp4j.agent.mo.MOTableIndex;
import org.snmp4j.agent.mo.MOTableSubIndex;
import org.snmp4j.smi.Gauge32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.SMIConstants;
import org.snmp4j.smi.Variable;

import ua.pp.msk.ModbusAnalyzer.RCUPacket;


public class RCUTable  {

	protected static DefaultMOTable createStaticStatsTable(){
		MOTableSubIndex[] subIndexes = new MOTableSubIndex[] { new MOTableSubIndex(SMIConstants.SYNTAX_GAUGE32)};
		MOTableIndex indexDef = new MOTableIndex(subIndexes, false);
		MOColumn[] columns = new MOColumn[RCUPacket.units];
		int c = 0;
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32, MOAccessImpl.ACCESS_READ_ONLY);
		DefaultMOTable deviceStats = new DefaultMOTable(new OID(".1.3.6.1.4.1.2006.3.1"), indexDef, columns);
		MOMutableTableModel model = (MOMutableTableModel) deviceStats.getModel();
		Variable[] testRow = new Variable[] {
			new Gauge32(100),
			new Gauge32(100),
			new Gauge32(100),
			new Gauge32(100),
			new Gauge32(100),
			new Gauge32(100),
			new Gauge32(100),
			new Gauge32(100),
			new Gauge32(100),
			new Gauge32(100),
			new Gauge32(100),
			new Gauge32(100),
			new Gauge32(100),
			new Gauge32(100),
			new Gauge32(100),
		};
		model.addRow(new DefaultMOMutableRow2PC(new OID("1"), testRow));
		deviceStats.setVolatile(true);
		return deviceStats;
	}

}
