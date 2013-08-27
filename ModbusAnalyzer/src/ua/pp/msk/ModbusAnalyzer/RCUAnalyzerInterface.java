package ua.pp.msk.ModbusAnalyzer;

import net.sourceforge.jmodbus.ModbusTCPMaster;

public interface RCUAnalyzerInterface {

	public static final short PM500 = 0;
	public static final short PM700 = 1;
	
	public RCUPacket askDevice() throws Exception;
	
	public RCUPacket askDevice(ModbusTCPMaster mtm, short device) throws IllegalArgumentException, Exception;
	
	
	
	public void stop();
	
}
