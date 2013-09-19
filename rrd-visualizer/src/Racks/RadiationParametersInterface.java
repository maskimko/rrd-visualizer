package Racks;

import java.io.Serializable;

public interface RadiationParametersInterface extends Serializable {

	public float[] getHueRange();
	public float[] getSaturationRange();
	public float[] getBrightnessRange();
	public int[] getAlphaRange();
	public float[] getFractions();
	
	public void setHueRange(float[] hueRange) throws IllegalArgumentException;
	public void setSaturationRange(float[] saturationRange) throws IllegalArgumentException;
	public void setBrightnessRange(float[] brightnessRange) throws IllegalArgumentException;
	public void setAlphaRange(int[] alphaRange) throws IllegalArgumentException;
	public void setFractions(float[] fractions) throws IllegalArgumentException;
}
