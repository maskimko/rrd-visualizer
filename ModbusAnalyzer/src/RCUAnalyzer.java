import java.util.ArrayList;

import net.sourceforge.jmodbus.ModbusTCPMaster;

public class ConnectionTest2 {

	/**
	 * @param args
	 */

	/*
	private static int[] get64BitWords(int[] int32Bit) {
		int val;
		int[] toReturn = new int[int32Bit.length / 2];
		for (int i = 0; i < int32Bit.length; i += 2) {
			val = int32Bit[i] << (Integer.SIZE / 2);
			val += int32Bit[i + 1];
			toReturn[i / 2] = val;
		}
		return toReturn;
	}
	*/
	
	private static void joiner(ArrayList<Integer> result, int[] registers){
		int val;
		for (int i = 0; i < registers.length; i += 2){
			registers[i+1] += 0x100;
			val = registers[i] << (Integer.SIZE / 2);
			val += registers[i + 1];
			result.add(val);
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
			System.out.print(Integer.toHexString(array[i]) + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		String host = "10.192.20.122";
		int port = 502;
		int devicePort = 2;
		try {
			ModbusTCPMaster mtm = new ModbusTCPMaster(host, port);
			int[] replyIUP = new int[28];
			int[] replyPF = new int[2];
			while (true) {
				ArrayList<Integer> output = new ArrayList<Integer>();
				mtm.readMultipleRegisters(devicePort, 768, replyIUP.length, 0, replyIUP);
				mtm.readMultipleRegisters(devicePort, 870, replyPF.length, 0, replyPF);
			
				joiner(output, replyIUP);
				joiner(output, replyPF);
				//hexDebug(replyIUP);
				//hexDebug(replyPF);
				
				
				showOutput(output);
				
				Thread.sleep(1000);
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

	}
}
