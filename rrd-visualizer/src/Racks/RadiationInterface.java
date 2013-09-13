package Racks;

import java.awt.Graphics;
import java.awt.RadialGradientPaint;

public interface RadiationInterface {
	
	public final static float[] fract = new float[] {0.0f,  0.07f, 0.14f, 0.4f, 0.5f, 0.7f, 0.9f, 0.95f, 0.97f, 1f};
	public final static float[] hrTemp = new float[] {0f, 0,5f};
	public final static float[] srTemp = new float[] {1f};
	public final static float[] brTemp = new float[] {0f};
	public final static int[] alpharTemp = new int[] {0xff, 0x00};
	public final static RadiationParameters RadiationParametersTemp = new RadiationParameters(hrTemp, srTemp, brTemp, alpharTemp, fract);
	public final static float[] hrHum = new float[] {0.8f, 0.7f};
	public final static float[] srHum = new float[] {1f, 0.6f};
	public final static float[] brHum = new float[] {1f, 0,7f};
	public final static int[] alpharHum = new int[] {0x88, 0x00};
	public final static RadiationParameters RadiationParametersHum = new RadiationParameters(hrHum, srHum, brHum, alpharHum, fract);
	
	
	public float[] getHueRange();
	public float[] getSaturationRange();
	public float[] getBrightnessRange();
	public int[] getAlphaRange();
	public float[] getFractions();
	public RadialGradientPaint getRadiationPaint();
	public void paintRadiation(Graphics g);
}
