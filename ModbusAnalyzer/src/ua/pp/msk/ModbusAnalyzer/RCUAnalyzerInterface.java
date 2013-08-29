package ua.pp.msk.ModbusAnalyzer;

import com.serotonin.modbus4j.ip.tcp.TcpMaster;

public interface RCUAnalyzerInterface {

	public static final short PM500 = 0;
	public static final short PM700 = 1;
	
	public RCUPacketFloat askDevice() throws Exception;
	
	public RCUPacketFloat askDevice(TcpMaster mtm, short device) throws IllegalArgumentException, Exception;
	
	
	
	public void stop();
	
}
