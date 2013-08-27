package ua.pp.msk.SNMPAgent;

import ua.pp.msk.ModbusAnalyzer.RCUAnalyzer;
import ua.pp.msk.ModbusAnalyzer.RCUPacket;
import ua.pp.msk.ModbusAnalyzer.RCUPacketGenerator;

public class RCUDevice {
	
	public static final int KYIV = 1;
	public static final int DNIPROPETROVSK = 2;
	public static final int DONETSK = 3;
	public static final int KHARKIV = 4;
	public static final int LVIV = 5;
	public static final int SIMFEROPOL = 6;
	public static final int ODESA = 7;
	
	private int city;
	private int port;
	private short modbusDeviceNumber;
	private String description;
	private String ipAddress;
	private RCUPacket pack;

	
	
	public RCUDevice(int city, String descr, String ipAddress, int port, short modbusDeviceNumber,  RCUPacket pack) {
		this.city = city;
		this.description = descr;
		this.ipAddress = ipAddress;
		this.port = port; 
		this.modbusDeviceNumber = modbusDeviceNumber;
		
		this.pack = pack;
	}
	
	public int getCity(){
		return city;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getIpAddress(){
		return ipAddress;
	}
	
	public int getPort(){
		return port;
	}
	
	public int getModbusDeviceNumber(){
		return modbusDeviceNumber;
	}
	

	
	public int[] getRCUStats(){
		return pack.getAll();
	}
	
	public RCUPacket getRCUPacket(){
		return pack;
	}
	
	public static RCUDevice createBaseKyivRCUDevice(short modbusDeviceNumber)  {
		
		RCUDevice dev = new RCUDevice(RCUDevice.KYIV, "Kyiv power input RCU device", "10.1.20.122", 502,  modbusDeviceNumber, RCUPacketGenerator.getZeroPacket());
		return dev;
	}
	public static RCUDevice createBaseDnipropetrovskRCUDevice(short modbusDeviceNumber){
		RCUDevice dev = new RCUDevice(RCUDevice.DNIPROPETROVSK, "Dnipropetrovsk power input RCU device", "10.144.20.122", 502, modbusDeviceNumber, RCUPacketGenerator.getZeroPacket());
		return dev;
	}
	public static RCUDevice createBaseDonetskRCUDevice(short modbusDeviceNumber){
		RCUDevice dev = new RCUDevice(RCUDevice.DONETSK, "Donetsk power input RCU device", "10.176.20.122", 502, modbusDeviceNumber, RCUPacketGenerator.getZeroPacket());
		return dev;
	}
	public static RCUDevice createBaseKharkivRCUDevice(short modbusDeviceNumber){
		RCUDevice dev = new RCUDevice(RCUDevice.KHARKIV, "Kharkiv power input RCU device", "10.160.20.122", 502, modbusDeviceNumber, RCUPacketGenerator.getZeroPacket());
		return dev;
	}
	public static RCUDevice createBaseLvivRCUDevice(short modbusDeviceNumber){
		RCUDevice dev = new RCUDevice(RCUDevice.LVIV, "Lviv power input RCU device", "10.224.20.122", 502, modbusDeviceNumber, RCUPacketGenerator.getZeroPacket());
		return dev;
	}
	public static RCUDevice createBaseSimferopolRCUDevice(short modbusDeviceNumber){
		RCUDevice dev = new RCUDevice(RCUDevice.SIMFEROPOL, "Simferopol power input RCU device", "10.192.20.122", 502, modbusDeviceNumber, RCUPacketGenerator.getZeroPacket());
		return dev;
	}
	public static RCUDevice createBaseOdesaRCUDevice(short modbusDeviceNumber){
		RCUDevice dev = new RCUDevice(RCUDevice.ODESA, "Odesa power input RCU device", "10.208.20.122", 502, modbusDeviceNumber, RCUPacketGenerator.getZeroPacket());
		return dev;
	}
}
