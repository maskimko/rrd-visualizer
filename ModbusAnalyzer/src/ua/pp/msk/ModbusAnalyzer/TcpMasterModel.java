package ua.pp.msk.ModbusAnalyzer;

import java.util.HashSet;
import java.util.Iterator;

import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;

public class TcpMasterModel {

	private final static HashSet<TcpMasterUnit> connections = new HashSet<TcpMasterUnit>();
	
	public static synchronized TcpMaster getConnection(String host){
		TcpMaster tm = null;
		TcpMasterUnit tmu = null;
	
			if (connections.contains(new TcpMasterUnit(host, 502, false, false))){
				Iterator<TcpMasterUnit> tmuIterator = connections.iterator();
				while (tmuIterator.hasNext()){
					tmu = tmuIterator.next();
					if (tmu.equals(new  TcpMasterUnit(host, 0, false, false))){
						tm = tmu.getTcpMaster();
					}
				}
			}
	
		return tm;
	}
	
	public static synchronized void setConnection(String host, int port, boolean keepAlive) {
		TcpMasterUnit tmu = new TcpMasterUnit(host, port, keepAlive, true);
		connections.add(tmu);
	}
	
	public static synchronized void replaceConnection(String host, int port, boolean keepAlive) {
		TcpMasterUnit tmu = new TcpMasterUnit(host, port, keepAlive, true);
		connections.remove(tmu);
		connections.add(tmu);
	}
	
	public static boolean containsConnection(String host, int port, boolean keepAlive){
		boolean exists = false;
		
		 exists= connections.contains(new TcpMasterUnit(host,port, keepAlive, false));
	
		return exists;
	}
}
