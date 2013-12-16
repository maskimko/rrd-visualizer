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

	private static TcpMaster tm = null;

	private static ModbusLocator currentScaleLocator = null;
	private static ModbusLocator voltageScaleLocator = null;
	private static ModbusLocator powerScaleLocator = null;
	private static ModbusLocator realPowerLocator = null;
	private static ModbusLocator apparentPowerLocator = null;
	private static ModbusLocator reactivePowerLocator = null;
	private static ModbusLocator powerFactorLocator = null;
	private static ModbusLocator frequencyLocator = null;
	private static ModbusLocator currentLocatorA = null;
	private static ModbusLocator currentLocatorB = null;
	private static ModbusLocator currentLocatorC = null;
	private static ModbusLocator currentLocatorN = null;
	private static ModbusLocator voltageLocatorAB = null;
	private static ModbusLocator voltageLocatorBC = null;
	private static ModbusLocator voltageLocatorCA = null;
	private static ModbusLocator voltageLocatorAN = null;
	private static ModbusLocator voltageLocatorBN = null;
	private static ModbusLocator voltageLocatorCN = null;

	private static void initLocators(int devNum) {
		currentScaleLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4104,
				DataType.TWO_BYTE_INT_SIGNED);
		voltageScaleLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4105,
				DataType.TWO_BYTE_INT_SIGNED);
		powerScaleLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4106,
				DataType.TWO_BYTE_INT_SIGNED);
		realPowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4005,
				DataType.TWO_BYTE_INT_UNSIGNED);
		apparentPowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4006,
				DataType.TWO_BYTE_INT_UNSIGNED);
		reactivePowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4007,
				DataType.TWO_BYTE_INT_UNSIGNED);
		powerFactorLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4008,
				DataType.TWO_BYTE_INT_UNSIGNED);
		frequencyLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4012,
				DataType.TWO_BYTE_INT_UNSIGNED);
		currentLocatorA = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4019,
				DataType.TWO_BYTE_INT_UNSIGNED);
		currentLocatorB = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4020,
				DataType.TWO_BYTE_INT_UNSIGNED);
		currentLocatorC = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4021,
				DataType.TWO_BYTE_INT_UNSIGNED);
		currentLocatorN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4022,
				DataType.TWO_BYTE_INT_UNSIGNED);
		voltageLocatorAB = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4029,
				DataType.TWO_BYTE_INT_UNSIGNED);
		voltageLocatorBC = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4030,
				DataType.TWO_BYTE_INT_UNSIGNED);
		voltageLocatorCA = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4031,
				DataType.TWO_BYTE_INT_UNSIGNED);
		voltageLocatorAN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4032,
				DataType.TWO_BYTE_INT_UNSIGNED);
		voltageLocatorBN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4032,
				DataType.TWO_BYTE_INT_UNSIGNED);
		voltageLocatorCN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 4034,
				DataType.TWO_BYTE_INT_UNSIGNED);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean keepAlive = true; // We don't keep alive TCP connection
		int devNum = 2;
		String ipAddr = "10.1.20.122";
		
		// IpParameters ipParm = new IpParameters();
		// ipParm.setHost("10.208.20.122");
		// ipParm.setPort(502);
		// ipParm.setEncapsulated(false);
		//
		// TcpMaster tm = new TcpMaster(ipParm, keepAlive);
		// tm.setTimeout(500);
		TcpMasterModel.setConnection(ipAddr, 502, keepAlive);
		tm = TcpMasterModel.getConnection(ipAddr);

		initLocators(devNum);

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
			// short dt = ConnectionTest4.determineRCUDeviceType(tm, (short) 2);
			// System.out.println("Device type is " + dt);
			for (int j = 0; j < 5; j++) {
				initLocators(devNum + j % 2);
				for (int i = 0; i < 2; i++) {
					int realPowerValue = (int) tm.getValue(realPowerLocator);
					System.out.println("got realPowervalue: ");

					int powerFactorValue = (int) tm
							.getValue(powerFactorLocator);
					System.out.println("got powerFactor");

					short powerScale = (short) tm.getValue(powerScaleLocator);
					System.out.println("got powerScale");

					int voltageABValue = (int) tm.getValue(voltageLocatorAB);
					System.out.println("got ABValue");

					short voltageScale = (short) tm
							.getValue(voltageScaleLocator);
					System.out.println("got voltageScale");

					int currentAValue = (int) tm.getValue(currentLocatorA);
					System.out.println("got AValue");

					short currentScale = (short) tm
							.getValue(currentScaleLocator);
					System.out.println("got currentScale");

					float realPower = (float) (realPowerValue * Math.pow(10,
							powerScale));
					float voltageAB = (float) (voltageABValue * Math.pow(10,
							voltageScale));
					float currentA = (float) (currentAValue * Math.pow(10,
							currentScale));
					float powerFactor = (float) (powerFactorValue * RCUPM700Analyzer.powerFactorScale);
					System.out.println("Real Power  " + realPower);
					System.out.println("Voltage AB " + voltageAB);
					System.out.println("Current A " + currentA);
					System.out.println("Power Factor " + powerFactor);
					Thread.sleep(1000);
				}
			}
		} catch (ModbusTransportException mte) {
			mte.printStackTrace();
		} catch (ErrorResponseException ere) {
			ere.printStackTrace();
		} catch (InterruptedException ie) {
			System.out.println("Thread has been inerrupted");
		} finally {
			tm.destroy();
		}

	}

}
