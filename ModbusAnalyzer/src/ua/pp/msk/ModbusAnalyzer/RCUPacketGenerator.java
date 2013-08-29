package ua.pp.msk.ModbusAnalyzer;

public  class RCUPacketGenerator {

	private static final float current = 80;
	private static final float current2n = 10;
	private static final float voltage2l = 370;
	private static final float voltage2n = 210;
	private static final float frequency = 50;
	private static final float power = 70;
	private static final float kvar= 14;
	private static final float kva = 80;
	private static final float powerFactor = 0.8f;
	
	
	public static RCUPacketFloat getZeroPacket(){
		float[] stats = new float[RCUPacketFloat.units];
		for (int i = 0; i < stats.length; i++) {
			stats[i] = 0;
		}
		RCUPacketFloat rcup = new RCUPacketFloat(stats);
		return rcup;
	}
	
	public static RCUPacketFloat genPacket(){
		//Generating RCU Packet
		float multiplyer = 0;
		float[] toPack = new float[RCUPacketFloat.units];
		for (int i = 0; i < RCUPacketFloat.units; i++){
			switch (i) {
			case 0: 
			case 1:
			case 2:
				multiplyer =(float) (Math.random() * 20000) + current;
				break;
			case 3:
				multiplyer =(float) (Math.random() * 1000) + current2n;
				break;
			case 4:
			case 5:
			case 6:
				multiplyer =(float) (Math.random() * 2000) + voltage2l;
				break;
			case 7:
			case 8:
			case 9:
				multiplyer =(float) (Math.random() * 2000) + voltage2n;
				break;
			case 10:
				multiplyer =(float) (Math.random() * 200) + frequency;
				break;
			case 11:
				multiplyer =(float) (Math.random() * 2000) + power;
				break;
			case 12: 
				multiplyer =(float) (Math.random() * 500) + kvar;
				break;
			case 13:
				multiplyer =(float) (Math.random() * 3000) + kva;
				break;
			case 14:
				multiplyer =(float) (Math.random() * 200) + powerFactor;
				break;
			}
			toPack[i] = multiplyer;
		}
		RCUPacketFloat rp = new RCUPacketFloat(toPack);
		return rp;
	}
}
