package ua.pp.msk.ModbusAnalyzer;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusLocator;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;

public class ConnectionTest3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean keepAlive = false; //We don't keep alive TCP connection
		int devNum = 2;
		ModbusFactory factory = new ModbusFactory();
		IpParameters ipParm = new IpParameters();
		ipParm.setHost("10.208.20.122");
		ipParm.setPort(502);
		ipParm.setEncapsulated(false);
		
		TcpMaster tm = new TcpMaster(ipParm, keepAlive);
		ModbusLocator ml  = new ModbusLocator(devNum, RegisterRange.HOLDING_REGISTER, 4106, DataType.TWO_BYTE_INT_SIGNED);
		ModbusLocator powerLocator = new ModbusLocator(devNum, RegisterRange.HOLDING_REGISTER, 4006, DataType.TWO_BYTE_INT_UNSIGNED);
		try {
			tm.init();
		} catch (ModbusInitException mbie){
			mbie.printStackTrace();
			return;
		}
		try {
			int powerValue = (int) tm.getValue(powerLocator);
			short someValue = (short) tm.getValue(ml);
			float power = (float) (powerValue * Math.pow(10, someValue));
			System.out.println("Scale value I got " + someValue + "\nPower value I got " + powerValue);
			System.out.println("Power Value " + power);
		
		} catch (ModbusTransportException mte) {
			mte.printStackTrace();
		} catch (ErrorResponseException ere) {
			ere.printStackTrace();
		} finally {
			tm.destroy();
		}
		
	}

}
