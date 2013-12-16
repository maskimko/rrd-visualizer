package ua.pp.msk.ModbusAnalyzer;

import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;

class TcpMasterUnit implements Comparable<TcpMasterUnit> {

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

	/**
	 * @param host
	 *            Host to make tcp connection
	 * @param port
	 *            TCP port to connect
	 * @param keepAlive
	 *            do not close connection between requests
	 * @param initialize
	 *            initialize connection fist
	 * @param isEncapsulated
	 *            use encapsulated protocol (ASTELIT does not use it)
	 */
	public TcpMasterUnit(String host, int port, boolean keepAlive,
			boolean initialize, boolean isEncapsulated) {
		this.host = host;
		this.port = port;
		this.keepAlive = keepAlive;
		ipP = new IpParameters();
		ipP.setHost(host);
		ipP.setPort(port);
		ipP.setEncapsulated(isEncapsulated);
		TcpMaster tm = new TcpMaster(ipP, keepAlive);
		tm.setTimeout(10000);
		if (initialize) {
			try {
				tm.init();
			} catch (ModbusInitException mie) {
				mie.printStackTrace();
			}
		}
		this.tcpM = tm;
	}

	public TcpMasterUnit(String host, int port, boolean keepAlive,
			boolean initialize) {
		this(host, port, keepAlive, initialize, false);
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