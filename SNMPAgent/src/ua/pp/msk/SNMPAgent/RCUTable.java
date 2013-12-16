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
import ua.pp.msk.ModbusAnalyzer.RCUPacketFloat;

public class RCUTable extends DefaultMOTable {

	public RCUTable(OID oid, MOTableIndex mti, MOColumn[] cols) {
		super(oid, mti, cols);
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

	/*
	 * public void update(MOScope updateScope) { if (canUpdate) { Runnable judje
	 * = new UpdateJudje(); Thread updThread = new Thread(judje);
	 * updThread.start();
	 * 
	 * try { RCUPacket rcup = rcua.askDevice(); Variable[] vars =
	 * toVar(rcup.getAll()); MOMutableTableModel model = (MOMutableTableModel)
	 * this .getModel(); synchronized (model) { model.clear(); model.addRow(new
	 * DefaultMOMutableRow2PC(new OID("1"), vars)); } } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * super.update(updateScope); }
	 */

	protected static RCUTable createStaticStatsTable() {

		MOTableSubIndex[] subIndexes = new MOTableSubIndex[] { new MOTableSubIndex(
				SMIConstants.SYNTAX_GAUGE32) };
		MOTableIndex indexDef = new MOTableIndex(subIndexes, false);
		MOColumn[] columns = new MOColumn[RCUPacketFloat.units + 3];
		int c = 0;
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_OCTET_STRING,
				MOAccessImpl.ACCESS_READ_ONLY); // Description
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_IPADDRESS,
				MOAccessImpl.ACCESS_READ_ONLY); // IP address of a device
		columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
				MOAccessImpl.ACCESS_READ_ONLY); // IP port of a device
		while (c < columns.length) {
			if (c == columns.length - 3) {
				columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_INTEGER32,
						MOAccessImpl.ACCESS_READ_ONLY);
			} else {
				columns[c++] = new MOColumn(c, SMIConstants.SYNTAX_GAUGE32,
						MOAccessImpl.ACCESS_READ_ONLY);
			}
		}

		RCUTable deviceStats = new RCUTable(new OID(".1.3.6.1.4.1.2006.1.1.1"),
				indexDef, columns);
		MOMutableTableModel model = (MOMutableTableModel) deviceStats
				.getModel();
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseKyivRCUDevice((short) 5));
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseKyivRCUDevice((short) 4));
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseKyivRCUDevice((short) 3));
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseKyivRCUDevice((short) 2));
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseDnipropetrovskRCUDevice((short) 2));
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseDnipropetrovskRCUDevice((short) 3));
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseDonetskRCUDevice((short) 2));
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseDonetskRCUDevice((short) 3));
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseKharkivRCUDevice((short) 2));
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseKharkivRCUDevice((short) 3));
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseLvivRCUDevice((short) 2));
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseLvivRCUDevice((short) 3));
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseSimferopolRCUDevice((short) 2));
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseSimferopolRCUDevice((short) 3));
		RCUModelFactory.addRCUDeviceToModel(model,
				RCUDevice.createBaseOdesaRCUDevice((short) 2));
		
		deviceStats.setVolatile(true);
		return deviceStats;
	}

	/*
	 * class UpdateJudje implements Runnable {
	 * 
	 * public void run() { if (canUpdate) { canUpdate = false; try {
	 * Thread.sleep(5000); } catch (InterruptedException ie) { } canUpdate =
	 * true; } } }
	 */
}
