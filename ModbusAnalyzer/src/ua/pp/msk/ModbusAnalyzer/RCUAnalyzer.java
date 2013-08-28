package ua.pp.msk.ModbusAnalyzer;

import java.util.ArrayList;

import net.sourceforge.jmodbus.ModbusTCPMaster;

import com.serotonin.modbus4j.ModbusLocator;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;

public abstract class RCUAnalyzer implements RCUAnalyzerInterface {

	protected ModbusTCPMaster mbTCP = null;
	protected short device = 1;
	protected short deviceType;
	protected TcpMaster tm = null;

	
	public RCUAnalyzer(String host, int port, short device, short type){
		boolean keepAlive = false;
		IpParameters ipParam = new IpParameters();
		ipParam.setHost(host);
		ipParam.setPort(port);
		ipParam.setEncapsulated(false);
		tm = new TcpMaster(ipParam, keepAlive);
		this.device = device;
		this.deviceType = type;
	}
	/*
	public RCUAnalyzer(String host, int port, short device, short type) {
		this.mbTCP = new ModbusTCPMaster(host, port);
		this.device = device;
		this.deviceType = type;
		if (!checkDevType()) {
			System.err
					.println("Warning: There is already implemented class for this device type");
		}

	}
	*/
	
	protected RCUAnalyzer(TcpMaster tm, short device, short type){
		this.tm = tm;
		this.device = device;
		this.deviceType = type;
	}
	
	/*
	protected RCUAnalyzer(ModbusTCPMaster mtm, short device, short deviceType) {
		this.mbTCP = mtm;
		this.device = device;
		this.deviceType = deviceType;
	}
	*/
	
	
	public static RCUAnalyzer getRCUDevice(String hostname, int port,
			short device) throws Exception {
		RCUAnalyzer ra = null;

		ModbusTCPMaster mtm = new ModbusTCPMaster(hostname, port);
		short type = determineRCUDeviceType(mtm, device);
		switch (type) {
		case PM500:
			ra = new RCUPM500Analyzer(mtm, device);
			break;
		case PM700:
			ra = new RCUPM700Analyzer(mtm, device);
			break;
		default:
			throw new Exception(
					"Error: Cannot create a RCUAnalyzer.\nUnknown type of device!");
		}

		return ra;
	}

