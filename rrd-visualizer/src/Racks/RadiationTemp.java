package Racks;

import java.util.Calendar;



public class RadiationTemp extends Radiation {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8009911782327517098L;
	
	
	
	public RadiationTemp(Rack rack, String propDescr, Calendar time){
		super(rack, propDescr, time, hrTemp, srTemp, brTemp, alpharTemp);
	}

	
	

}
