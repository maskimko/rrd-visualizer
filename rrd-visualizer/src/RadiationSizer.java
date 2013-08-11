import java.awt.geom.Ellipse2D;


public class RadiationSizer {

	private final static float influence = 7f;
	
	public static Ellipse2D.Float getRadiationEllipseFromRack(Rack rack){
		float centerX = (float) rack.getCenterX();
		float centerY = (float) rack.getCenterY();
		//float diagonal = (float) Math.sqrt(Math.pow(rack.getWidth(), 2) + Math.pow(rack.getHeight(), 2));
		float coefficient =  rack.getTemperature() / 100 * influence + 1;
		float ellipseW = (float) rack.getWidth() * coefficient;
		float ellipseH = (float) rack.getHeight() * coefficient;
		float ellipseX = centerX - ellipseW / 2;
		float ellipseY = centerY - ellipseH / 2;
		return new Ellipse2D.Float(ellipseX, ellipseY, ellipseW, ellipseH);
		
	}
	
	public static float  getRadiationXFromRack(Rack rack){
		float centerX = (float) rack.getCenterX();

		float coefficient =  rack.getTemperature() / 100 * influence + 1;
		float ellipseW = (float) rack.getWidth() * coefficient;
	
		float ellipseX = centerX - ellipseW / 2;

		return ellipseX;
		
	}
	
	public static float  getRadiationYFromRack(Rack rack){
	
		float centerY = (float) rack.getCenterY();

		float coefficient =  rack.getTemperature() / 100 * influence + 1;
		float ellipseH = (float) rack.getHeight() * coefficient;
		float ellipseY = centerY - ellipseH / 2;
		return ellipseY;
		
	}
	
	public static float  getRadiationWidthFromRack(Rack rack){

		float coefficient =  rack.getTemperature() / 100 * influence + 1;
		float ellipseW = (float) rack.getWidth() * coefficient;

		return ellipseW;
		
	}
	
	public static float  getRadiationHeightFromRack(Rack rack){

		float coefficient =  rack.getTemperature() / 100 * influence + 1;
	
		float ellipseH = (float) rack.getHeight() * coefficient;
		
		return ellipseH;
		
	}
	
	
	public static float getRadiationRadiusFromRack(Rack rack){
		float centerX = (float) rack.getCenterX();
		float centerY = (float) rack.getCenterY();
		//float diagonal = (float) Math.sqrt(Math.pow(rack.getWidth(), 2) + Math.pow(rack.getHeight(), 2));
		float temperature = rack.getTemperature();
		float coefficient =  temperature / 100 * influence + 1;
		//System.out.println("Coefficient: " + coefficient);
		float ellipseW = (float) rack.getWidth() * coefficient;
		float ellipseH = (float) rack.getHeight() * coefficient;
		float ellipseX = centerX - ellipseW / 2;
		float ellipseY = centerY - ellipseH / 2;
		float radius = (float)Math.min(ellipseW/2, ellipseH/2);
		return radius;
		
	}

}
