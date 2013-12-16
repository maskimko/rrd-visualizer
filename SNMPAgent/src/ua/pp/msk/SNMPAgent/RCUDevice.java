package ua.pp.msk.SNMPAgent;

import java.net.SocketException;

import ua.pp.msk.ModbusAnalyzer.RCUAnalyzer;
import ua.pp.msk.ModbusAnalyzer.RCUPacketFloat;
import ua.pp.msk.ModbusAnalyzer.RCUPacketGenerator;

import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;

public class RCUDevice {

	public static final int KYIV = 1;
	public static final int DNIPROPETROVSK = 2;
	public static final int DONETSK = 3;
	public static final int KHARKIV = 4;
	public static final int LVIV = 5;
	public static final int SIMFEROPOL = 6;
	public static final int ODESA = 7;
	private RCUAnalyzer rcuAnalyzer = null;
	private int city;
	private int port;
	private short modbusDeviceNumber;
	private String description;
	private String ipAddress;
	private RCUPacketFloat pack;
	private int reCounter = 0;

	public RCUDevice(int city, String descr, String ipAddress, int port,
			short modbusDevice, RCUPacketFloat pack) {
		this.city = city;
		this.description = descr;
		this.ipAddress = ipAddress;
		this.port = port;
		this.modbusDeviceNumber = modbusDevice;

		this.pack = pack;
		createRCUAnalyzer();
	}

	
	
	
	
