package Racks;

import java.util.Calendar;

public class RadiationHum extends Radiation {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4389931214689593298L;

	public RadiationHum(Rack rack, String propDescr, Calendar time){
			super(rack, propDescr, time, hrHum, srHum, brHum, alpharHum);
		}

}
