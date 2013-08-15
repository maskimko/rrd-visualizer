public class RCUPacket {

	private float i1, i2, i3, in, u12, u23, u31, u1n, u2n, u3n, freq, p, q, s,
			powerFactor;

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
		if (modbusResponseResult.length != 15) {
			throw new IllegalArgumentException("Array length have to be 15");
		} else {
			for (int i = 0; i < modbusResponseResult.length; i++) {

				switch (i) {
				case 0:
					i1 = modbusResponseResult[i] / 1000;
					break;
				case 1:
					i2 = modbusResponseResult[i] / 1000;
					break;
				case 2:
					i3 = modbusResponseResult[i] / 1000;
					break;
				case 3:
					in = modbusResponseResult[i] / 1000;
					break;
				case 4:
					u12 = modbusResponseResult[i] / 100;
					break;
				case 5:
					u23 = modbusResponseResult[i] / 100;
					break;
				case 6:
					u31 = modbusResponseResult[i] / 100;
					break;
				case 7:
					u1n = modbusResponseResult[i] / 100;
					break;
				case 8:
					u2n = modbusResponseResult[i] / 100;
					break;
				case 9:
					u3n = modbusResponseResult[i] / 100;
					break;
				case 10:
					freq = modbusResponseResult[i] / 100;
					break;
				case 11:
					p = modbusResponseResult[i] / 100;
					break;
				case 12:
					q = modbusResponseResult[i] / 100;
					break;
				case 13:
					s = modbusResponseResult[i] / 100;
					break;
				case 14:
					powerFactor = modbusResponseResult[i] / 1000;
				}

			}
		}
	}

}
