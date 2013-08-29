package ua.pp.msk.ModbusAnalyzer;

import com.serotonin.modbus4j.ModbusLocator;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;

public class RCUPM700Analyzer extends RCUAnalyzer  {

	public static float powerFactorScale = -4;
	public static float frequencyScale = -2;
	
	ModbusLocator currentScaleLocator = null;
	ModbusLocator voltageScaleLocator = null;
	ModbusLocator powerScaleLocator = null;
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
	
	
	int[] scaleFactor = new int[4]; 
	
	
	public RCUPM700Analyzer(String host, int port, short device){
		super (host, port, device, PM700);
	}
	
	
	
	
	public RCUPM700Analyzer(TcpMaster mtm, short device){
		super(mtm, device, PM700);
	}
	
	
	private void setUpLocators(){
		short devNum = this.device;
		currentScaleLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4104,
				DataType.TWO_BYTE_INT_SIGNED);
		voltageScaleLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4105,
				DataType.TWO_BYTE_INT_SIGNED);
		powerScaleLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4106,
				DataType.TWO_BYTE_INT_SIGNED);
		realPowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4005,
				DataType.TWO_BYTE_INT_UNSIGNED);
		apparentPowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4006,
				DataType.TWO_BYTE_INT_UNSIGNED);
		reactivePowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4007,
				DataType.TWO_BYTE_INT_UNSIGNED);
		powerFactorLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4008,
				DataType.TWO_BYTE_INT_UNSIGNED);
		frequencyLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4012,
				DataType.TWO_BYTE_INT_UNSIGNED);
		currentLocatorA = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4019,
				DataType.TWO_BYTE_INT_UNSIGNED);
		currentLocatorB = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4020,
				DataType.TWO_BYTE_INT_UNSIGNED);
		currentLocatorC = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4021,
				DataType.TWO_BYTE_INT_UNSIGNED);
		currentLocatorN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4022,
				DataType.TWO_BYTE_INT_UNSIGNED);
		voltageLocatorAB = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4029,
				DataType.TWO_BYTE_INT_UNSIGNED);
		voltageLocatorBC = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4030,
				DataType.TWO_BYTE_INT_UNSIGNED);
		voltageLocatorCA = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4031,
				DataType.TWO_BYTE_INT_UNSIGNED);
		voltageLocatorAN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4032,
				DataType.TWO_BYTE_INT_UNSIGNED);
		voltageLocatorBN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4032,
				DataType.TWO_BYTE_INT_UNSIGNED);
		voltageLocatorCN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4034,
				DataType.TWO_BYTE_INT_UNSIGNED);
	}
	
	
	
	

	
	public RCUPacketFloat askDevice() throws ModbusInitException, ErrorResponseException, ModbusTransportException{
		return askDevice(tm, device);
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
	public RCUPacketFloat askDevice(TcpMaster mtm, short device)
			throws ModbusInitException, ErrorResponseException, ModbusTransportException {
		setUpLocators();
		if (!tm.isInitialized()) {
			tm.init();
		}
		short powerScale = (short) tm.getValue(powerScaleLocator);
		short currentScale = (short) tm.getValue(currentScaleLocator);
		short voltageScale = (short) tm.getValue(voltageScaleLocator);
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
		float[] toPack = new float[] { currentA, currentB, currentC, currentN,
				voltageAB, voltageBC, voltageCA, voltageAN, voltageBN,
				voltageCN, frequency, realPower, reactivePower, apparentPower,
				powerFactor };
		RCUPacketFloat rcup = new RCUPacketFloat(toPack);
		return rcup;
	}
	
	
	
	
}
