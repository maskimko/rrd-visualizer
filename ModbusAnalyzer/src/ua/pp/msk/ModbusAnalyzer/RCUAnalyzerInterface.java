package ua.pp.msk.ModbusAnalyzer;

import java.net.SocketException;

import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;

public interface RCUAnalyzerInterface {

	public static final short PM500 = 0;
	public static final short PM700 = 1;
	public static final short PM1200 = 2;
	
	public RCUPacketFloat askDevice() throws ModbusInitException, ErrorResponseException, ModbusTransportException, SocketException;
	
	public RCUPacketFloat askDevice(TcpMaster mtm) throws  ModbusInitException, ErrorResponseException, ModbusTransportException, SocketException;
	
	public String getIpAddress();
	public int getPort();
	public short getModbusDeviceNumber();
	public short getDeviceType();

	
}
