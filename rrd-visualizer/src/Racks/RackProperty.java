package Racks;

import java.util.Calendar;


public abstract class RackProperty implements RackPropertyInterface, Comparable<RackProperty> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2614511279396352386L;
	protected String description;
	protected RadiationParameters radParam;
	protected Calendar start, stop;
	
	
	public RackProperty(String descr, RadiationParameters rp, Calendar start, Calendar stop) throws NullPointerException{
		description = descr;
		radParam = rp;
		if (start != null) { 
			if (stop != null) {
				this.stop = stop;
				this.start = start; 
				swapBounds();
			} else {
				throw new NullPointerException("Error: Stop time cannot be 'null'");
			}
		} else {
			throw new NullPointerException("Error: Start time cannot be 'null'");
		}
		
	}
	
	public RackProperty(String descr, RadiationParameters rp, Calendar[] bounds){
		this(descr, rp, bounds[0], bounds[bounds.length - 1]);
	}
	
	public Calendar[] getBounds(){
		return new Calendar[] {start, stop};
	}
	
	public Calendar getStartTime(){
		return start;
	}
	
	public Calendar getStopTime(){
		return stop;
	}
	
	public void setStartTime(){
		Calendar st = Calendar.getInstance();
		st.add(Calendar.WEEK_OF_YEAR, -1);
		start = st;
	}
	
	public void setStopTime(){
		stop = Calendar.getInstance();
	}
	
	public void setStartTime(Calendar cal){
		if (cal != null) { 
			this.start = cal; 
		} else {
			throw new NullPointerException("Error: Start time cannot be 'null'");
		}
	}
	
	public void setStopTime(Calendar cal){
		if (cal != null) {
			this.stop = cal;
		} else {
			throw new NullPointerException("Error: Stop time cannot be 'null'");
		}
	}
	
	private void swapBounds(){
		if (start.after(stop)) {
			Calendar tmpCal = stop;
			stop = start;
			start  = tmpCal;
		}
	}
	
	public void setBounds(Calendar[] bounds){
		if (bounds != null) {
			if (bounds.length < 1) {
				throw new IllegalArgumentException("Error: Array must be at least one value long");
			} else {
				start = bounds[0];
				stop = bounds[bounds.length - 1];
				swapBounds();
			}
		} else {
			throw new NullPointerException("Error: Array must be at least one value long");
		}
	}
	
	public int compareTo(RackProperty rackProperty){
		return description.compareTo(rackProperty.description);
	}
	
	@Override
	public String toString(){
		return description;
	}
	
	@Override
	public boolean equals(Object a){
		RackProperty rp = (RackProperty) a;
		return description.equals(rp.description);
	}
	
	@Override 
	public int hashCode(){
		return description.hashCode();
	}
}