	private boolean checkDevType() {
		boolean know = false;
		switch (this.deviceType) {
		case PM500:
			know = true;
			break;
		case PM700:
			know = true;
			break;
		}
		return know;
	}

	
	public static short determineRCUDeviceType(TcpMaster tm, short devnum) throws ErrorResponseException, ModbusTransportException, Exception {
		short dt = -1;
		boolean isPM500 = false;
		boolean isPM700 = false;
		ModbusLocator devTypePM500Locator = new ModbusLocator(devnum, RegisterRange.HOLDING_REGISTER, 64646, DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator devTypePM700Locator = new ModbusLocator(devnum, RegisterRange.HOLDING_REGISTER, 7003, DataType.TWO_BYTE_INT_UNSIGNED);
		int someValue = (int) tm.getValue(devTypePM500Locator);
		int someValue2 = (int) tm.getValue(devTypePM700Locator);
		System.out.println("I got value for PM500 " + someValue);
		System.out.println("I got value for PM700 " + someValue2);
		
		return dt;
	}
	/*
	public static short determineRCUDeviceType(ModbusTCPMaster mbtcp,
			short devnum) throws Exception {
		short dt = -1;
		boolean isPM500 = false;
		boolean isPM700 = false;
		// Trying to ask PM500 device
		int[] devPM500check = new int[4];
		mbtcp.readMultipleRegisters(devnum, 64646, devPM500check.length, 0,
				devPM500check);
		if (devPM500check[0] == 256 && devPM500check[1] == 50980
				&& devPM500check[2] == 0 && devPM500check[3] == 1) {
			isPM500 = true;
		}
		int[] devPM700check = new int[2];
		mbtcp.readMultipleRegisters(devnum, 7003, devPM700check.length, 0,
				devPM700check);
		if (devPM700check[0] == 15165) {
			isPM700 = true;
		}
		if (isPM500) {
			dt = RCUAnalyzer.PM500;
			System.out.println("Modbus device number devnum is PM500");
		} else if (isPM700) {
			dt = RCUAnalyzer.PM700;
			System.out.println("Modbus device number devnum is PM700");
		} else {
			throw new Exception("I cannot determine device type!");
		}
		return dt;

	}
	
	*/
	

	protected static String showSwitcher(int counter) {
		String key = null;

		switch (counter) {
		case 0:
			key = "I1 = ";
			break;
		case 1:
			key = "I2 = ";
			break;
		case 2:
			key = "I3 = ";
			break;
		case 3:
			key = "IN = ";
			break;
		case 4:
			key = "U12 = ";
			break;
		case 5:
			key = "U23 = ";
			break;
		case 6:
			key = "U32 = ";
			break;
		case 7:
			key = "U1N = ";
			break;
		case 8:
			key = "U2N = ";
			break;
		case 9:
			key = "U3N = ";
			break;
		case 10:
			key = "Freq = ";
			break;
		case 11:
			key = "P = ";
			break;
		case 12:
			key = "Q = ";
			break;
		case 13:
			key = "S = ";
			break;
		case 14:
			key = "Power Factor = ";
			break;
		}
		return key;
	}

	private static void showOutputFloat(ArrayList<Float> output) {

		int counter = 0;

		for (float value : output) {

			if (showSwitcher(counter) != null) {
				System.out.println(showSwitcher(counter) + value);
			}
			counter++;

		}
	}

	private static void showOutputInteger(ArrayList<Integer> output) {
		int counter = 0;
		for (int value : output) {

			if (showSwitcher(counter) != null) {
				System.out.println(showSwitcher(counter) + value);
			}
			counter++;

		}
	}

	private static void showOutput(int[] outputArray) {
		ArrayList<Integer> toShow = new ArrayList<Integer>();
		for (int i = 0; i < outputArray.length; i++) {
			toShow.add(outputArray[i]);
		}
		showOutputInteger(toShow);
	}

	private static void showOutput(float[] outputArray) {
		ArrayList<Float> toShow = new ArrayList<Float>();
		for (int i = 0; i < outputArray.length; i++) {
			toShow.add(outputArray[i]);
		}
		showOutputFloat(toShow);
	}

	protected static boolean fallChecker(ArrayList<Integer> arr) {
		boolean isFalled = true;
		int fallVal = 256;
		for (int val : arr) {
			if (val != fallVal) {
				return false;
			}
		}
		return isFalled;
	}

	public RCUPacket askDevice() throws Exception {
		return this.askDevice(mbTCP, this.device);
	}

	/**
	 * 
	 * @param host
	 *            Provides a host ip address
	 * @param port
	 *            Provides a host ip port
	 * @param device
	 *            Provides a modbus device number
	 * 
	 * @return Returns object of RCUPacket class with all gathered information
	 *         about polled device
	 */
	public abstract RCUPacket askDevice(ModbusTCPMaster mtm, short device)
			throws Exception;

	public void stop() {
		if (mbTCP != null) {

			mbTCP = null;
		}
	}

	// TODO
	// Rewrite main method !

	public static void main(String[] args) {
		String hostname = null;
		int port = 0;
		short dev = 0, type = -1;
		if (args.length != 4) {
			System.out
					.println("Usage: RCUAnalyzer <hostname> [port] [modbus device] [rcu device type]");
			return;
		} else {
			try {
				hostname = args[0];
				port = Integer.parseInt(args[1]);

				dev = Short.parseShort(args[2]);
				type = Short.parseShort(args[3]);
				ModbusTCPMaster mbMstr = new ModbusTCPMaster(hostname, port);
				RCUAnalyzer rcuan = new RCUAnalyzer(hostname, port, dev, type) {

					private boolean isAsked = false;
					short devT = -1;

					public RCUPacket askDevice(ModbusTCPMaster mbMstr, short dev)
							throws Exception {
						RCUPacket rcuPack = null;

						if (!isAsked) {
							devT = determineRCUDeviceType(mbMstr, dev);

							if (devT != this.deviceType) {
								throw new Exception(
										"Error: Looks like you provide wrong device type");
							} else {
								isAsked = true;
							}
						}
						switch (devT) {
						case 0:
							RCUPM500Analyzer rcuPM500An = new RCUPM500Analyzer(
									this.mbTCP, dev);
							rcuPack = rcuPM500An.askDevice();
							break;
						case 1:
							RCUPM700Analyzer rcuPM700An = new RCUPM700Analyzer(
									this.mbTCP, dev);
							rcuPack = rcuPM700An.askDevice();
							break;
						default:
							throw new Exception(
									"Error: Unfortunately I cannot handle this device type!");
						}
						return rcuPack;
					}
				};

				while (true) {
					RCUPacket rcup = rcuan.askDevice();
					System.out.println(rcup);
					// showOutput(rcup.getAll());

					Thread.sleep(1000);
				}
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("from host " + hostname + ":" + port
						+ " device " + dev);
			}
		}
	}
}
