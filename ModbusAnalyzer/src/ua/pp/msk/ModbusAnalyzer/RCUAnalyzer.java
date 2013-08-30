package ua.pp.msk.ModbusAnalyzer;

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
	protected short deviceType;
	protected TcpMaster tm = null;

	
	public RCUAnalyzer(String host, int port, short device, short type){
		this.ip = host;
		this.port = port;
		boolean keepAlive = true;
		IpParameters ipParam = new IpParameters();
		ipParam.setHost(host);
		ipParam.setPort(port);
		ipParam.setEncapsulated(false);
		tm = new TcpMaster(ipParam, keepAlive);
		tm.setTimeout(1500);
		this.device = device;
		this.deviceType = type;
	}
	
	

	public short getModbusDeviceNumber(){
		return this.device;
	}
	
	public String getIpAddress(){
		return ip;
	}
	
	public int getPort(){
		return port;
	}
	
	public short getDeviceType(){
		return this.deviceType;
	}
	
	public static String getDeviceTypeAsString(short deviceType){
		String dt = null;
		switch (deviceType) {
		case PM500:
			dt = "PM500";
			break;
		case PM700:
			dt = "PM700";
			break;
			default:
				dt = "Unknown";
				break;
		}
		return dt;
	}
	
	public static RCUAnalyzer getRCUDevice(String hostname, int port,
			short device) throws ModbusInitException, InterruptedException, ModbusTransportException, Exception {
		RCUAnalyzer ra = null;
		boolean keepAlive = true;
		IpParameters ipParm = new IpParameters();
		ipParm.setHost(hostname);
		ipParm.setPort(port);
		ipParm.setEncapsulated(false);
		
		
		TcpMaster mtm = new TcpMaster(ipParm, keepAlive);
		mtm.init();
		short type = determineRCUDeviceType(mtm, device);
		switch (type) {
		case PM500:
			ra = new RCUPM500Analyzer(hostname, port, device);
			break;
		case PM700:
			ra = new RCUPM700Analyzer(hostname, port, device);
			break;
		default:
			throw new Exception(
					"Error: Cannot create a RCUAnalyzer.\nUnknown type of device!");
		}

		return ra;
	}

	

	
	public static short determineRCUDeviceType(TcpMaster tm, short devnum)
			throws ModbusTransportException, InterruptedException, ModbusInitException {
		short dt = -1;
		boolean isPM500 = false;
		boolean isPM700 = false;
		try {
			ModbusLocator devTypePM500Locator = new ModbusLocator(devnum,
					RegisterRange.HOLDING_REGISTER, 64647,
					DataType.TWO_BYTE_INT_UNSIGNED);
			if (!tm.isInitialized()) {
				tm.init();
			}

			int idPM500 = (int) tm.getValue(devTypePM500Locator);
			isPM500 = true;
		} catch (ErrorResponseException erePM500) {
			
		}
		try {
			ModbusLocator devTypePM700Locator = new ModbusLocator(devnum,
					RegisterRange.HOLDING_REGISTER, 7003,
					DataType.TWO_BYTE_INT_UNSIGNED);
			int idPM700 = (int) tm.getValue(devTypePM700Locator);
			isPM700 = true;
		} catch (ErrorResponseException erePM700) {
			
		}
		if (isPM500) {
			dt = 0;
		} else if (isPM700) {
			dt = 1;
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

	

	public RCUPacketFloat askDevice() throws ModbusInitException, Exception {
		return this.askDevice(tm, this.device);
	}

	/**
	 * 
	 * @param mtm
	 * 			Provides a Modbus TCP Master class object
	 * @param device
	 *            Provides a modbus device number
	 * 
	 * @return Returns object of RCUPacket class with all gathered information
	 *         from polled device
	 */
	public abstract RCUPacketFloat askDevice(TcpMaster mtm, short device)
			throws ModbusInitException, Exception;

	public void stop() {
		if (tm != null) {

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
					
					
					public RCUPacketFloat askDevice(TcpMaster mbMstr, short dev)
							throws Exception {
						RCUPacketFloat rcuPack = null;

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
						case PM500:
							RCUPM500Analyzer rcuPM500An = new RCUPM500Analyzer(ip, port, dev);
							rcuPack = rcuPM500An.askDevice();
							break;
						case PM700:
							RCUPM700Analyzer rcuPM700An = new RCUPM700Analyzer(ip, port, dev);
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
					RCUPacketFloat rcup = rcuan.askDevice();
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
			} finally {
				rcuan.stop();
			}
		}
	}
}
