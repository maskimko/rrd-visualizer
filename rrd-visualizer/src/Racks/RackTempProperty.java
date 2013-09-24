package Racks;


import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.TreeSet;

import RRDtools.CommandResult;
import RRDtools.RRDRR;
import RRDtools.RRDValue;
import RRDtools.RRDp;

public class RackTempProperty extends RackProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2796204285632862114L;
	public static final String rackTempDescription = "Rack Temperature";
	private File rrdf;
	private transient RRDp rrdProcessor;
	private transient String tmpDir;
	private String cf = AVERAGE;
	private TreeSet<RRDRR>  rrdValues;
	private static final String fieldId ="sensStateTemper";
	public static final String AVERAGE = "AVERAGE";
	public static final String MIN = "MIN";
	public static final String MAX = "MAX";
	public static final String LAST = "LAST";
	
	public RackTempProperty(RadiationParameters radParm, File rrdFile, String consFunc,  Calendar startTime, Calendar stopTime) throws IllegalArgumentException, NullPointerException, IOException{
		super(rackTempDescription, radParm, startTime, stopTime);
		if (rrdFile != null) {
			rrdf = rrdFile;
		} else {
			throw new NullPointerException("Error: You should provide RRD file");
		}
		tmpDir = System.getProperty("java.io.tmpdir");
		rrdProcessor = new RRDp(tmpDir); 
		
		if (consFunc == AVERAGE || consFunc == MAX || consFunc == MIN || consFunc == LAST) {
			cf = consFunc;
			try {
				rrdValues = getResult(getRRDCommand());
			} catch (Exception e){
				System.err.println("Cannot get data " + e.getMessage());
			}
		} else {
			throw new IllegalArgumentException("Consolidation Function must be one of those AVERAGE MIN MAX LAST");
		}
	}
	
	
	public void updateTimeBounds(Calendar start, Calendar stop){
		this.start = start; 
		this.stop = stop;
		try {
			rrdValues = getResult(getRRDCommand());
		} catch (Exception e) {
			System.err.println("Cannot get data " + e.getMessage());
		}
	}
	
	
	@Override
	public float getValue(Calendar cal){
		float retVal = 0;
		RRDRR resRecord = rrdValues.floor(new RRDRR(cal, null));
		try {
			RRDValue rrdVal = resRecord.getContent(RackTempProperty.fieldId);
			retVal = (float) rrdVal.getValue();
		} catch (IllegalArgumentException iae) {
			System.err.println(iae.getMessage());
		} 
			return retVal;
	}
	

	
	private TreeSet<RRDRR> getResult(String[] command) throws Exception{
		CommandResult result = rrdProcessor.command(command);
		TreeSet<RRDRR> answer = null;
		if (!result.ok){
			throw new Exception("Error: " + result.error);
		} else {
			answer = result.getOutputObject();
		}
		return answer;
	}
	
	private String[] getRRDCommand(){
		String startUnix = "" + (int) (start.getTimeInMillis() / 1000);
		String stopUnix = "" + (int) (stop.getTimeInMillis() / 1000);
		String[] cmd = new String[] {"fetch", rrdf.getAbsolutePath(), cf, "-s", startUnix, "-e", stopUnix};
		return cmd;
	}
	
	public String getConsolidationFunction(){
		return cf;
	}
	
	public String getTempDirectory(){
		return tmpDir;
	}
	
	

	
	

	@Override
	public Radiation getRadiation(Rack rack, Calendar cal) {
	  return new Radiation(rack, this.description, cal, RadiationTemp.RadiationParametersTemp);
	}

	

}
