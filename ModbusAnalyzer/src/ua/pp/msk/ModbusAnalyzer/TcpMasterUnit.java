package ua.pp.msk.ModbusAnalyzer;

import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;

class TcpMasterUnit  implements Comparable<TcpMasterUnit>{

	private TcpMaster tcpM = null;
	private IpParameters ipP = null;
	private String host;
	private int port; 
	private boolean keepAlive = true;
	
	
	
	public TcpMaster getTcpMaster() {
		return tcpM;
	}


	public IpParameters getIpPapameters() {
		return ipP;
	}


	public String getHost() {
		return host;
	}


	public int getPort() {
		return port;
	}


	public boolean isKeepAlive() {
		return keepAlive;
	}

	
	
	public TcpMasterUnit(String host, int port, boolean keepAlive, boolean initialize) throws ModbusInitException{
		this.host = host;
		this.port = port;
		this.keepAlive = keepAlive;
		ipP = new IpParameters();
		ipP.setHost(host);
		ipP.setPort(port);
		TcpMaster tm = new TcpMaster(ipP, keepAlive);
		tm.setTimeout(60);
		if (!tm.isInitialized() && initialize) {
			tm.init();
		}
		this.tcpM = tm;
	}
	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TcpMasterUnit other = (TcpMasterUnit) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		return true;
	}


	@Override
	public int compareTo(TcpMasterUnit o) {
		return host.compareTo(o.getHost());
	}




	

	
	
}