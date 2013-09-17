package RRDtools;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*This class describes Resource Records in RRD file, which were gained from 
 * rrdtool fetch mechanism; 
 */

public class RRDRR implements Comparable<RRDRR>, Serializable {

	/**
	 * Generated 17/09/2013
	 */
	private static final long serialVersionUID = 2084976594649667099L;
	private Calendar recordtime;
	private RRDValue[] recordvalues;
	public final static String regexpstring = "^([0-9]{10}):\\s+(([0-9]+,[0-9]{10}e[+-][0-9]{1,3}\\s*)+)$";
	
	
	public RRDRR(Calendar cal, RRDValue[] rrdvalues){
		this.recordtime = cal;
		this.recordvalues = rrdvalues;
	}
	
	public RRDRR(Calendar cal, double[] rrdvalues, String[] fieldnames) throws Exception{
		
		if (rrdvalues.length == fieldnames.length) {
			recordvalues = new RRDValue[rrdvalues.length];
			for (int i = 0; i < rrdvalues.length; i++) {
				recordvalues[i] = new RRDValue(rrdvalues[i], fieldnames[i]);
			}
		} else throw new Exception("Number of name and value fields differs!");
		this.recordtime = cal;
	}
	
	public RRDRR(Date dt, double[] rrdvalues, String[] fieldnames) throws Exception{
		this(Calendar.getInstance(), rrdvalues, fieldnames);
		recordtime.setTime(dt);
	}
	
	public RRDRR(Date dt, RRDValue[] rrdvalues){
		this(Calendar.getInstance(), rrdvalues);
		recordtime.setTime(dt);	
	}
	
	public int compareTo(RRDRR resRecord){
		return recordtime.compareTo(resRecord.getTime());
	}
	
	public RRDRR(String str, String[] fieldnames) throws Exception {
		this.recordvalues = new RRDValue[fieldnames.length];
		Matcher match;
		long timefield = 0;
		String[] valarray = null;
		Pattern recordpat = Pattern.compile(regexpstring);
		match = recordpat.matcher(str);
		if (match.find()) {
			this.recordtime = Calendar.getInstance(); 
			timefield = Long.parseLong(match.group(1)) * 1000;
			recordtime.setTime(new Date(timefield));
			valarray = match.group(2).split("\\s+");
			if (valarray.length == fieldnames.length) {
				for (int i = 0; i < valarray.length; i++){
					this.recordvalues[i] = new RRDValue(Double.parseDouble(valarray[i].replace(',', '.')), fieldnames[i]);
				}
			} else throw new Exception("Interface error! Number of field is not equal to number of parsed values!");
		} else throw new Exception("Error: Cannot parse input");
	}
	
	public static boolean RRDRRMatcher(String str){
		boolean istrue = false;
		Matcher match;
		Pattern recordpat = Pattern.compile(regexpstring);
		match = recordpat.matcher(str);
		if (match.find()) istrue = true;
		return istrue;
	}
	
	
	public Calendar getTime(){
		return recordtime;
	}
	
	public RRDValue[] getContents(){
		return recordvalues;
	}
	
	public RRDValue getContent(String name) throws IllegalArgumentException {
		int position = -1;
		for (int i = 0; i < recordvalues.length; i++){
			if (name.equals(recordvalues[i].getName())){
				position = i;
				break;
			} 
		}
		if (position != -1) {
			return recordvalues[position];
		} else throw new IllegalArgumentException("There is now field with specified name");
		
		
	}
	
	public RRDValue getContent(double value) throws Exception {
		int position = -1;
		for (int i = 0; i < recordvalues.length; i++){
			if (value == recordvalues[i].getValue()){
				position = i;
				break;
			} 
		}
		if (position != -1) {
			return recordvalues[position];
		} else throw new Exception("There is now field with specified name");
		
		
	}
	
	public String toString(){
		Long timestamp = (Long) recordtime.getTimeInMillis() / 1000;
		String output = timestamp + " - ";
		
		for (int i = 0; i < recordvalues.length; i++){
			Double valuetoprint = (Double) recordvalues[i].getValue(); 
			output += valuetoprint;
			if ( recordvalues.length - i != 1 ) output += " ";
			//output += recordvalues[i].getValue() + " ";
		}
		return output;
	}
}


