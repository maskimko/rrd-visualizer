package ua.pp.msk.ModbusAnalyzer;

import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;

public interface RCUAnalyzerInterface {

	public static final short PM500 = 0;
	public static final short PM700 = 1;
	
	public RCUPacketFloat askDevice() throws ModbusInitException, ErrorResponseException, ModbusTransportException;
	
	public RCUPacketFloat askDevice(TcpMaster mtm) throws  ModbusInitException, ErrorResponseException, ModbusTransportException;
	
	public String getIpAddress();
	public int getPort();
	public short getModbusDeviceNumber();
	public short getDeviceType();
	public void stop();
	
}
