package ua.pp.msk.ModbusAnalyzer;

import java.util.ArrayList;

import net.sourceforge.jmodbus.ModbusTCPMaster;

import com.serotonin.modbus4j.ModbusLocator;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;

public class RCUPM700Analyzer extends RCUAnalyzer  {

	public static float powerFactorScale = -3;
	public static float frequencyScale = -1;
	
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
		ModbusLocator currentScaleLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4104,
				DataType.TWO_BYTE_INT_SIGNED);
		ModbusLocator voltageScaleLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4105,
				DataType.TWO_BYTE_INT_SIGNED);
		ModbusLocator powerScaleLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4106,
				DataType.TWO_BYTE_INT_SIGNED);
		ModbusLocator realPowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4005,
				DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator apparentPowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4006,
				DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator reactivePowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4007,
				DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator powerFactorLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4008,
				DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator frequencyLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4012,
				DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator currentLocatorA = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4019,
				DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator currentLocatorB = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4020,
				DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator currentLocatorC = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4021,
				DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator currentLocatorN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4022,
				DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorAB = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4029,
				DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorBC = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4030,
				DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorCA = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4031,
				DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorAN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4032,
				DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorBN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4032,
				DataType.TWO_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorCN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4034,
				DataType.TWO_BYTE_INT_UNSIGNED);
	}
	
	
	

	
	public RCUPacketFloat askDevice() throws Exception{
		return askDevice(this.tm, this.device);
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
	public RCUPacketFloat askDevice(ModbusTCPMaster mtm, short device)
			throws Exception {

		
		return rcup;
	}
	
	
	
	
}
