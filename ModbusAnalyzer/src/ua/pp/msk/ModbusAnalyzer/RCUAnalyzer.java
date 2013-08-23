package ua.pp.msk.ModbusAnalyzer;

import java.util.ArrayList;

import net.sourceforge.jmodbus.ModbusTCPMaster;

public class RCUAnalyzer {

	public static final short PM500 = 0;
	public static final short PM700 = 1;

	private ModbusTCPMaster mbTCP = null;
	private short device = 1;
	private short deviceType = 0;
	

	public RCUAnalyzer(String host, int port, short device, short type) {
		this.mbTCP = new ModbusTCPMaster(host, port);
		this.device = device;
		this.deviceType = type;
	}

	private static void joinerPM500(ArrayList<Integer> result, int[] registers) {
		int val;
		for (int i = 0; i < registers.length; i += 2) {
			registers[i + 1] += 0x100;
			val = registers[i] << (Integer.SIZE / 2);
			val += registers[i + 1];
			result.add(val);
		}
	}

	private static void joinerPM700(ArrayList<Integer> result, int[] registers) {

		for (int i = 0; i < registers.length; i++) {

			result.add(registers[i] + 0x100);
		}
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
	
	
	public static short determineRCUDeviceType(ModbusTCPMaster mbtcp, short devnum) throws Exception{
		short dt = -1;
		boolean isPM500 = false;
		boolean isPM700 = false;
		//Trying to ask PM500 device
		int[] devPM500check = new int[4];
		mbtcp.readMultipleRegisters(devnum, 64646, devPM500check.length, 0, devPM500check);
		if (devPM500check[0] == 256 && devPM500check[1] == 50980 && devPM500check[2] == 0 && devPM500check[3] == 1){
			isPM500 = true;
		}
		int[] devPM700check = new int[2];
		mbtcp.readMultipleRegisters(devnum, 7003, devPM700check.length, 0, devPM700check);
		if (devPM700check[0] == 15165){
			isPM700 = true;
		}
		if (isPM500) {
			dt = RCUAnalyzer.PM500;
			System.out.println("Modbus device number devnum is PM500");
		} else 
			if (isPM700){
				dt = RCUAnalyzer.PM700;
				System.out.println("Modbus device number devnum is PM700");
			} else {
				throw new Exception("I cannot determine device type!");
			}
		return dt;
		
	}
	
private void msgToPM500Normalizer(short devType, int[] scaleFactor, ArrayList<Integer> toPack){
	int multiplierI = (int) (RCUPacket.currentScaleFactor / Math.pow(10, scaleFactor[0]));
	int multiplierV = (int) (RCUPacket.voltageScaleFactor / Math.pow(10, scaleFactor[1]));
	int multiplierW = (int) (RCUPacket.powerScaleFactor / Math.pow(10, scaleFactor[1]));;
		
	}

	private static String showSwitcher(int counter) {
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

	private static void normalizePM700Power(int[] rp){
		int swap = rp[1];
		rp[1] = rp[2];
		rp[2] = swap;
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

	private static boolean fallChecker(ArrayList<Integer> arr) {
		boolean isFalled = true;
		int fallVal = 256;
		for (int val : arr) {
			if (val != fallVal) {
				return false;
			}
		}
		return isFalled;
	}

	/**
	 * 
	 * @param output
	 *            Accepts ArrayList<Integer> with list of data to produce
	 *            Generally this method is intended to correct data which we
	 *            take from jmodbus module.
	 * @throws Exception
	 */
	private static void fit2RCUPM500(ArrayList<Integer> output)
			throws Exception {
		int counter = 0;

		for (int value : output) {
			if (!fallChecker(output)) {
				switch (counter) {
				case 11:
					value -= 0x100;
					break;
				case 12:
					value -= 0x100;
					break;
				}
			} else {
				throw new Exception("Cannot get an answer");
			}
			counter++;
		}
	}

	/**
	 * Generally this method is intended to correct data which we take from
	 * jmodbus module.
	 * 
	 * @param output
	 *            Accepts ArrayList<Integer> with list of data to produce
	 * @throws Exception
	 */
	private static void fit2RCUPM700(ArrayList<Integer> output)
			throws Exception {
		
		if (!fallChecker(output)) {
		for (int counter = 0; counter < output.size(); counter++) {
			
				switch (counter) {
				/*case 2:
					output.set(counter, output.get(counter) - 0x100)   ;
					break;*/
				case 6:
					output.set(counter, output.get(counter) - 0x100)   ;
					break;			
				case 8:
					output.set(counter, output.get(counter) - 0x100)   ;
					break;
				case 11:
				case 12:
				case 13:
					output.set(counter, output.get(counter) - 0x100)   ;
					break;
				}
		}
			} else {
				throw new Exception("Cannot get an answer");
			
			
		}
	}

	/**
	 * 
	 * @param host
	 *            Provides a host ip address
	 * @param port
	 *            Provides a host ip port
	 * @param device
	 *            Provides a modbus device number
	 * @return Returns object of RCUPacket class with all gathered information
	 *         about polled device
	 */
	private RCUPacket askRCUPM500Device(ModbusTCPMaster mtm, short device)
			throws Exception {

		int[] replyIUP = new int[28];
		int[] replyPF = new int[2];

		ArrayList<Integer> output = new ArrayList<Integer>();
		// Requesting for i, u, p, q, s, freq
		mtm.readMultipleRegisters(device, 768, replyIUP.length, 0, replyIUP);
		// Requesting power factor
		mtm.readMultipleRegisters(device, 870, replyPF.length, 0, replyPF);

		joinerPM500(output, replyIUP);
		joinerPM500(output, replyPF);

		fit2RCUPM500(output);

		RCUPacket rcup = new RCUPacket(output, deviceType);

		return rcup;
	}

	/**
	 * 
	 * @param mtm
	 *            ModbusTCPMaSTER worker which handles the connection
	 * @param device
	 *            Device number on the modbus bus
	 * @return RCUPacket object with measurements data
	 * @throws Exception
	 */
	private RCUPacket askRCUPM700Device(ModbusTCPMaster mtm, short device)
			throws Exception {

		/**
		 * replyPower 4006 Total Real Power kW 4007 Total Apparent Power kVA
		 * 4008 Total Reactive Power kVAr 4009 Total Power Factor
		 */
		int[] replyPower = new int[4];
		/**
		 * 4030 Voltage, Phase 12 4031 Voltage, Phase 23 4032 Voltage, Phase 31
		 * 4033 Voltage, Phase 1N 4034 Voltage, Phase 2N 4035 Voltage, Phase 3N
		 */
		int[] replyVoltage = new int[6];

		/**
		 * 4020 Current, Instantaneous, Phase 1 4021 Current, Instantaneous,
		 * Phase 2 4022 Current, Instantaneous, Phase 3 4023 Current,
		 * Instantaneous, Neutral
		 */

		int[] replyCurrent = new int[4];
		/**
		 * 4013 Frequency (derived from Phase 1 )
		 */
		int[] replyFrequency = new int[1];

		ArrayList<Integer> output = new ArrayList<Integer>();
		mtm.readMultipleRegisters(device, 4019, replyCurrent.length, 0,
				replyCurrent);
		mtm.readMultipleRegisters(device, 4029, replyVoltage.length, 0,
				replyVoltage);
		mtm.readMultipleRegisters(device, 4012, replyFrequency.length, 0,
				replyFrequency);
		mtm.readMultipleRegisters(device, 4005, replyPower.length, 0,
				replyPower);

		joinerPM700(output, replyCurrent);
		joinerPM700(output, replyVoltage);
		joinerPM700(output, replyFrequency);
		normalizePM700Power(replyPower);
		joinerPM700(output, replyPower);

		fit2RCUPM700(output);

		RCUPacket rcup = new RCUPacket(output, deviceType);

		return rcup;
	}

	public RCUPacket askDevice() throws Exception {
		return this.askDevice(mbTCP, this.device, this.deviceType);
	}

	/**
	 * 
	 * @param host
	 *            Provides a host ip address
	 * @param port
	 *            Provides a host ip port
	 * @param device
	 *            Provides a modbus device number
	 * @param deviceType
	 *            What device we have to poll RCUInput = 0;
	 * @return Returns object of RCUPacket class with all gathered information
	 *         about polled device
	 */
	public RCUPacket askDevice(ModbusTCPMaster mtm, short device,
			short deviceType) throws IllegalArgumentException, Exception {

		this.device = device;
		RCUPacket rp = null;
		switch (deviceType) {
		case PM500:
			rp = askRCUPM500Device(mtm, device);
			break;
		case PM700:
			rp = askRCUPM700Device(mtm, device);
			break;
		default:
			throw new IllegalArgumentException(
					"I know nothing about this device type!");
		}

		return rp;

	}

	public void stop() {
		if (mbTCP != null) {

			mbTCP = null;
		}
	}

	// TODO
	// Rewrite main method !

	public static void main(String[] args) {
		String hostname = "127.0.0.1";
		int port = 502;
		short dev = 2, type = 0;
		if (args.length == 0 || args.length > 4) {
			System.out
					.println("Usage: RCUAnalyzer <hostname> [port] [modbus device] [rcu device type]");
			return;
		} else {
			hostname = args[0];
			if (args.length > 1) {
				port = Integer.parseInt(args[1]);
				if (args.length > 2) {
					dev = Short.parseShort(args[2]);
					if (args.length > 3) {
						type = Short.parseShort(args[3]);
					}
				}
			}
		}
		RCUAnalyzer rcuan = new RCUAnalyzer(hostname, port, dev, type);
		try {
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
