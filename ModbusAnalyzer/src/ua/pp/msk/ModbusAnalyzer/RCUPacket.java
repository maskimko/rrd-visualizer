package ua.pp.msk.ModbusAnalyzer;
import java.util.ArrayList;

public class RCUPacket {

	private int i1, i2, i3, in, u12, u23, u31, u1n, u2n, u3n, freq, p, q, s,
			powerFactor;
	public static final short units = 15;

	/**
	 * This constructor is intended to accept row data from modbus PDU in
	 * decimal format without formatting it to human readable format. 
	 * i1 - First phase current Measures miliAmpers
	 * i2 - Second phase current Measures miliAmpers
	 * i3 - Third phase current Measures miliAmpers
	 * in - Current to neutral Measures miliAmpers
	 * u12 - Phase 1 to phase 2 voltage Measures in hundreds of volts
	 * u23 - Phase 2 to phase 3 voltage Measures in hundreds of volts
	 * u31 - Phase 3 to phase 1 voltage Measures in hundreds of volts
	 * u1n - Phase 1 to neutral voltage Measures in hundreds of volts
	 * u2n - Phase 2 to neutral voltage Measures in hundreds of volts
	 * u3n - Phase 3 to neutral voltage Measures in hundreds of volts
	 * freq - Current frequency Measures in hundreds of hertz
	 * p - real power kW Measures in hundreds of kiloWatts
	 * q - reactive power kVAr Measures in hundreds of kiloVoltAmpersResitance
	 * s - apparent power kVA Measures in hundreds of kiloVoltAmpers
	 * powerFactor - Power Factor Measures in thousands
	 */
	public RCUPacket(int i1, int i2, int i3, int in, int u12, int u23, int u31,
			int u1n, int u2n, int u3n, int freq, int p, int q, int s,
			int powerFactor) {
		this.i1 =  i1; // / 1000;
		this.i2 =  i2; // / 1000;
		this.i3 =  i3; // / 1000;
		this.in =  in; // / 1000;
		this.u12 =  u12; // / 100;
		this.u23 =  u23; // / 100;
		this.u31 =  u31; // / 100;
		this.u1n =  u1n; // / 100;
		this.u2n =  u2n; // / 100;
		this.u3n =  u3n; // / 100;
		this.freq =  freq; // / 100;
		this.p =  p; // / 100;
		this.q =  q; // / 100;
		this.s =  s; // / 100;
		this.powerFactor =  powerFactor; // / 1000;
	}

	public RCUPacket(int[] modbusResponseResult)
			throws IllegalArgumentException {
		if (modbusResponseResult.length != units) {
			throw new IllegalArgumentException("Array length have to be 15");
		} else {
			for (int i = 0; i < modbusResponseResult.length; i++) {

				switcher(i, modbusResponseResult[i]);

			}
		}
	}

	public RCUPacket(ArrayList<Integer> modbusResponseResult) {

		int counter = 0;
		for (int val : modbusResponseResult) {
			switcher(counter, val);
			counter++;
		}

	}

	private void switcher(int i, int value) {
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

	public int getI1() {
		return i1;
	}

	public int getI2() {
		return i2;
	}

	public int getI3() {
		return i3;
	}

	public int getIN() {
		return in;
	}

	public int getU12() {
		return u12;
	}

	public int getU23() {
		return u23;
	}

	public int getU31() {
		return u31;
	}

	public int getU1N() {
		return u1n;
	}

	public int getU2N() {
		return u2n;
	}

	public int getU3N() {
		return u3n;
	}

	public int getFreq() {
		return freq;
	}

	public int getP() {
		return p;
	}

	public int getQ() {
		return q;
	}

	public int getS() {
		return s;
	}

	public int getPowerFactor() {
		return powerFactor;
	}

	/**
	 * The methods getAll returns values in the described above order
	 * 
	 * @return
	 */

	public int[] getAll() {
		int[] retArray = { i1, i2, i3, in, u12, u23, u31, u1n, u2n, u3n,
				freq, p, q, s, powerFactor };
		return retArray;
	}

	public String toString() {
		return new String("I1=" + (float) i1 / 1000 + " I2=" + (float) i2  / 1000 + " I3=" + (float) i3  / 1000 + " IN=" + (float) in / 1000 
				+ "\nU12=" + (float) u12  / 100  + " U23=" + (float) u23  / 100 + " U31=" + (float) u31 / 100  + " U1N="
				+ (float) u1n  / 100 + " U2N=" + (float) u2n  / 100 + " U3N=" + (float) u3n  / 100 + "\nFreq=" + (float) freq / 100 
				+ " P=" + (float) p / 100  + " Q=" + (float) q / 100 + " S=" + (float) s  / 100 + " PowerFactor="
				+ (float) powerFactor / 1000 );
	}

}
