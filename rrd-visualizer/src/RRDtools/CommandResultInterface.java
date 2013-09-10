package RRDtools;

import java.util.ArrayList;

public interface CommandResultInterface{
	float user = 0;
    float system = 0;
    float total = 0;
    public boolean ok = false;
    public String error = null;
    //public String output = null;
    
    ArrayList<String> names = null;
    
    public void setFieldNames(String[] fields);
    public void addFieldName(String fname);
    
    public float getUser();

	public float getSystem();

	public float getTotal();

	public boolean isOk();

	public String getError();

	public void setOutput(String output)throws Exception;
	public void setOutput(StringBuffer sb) throws Exception;
	
	public String getOutput();

	//public byte[] getImageBytes();

    public String toString(); 
}
