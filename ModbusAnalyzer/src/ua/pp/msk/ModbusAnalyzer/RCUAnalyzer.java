package ua.pp.msk.ModbusAnalyzer;

import java.net.SocketException;
import java.util.ArrayList;

import com.serotonin.modbus4j.ModbusLocator;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;

public abstract class RCUAnalyzer implements RCUAnalyzerInterface {
	protected String ip = null;
	protected int port = 0;
	protected short device = 1;
	protected short deviceType = -1;
	protected TcpMaster tm = null;

	/**
	 * @param host
	 *            Modbus Slave host ip address
	 * @param port
	 *            Modbus slave host tcp port
	 * @param device
	 *            Number of the device on modbus bus
	 * @param type
	 *            Type of the device (eg. PM500, PM700, PM 1200)
	 * @throws ModbusInitException 
	 */
	public RCUAnalyzer(String host, int port, short device, short type) throws ModbusInitException {
		this(host, port, device, type, true);
	}

	// TODO Document this class
	/**
	 * @param host
	 *            Modbus Slave host ip address
	 * @param port
	 *            Modbus slave host tcp port
	 * @param device
	 *            Number of the device on modbus bus
	 * @param type
	 *            Type of the device (eg. PM500, PM700, PM 1200)
	 * @param keepAlive
	 *            keep TCP connection alive
	 * @throws ModbusInitException 
	 */
	public RCUAnalyzer(String host, int port, short device, short type,
			boolean keepAlive) throws ModbusInitException {
		this.ip = host;
		this.port = port;
		if (TcpMasterModel.containsConnection(host, port, keepAlive)) {
			tm = TcpMasterModel.getConnection(host);
		} else {
			TcpMasterModel.setConnection(host, port, keepAlive);
			this.tm = TcpMasterModel.getConnection(host);
			this.device = device;
			this.deviceType = type;
		}
	}

	/**
	 * @param tm
	 *            TcpMaster object, which handles connection
	 * @param device
	 *            Number of the device on modbus bus
	 * @param type
	 *            Type of the device (eg. PM500, PM700, PM 1200)
	 */


	public short getModbusDeviceNumber() {
		return this.device;
	}

	public String getIpAddress() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public short getDeviceType() {
		return this.deviceType;
	}

	public static String getDeviceTypeAsString(short deviceType) {
		String dt = null;
		switch (deviceType) {
		case PM500:
			dt = "PM500";
			break;
		case PM700:
			dt = "PM700";
			break;
		case PM1200:
			dt = "PM1200";
		default:
			dt = "Unknown";
			break;
		}
		return dt;
	}

	/**
	 * @param hostname
	 *            Modbus Slave host ip address
	 * @param port
	 *            Modbus slave host tcp port
	 * @param device
	 *            Number of the device on modbus bus
	 * @param keepAlive
	 *            keep TCP connection alive
	 * @return Class RCUAnalyzer
	 * @throws ModbusInitException
	 *             Cannot init modbus section
	 * @throws InterruptedException
	 *             Modbus session was interrupted
	 * @throws ModbusTransportException
	 *             tcp errors
	 * @throws NullPointerException
	 *             Cannot create a RCUAnalyzer. Unknown type of device!
	 */
	public static RCUAnalyzer getRCUDevice(String hostname, int port,
			short device, boolean keepAlive) throws ModbusInitException,
			InterruptedException, ModbusTransportException,
			NullPointerException {
		RCUAnalyzer ra = null;
		TcpMaster mtm = null;
		System.out.println("Checking whether connection exists...");
		if (TcpMasterModel.containsConnection(hostname, port, keepAlive)){
			mtm = TcpMasterModel.getConnection(hostname);
			System.out.println("Existed connection has been aquired");
		} else {
			System.out.print("Creating new connection... ");
			TcpMasterModel.setConnection(hostname, port, keepAlive);
			mtm = TcpMasterModel.getConnection(hostname);
			System.out.println(" .created.");
		}
		
		if (!mtm.isInitialized()) mtm.init();
		
		short type = determineRCUDeviceType(hostname, port, device);
		switch (type) {
		case PM500:
			ra = new RCUPM500Analyzer(hostname, port, device);
			break;
		case PM700:
			ra = new RCUPM700Analyzer(hostname, port, device);
			break;
		case PM1200:
			ra = new RCUPM1200Analyzer(hostname, port, device);
			break;
		default:
			throw new NullPointerException(
					"Error: Cannot create a RCUAnalyzer.\nUnknown type of device!");
		}

		return ra;
	}

	/**
	 * @param hostname
	 *            Modbus Slave host ip address
	 * @param port
	 *            Modbus slave host tcp port
	 * @param device
	 *            Number of the device on modbus bus
	 * @return Class RCUAnalyzer
	 * @throws ModbusInitException
	 *             Cannot init modbus section
	 * @throws InterruptedException
	 *             Modbus session was interrupted
	 * @throws ModbusTransportException
	 *             tcp errors
	 * @throws NullPointerException
	 *             Cannot create a RCUAnalyzer. Unknown type of device!
	 */
	public static RCUAnalyzer getRCUDevice(String hostname, int port,
			short device) throws ModbusInitException, InterruptedException,
			ModbusTransportException, NullPointerException {
		return getRCUDevice(hostname, port, device, true);
	}

	

