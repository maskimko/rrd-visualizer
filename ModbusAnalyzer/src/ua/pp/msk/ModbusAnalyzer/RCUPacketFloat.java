package ua.pp.msk.ModbusAnalyzer;
import java.util.ArrayList;

public class RCUPacketFloat {

	private float i1, i2, i3, in, u12, u23, u31, u1n, u2n, u3n, freq, p, q, s,
			powerFactor;
	public static final short units = 15;
	
	

	/**
	 * This constructor is intended to accept  data from modbus PDU in
	 * human readable format. 
	 * i1 - First phase current 
	 * i2 - Second phase current 
	 * i3 - Third phase current 
	 * in - Current to neutral 
	 * u12 - Phase 1 to phase 2 voltage 
	 * u23 - Phase 2 to phase 3 voltage 
	 * u31 - Phase 3 to phase 1 voltage 
	 * u1n - Phase 1 to neutral voltage 
	 * u2n - Phase 2 to neutral voltage 
	 * u3n - Phase 3 to neutral voltage 
	 * freq - Current frequency 
	 * p - real power kW 
	 * q - reactive power kVAr 
	 * s - apparent power kVA 
	 * powerFactor - Power Factor 
	 */
	public RCUPacketFloat(float i1, float i2, float i3, float in, float u12, float u23, float u31,
			float u1n, float u2n, float u3n, float freq, float p, float q, float s,
			float powerFactor) {
		this.i1 =  i1; 
		this.i2 =  i2; 
		this.i3 =  i3; 
		this.in =  in; 
		this.u12 =  u12; 
		this.u23 =  u23; 
		this.u31 =  u31; 
		this.u1n =  u1n; 
		this.u2n =  u2n; 
		this.u3n =  u3n; 
		this.freq =  freq; 
		this.p =  p; 
		this.q =  q; 
		this.s =  s; 
		this.powerFactor =  powerFactor; 
		
	}

	public RCUPacketFloat(float[] modbusResponseResult)
			throws IllegalArgumentException {
		
		if (modbusResponseResult.length != units) {
			throw new IllegalArgumentException("Array length have to be " + units);
		} else {
			for (int i = 0; i < modbusResponseResult.length; i++) {

				switcher(i, modbusResponseResult[i]);

			}
		}
	}

	public RCUPacketFloat(ArrayList<Float> modbusResponseResult) {
		
		int counter = 0;
		for (float val : modbusResponseResult) {
			switcher(counter, val);
			counter++;
		}

	}

	
	
	
	private void switcher(int i, float value) {
		switch (i) {
		case 0:
			this.i1 = value;
			break;
		case 1:
			this.i2 = value;
			break;
		case 2:
			this.i3 = value;
			break;
		case 3:
			this.in = value;
			break;
		case 4:
			this.u12 = value;
			break;
		case 5:
			this.u23 = value;
			break;
		case 6:
			this.u31 = value;
			break;
		case 7:
			this.u1n = value;
			break;
		case 8:
			this.u2n =value;
			break;
		case 9:
			this.u3n = value;
			break;
		case 10:
			this.freq = value;
			break;
		case 11:
			this.p = value;
			break;
		case 12:
			this.q = value;
			break;
		case 13:
			this.s = value;
			break;
		case 14:
			this.powerFactor = value;
			break;
		}
	}

	public float getI1() {
		return i1;
	}

	public float getI2() {
		return i2;
	}

	public float getI3() {
		return i3;
	}

	public float getIN() {
		return in;
	}

	public float getU12() {
		return u12;
	}

	public float getU23() {
		return u23;
	}

	public float getU31() {
		return u31;
	}

	public float getU1N() {
		return u1n;
	}

	public float getU2N() {
		return u2n;
	}

	public float getU3N() {
		return u3n;
	}

	public float getFreq() {
		return freq;
	}

	public float getP() {
		return p;
	}

	public float getQ() {
		return q;
	}

	public float getS() {
		return s;
	}

	public float getPowerFactor() {
		return powerFactor;
	}

	/**
	 * The methods getAll returns values in the described above order
	 * 
	 * @return
	 */

	public float[] getAll() {
		float[] retArray = { i1, i2, i3, in, u12, u23, u31, u1n, u2n, u3n,
				freq, p, q, s, powerFactor };
		return retArray;
	}
	
	public int[] getAllInteger(){
		int[] retArray = new int[] {(int) i1 * 1000,
				(int) i2 * 1000,
				(int) i3 * 1000,
				(int) in * 1000,
				(int) u12 * 100,
				(int) u23 * 100,
				(int) u31 * 100,
				(int) u1n * 100,
				(int) u2n * 100,
				(int) u3n * 100,
				(int) freq * 100,
				(int) p * 1000,
				(int) q * 1000,
				(int) s * 1000,
				(int) powerFactor * 1000};
		return retArray;
	}

	public String toString() {
		
		String present = null;
		present =  new String("I1=" + i1 + " I2=" +  i2  + " I3=" +  i3   + " IN=" +  in  
				+ "\nU12=" +  u12   + " U23=" +  u23  + " U31=" +  u31   + " U1N="
				+  u1n   + " U2N=" +  u2n   + " U3N=" +  u3n  + "\nFreq=" +  freq  
				+ " P=" +  p   + " Q=" +  q  + " S=" +  s   + " PowerFactor="
				+  powerFactor  );
		
		return present;
	}

}
