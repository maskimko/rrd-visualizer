package RRDtools;
/*

 Copyright (c) 2010 by Peter Stamfest <peter@stamfest.at>

 This file is part of java-rrd.

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 Except as contained in this notice, the name of Peter Stamfest shall not 
 be used in advertising or otherwise to promote the sale, use or other 
 dealings in this Software without prior written authorization from 
 Peter Stamfest.

*/




import java.util.ArrayList;
import java.util.TreeSet;
//import java.util.HashMap;

public class CommandResult implements CommandResultInterface{
    float user;
    float system;
    float total;
    public boolean ok = false;
    public String error = null;
    private String output = null;
    private ArrayList<String> fieldsnamelist= new ArrayList<String>();
    private TreeSet<RRDRR> outputobject= new TreeSet<RRDRR>();
    // support for output from special commands
    //public HashMap<String, String> info = null;  // info, graphv
	//public byte image[] = null; // graphv
    
	public void addFieldName(String fieldname){
		fieldsnamelist.add(fieldname);
	}
	
	public void setFieldNames(String[] fieldnames){
		for (String fname : fieldnames){
			addFieldName(fname);
		}
	}
	
	protected String[] getFieldNames(){
		String[] fn = new String[fieldsnamelist.size()];
		int i = 0; 
		for (String cs : fieldsnamelist){
			fn[i++] = cs;
		}
		return fn;
	}
	
    public float getUser() {
		return user;
	}

	public float getSystem() {
		return system;
	}

	public float getTotal() {
		return total;
	}

	public boolean isOk() {
		return ok;
	}

	public String getError() {
		return error;
	}

	public void setOutput(String output) throws Exception{
		this.output = output;
		output2Object();
	}
	
	public void setOutput(StringBuffer sb) throws Exception{
		this.output = sb.toString();
		output2Object();
	}
	
	public String getOutput() {
		return output;
	}

	private void output2Object() throws Exception{
		if (this.output.length() == 0) throw new Exception("Error: Empty output"); 
		else if (fieldsnamelist.size() == 0) throw new Exception("Error: Empty fields list");
			else {
			String[] fl = new String[fieldsnamelist.size()];
			int i = 0;
			for (String field : fieldsnamelist){
				fl[i] = field;
				i++;
			}
			String[] valrecords = this.output.split("\n");
			for (String vr :valrecords) {
				if (RRDRR.RRDRRMatcher(vr)) outputobject.add(new RRDRR(vr, fl));
			}
		}
	}
	
	public TreeSet<RRDRR> getOutputObject(){
		return outputobject;
	}
	
	/*public HashMap<String, String> getInfo() {
		return info;
	}*/

	/*public byte[] getImageBytes() {
		return image;
	}*/

    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append("ok:").append(ok).append('\n');
    	sb.append("u:").append(user);
    	sb.append(" s:").append(system);
    	sb.append(" t:").append(total).append('\n');
    	sb.append("message:").append(error).append('\n');
    	sb.append("output:").append(output).append('\n');

    	return sb.toString();
    }
}
