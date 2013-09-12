package Racks;

import java.util.Calendar;

public interface RackPropertyInterface {

	public Calendar[] getBounds();
	public void setBounds(Calendar[] bounds) throws NullPointerException;
	public Calendar getStartTime();
	public Calendar getStopTime();
	public void setStartTime() throws NullPointerException;
	public void setStopTime() throws NullPointerException;
	public float getValue(Calendar cal) throws IllegalArgumentException, NullPointerException;
	public Radiation getRadiation(Rack rack, Calendar cal) throws IllegalArgumentException, NullPointerException; 
	public String getDescription();
}
