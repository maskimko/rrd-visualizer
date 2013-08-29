package ua.pp.msk.ModbusAnalyzer;

import java.util.ArrayList;

import net.sourceforge.jmodbus.ModbusTCPMaster;

import com.serotonin.modbus4j.ModbusLocator;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;

public class RCUPM500Analyzer extends RCUAnalyzer {

	public static float currentScale = -3;
	public static float voltageScale = -2;
	public static float powerScale = -2;
	public static float powerFactorScale = -3;
	public static float frequencyScale = -1;

	private ModbusLocator realPowerLocator = null;
	private ModbusLocator apparentPowerLocator = null;
	private ModbusLocator reactivePowerLocator = null;
	private ModbusLocator powerFactorLocator = null;
	private ModbusLocator frequencyLocator = null;
	private ModbusLocator currentLocatorA = null;
	private ModbusLocator currentLocatorB = null;
	private ModbusLocator currentLocatorC = null;
	private ModbusLocator currentLocatorN = null;
	private ModbusLocator voltageLocatorAB = null;
	private ModbusLocator voltageLocatorBC = null;
	private ModbusLocator voltageLocatorCA = null;
	private ModbusLocator voltageLocatorAN = null;
	private ModbusLocator voltageLocatorBN = null;
	private ModbusLocator voltageLocatorCN = null;

	public RCUPM500Analyzer(String host, int port, short device) {
		super(host, port, device, PM500);
	}

	public RCUPM500Analyzer(TcpMaster tm, short device) {
		super(tm, device, PM500);
	}

	

	private void setUpLocators() {
		short devNum = this.device;
		ModbusLocator realPowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 790,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator apparentPowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 792,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator reactivePowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 794,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator powerFactorLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 870,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator frequencyLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 788,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator currentLocatorA = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 768,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator currentLocatorB = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 770,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator currentLocatorC = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 772,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator currentLocatorN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 774,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorAB = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 776,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorBC = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 778,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorCA = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 780,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorAN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 782,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorBN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 784,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorCN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 786,
				DataType.FOUR_BYTE_INT_UNSIGNED);
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

	public RCUPacketFloat askDevice(TcpMaster mtm, short device)
			throws Exception {

		setUpLocators();
		long realPowerValue = (long) mtm.getValue(realPowerLocator);
		long apparentPowerValue = (long) mtm.getValue(apparentPowerLocator);
		long reactivePowerValue = (long) mtm.getValue(reactivePowerLocator);
		long powerFactorValue = (long) mtm.getValue(powerFactorLocator);
		long frequencyValue = (long) mtm.getValue(frequencyLocator);
		long currentValueA = (long) mtm.getValue(currentLocatorA);
		long currentValueB = (long) mtm.getValue(currentLocatorB);
		long currentValueC = (long) mtm.getValue(currentLocatorC);
		long currentValueN = (long) mtm.getValue(currentLocatorN);
		long voltageValueAB = (long) mtm.getValue(voltageLocatorAB);
		long voltageValueBC = (long) mtm.getValue(voltageLocatorBC);
		long voltageValueCA = (long) mtm.getValue(voltageLocatorCA);
		long voltageValueAN = (long) mtm.getValue(voltageLocatorAN);
		long voltageValueBN = (long) mtm.getValue(voltageLocatorBN);
		long voltageValueCN = (long) mtm.getValue(voltageLocatorCN);
		float realPower = (float) (realPowerValue * Math.pow(10, powerScale));
		float apparentPower = (float) (apparentPowerValue * Math.pow(10,
				powerScale));
		float reactivePower = (float) (reactivePowerValue * Math.pow(10,
				powerScale));
		float powerFactor = (float) (powerFactorValue * Math.pow(10,
				powerFactorScale));
		float currentA = (float) (currentValueA * Math.pow(10, currentScale));
		float currentB = (float) (currentValueB * Math.pow(10, currentScale));
		float currentC = (float) (currentValueC * Math.pow(10, currentScale));
		float currentN = (float) (currentValueN * Math.pow(10, currentScale));
		float voltageAB = (float) (voltageValueAB * Math.pow(10, voltageScale));
		float voltageBC = (float) (voltageValueBC * Math.pow(10, voltageScale));
		float voltageCA = (float) (voltageValueCA * Math.pow(10, voltageScale));
		float voltageAN = (float) (voltageValueAN * Math.pow(10, voltageScale));
		float voltageBN = (float) (voltageValueBN * Math.pow(10, voltageScale));
		float voltageCN = (float) (voltageValueCN * Math.pow(10, voltageScale));
		float frequency = (float) (frequencyValue * Math
				.pow(10, frequencyScale));
		float[] toPack = new float[] { currentA, currentB, currentC, currentN,
				voltageAB, voltageBC, voltageCA, voltageAN, voltageBN,
				voltageCN, frequency, realPower, reactivePower, apparentPower,
				powerFactor };

		RCUPacketFloat rcup = new RCUPacketFloat(toPack);

		return rcup;
	}

}
