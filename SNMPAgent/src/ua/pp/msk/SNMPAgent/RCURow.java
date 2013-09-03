package ua.pp.msk.SNMPAgent;

import org.snmp4j.agent.mo.DefaultMOMutableRow2PC;
import org.snmp4j.smi.Gauge32;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;

import ua.pp.msk.ModbusAnalyzer.RCUAnalyzer;
import ua.pp.msk.ModbusAnalyzer.RCUPacketFloat;

import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;

public class RCURow extends DefaultMOMutableRow2PC {

	private static int index4OID = 1;
	private boolean canUpdate = true;
	private RCUAnalyzer rcuAnalyzer = null;
	private RCUDevice rcuDev = null;
	private String ipAddress = null;
	private int port = 0;
	private short modbusDevice = 0;
	private long renewDuration = 15000;

	public RCURow(RCUDevice rcudev) {
		super(makeOID(), makeStatsArray(rcudev));
		//super(makeOID(rcudev), makeStatsArray(rcudev));
		rcuDev = rcudev;
		values = makeStatsArray(rcuDev);
		ipAddress = rcuDev.getIpAddress();
		port = rcuDev.getPort();
		modbusDevice = (short) rcuDev.getModbusDeviceNumber();
		rcuAnalyzer = rcuDev.getRCUAnalyzer();
		AutoUpdater autoRenew = new AutoUpdater(renewDuration);
		Thread willBeUpdated = new Thread(autoRenew);
		willBeUpdated.start();
	}

	/*
	private static OID makeOID(RCUDevice rcud) {
		String oid = "" + rcud.getCity();
		OID rowOID = new OID(oid).append(rcud.getModbusDeviceNumber());
		return rowOID;
	}*/
	
	private synchronized void  resetRCUAnalyzer(){
		rcuAnalyzer = null;
		rcuDev.resetRCUAnalyzer();
		rcuAnalyzer = rcuDev.getRCUAnalyzer();
	}
	
	private static OID makeOID(){
		return new OID().append(index4OID++);
	}

	private static Variable[] makeStatsArray(RCUDevice rcud) {
		int counter = 0;
		int[] stats = rcud.getRCUStats();
		Variable[] vars = new Variable[3 + stats.length];
		vars[counter++] = new OctetString(rcud.getDescription());
		vars[counter++] = new IpAddress(rcud.getIpAddress());
		vars[counter++] = new Gauge32(rcud.getPort());
		while (counter < vars.length) {
			vars[counter] = new Gauge32(stats[counter++ - 3]);
		}
		return vars;
	}

	private int[] getRCUPackStatsArray() throws Exception {
		Runnable cooldowner = new CoolDown(renewDuration-1);
		Thread willWait = new Thread(cooldowner);
		willWait.start();
		RCUPacketFloat rcuPack = rcuAnalyzer.askDevice();
		return rcuPack.getAllInteger();
	}

	private synchronized Variable[] getStatsArray() throws Exception {
		int counter = 3;
		Variable[] rowValues = values;
		int[] stats = getRCUPackStatsArray();
		while (counter < rowValues.length) {
			rowValues[counter] = new Gauge32(stats[counter++ - 3]);
		}
		return rowValues;
	}
	
	
	private synchronized void renewValues() {
		if (canUpdate) {
			try {
				values = getStatsArray();
			} catch (NullPointerException npe) {
				System.err.println("Cannot update unknown type device table");
			} catch (ModbusInitException mie) {
				System.err.println("Error: Could not initialize TCP Modbus Master Connection!");
				System.err.println("This row has inactual data " + rcuDev.getDescription());
			}
			catch (Exception e) {
				System.out.println("Could not get values. Trying to reinitialize RCU Analyzer");
				resetRCUAnalyzer();
				System.out.println(e.getMessage());
			}
		} /*else {
			System.out.println("Skipping updating this row");
		}*/
	}

	@Override
	public Variable getValue(int column) {
		//renewValues();
		return super.getValue(column);
	}

	class CoolDown implements Runnable {

		private long duration = 1000;

		public CoolDown(long time) {
			this.duration = time;
		}

		private void pleaseWait() {
			try {
				canUpdate = false;
				Thread.sleep(duration);
				canUpdate = true;
			} catch (InterruptedException ie) {
				System.out
						.println("Bruteforce guard was broken. Cool down has been interruped.");
			}
		}

		public void setDuration(long time) {
			this.duration = time;
		}

		public void run() {
			pleaseWait();
		}
	}

	class AutoUpdater implements Runnable {
		
		private long duration = 150000;
		
		public AutoUpdater(long time){
			duration  = time;
		}
		
		public void setDuration(long time){
			duration  = time;
		}
		
		private void makeUpdate(){
			try {
			while (true) {
				canUpdate = true;
				renewValues();
				
				Thread.sleep(duration);
			}
		} catch (InterruptedException ie) {
			System.err.println(rcuDev.getDescription() + "stops auto renewing");
		}
		}
		
		public void run(){
			makeUpdate();
		}
	}
}
