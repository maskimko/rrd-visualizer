package ua.pp.msk.ModbusAnalyzer;

public  class RCUPacketGenerator {

	private static final int current = 80000;
	private static final int current2n = 10000;
	private static final int voltage2l = 37000;
	private static final int voltage2n = 21000;
	private static final int frequency = 5000;
	private static final int power = 7000;
	private static final int kvar= 1400;
	private static final int kva = 8000;
	private static final int powerFactor = 800;
	
	
			
	
	public static RCUPacket genPacket(){
		//Generating RCU Packet
		int multiplyer = 0;
		int[] toPack = new int[RCUPacket.units];
		for (int i = 0; i < RCUPacket.units; i++){
			switch (i) {
			case 0: 
			case 1:
			case 2:
				multiplyer =(int) Math.random() * 20000 + current;
				break;
			case 3:
				multiplyer =(int) Math.random() * 1000 + current2n;
				break;
			case 4:
			case 5:
			case 6:
				multiplyer =(int) Math.random() * 2000 + voltage2l;
				break;
			case 7:
			case 8:
			case 9:
				multiplyer =(int) Math.random() * 2000 + voltage2n;
				break;
			case 10:
				multiplyer =(int) Math.random() * 200 + frequency;
				break;
			case 11:
				multiplyer =(int) Math.random() * 2000 + power;
				break;
			case 12: 
				multiplyer =(int) Math.random() * 500 + kvar;
				break;
			case 13:
				multiplyer =(int) Math.random() * 3000 + kva;
				break;
			case 14:
				multiplyer =(int) Math.random() * 200 + powerFactor;
				break;
			}
			toPack[i] = multiplyer;
		}
		RCUPacket rp = new RCUPacket(toPack);
		return rp;
	}
}
