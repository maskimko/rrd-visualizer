import java.awt.Color;


public class RadiationColors {

	//private Color[] temperaturecolors = new Color[] {new Color(0xffff0000), new Color(0xd0ff0000), new Color(0x78ff0000), new Color(0x30ff0000), new Color(0x00ff0000)};
	private final static float[] temperaturedistances = new float[] {0.0f, 0.01f, 0.2f, 0.3f, 0.7f, 0.9f, 1f};
	private final static Color[] temperaturecolors = new Color[] {new Color(255, 0, 0, 220 ), new Color(255, 0, 0, 150 ),  new Color(255, 0, 0, 100 ), new Color(255, 0, 0, 50 ), new Color(255, 0, 0, 30 ), new Color(255, 0, 0, 15 ), new Color(255, 0, 0, 0 )};
	private final static float[] humiditydistances = new float[] {0.2f, 0.8f};
	private final static Color[] humiditycolors = new Color[] {new Color(255, 0, 0, 255),  new Color(0, 0, 0, 255)};
	protected Color[] colors;
	protected float[] fractions;
	
	private RadiationColors(Color[] colors, float[] fractions){
		this.colors = colors;
		this.fractions = fractions;
	}
	
	public static RadiationColors temperatureColors(){
		return new RadiationColors(temperaturecolors, temperaturedistances);
	}

	
	public static Color[] getTemperatureColorsArray (){
		return temperaturecolors;
	}
	
	public static Color[] getHumidityColorsArray (){
		return humiditycolors;
	}
	
	public static float[] getTemperatureFractionsArray (){
		return temperaturedistances;
	}
	
	public static float[] getHumidityFractionsArray (){
		return humiditydistances;
	}
	
	
	public static RadiationColors humidityColors(){
		return new RadiationColors(humiditycolors, humiditydistances);
	}
	
	public Color[] getColors(){
		return this.colors;
	}
	
	public float[] getFractions(){
		return this.fractions;
	}
}
