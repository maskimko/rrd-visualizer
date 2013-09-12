package Racks;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.util.Calendar;

public class Radiation extends Ellipse2D.Float implements RadiationInterface {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5592985157892531905L;
	protected RadialGradientPaint radiation= null;
	private float hueDiff, brightnessDiff, saturationDiff;
	private int alphaDiff;
	protected float[] hr, sr, br, fractions;
	protected int[] ar;
	
	
	
	public Radiation(Rack rack, String propDescr, Calendar time, float[] hueRange, float[] saturationRange, float[] brightnessRange, int[] alphaRange, float[] fractions){
		super(RadiationSizer.getRadiationXFromRack(rack, propDescr, time), RadiationSizer.getRadiationYFromRack(rack, propDescr, time), RadiationSizer.getRadiationWidthFromRack(rack, propDescr, time), RadiationSizer.getRadiationHeightFromRack(rack, propDescr, time));
		this.fractions = fractions;
		hr = hueRange;
		sr = saturationRange;
		br = brightnessRange;
		ar = alphaRange;
		Color[] colors = new Color[fractions.length];
		hueDiff = (hr[0] - hr[hr.length - 1]) / colors.length;
		brightnessDiff = (br[0] - br[br.length - 1]) / colors.length;
		saturationDiff = (sr[0] - sr[sr.length - 1]) / colors.length;
		//alphaDiff = Math.round((ar[0] - ar[ar.length - 1]) / colors.length);
		alphaDiff = Math.max(ar[0], ar[ar.length -1]);
		int colorIntRGBValue, alpha, colorIntRGBAValue;
		float hue, saturation, brightness;
		for (int i = 0; i < colors.length; i++){
			hue = hr[0] - hueDiff * i;
			saturation = sr[0] - saturationDiff * i;
			brightness = br[0] - brightnessDiff * i;
			alphaDiff = (int) (ar[0] / Math.pow(2, (i + 1)));
			System.out.print(" alphaDiff: " + alphaDiff);
			alpha = alphaDiff;
			System.out.print(" alpha: " + alpha);
			colorIntRGBValue = Color.HSBtoRGB(hue, saturation, brightness);
			colorIntRGBAValue = colorIntRGBValue + alpha * 0x1000000;
			colors[i] = new Color(colorIntRGBAValue, true);
		}
		System.out.println();
		radiation = new RadialGradientPaint((float)rack.getCenterX(), (float)rack.getCenterY(), RadiationSizer.getRadiationRadiusFromRack(rack, propDescr, time), fract, colors, CycleMethod.NO_CYCLE);
	}

	
	
	public Radiation(Rack rack, String propDescr, Calendar time,  float[] hueRange, float[] saturationRange, float[] brightnessRange, int[] alphaRange){
		this(rack, propDescr, time,  hueRange, saturationRange, brightnessRange, alphaRange, fract);
	}
	
	public Radiation(Rack rack, String propDescr, Calendar time, RadiationParameters rp){
		this(rack, propDescr, time,   rp.getHueRange(), rp.getSaturationRange(), rp.getBrightnessRange(), rp.getAlphaRange(), rp.getFractions());
	}
	
	
	
	@Override
	public float[] getFractions(){
		return fractions; 
	}
	
	@Override
	public float[] getHueRange() {
		return hr;
	}

	@Override
	public float[] getSaturationRange() {
		return sr;
	}

	@Override
	public float[] getBrightnessRange() {
		return br;
	}

	@Override
	public int[] getAlphaRange(){
		return ar;
	}
	
	@Override
	public RadialGradientPaint getRadiationPaint() {
		return radiation;
	}

	@Override
	public void paintRadiation(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.SrcOver);
		g2.setPaint(radiation);
		g2.fill(this);
	}

	


}
