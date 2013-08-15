import java.util.ArrayList;

public class RCUPacket {

	private float i1, i2, i3, in, u12, u23, u31, u1n, u2n, u3n, freq, p, q, s,
			powerFactor;
	private static final short units = 15;

	/**
	 * This constructor is intended to accept row data from modbus PDU in
	 * decimal format without formatting it to human readable format. i1 - First
	 * phase current i2 - Second phase current i3 - Third phase current in -
	 * Current to neutral u12 - Phase 1 to phase 2 voltage u23 - Phase 2 to
	 * phase 3 voltage u31 - Phase 3 to phase 1 voltage u1n - Phase 1 to neutral
	 * voltage u2n - Phase 2 to neutral voltage u3n - Phase 3 to neutral voltage
	 * freq - Current frequency p - real power kW q - reactive power kVAr s -
	 * apparent power kVA powerFactor - Power Factor
	 */
	public RCUPacket(int i1, int i2, int i3, int in, int u12, int u23, int u31,
			int u1n, int u2n, int u3n, int freq, int p, int q, int s,
			int powerFactor) {
		this.i1 = (float) i1 / 1000;
		this.i2 = (float) i2 / 1000;
		this.i3 = (float) i3 / 1000;
		this.in = (float) in / 1000;
		this.u12 = (float) u12 / 100;
		this.u23 = (float) u23 / 100;
		this.u31 = (float) u31 / 100;
		this.u1n = (float) u1n / 100;
		this.u2n = (float) u2n / 100;
		this.u3n = (float) u3n / 100;
		this.freq = (float) freq / 100;
		this.p = (float) p / 100;
		this.q = (float) q / 100;
		this.s = (float) s / 100;
		this.powerFactor = (float) powerFactor / 1000;
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
			this.i1 = (float) value / 1000;
			break;
		case 1:
			this.i2 = (float) value / 1000;
			break;
		case 2:
			this.i3 = (float) value / 1000;
			break;
		case 3:
			this.in = (float) value / 1000;
			break;
		case 4:
			this.u12 = (float) value / 100;
			break;
		case 5:
			this.u23 = (float) value / 100;
			break;
		case 6:
			this.u31 = (float) value / 100;
			break;
		case 7:
			this.u1n = (float) value / 100;
			break;
		case 8:
			this.u2n = value / 100;
			break;
		case 9:
			this.u3n = (float) value / 100;
			break;
		case 10:
			this.freq = (float) value / 100;
			break;
		case 11:
			this.p = (float) value / 100;
			break;
		case 12:
			this.q = (float) value / 100;
			break;
		case 13:
			this.s = (float) value / 100;
			break;
		case 14:
			this.powerFactor = (float) value / 1000;
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

	public String toString() {
		return new String("I1=" + i1 + " I2=" + i2 + " I3=" + i3 + " IN=" + in
				+ "\nU12=" + u12 + " U23=" + u23 + " U31=" + u31 + " U1N="
				+ u1n + " U2N=" + u2n + " U3N=" + u3n + "\nFreq=" + freq
				+ " P=" + p + " Q=" + q + " S=" + s + " PowerFactor="
				+ powerFactor);
	}

}
