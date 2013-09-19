package Racks;
import java.util.Calendar;


public  class RadiationSizer {

	private final static float influence = 7f;
	
	/*
	public static Ellipse2D.Float getRadiationEllipseFromRack(Rack rack, String propertyDescr, Calendar time){
		
		RackProperty rackProp = rack.getRackProperty(propertyDescr);
		float value = rackProp.getValue(time);
		return getRadiationEllipseFromRack(rack, value);	
	}
	
	
	public static Ellipse2D.Float getRadiationEllipseFromRack(Rack rack, float value){
		float centerX = (float) rack.getCenterX();
		float centerY = (float) rack.getCenterY();
		float ellipseW = getRadiationWidthFromRack(rack, value);
		float ellipseH = getRadiationHeightFromRack(rack, value);
		float ellipseX = centerX - ellipseW / 2;
		float ellipseY = centerY - ellipseH / 2;
		return new Ellipse2D.Float(ellipseX, ellipseY, ellipseW, ellipseH);
	}
	*/
	public static float getRadiationXFromRack(Rack rack, String propertyDescr, Calendar time){
		RackProperty rp  = rack.getRackProperty(propertyDescr);
		return getRadiationXFromRack(rack, rp.getValue(time)); 	
	}
	
	public static float  getRadiationXFromRack(Rack rack, float value){
		float centerX = (float) rack.getCenterX();
	    float ellipseW = getRadiationWidthFromRack(rack, value);
		float ellipseX = centerX - (ellipseW / 2);
		//System.out.println("Ellipse X: " + ellipseX);
		return ellipseX;
	}
	
	public static float getRadiationYFromRack(Rack rack, String propertyDescr, Calendar time){
		RackProperty rp  = rack.getRackProperty(propertyDescr);
		return getRadiationYFromRack(rack, rp.getValue(time)); 	
	}
	
	public static float  getRadiationYFromRack(Rack rack, float value){
	
		float centerY = (float) rack.getCenterY();
		float ellipseH = getRadiationHeightFromRack(rack, value);
		float ellipseY = centerY - (ellipseH / 2);
		//System.out.println("Ellipse Y: " + ellipseY);
		return ellipseY;
		
	}
	
	public static float getRadiationWidthFromRack(Rack rack, String propertyDescr, Calendar time){
		RackProperty rp  = rack.getRackProperty(propertyDescr);
		return getRadiationWidthFromRack(rack, rp.getValue(time)); 	
	}
	
	public static float  getRadiationWidthFromRack(Rack rack, float value){
		float coefficient =  value / 100 * influence + 1;
		float ellipseW = (float) (rack.getWidth() * coefficient);
		//System.out.println("Ellipse width: " + ellipseW);
		return ellipseW;
		
	}
	
	public static float getRadiationHeightFromRack(Rack rack, String propertyDescr, Calendar time){
		RackProperty rp  = rack.getRackProperty(propertyDescr);
		return getRadiationHeightFromRack(rack, rp.getValue(time)); 	
	}
	
	public static float  getRadiationHeightFromRack(Rack rack, float value){
		float coefficient =  value / 100 * influence + 1;
		float ellipseH = (float) (rack.getHeight() * coefficient);
		//System.out.println("Ellipse Height: " + ellipseH);
		return ellipseH;
		
	}
	
	public static float getRadiationRadiusFromRack(Rack rack, String propertyDescr, Calendar time){
		RackProperty rp  = rack.getRackProperty(propertyDescr);
		return getRadiationRadiusFromRack(rack, rp.getValue(time)); 	
	}
	
	public static float getRadiationRadiusFromRack(Rack rack, float value){
		float ellipseW = getRadiationWidthFromRack(rack, value);
		float ellipseH = getRadiationHeightFromRack(rack, value);
		float radius = (float)Math.min(ellipseW/2, ellipseH/2);
		//System.out.println("Ellipse radius: " + radius);
		return radius;
		
	}

}