	private synchronized void createRCUAnalyzer() {
		RCUAnalyzerCreator rcuac = new RCUAnalyzerCreator();
		Thread creationOfRCU = new Thread(rcuac);
		creationOfRCU.setName(description);
		System.out.println("Starting of " + getDescription()
				+ " RCUAnalyzer creation... ");
		creationOfRCU.start();
	
		Thread checkCreated = new Thread(new Runnable() {
			public void run() {
				long timeWaiting = 0;
				while (rcuAnalyzer == null) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						System.err
								.println("Method checker of RCUAnalyzer creation has been interrupted!");
						e.printStackTrace();
					}
					timeWaiting += 10;
				}
				System.out.println(getDescription()
						+ " RCUAnalyzer created in " + timeWaiting
						+ " milliseconds");
			}
		});
		checkCreated.start();
	}

	synchronized void resetRCUAnalyzer() {

		rcuAnalyzer = null;
		System.out.println("Try to recreate RCUAnalyzer");
		createRCUAnalyzer();
	}

	public RCUAnalyzer getRCUAnalyzer() {
		return this.rcuAnalyzer;
	}

	
	public synchronized RCUPacketFloat askDevice() throws SocketException, ModbusInitException, ErrorResponseException, ModbusTransportException{
		return rcuAnalyzer.askDevice();
	}
	
	
	public int getCity() {
		return city;
	}

	public String getDescription() {
		return description;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public int getPort() {
		return port;
	}

	public int getModbusDeviceNumber() {
		return modbusDeviceNumber;
	}

	public int[] getRCUStats() {
		return pack.getAllInteger();
	}

	public RCUPacketFloat getRCUPacket() {
		return pack;
	}

	private void describeDevice() {
		Thread descriptionThread = new Thread(new RCUDeviceDescriber());
		descriptionThread.start();
	}

	public static RCUDevice createBaseKyivRCUDevice(short modbusDeviceNumber) {

		RCUDevice dev = new RCUDevice(RCUDevice.KYIV, "Kyiv power input RCU ",
				"10.1.20.122", 502, modbusDeviceNumber,
				RCUPacketGenerator.getZeroPacket());
		dev.describeDevice();
		return dev;
	}

	public static RCUDevice createBaseDnipropetrovskRCUDevice(
			short modbusDeviceNumber) {
		RCUDevice dev = new RCUDevice(RCUDevice.DNIPROPETROVSK,
				"Dnipropetrovsk power input RCU device", "10.144.20.122", 502,
				modbusDeviceNumber, RCUPacketGenerator.getZeroPacket());
		dev.describeDevice();
		return dev;
	}

	public static RCUDevice createBaseDonetskRCUDevice(short modbusDeviceNumber) {
		RCUDevice dev = new RCUDevice(RCUDevice.DONETSK,
				"Donetsk power input RCU device", "10.176.20.122", 502,
				modbusDeviceNumber, RCUPacketGenerator.getZeroPacket());
		dev.describeDevice();
		return dev;
	}

	public static RCUDevice createBaseKharkivRCUDevice(short modbusDeviceNumber) {
		RCUDevice dev = new RCUDevice(RCUDevice.KHARKIV,
				"Kharkiv power input RCU device", "10.160.20.122", 502,
				modbusDeviceNumber, RCUPacketGenerator.getZeroPacket());
		dev.describeDevice();
		return dev;
	}

	public static RCUDevice createBaseLvivRCUDevice(short modbusDeviceNumber) {
		RCUDevice dev = new RCUDevice(RCUDevice.LVIV,
				"Lviv power input RCU device", "10.224.20.122", 502,
				modbusDeviceNumber, RCUPacketGenerator.getZeroPacket());

		return dev;
	}

	public static RCUDevice createBaseSimferopolRCUDevice(
			short modbusDeviceNumber) {
		RCUDevice dev = new RCUDevice(RCUDevice.SIMFEROPOL,
				"Simferopol power input RCU device", "10.192.20.122", 502,
				modbusDeviceNumber, RCUPacketGenerator.getZeroPacket());
		dev.describeDevice();
		return dev;
	}

	public static RCUDevice createBaseOdesaRCUDevice(short modbusDeviceNumber) {
		RCUDevice dev = new RCUDevice(RCUDevice.ODESA,
				"Odesa power input RCU device", "10.208.20.122", 502,
				modbusDeviceNumber, RCUPacketGenerator.getZeroPacket());
		dev.describeDevice();
		return dev;
	}

	class RCUDeviceDescriber implements Runnable {


		public void run() {
			try {
			
			while (rcuAnalyzer == null ){
			
					Thread.sleep(5000);
				
				
			}
			description += " (Modbus device "
					+ modbusDeviceNumber
					+ " Model: "
					+ RCUAnalyzer.getDeviceTypeAsString(rcuAnalyzer.getDeviceType()) + ")";
			} catch (InterruptedException ie){
				System.err.println("RCU device describer has been interrupted " + ie.getMessage());
			} catch (NullPointerException npe ){
				System.err.println("RCU device describer failed with  " );
				npe.printStackTrace();
			}
		}
	}

	class RCUAnalyzerCreator implements Runnable {

		private static final long below5Duration = 30000;
		private static final long between5and10 = 150000;
		private static final long above10 = 300000;

		private void recreateRCUAnalyzer() throws InterruptedException {

			if (rcuAnalyzer != null) {
	
				reCounter = 0;
			}
			while (rcuAnalyzer == null) {

				try {
					if (reCounter != 0) {
						if (reCounter < 5) {
							Thread.sleep(below5Duration);
						} else if (reCounter < 10) {
							Thread.sleep(between5and10);
						} else {
							Thread.sleep(above10);
						}
					}
					reCounter++;
					makeRCUAnalyzer();
				} catch (NullPointerException npe) {
					rcuAnalyzer = null;
					npe.printStackTrace();
				}
			}
		}

		private  void makeRCUAnalyzer() throws InterruptedException,
				NullPointerException {
			try {
				// rcuAnalyzer = RCUAnalyzer.getRCUDevice(ipAddress, port,
				// modbusDeviceNumber);
				rcuAnalyzer = RCUAnalyzer.getRCUDevice(ipAddress, port,
						modbusDeviceNumber, true);
			} catch (ModbusInitException exc) {
				System.err.println("Error: Cannot init Modbus TCP session\n"
						+ exc.getMessage());
				System.err.println("Device " + ipAddress + ":" + port + "/"
						+ modbusDeviceNumber);
				rcuAnalyzer = null;
			} catch (ModbusTransportException mte) {
				System.err
						.println("Error: Cannot create TCP session to " +getDescription()+ "\nCheck you network connection\n");
				mte.printStackTrace();
				System.err.println("Device " + ipAddress + ":" + port + "/"
						+ modbusDeviceNumber);
				rcuAnalyzer = null;
			}
		}



		public void run() {
			try {
				recreateRCUAnalyzer();
			} catch (InterruptedException ie) {
				System.err.println("Recreation of RCUAnalyzer for device "
						+ getDescription() + " failed");
				ie.printStackTrace();
			}
		}
	}

}
