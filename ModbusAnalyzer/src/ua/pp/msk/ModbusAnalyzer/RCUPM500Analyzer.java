package ua.pp.msk.ModbusAnalyzer;

import java.util.ArrayList;

import net.sourceforge.jmodbus.ModbusTCPMaster;

public class RCUPM500Analyzer extends RCUAnalyzer  {

	
	public RCUPM500Analyzer(String host, int port, short device){
		super(new ModbusTCPMaster(host, port), device, PM500);
	}
	
	 RCUPM500Analyzer(ModbusTCPMaster mtm, short device){
		super(mtm, device, PM500);
	}
	
	
	
	
	/**
	 * 
	 * @param output
	 *            Accepts ArrayList<Integer> with list of data to produce
	 *            Generally this method is intended to correct data which we
	 *            take from jmodbus module.
	 * @throws Exception
	 */
	private static void fit2RCU(ArrayList<Integer> output)
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
	
	public RCUPacket askDevice(ModbusTCPMaster mtm, short device)
			throws Exception {

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

		RCUPacket rcup = new RCUPacket(output, deviceType);

		return rcup;
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
	
}
