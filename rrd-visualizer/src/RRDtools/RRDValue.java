package RRDtools;

public class RRDValue {
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