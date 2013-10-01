package ua.pp.msk.ModbusAnalyzer;

import java.net.SocketException;

import com.serotonin.modbus4j.ModbusLocator;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;

public class RCUPM1200Analyzer extends RCUAnalyzer {

	//public static float powerFactorScale = -4;
	//public static float frequencyScale = -2;

	//ModbusLocator currentScaleLocator = null;
	//ModbusLocator voltageScaleLocator = null;
	//ModbusLocator powerScaleLocator = null;
	ModbusLocator realPowerLocator = null;
	ModbusLocator apparentPowerLocator = null;
	ModbusLocator reactivePowerLocator = null;
	ModbusLocator powerFactorLocator = null;
	ModbusLocator frequencyLocator = null;
	ModbusLocator currentLocatorA = null;
	ModbusLocator currentLocatorB = null;
	ModbusLocator currentLocatorC = null;
	ModbusLocator currentLocatorN = null;
	ModbusLocator voltageLocatorAB = null;
	ModbusLocator voltageLocatorBC = null;
	ModbusLocator voltageLocatorCA = null;
	ModbusLocator voltageLocatorAN = null;
	ModbusLocator voltageLocatorBN = null;
	ModbusLocator voltageLocatorCN = null;

	// int[] scaleFactor = new int[4];

	public RCUPM1200Analyzer(String host, int port, short device) {
		super(host, port, device, PM1200);
	}

	/*
	 * public RCUPM700Analyzer(TcpMaster mtm, short device){ super(mtm, device,
	 * PM700); }
	 */
	// device id register address 0125

	private void setUpLocators() {
		short devNum = this.device;
		/*
		 * currentScaleLocator = new ModbusLocator(devNum,
		 * RegisterRange.HOLDING_REGISTER, 4104, DataType.TWO_BYTE_INT_SIGNED);
		 * voltageScaleLocator = new ModbusLocator(devNum,
		 * RegisterRange.HOLDING_REGISTER, 4105, DataType.TWO_BYTE_INT_SIGNED);
		 * powerScaleLocator = new ModbusLocator(devNum,
		 * RegisterRange.HOLDING_REGISTER, 4106, DataType.TWO_BYTE_INT_SIGNED);
		 */
		realPowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 3902, DataType.FOUR_BYTE_FLOAT_SWAPPED);
		apparentPowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 3900,
				DataType.FOUR_BYTE_FLOAT_SWAPPED);
		reactivePowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 3904,
				DataType.FOUR_BYTE_FLOAT_SWAPPED);
		powerFactorLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 3906,
				DataType.FOUR_BYTE_FLOAT_SWAPPED);
		frequencyLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 3914,
				DataType.FOUR_BYTE_FLOAT_SWAPPED);
		currentLocatorA = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 3928,
				DataType.FOUR_BYTE_FLOAT_SWAPPED);
		currentLocatorB = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 3942,
				DataType.FOUR_BYTE_FLOAT_SWAPPED);
		currentLocatorC = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 3956,
				DataType.FOUR_BYTE_FLOAT_SWAPPED);
		currentLocatorN = new ModbusLocator(devNum,
				//TODO
				//find needed parameter
				//RegisterRange.HOLDING_REGISTER, 3702,
				RegisterRange.HOLDING_REGISTER, 3912,
				DataType.FOUR_BYTE_FLOAT_SWAPPED);
		voltageLocatorAB = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 3924,
				DataType.FOUR_BYTE_FLOAT_SWAPPED);
		voltageLocatorBC = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 3938,
				DataType.FOUR_BYTE_FLOAT_SWAPPED);
		voltageLocatorCA = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 3952,
				DataType.FOUR_BYTE_FLOAT_SWAPPED);
		voltageLocatorAN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 3926,
				DataType.FOUR_BYTE_FLOAT_SWAPPED);
		voltageLocatorBN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 3940,
				DataType.FOUR_BYTE_FLOAT_SWAPPED);
		voltageLocatorCN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 3954,
				DataType.FOUR_BYTE_FLOAT_SWAPPED);
	}

	public RCUPacketFloat askDevice() throws ModbusInitException,
			ErrorResponseException, ModbusTransportException, SocketException {
		return askDevice(tm);
	}

	/**
	 * 
	 * @param mtm
	 *            TcpMaster worker which handles the connection
	 * @param device
	 *            Device number on the modbus bus
	 * @return RCUPacket object with measurements data
	 * @throws Exception
	 */
	public RCUPacketFloat askDevice(TcpMaster mtm) throws ModbusInitException,
			ErrorResponseException, ModbusTransportException, SocketException {
		setUpLocators();
		if (!tm.isInitialized()) {
			tm.init();
		}
		//short powerScale = (short) tm.getValue(powerScaleLocator);
		//short currentScale = (short) tm.getValue(currentScaleLocator);
		//short voltageScale = (short) tm.getValue(voltageScaleLocator);
		
		/*
		int realPowerValue = (int) mtm.getValue(realPowerLocator);
		int apparentPowerValue = (int) mtm.getValue(apparentPowerLocator);
		int reactivePowerValue = (int) mtm.getValue(reactivePowerLocator);
		int powerFactorValue = (int) mtm.getValue(powerFactorLocator);
		int frequencyValue = (int) mtm.getValue(frequencyLocator);
		int currentValueA = (int) mtm.getValue(currentLocatorA);
		int currentValueB = (int) mtm.getValue(currentLocatorB);
		int currentValueC = (int) mtm.getValue(currentLocatorC);
		int currentValueN = (int) mtm.getValue(currentLocatorN);
		int voltageValueAB = (int) mtm.getValue(voltageLocatorAB);
		int voltageValueBC = (int) mtm.getValue(voltageLocatorBC);
		int voltageValueCA = (int) mtm.getValue(voltageLocatorCA);
		int voltageValueAN = (int) mtm.getValue(voltageLocatorAN);
		int voltageValueBN = (int) mtm.getValue(voltageLocatorBN);
		int voltageValueCN = (int) mtm.getValue(voltageLocatorCN);
		
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
		*/
		
		float realPower = (float) mtm.getValue(realPowerLocator) /1000;
		float apparentPower = (float) mtm.getValue(apparentPowerLocator) / 1000;
		float reactivePower = (float) mtm.getValue(reactivePowerLocator) / 1000;
		float powerFactor = (float) mtm.getValue(powerFactorLocator);
		float frequency = (float) mtm.getValue(frequencyLocator);
		float currentA = (float) mtm.getValue(currentLocatorA);
		float currentB = (float) mtm.getValue(currentLocatorB);
		float currentC = (float) mtm.getValue(currentLocatorC);
		float currentN = (float) mtm.getValue(currentLocatorN);
		float voltageAB = (float) mtm.getValue(voltageLocatorAB);
		float voltageBC = (float) mtm.getValue(voltageLocatorBC);
		float voltageCA = (float) mtm.getValue(voltageLocatorCA);
		float voltageAN = (float) mtm.getValue(voltageLocatorAN);
		float voltageBN = (float) mtm.getValue(voltageLocatorBN);
		float voltageCN = (float) mtm.getValue(voltageLocatorCN);
		
		float[] toPack = new float[] { currentA, currentB, currentC, currentN,
				voltageAB, voltageBC, voltageCA, voltageAN, voltageBN,
				voltageCN, frequency, realPower, reactivePower, apparentPower,
				powerFactor };
		RCUPacketFloat rcup = new RCUPacketFloat(toPack);
		return rcup;
	}

}
