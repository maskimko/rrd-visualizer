package ua.pp.msk.ModbusAnalyzer;

import java.util.ArrayList;

import net.sourceforge.jmodbus.ModbusTCPMaster;

import com.serotonin.modbus4j.ModbusLocator;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;

public class RCUPM700Analyzer extends RCUAnalyzer  {

	public static final float powerFactorScale = 0.0001f;
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
		
	}
	//TODO
	//Connect new modbus library
	
	
	
	
	public RCUPM700Analyzer(ModbusTCPMaster mtm, short device){
		super(mtm, device, PM700);
		mtm.readMultipleRegisters(device, 4105, scaleFactor.length, 0,  scaleFactor);
		for (int i = 0; i < scaleFactor.length; i++){
			scaleFactor[i] += 0x100;
			System.out.print(" " + scaleFactor[i] + " :");
			
		}
	}
	
	
	private void setUpModbusLocators(){
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
	
	
	/**
	 * Generally this method is intended to correct data which we take from
	 * jmodbus module.
	 * 
	 * @param output
	 *            Accepts ArrayList<Integer> with list of data to produce
	 * @throws Exception
	 */
	private static void fit2RCU(ArrayList<Integer> output)
			throws Exception {

		if (!fallChecker(output)) {
			for (int counter = 0; counter < output.size(); counter++) {

				switch (counter) {
				/*
				 * case 2: output.set(counter, output.get(counter) - 0x100) ;
				 * break;
				 */
				case 6:
					output.set(counter, output.get(counter) - 0x100);
					break;
				case 8:
					output.set(counter, output.get(counter) - 0x100);
					break;
				case 11:
				case 12:
				case 13:
					output.set(counter, output.get(counter) - 0x100);
					break;
				}
			}
		} else {
			throw new Exception("Cannot get an answer");

		}
	}
	
	
	
	private static void joiner(ArrayList<Integer> result, int[] registers) {

		for (int i = 0; i < registers.length; i++) {

			result.add(registers[i] + 0x100);
		}
	}
	
	
	

	private static void normalizePower(int[] rp) {
		int swap = rp[1];
		rp[1] = rp[2];
		rp[2] = swap;
	}

	
	
	/**
	 * 
	 * @param mtm
	 *            ModbusTCPMaSTER worker which handles the connection
	 * @param device
	 *            Device number on the modbus bus
	 * @return RCUPacket object with measurements data
	 * @throws Exception
	 */
	public RCUPacket askDevice(ModbusTCPMaster mtm, short device)
			throws Exception {

		/**
		 * replyPower 4006 Total Real Power kW 4007 Total Apparent Power kVA
		 * 4008 Total Reactive Power kVAr 4009 Total Power Factor
		 */
		int[] replyPower = new int[4];
		/**
		 * 4030 Voltage, Phase 12 4031 Voltage, Phase 23 4032 Voltage, Phase 31
		 * 4033 Voltage, Phase 1N 4034 Voltage, Phase 2N 4035 Voltage, Phase 3N
		 */
		int[] replyVoltage = new int[6];

		/**
		 * 4020 Current, Instantaneous, Phase 1 4021 Current, Instantaneous,
		 * Phase 2 4022 Current, Instantaneous, Phase 3 4023 Current,
		 * Instantaneous, Neutral
		 */

		int[] replyCurrent = new int[4];
		/**
		 * 4013 Frequency (derived from Phase 1 )
		 */
		int[] replyFrequency = new int[1];

		ArrayList<Integer> output = new ArrayList<Integer>();
		mtm.readMultipleRegisters(device, 4019, replyCurrent.length, 0,
				replyCurrent);
		mtm.readMultipleRegisters(device, 4029, replyVoltage.length, 0,
				replyVoltage);
		mtm.readMultipleRegisters(device, 4012, replyFrequency.length, 0,
				replyFrequency);
		mtm.readMultipleRegisters(device, 4005, replyPower.length, 0,
				replyPower);

		joiner(output, replyCurrent);
		joiner(output, replyVoltage);
		joiner(output, replyFrequency);
		normalizePower(replyPower);
		joiner(output, replyPower);
		
		fit2RCU(output);
		

		RCUPacket rcup = new RCUPacket(output, deviceType);
		scale(scaleFactor, output);
		return rcup;
	}
	
	
	
	private void scale(int[] scaleFactor, ArrayList<Integer> toPack) {
		int multiplierI = (int) (RCUPacket.currentScaleFactor / Math.pow(10,
				scaleFactor[0]));
		int multiplierV = (int) (RCUPacket.voltageScaleFactor / Math.pow(10,
				scaleFactor[1]));
		int multiplierW = (int) (RCUPacket.powerScaleFactor / Math.pow(10,
				scaleFactor[1]));
		System.out.println("I* " + multiplierI + "V* " + multiplierV + "W* " + multiplierW);
		

	}
}
