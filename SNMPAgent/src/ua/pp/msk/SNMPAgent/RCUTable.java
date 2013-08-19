package ua.pp.msk.SNMPAgent;

import org.snmp4j.agent.MOScope;
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

import ua.pp.msk.ModbusAnalyzer.RCUAnalyzer;
import ua.pp.msk.ModbusAnalyzer.RCUPacket;

public class RCUTable extends DefaultMOTable {

	private static boolean canUpdate = true;
	RCUAnalyzer rcua = null;
	
	public RCUTable(OID oid, MOTableIndex mti, MOColumn[] cols, RCUAnalyzer rcua) {
		super(oid, mti, cols);
		this.rcua = rcua;
	}

	private static Variable[] toVar(int[] array) {
		Variable[] varar = new Variable[array.length];
		for (int i = 0; i < varar.length; i++) {
			varar[i] = new Gauge32(array[i]);
		}
		return varar;
	}

	/*
	 * public void update(MOScope updateScope){ RCUPacket rcup =
	 * RCUPacketGenerator.genPacket(); Variable[] vars = toVar(rcup.getAll());
	 * MOMutableTableModel model = (MOMutableTableModel) this.getModel();
	 * model.clear(); model.addRow(new DefaultMOMutableRow2PC(new OID("1"),
	 * vars)); super.update(updateScope); }
	 */

	public void update(MOScope updateScope) {
		if (canUpdate) {
			Runnable judje = new UpdateJudje();
			Thread updThread = new Thread(judje);
			updThread.start();
			
			try {
				RCUPacket rcup = rcua.askDevice();
				Variable[] vars = toVar(rcup.getAll());
				MOMutableTableModel model = (MOMutableTableModel) this
						.getModel();
				synchronized (model) {
					model.clear();
					model.addRow(new DefaultMOMutableRow2PC(new OID("1"), vars));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		super.update(updateScope);
	}

	protected static RCUTable createStaticStatsTable() {
		
		MOTableSubIndex[] subIndexes = new MOTableSubIndex[] { new MOTableSubIndex(
				SMIConstants.SYNTAX_GAUGE32) };
		MOTableIndex indexDef = new MOTableIndex(subIndexes, false);
		MOColumn[] columns = new MOColumn[RCUPacket.units];
		int c = 0;
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY);
		RCUTable deviceStats = new RCUTable(new OID(".1.3.6.1.4.1.2006.3.1"),
				indexDef, columns,  new RCUAnalyzer("10.192.20.122", 502, (short) 2));
		MOMutableTableModel model = (MOMutableTableModel) deviceStats
				.getModel();
		Variable[] testRow = new Variable[] { new Gauge32(100),
				new Gauge32(100), new Gauge32(100), new Gauge32(100),
				new Gauge32(100), new Gauge32(100), new Gauge32(100),
				new Gauge32(100), new Gauge32(100), new Gauge32(100),
				new Gauge32(100), new Gauge32(100), new Gauge32(100),
				new Gauge32(100), new Gauge32(100), };
		model.addRow(new DefaultMOMutableRow2PC(new OID("1"), testRow));
		deviceStats.setVolatile(true);
		return deviceStats;
	}

	class UpdateJudje implements Runnable {

		public void run() {
			if (canUpdate) {
				canUpdate = false;
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ie) {
				}
				canUpdate = true;
			}
		}
	}

}
