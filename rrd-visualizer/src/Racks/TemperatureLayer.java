package Racks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;

public class TemperatureLayer {
	private ArrayList<Rack> racks = new ArrayList<Rack>();
	private int width = 0, height = 0;
	private static int alphaValue = 0x55;

	public TemperatureLayer(int width, int height,  ArrayList<Rack> racks) {
		this.width = width;
		this.height = height;
		this.racks = racks;
		
	}
	
	
	
	public TemperatureLayer(BufferedImage img,  ArrayList<Rack> racks){
		this(img.getWidth(), img.getHeight(),  racks);
	}
	
	public TemperatureLayer(Dimension dim, ArrayList<Rack> racks){
		this(dim.width,  dim.height, racks);
	}

	public void addRack(Rack rack) {
		racks.add(rack);
	}

	public void clearRacks() {
		racks = new ArrayList<Rack>();
	}

	
	public BufferedImage getLayer(RackProperty rp){
		return getLayer(this.width, this.height, rp, this.racks);
	}
	
	public static BufferedImage getLayer(int width, int height, RackProperty rackProperty, ArrayList<Rack> racks) {
		BufferedImage layer = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D lGr2 = layer.createGraphics();

		if (racks.size() != 0) {
			for (Rack rack : racks) {
				rack.paintRadiation(rackProperty,
						Calendar.getInstance(), lGr2);
			}
			float hue;
			Color point = null;
			int dotRGB, dotRGBA;
			for (int k = 0; k < layer.getWidth(); k++) {
				for (int j = 0; j < layer.getHeight(); j++) {
					point = new Color(layer.getRGB(k, j), true);
					hue = point.getAlpha();
					hue /= 256;
					hue = 1 - hue;
					hue = Math.min(hue, 0.55f);
					dotRGB = Color.HSBtoRGB(hue, 1f, 1f);
					dotRGBA = dotRGB + (alphaValue * 0x1000000);
					dotRGB = 0;
					layer.setRGB(k, j, dotRGBA);
				}
			}
		}
		return layer;
	}

}
