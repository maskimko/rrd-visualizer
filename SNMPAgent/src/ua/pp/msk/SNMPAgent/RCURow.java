package ua.pp.msk.SNMPAgent;

import java.net.SocketException;

import org.snmp4j.agent.mo.DefaultMOMutableRow2PC;
import org.snmp4j.smi.Gauge32;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;

import ua.pp.msk.ModbusAnalyzer.RCUAnalyzer;
import ua.pp.msk.ModbusAnalyzer.RCUPacketFloat;

import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;

public class RCURow extends DefaultMOMutableRow2PC {

	private static int index4OID = 1;
	private boolean canUpdate = true;

	private RCUDevice rcuDev = null;
	private String ipAddress = null;
	private int port = 0;
	private short modbusDevice = 0;
	private long renewDuration = 30000;

	public RCURow(RCUDevice rcudev) {
		super(makeOID(), makeStatsArray(rcudev));
		// super(makeOID(rcudev), makeStatsArray(rcudev));
		rcuDev = rcudev;
		values = makeStatsArray(rcuDev);
		ipAddress = rcuDev.getIpAddress();
		port = rcuDev.getPort();
		modbusDevice = (short) rcuDev.getModbusDeviceNumber();
		
		AutoUpdater autoRenew = new AutoUpdater(renewDuration);
		Thread willBeUpdated = new Thread(autoRenew);
		willBeUpdated.start();
	}

	/*
	 * private static OID makeOID(RCUDevice rcud) { String oid = "" +
	 * rcud.getCity(); OID rowOID = new
	 * OID(oid).append(rcud.getModbusDeviceNumber()); return rowOID; }
	 */

	

	private static OID makeOID() {
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

	private int[] getRCUPackStatsArray() throws ModbusTransportException,
			ErrorResponseException, ModbusInitException, SocketException {
		long waitingTime = 0;
		Runnable cooldowner = new CoolDown(renewDuration - 1);
		Thread willWait = new Thread(cooldowner);
		willWait.start();
		while (rcuDev.getRCUAnalyzer() == null) {
			try {
				Thread.sleep(100);
				waitingTime += 100;
				if (waitingTime == 60000) {
					System.out.println("1 minute waiting for RCUAnalyzer for "
							+ rcuDev.getDescription());
				} else if (waitingTime == 300000) {
					System.err.println(rcuDev.getDescription()
							+ " is not responcing for at least 5 minutes");
					waitingTime = 0;
				}
			} catch (InterruptedException ie) {
				System.err.println("Waiting for RCUAnalyzer for "
						+ rcuDev.getDescription() + " has been interrupted");
			}
		}
		//TODO add askdevice method to RCUDevice
		RCUPacketFloat rcuPack = rcuDev.getRCUAnalyzer().askDevice();
		return rcuPack.getAllInteger();
	}

	private synchronized Variable[] getStatsArray()
			throws ModbusTransportException, ErrorResponseException,
			ModbusInitException, SocketException {
		int counter = 3;
		Variable[] rowValues = values;
		int[] stats = getRCUPackStatsArray();
		while (counter < rowValues.length) {
			if (counter == rowValues.length - 3) {
				rowValues[counter] = new Integer32(stats[counter++ - 3]);
			}
			rowValues[counter] = new Gauge32(stats[counter++ - 3]);
		}
		return rowValues;
	}

	private synchronized void renewValues() {
		if (canUpdate) {
			try {
				values = getStatsArray();
			} catch (NullPointerException npe) {
				System.err.println("Cannot update " + rcuDev.getDescription()
						+ " table");
			} catch (SocketException se) {
				System.err.println("Error: Socket error on "
						+ rcuDev.getDescription());
				System.err.println(se.getMessage());
				rcuDev.resetRCUAnalyzer();
			} catch (ModbusInitException mie) {
				System.err
						.println("Error: Could not initialize TCP Modbus Master Connection!");
				System.err.println("This row has inactual data "
						+ rcuDev.getDescription());
			} catch (ModbusTransportException e) {
				System.out
						.println("Failed to handle modbus transport. Trying to reinitialize RCU Analyzer");
				rcuDev.resetRCUAnalyzer();
				System.out.println(e.getMessage());
			} catch (ErrorResponseException ere) {
				System.err
						.println("Got an error response! Somethin is going wrong! Trying to reconnect!");
				rcuDev.resetRCUAnalyzer();
				System.out.println(ere.getMessage());
			}
		} /*
		 * else { System.out.println("Skipping updating this row"); }
		 */
	}

	@Override
	public Variable getValue(int column) {
		// renewValues();
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

		public AutoUpdater(long time) {
			duration = time;
		}

		public void setDuration(long time) {
			duration = time;
		}

		private void makeUpdate() throws InterruptedException {

			while (true) {
				canUpdate = true;
				renewValues();

				Thread.sleep(duration);
			}

		}

		public void run() {
			try {
				Thread.sleep(5000);
				makeUpdate();
			} catch (InterruptedException ie) {
				System.err.println(rcuDev.getDescription()
						+ "stops auto renewing");
			}
		}
	}
}
