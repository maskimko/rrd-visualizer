import net.sourceforge.jmodbus.ModbusTCPMaster;

public class ConnectionTest2 {

	/**
	 * @param args
	 */

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String host = "10.192.20.122";
		int port = 502;
		try {
			ModbusTCPMaster mtm = new ModbusTCPMaster(host, port);
			int[] reply = new int[28];
			while (true) {
				mtm.readMultipleRegisters(2, 768, reply.length, 0, reply);
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
				System.out.println("showing values");
				int[] result = get64BitWords(reply);
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
				Thread.sleep(1000);
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

	}
}
