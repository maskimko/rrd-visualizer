package Racks;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;


public class Rack extends Rectangle2D.Float implements RackInterface{

	
	private static final long serialVersionUID = -7185207675494579252L;
	protected String name;
	protected HashMap<String, RackProperty> props = new HashMap<String, RackProperty>();
	
	public Rack(String name, int x, int y, int width, int height, RackProperty rp) {
		super(x, y, width, height);
		this.name = name;
		if (rp != null) {
			props.put(rp.description, rp);
		}
	}
	
	
	public Rack(String name, Rectangle size, RackProperty rp){
		this(name, (int) size.getX(), (int) size.getY(), (int) size.getWidth(), (int) size.getHeight(), rp);
	}
	
	public Rack(String name, Point2D startpoint, Dimension dimension, RackProperty rp){
		this(name, (int)startpoint.getX(), (int)startpoint.getY(), (int)dimension.getWidth(), (int)dimension.getHeight(), rp);
	}
	
	
	
	public Radiation getRadiation(RackProperty rackProp, Calendar time){
		return rackProp.getRadiation(this, time);
	}
	
	public Radiation getRadiation(String rackPropDescr, Calendar time){
		return getRadiation(props.get(rackPropDescr), time);
	}
	
	public void paintRadiation(RackProperty rackProp, Calendar time, Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.SrcOver);
		Radiation rd = getRadiation(rackProp, time);
		rd.paintRadiation(g2);
	}
	
	public void paintRadiation(String propertyDescr, Calendar time, Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.SrcOver);
		Radiation rd = getRadiation(propertyDescr, time);
		rd.paintRadiation(g2);
	}
	
	public String getName(){
		return name;
	}
	
	public RackProperty getRackProperty(String propDescr){
		return props.get(propDescr);
	}
	
	public HashMap<String, RackProperty> getRackPropertyMap(){
		return props;
	}
	
	
	public void addRackProperty(RackProperty rp){
		props.put(rp.description, rp);
	}
	
	
	
	public void paintRack(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.SrcOver);
		g2.setStroke(new BasicStroke(1.3f));
		g2.setColor(new Color(0xff55ca));
		g2.draw(this);
		
	}
	/*
	public BufferedImage drawRack(BufferedImage sourceimage){
		BufferedImage destinationimage = new BufferedImage(sourceimage.getWidth(), sourceimage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) destinationimage.getGraphics();
		//g2.setComposite(AlphaComposite.SrcOver);
		g2.drawImage(sourceimage, null, 0, 0);
		this.paintRack(g2);
		g2.dispose();
		return destinationimage;
	}*/
	
	public void updateProperties(Calendar start, Calendar stop){
		Iterator<RackProperty> propIt = props.values().iterator();
		while (propIt.hasNext()){
			propIt.next().updateTimeBounds(start, stop);
		}
	}

}
