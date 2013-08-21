package ua.pp.msk.ModbusAnalyzer;

import java.util.ArrayList;

import net.sourceforge.jmodbus.ModbusTCPMaster;

public class RCUAnalyzer {

	public static final short TYPE_RCUPOWERINPUT = 0;
	private ModbusTCPMaster mbTCP = null;
	private short device = 1;
	private short deviceType = 0;

	public RCUAnalyzer(String host, int port, short device, short type) {
		this.mbTCP = new ModbusTCPMaster(host, port);
		this.device = device;
		this.deviceType = type;
	}

	public RCUAnalyzer(String host, int port, short device) {
		this(host, port, device, (short) 0);
	}

	private static void joiner(ArrayList<Integer> result, int[] registers) {
		int val;
		for (int i = 0; i < registers.length; i += 2) {
			registers[i + 1] += 0x100;
			val = registers[i] << (Integer.SIZE / 2);
			val += registers[i + 1];
			result.add(val);
		}
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

	private static void showOutputFloat(ArrayList<Float> output) {
		
			int counter = 0;
			
				
				for (float value : output) {
				
					if (showSwitcher(counter) != null) {
						System.out.println(showSwitcher(counter) + value);
					}
					counter++;

				
		}
	}
	
	private static void showOutputInteger(ArrayList<Integer> output){
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

	private static boolean fallChecker(ArrayList<Integer> arr){
		boolean isFalled = true;
		int fallVal = 256;
		for (int val : arr ){
			if (val != fallVal) {
				return false;
			}
		}
		return isFalled;
	}
	
	private static void fit2RCU(ArrayList<Integer> output) throws Exception{
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
	private RCUPacket askRCUInputDevice(ModbusTCPMaster mtm, short device) throws Exception {
		
		int[] replyIUP = new int[28];
		int[] replyPF = new int[2];

		ArrayList<Integer> output = new ArrayList<Integer>();
		// Requesting for i, u, p, q, s, freq
		mtm.readMultipleRegisters(device, 768, replyIUP.length, 0, replyIUP);
		// Requesting power factor
		mtm.readMultipleRegisters(device, 870, replyPF.length, 0, replyPF);

		joiner(output, replyIUP);
		joiner(output, replyPF);
		
		fit2RCU(output);
		
		RCUPacket rcup = new RCUPacket(output);
		
		return rcup;
	}

	public RCUPacket askDevice() throws Exception {
		return this.askDevice(mbTCP,  this.device,
				this.deviceType);
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
		if (deviceType != this.TYPE_RCUPOWERINPUT) {
			throw new IllegalArgumentException("There is no such device type");
		} else {
			this.device = device;
			return askRCUInputDevice(mtm, device);
		}
	}
	
	public void stop(){
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
			//showOutput(rcup.getAll());

			Thread.sleep(1000);
			 }
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (Exception e) {
			System.err.println(e.getMessage() + " from host " + hostname + ":" + port + " device " + dev);
		}

	}
}
