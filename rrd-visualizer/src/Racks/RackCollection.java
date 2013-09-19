package Racks;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;


public class RackCollection extends ArrayList<Rack> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5328146854452255744L;

	public RackCollection(){
		super();
	}
	
	public RackCollection(Rack firstrack){
		this.add(firstrack);
	}
	
	public RackCollection(ArrayList<Rack> racklist){
		for (Rack currentrack : racklist){
			this.add(currentrack);
		}
	}
	
	public RackCollection(String name, Rectangle size, RackProperty rp){
		
		this.add(new Rack(name, size, rp));
	}
	
	public RackProperty getRackProperty(String propertyDescription){
		RackProperty desiredProp = null;
		Iterator<Rack> itr = this.iterator();
		while(itr.hasNext() && (desiredProp == null)){
			desiredProp = itr.next().getRackProperty(propertyDescription);
		}
		return desiredProp;
	}
	
	public BufferedImage drawRackCollection(BufferedImage sourceimage){
		BufferedImage destinationimage = new BufferedImage(sourceimage.getWidth(), sourceimage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) destinationimage.getGraphics();
		//g2.setComposite(AlphaComposite.Src);
		
		g2.drawImage(sourceimage, null, 0, 0);
	
		
		for (Rack currentrack : this) {
		//g2.setComposite(AlphaComposite.Src);
		currentrack.paintRack(g2);
		}
		g2.dispose();
		return destinationimage;
	}
	
	public void paintRackCollection(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		for (Rack currentrack : this){
			currentrack.paintRack(g2);
		}
				
	}
	
}
