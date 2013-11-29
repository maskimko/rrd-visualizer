package ua.pp.msk.ModbusAnalyzer;

import com.serotonin.modbus4j.ModbusLocator;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;

public class RCUPM500Analyzer extends RCUAnalyzer {

	public static float currentScale = -3;
	public static float voltageScale = -2;
	public static float powerScale = -2;
	public static float powerFactorScale = -3;
	public static float frequencyScale = -2;

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

	public RCUPM500Analyzer(String host, int port, short device, boolean keepAlive) {
		super(host, port, device, PM500, keepAlive);
	}
	
	public RCUPM500Analyzer(String host, int port, short device) {
		super(host, port, device, PM500);
	}

	public RCUPM500Analyzer(TcpMaster tm, short device) {
		super(tm, device, PM500);
	}
	/*
	public RCUPM500Analyzer(TcpMaster tm, short device) {
		super(tm, device, PM500);
	}
*/
	

	private void setUpLocators() {
		short devNum = this.device;
		realPowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 790,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		apparentPowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 794,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		reactivePowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 792,
				DataType.FOUR_BYTE_INT_SIGNED);
		powerFactorLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 870,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		frequencyLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 788,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		currentLocatorA = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 768,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		currentLocatorB = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 770,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		currentLocatorC = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 772,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		currentLocatorN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 774,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		voltageLocatorAB = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 776,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		voltageLocatorBC = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 778,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		voltageLocatorCA = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 780,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		voltageLocatorAN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 782,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		voltageLocatorBN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 784,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		voltageLocatorCN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 786,
				DataType.FOUR_BYTE_INT_UNSIGNED);
	}

	
	public RCUPacketFloat askDevice() throws ModbusInitException, ErrorResponseException, ModbusTransportException {
		return askDevice(tm);
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

	public RCUPacketFloat askDevice(TcpMaster mtm) throws ModbusInitException, ErrorResponseException, ModbusTransportException
			 {
		if (!tm.isInitialized()) {
			tm.init();
		}
		setUpLocators();
		long realPowerValue = (long) mtm.getValue(realPowerLocator);
		long apparentPowerValue = (long) mtm.getValue(apparentPowerLocator);
		long reactivePowerValue = (int) mtm.getValue(reactivePowerLocator);
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
