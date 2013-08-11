import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class Rack extends Rectangle {

	
	private float temperature = 20f;
	private float humidity = 50f;
	//Power measures in kiloWatts
	private float power = 1;
	private String name;
	private Radiation rackradiation;
	final static int tbasecolor = 0xff0000;
	final static int hbasecolor = 0x0000ff;
	final static int pbasecolor = 0xdd20dd;
	
	

	
	
	
	
	public Rack(String name, int x, int y, int width, int height, float temperature) {
		super(x, y, width, height);
		this.temperature = temperature;
		this.name = name;
		this.rackradiation = new Radiation(this, RadiationColors.temperatureColors());
	}
	public Rack(String name, int x, int y, int width, int height) {
		this(name, x, y , width, height, 20);
	}
	
	public Rack(String name, Rectangle size, float temperature){
		this(name, (int) size.getX(), (int) size.getY(), (int) size.getWidth(), (int) size.getHeight(), temperature);
	}
	
	public Rack(String name, Point2D startpoint, Dimension dimension, float temperature){
		this(name, (int)startpoint.getX(), (int)startpoint.getY(), (int)dimension.getWidth(), (int)dimension.getHeight(), temperature);
	}
	
	private Color getColor(float temperature){
		int colorvalue;
		temperature = Math.min(100, Math.max(0, temperature));
		colorvalue = Math.round(temperature * 0xff / 100) * 0x1000000 + tbasecolor;
		//System.out.printf("Color value %x %<d", colorvalue);
		Color rackcolor = new Color(colorvalue, true);
		return rackcolor;
	}
	
	public float getTemperature(){
		return this.temperature;
	}
	
	public Radiation getRadiation(){
		return rackradiation;
	}
	
	public void paintRack(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
		
		//g2.setColor(this.getColor(temperature));
		g2.setComposite(AlphaComposite.SrcOver);
		
		//g2.setPaint(rackradiation.getRadiationPaint());
		//g2.fill(this);
		rackradiation.paintRadiation(g2);
		g2.setStroke(new BasicStroke(1.3f));
		g2.setColor(new Color(0xff55ca));
		g2.draw(this);
		
	}
	
	public BufferedImage drawRack(BufferedImage sourceimage){
		BufferedImage destinationimage = new BufferedImage(sourceimage.getWidth(), sourceimage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) destinationimage.getGraphics();
		//g2.setComposite(AlphaComposite.SrcOver);
		g2.drawImage(sourceimage, null, 0, 0);
		this.paintRack(g2);
		g2.dispose();
		return destinationimage;
	}

}
