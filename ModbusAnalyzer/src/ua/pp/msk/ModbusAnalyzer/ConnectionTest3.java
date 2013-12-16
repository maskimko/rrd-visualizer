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


	private  static TcpMaster tm = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean keepAlive = false; // We don't keep alive TCP connection
		int devNum = 2;
		
		long slp = 500;
//		IpParameters ipParm = new IpParameters();
//		ipParm.setHost("10.208.20.122");
//		ipParm.setPort(502);
//		ipParm.setEncapsulated(false);
//
//		TcpMaster tm = new TcpMaster(ipParm, keepAlive);
//		tm.setTimeout(500);
		TcpMasterModel.setConnection("10.208.20.122", 502, keepAlive);
		tm = TcpMasterModel.getConnection("10.208.20.122");
		
		
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
		try {
			if (tm.isInitialized()) {
				System.out.println("tcpMaster has already been initialized.");
			} else {
			System.out.println("Trying to initialize connection");
			tm.init();
			}
		} catch (ModbusInitException mbie) {
			mbie.printStackTrace();
			return;
		}
		try {
			//short dt = ConnectionTest4.determineRCUDeviceType(tm, (short) 2);
			//System.out.println("Device type is " + dt);
			for (int i = 0; i < 10; i++) {
				int realPowerValue = (int) tm.getValue(realPowerLocator);
				System.out.println("got realPowervalue");
				Thread.sleep(slp);
				int powerFactorValue = (int) tm.getValue(powerFactorLocator);
				System.out.println("got powerFactor");
				Thread.sleep(slp);
				short powerScale = (short) tm.getValue(powerScaleLocator);
				System.out.println("got powerScale");
				Thread.sleep(slp);
				int voltageABValue = (int) tm.getValue(voltageLocatorAB);
				System.out.println("got ABValue");
				Thread.sleep(slp);
				short voltageScale = (short) tm.getValue(voltageScaleLocator);
				System.out.println("got voltageScale");
				Thread.sleep(slp);
				int currentAValue = (int) tm.getValue(currentLocatorA);
				System.out.println("got AValue");
				Thread.sleep(slp);
				short currentScale = (short) tm.getValue(currentScaleLocator);
				System.out.println("got currentScale");
				Thread.sleep(slp);
				float realPower = (float) (realPowerValue * Math.pow(10,
						powerScale));
				float voltageAB = (float) (voltageABValue * Math.pow(10,
						voltageScale));
				float currentA = (float) (currentAValue * Math.pow(10,
						currentScale));
				float powerFactor = (float) (powerFactorValue * RCUPM700Analyzer.powerFactorScale);
				System.out.println("Real Power  " + realPower );
				System.out.println("Voltage AB " + voltageAB );
				System.out.println("Current A " + currentA );
				System.out.println("Power Factor "+ powerFactor );
				Thread.sleep(1000);
			}

		} catch (ModbusTransportException mte) {
			mte.printStackTrace();
		} catch (ErrorResponseException ere) {
			ere.printStackTrace();
		} catch (InterruptedException ie) {
			System.out.println("Thread has been inerrupted");
		}
		finally {
			tm.destroy();
		}

	}

}
