package RRDtools;

import java.io.Serializable;

public class RRDValue implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 346227018013151305L;
	protected double value;
	protected String name;
	
	public RRDValue(double value, String name){
		this.value = value;
		this.name = name;
	}
	
	public double getValue(){
		return value;
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		String output = ((Double)this.getValue()).toString();
		output.concat(" " + this.getName());
		return output;
	}
}