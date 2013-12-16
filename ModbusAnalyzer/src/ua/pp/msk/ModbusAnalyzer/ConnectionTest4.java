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

public class ConnectionTest4 {

	
	private static float currentScale = -3;
	private static float voltageScale = -2;
	private static float powerScale = -2;
	private static float powerFactorscale = -3;
	
	
	public static short determineRCUDeviceType(TcpMaster tm, short devnum)
			throws ModbusTransportException, InterruptedException {
		short dt = -1;
		boolean isPM500 = false;
		boolean isPM700 = false;
		try {
			ModbusLocator devTypePM500Locator = new ModbusLocator(devnum,
					RegisterRange.HOLDING_REGISTER, 64647,
					DataType.TWO_BYTE_INT_UNSIGNED);
			int idPM500 = (int) tm.getValue(devTypePM500Locator);
			System.out.println("This is PM500 device id:" + idPM500);
			isPM500 = true;
		} catch (ErrorResponseException erePM500) {
			
		}
		try {
			ModbusLocator devTypePM700Locator = new ModbusLocator(devnum,
					RegisterRange.HOLDING_REGISTER, 7003,
					DataType.TWO_BYTE_INT_UNSIGNED);
			int idPM700 = (int) tm.getValue(devTypePM700Locator);
			System.out.println("This is PM700 device id:" + idPM700);
			isPM700 = true;
		} catch (ErrorResponseException erePM700) {
			
		}
		if (isPM500) {
			dt = 0;
		} else if (isPM700) {
			dt = 1;
		}
		Thread.sleep(10);
		return dt;
	}

	//768 28
	//870 2
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean keepAlive = true; // We don't keep alive TCP connection
		int devNum = 2;
		IpParameters ipParm = new IpParameters();
		ipParm.setHost("10.192.20.122");
		ipParm.setPort(502);
		ipParm.setEncapsulated(false);

		TcpMaster tm = new TcpMaster(ipParm, keepAlive);
		tm.setTimeout(2000);


		ModbusLocator realPowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 790,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator apparentPowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 792,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator reactivePowerLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 794,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator powerFactorLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 870,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator frequencyLocator = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 788,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator currentLocatorA = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 768,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator currentLocatorB = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 770,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator currentLocatorC = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 772,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator currentLocatorN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 774,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorAB = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 776,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorBC = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 778,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorCA = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 780,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorAN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 782,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorBN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 784,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		ModbusLocator voltageLocatorCN = new ModbusLocator(devNum,
				RegisterRange.HOLDING_REGISTER, 786,
				DataType.FOUR_BYTE_INT_UNSIGNED);
		try {
			tm.init();
		} catch (ModbusInitException mbie) {
			mbie.printStackTrace();
			return;
		}
		try {
			short dt = determineRCUDeviceType(tm, (short) 2);
			System.out.println("Device type is " + dt);
			for (int i = 0; i < 10; i++) {
				long realPowerValue = (long) tm.getValue(realPowerLocator);
				long powerFactorValue = (long) tm.getValue(powerFactorLocator);
			
				long voltageABValue = (long) tm.getValue(voltageLocatorAB);

				long currentAValue = (long) tm.getValue(currentLocatorA);
				long currentBValue = (long) tm.getValue(currentLocatorB);
		
				float realPower = (float) (realPowerValue * Math.pow(10, powerScale));
				float voltageAB = (float) (voltageABValue * Math.pow(10, voltageScale));
				float currentA = (float) (currentAValue * Math.pow(10, currentScale));
				float currentB = (float) (currentBValue * Math.pow(10, currentScale));
				float powerFactor = (float) (powerFactorValue * Math.pow(10, powerFactorscale));
				System.out.println("Real Power  " + realPower);
				System.out.println("Voltage AB " + voltageAB);
				System.out.println("Current A " + currentA);
				System.out.println("Current B " + currentB);
				System.out.println("Power Factor " + powerFactor);
				Thread.sleep(3000);
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
