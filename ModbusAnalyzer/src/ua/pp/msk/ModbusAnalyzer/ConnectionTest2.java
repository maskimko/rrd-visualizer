package ua.pp.msk.ModbusAnalyzer;
import java.util.ArrayList;

import net.sourceforge.jmodbus.ModbusTCPMaster;

public class ConnectionTest2 {

	/**
	 * @param args
	 */

	//TODO
	//Make auto scaling for PM700
	
	private static void joiner(ArrayList<Integer> result, int[] registers){
		int val;
		for (int i = 0; i < registers.length; i += 2){
			registers[i+1] += 0x100;
			val = registers[i] << (Integer.SIZE / 2);
			val += registers[i + 1];
			result.add(val);
		}
	}
	
	private static void showAll(ArrayList<Integer> output){
		for (int val : output){
			System.out.println(val);
		}
	}
	
	
	private static void showAll(int[] output){
		for (int i = 0; i < output.length; i++){
			System.out.println(output[i]);
		}
	}
	
	private static void fitter(int[] arr){
		for (int i = 0; i < arr.length; i++){
			arr[i] += 0x100;
		}
	}
	
	private static void showOutput(ArrayList<Integer> output){
		int counter = 0;
		for (int value : output){

			String key = null;
			// System.out.println(Integer.toHexString(result[i]) + " " +
			// result[i]);
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
			case 10:
				key = "Freq = ";
				break;
			case 11:
				key = "P = ";
				value -= 0x100;
				break;
			case 12:
				key = "Q = ";
				value -= 0x100;
				break;
			case 13:
				key = "S = ";
				break;
			case 14:
				key = "Power Factor = ";
	
				System.out.println(Integer.toHexString(value));
				break;

			}
			counter++;
			if (key != null) System.out.println(key + value);
		
		}
	}

	private static void hexDebug(int[] array){
		for (int i  = 0; i < array.length; i++) {
			System.out.print(" HEX:\t" + Integer.toHexString(array[i]));
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String host = "10.208.20.122";
		int port = 502;
		int devicePort = 2;
		try {
			ModbusTCPMaster mtm = new ModbusTCPMaster(host, port);
			/**
			 * replyPower
			 * 4006 Total Real Power kW
			 * 4007 Total Apparent Power kVA
			 * 4008 Total Reactive Power kVAr
			 * 4009 Total Power Factor
			 */
			
			int[] replyPower = new int[4];
			/**
			 * 4030 Voltage, Phase 12
			 * 4031 Voltage, Phase 23
			 * 4032 Voltage, Phase 31
			 * 4033 Voltage, Phase 1N
			 * 4034 Voltage, Phase 2N
			 * 4035 Voltage, Phase 3N
			 */
			int[] replyVoltage = new int[6];
			
			/**
			 * 4020 Current, Instantaneous, Phase 1
			 * 4021 Current, Instantaneous, Phase 2
			 * 4022 Current, Instantaneous, Phase 3
			 * 4023 Current, Instantaneous, Neutral
			 */
			
			int[] replyCurrent = new int[4];
			/**
			 * 4013 Frequency (derived from Phase 1 )
			 */
			int[] replyFrequency = new int[1];
			
			int[] all = new int[50];
			
			
			//int[] replyPF = new int[2];
			int[]devCheck;
			
			while (true) {
				ArrayList<Integer> output = new ArrayList<Integer>();
				mtm.readMultipleRegisters(devicePort, 4005, replyPower.length, 0, replyPower);
				mtm.readMultipleRegisters(devicePort, 4029, replyVoltage.length, 0, replyVoltage);
				mtm.readMultipleRegisters(devicePort, 4019, replyCurrent.length, 0, replyCurrent);
				mtm.readMultipleRegisters(devicePort, 4012, replyFrequency.length, 0, replyFrequency);
				//mtm.readMultipleRegisters(devicePort, 870, replyPF.length, 0, replyPF);
				//mtm.readMultipleRegisters(devicePort, 3999, all.length, 0, all);
				/*
				 * for (int i = 0; i < reply.length; i++) { //if (i % 2 == 0)
				 * System.out.print(Integer.toBinaryString(reply[i] << 16) + " "
				 * + Integer.toHexString((reply[i] << 16) + reply[i+1]) + " ");
				 * 
				 * //System.out.print(Integer.toBinaryString(reply[i]) + " ");
				 * //System.out.print(Integer.toOctalString(reply[i]) + " ");
				 * System.out.println(Integer.toHexString(reply[i]));
				 * 
				 * //System.out.println(reply[i]); }
				 */
				
				//int[] result = get64BitWords(reply);
				//joiner(output, replyCurrent);
				//joiner(output, replyVoltage);
				//joiner(output, replyFrequency);
				//joiner(output, replyPower);
				//joiner(output, replyPF);
				//joiner(output, all);
				//hexDebug(all);
				fitter(replyVoltage);
				hexDebug(replyVoltage);
				fitter(replyCurrent);
				hexDebug(replyCurrent);
				fitter(replyFrequency);
				hexDebug(replyFrequency);
				
				hexDebug(replyPower);
				
				//hexDebug(replyPF);
				/*for (int vl : output){
					System.out.print(vl + " ");
				}
				System.out.println(); */
				
				
				//System.out.println("showing values of Power Factor " + Integer.toHexString(replyPF[0]) + " " + Integer.toHexString(replyPF[1]) );
				
				/*
				for (int i = 0; i < result.length; i++) {
					String key = null;
					// System.out.println(Integer.toHexString(result[i]) + " " +
					// result[i]);
					switch (i) {
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

					}

					if (key != null) System.out.println(key + result[i]);
				}
				
				*/
				
				//showOutput(output);
				System.out.println("Frequency");
				showAll(replyFrequency);
				System.out.println("Current");
				showAll(replyCurrent);
				System.out.println("Voltage");
				showAll(replyVoltage);
				System.out.println("Power");
				showAll(replyPower);
				
				
				Thread.sleep(1000);
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

	}
}