	public static short determineRCUDeviceType(String host, int port, short devnum)
			throws ModbusTransportException, InterruptedException,
			ModbusInitException {
		short dt = -1;
		boolean isPM500 = false;
		boolean isPM700 = false;
		boolean isPM1200 = false;
		
		TcpMaster tm = null;
		
		
		if (TcpMasterModel.containsConnection(host, port, true)){
			tm = TcpMasterModel.getConnection(host);
		} else {
			TcpMasterModel.setConnection(host, port, true);
			tm = TcpMasterModel.getConnection(host);
		}
		
		
		try {
			ModbusLocator devTypePM500Locator = new ModbusLocator(devnum,
					RegisterRange.HOLDING_REGISTER, 64647,
					DataType.TWO_BYTE_INT_UNSIGNED);
			if (!tm.isInitialized()) {
				tm.init();
			}

			tm.getValue(devTypePM500Locator);
			isPM500 = true;
		} catch (ErrorResponseException erePM500) {

		}
		try {
			ModbusLocator devTypePM700Locator = new ModbusLocator(devnum,
					RegisterRange.HOLDING_REGISTER, 7003,
					DataType.TWO_BYTE_INT_UNSIGNED);
			tm.getValue(devTypePM700Locator);
			isPM700 = true;
		} catch (ErrorResponseException erePM700) {

		}

		try {
			// ModbusLocator devTypePM1200Locator = new ModbusLocator(devnum,
			// RegisterRange.HOLDING_REGISTER, 3914,
			// DataType.FOUR_BYTE_FLOAT_SWAPPED);
			ModbusLocator devTypePM1200Locator = new ModbusLocator(devnum,
					RegisterRange.HOLDING_REGISTER, 3914,
					DataType.FOUR_BYTE_FLOAT_SWAPPED);

			float idPM1200 = (float) tm.getValue(devTypePM1200Locator);
			// System.out.println("PM1200 id " + idPM1200);
			isPM1200 = true;
		} catch (ErrorResponseException erePM1200) {

		}

		if (isPM500) {
			dt = PM500;
		} else if (isPM700) {
			dt = PM700;
		} else if (isPM1200) {
			dt = PM1200;
		}
		Thread.sleep(10);
		tm.destroy();
		return dt;
	}

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

	public RCUPacketFloat askDevice() throws ModbusInitException,
			ErrorResponseException, ModbusTransportException, SocketException {
		return this.askDevice(tm);
	}

	/**
	 * 
	 * @param mtm
	 *            Provides a Modbus TCP Master class object
	 * @param device
	 *            Provides a modbus device number
	 * 
	 * @return Returns object of RCUPacket class with all gathered information
	 *         from polled device
	 * @throws SocketException
	 */
	public abstract RCUPacketFloat askDevice(TcpMaster mtm)
			throws ModbusInitException, ErrorResponseException,
			ModbusTransportException, SocketException;

	public void stop() {
		if (tm != null) {
			tm.destroy();
			tm = null;
		}
	}

	// TODO
	// Rewrite main method !

	public static void main(String[] args) {
		String hostname = null;
		int port = 0;
		short dev = 0, type = -1;
		RCUAnalyzer rcuan = null;
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
				rcuan = new RCUAnalyzer(hostname, port, dev, type) {

					private boolean isAsked = false;
					short devT = -1;

					public RCUPacketFloat askDevice(TcpMaster mbMstr)
							throws ModbusInitException, ErrorResponseException,
							ModbusTransportException, SocketException {
						RCUPacketFloat rcuPack = null;

						try {
							if (!isAsked) {
								devT = determineRCUDeviceType(getIpAddress(), getPort(),
										this.device);
								System.out.println("I got "
										+ RCUAnalyzer
												.getDeviceTypeAsString(devT));
								if (devT != this.deviceType) {
									System.err
											.println("Error: Looks like you provide wrong device type");
									throw new ModbusInitException(
											"Error: Looks like you provide wrong device type");
								} else {
									isAsked = true;
								}
							}
						} catch (InterruptedException ie) {
							ie.printStackTrace();
						}
						switch (devT) {
						case PM500:
							RCUPM500Analyzer rcuPM500An = new RCUPM500Analyzer(
									ip, port, this.device);
							rcuPack = rcuPM500An.askDevice();
							break;
						case PM700:
							RCUPM700Analyzer rcuPM700An = new RCUPM700Analyzer(
									ip, port, this.device);
							rcuPack = rcuPM700An.askDevice();
							break;
						case PM1200:
							RCUPM1200Analyzer rcuPM1200An = new RCUPM1200Analyzer(
									ip, port, this.device);
							rcuPack = rcuPM1200An.askDevice();
							break;
						default:
							throw new ModbusInitException(
									"Error: Unfortunately I cannot handle this device type!");
						}

						return rcuPack;

					}
				};

				while (true) {
					RCUPacketFloat rcup = rcuan.askDevice();
					System.out.println(rcup);
					// showOutput(rcup.getAll());

					Thread.sleep(1000);
				}
			} catch (ModbusInitException mie) {
				System.err.println("Error: Cannot init Modbus tcp session");
				mie.printStackTrace();
			}

			catch (InterruptedException ie) {
				ie.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("from host " + hostname + ":" + port
						+ " device " + dev);
			} finally {
				rcuan.stop();
			}
		}
	}
}
