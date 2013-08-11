import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;


public class Radiation extends Ellipse2D.Float {


	

	private RadialGradientPaint rackradiation = new RadialGradientPaint(0, 0, 50,  new float[] {0.0f, 1f}, new Color[] {new Color(255, 0, 0 ,255), new Color(0, 0, 0, 0)}, CycleMethod.NO_CYCLE);

	
		
	public Radiation(Rack rack, RadiationColors colorset){
		super(RadiationSizer.getRadiationXFromRack(rack), RadiationSizer.getRadiationYFromRack(rack), RadiationSizer.getRadiationWidthFromRack(rack), RadiationSizer.getRadiationHeightFromRack(rack));
		this.rackradiation = new RadialGradientPaint((float)rack.getCenterX(), (float)rack.getCenterY(), RadiationSizer.getRadiationRadiusFromRack(rack), colorset.getFractions() ,colorset.getColors());
		//this.rackradiation = new RadialGradientPaint((float)rack.getCenterX(), (float)rack.getCenterY(), 50, colorset.getFractions() ,colorset.getColors());
	}
	
	public Radiation(Rack rack){
		this(rack, RadiationColors.temperatureColors());
	}

	public RadialGradientPaint getRadiationPaint(){
		return rackradiation;
	}
	
	public void paintRadiation(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setComposite(AlphaComposite.SrcOver);
		g2.setPaint(rackradiation);
		g2.fill(this);
	}
}

