import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JPanel;

import Racks.Rack;
import Racks.RackCollection;

public class ImagePanel extends JPanel {

	private HashMap<String, BufferedImage> bfimgs = new HashMap<String, BufferedImage>();
	private FloorMap fm = null;
	public Dimension imageDimension;
	private RackCollection drawings = new RackCollection();
	private int x, y;

	public ImagePanel(String mapname, int x, int y) {
		super();
		this.x = x;
		this.y = y;
		try {
			fm = new FloorMap(mapname);
			bfimgs.put("Base", fm.getImage());
			imageDimension = new Dimension(fm.getImage().getWidth(), fm
					.getImage().getHeight());
			this.setPreferredSize(imageDimension);
			BufferedImage racklayer = new BufferedImage(fm.getImage().getWidth(), fm.getImage().getHeight(), BufferedImage.TYPE_INT_ARGB);
			bfimgs.put("Racks", racklayer);
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} catch (IllegalArgumentException iae) {
			System.err.println(iae.getMessage());
		}

	}

	public ImagePanel(String mapname) {
		this(mapname, 0, 0);
	}

	@Override
	public void paintComponent(Graphics g) {
		// super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Iterator<BufferedImage> bfitr = bfimgs.values().iterator();
		if (bfitr.hasNext()) {
			g2.setComposite(AlphaComposite.Src);
			g2.drawImage(bfitr.next(), null, 0, 0);
			g2.setComposite(AlphaComposite.SrcOver);
			drawRacks();			
			while (bfitr.hasNext()) {
				g2.drawImage(bfitr.next(), null, 0, 0);
			}
			g2.dispose();
		}
	}

	private void drawRacks(){
		Graphics2D racksGraphics = (Graphics2D) getRackImage().getGraphics();
		for (Rack currentrack : drawings) {
		currentrack.paintRack(racksGraphics);
	}
	}
	
	public void add2Image(RackCollection racks) {
		for (Rack currentrack : racks) {
			drawings.add(currentrack);
		}
	}

	public void add2Image(Rack rack) {
		drawings.add(rack);
	}

	public void clearDrawings() {
		drawings = new RackCollection();
	}

	public void clearLayers() {
		bfimgs = new HashMap<String, BufferedImage>();
	}

	public void setBufferedImage(String layerName, BufferedImage bfImage) {
		bfimgs.put(layerName, bfImage);
	}

	public BufferedImage getLayer(String layerName) {
		return bfimgs.get(layerName);
	}

	public void rmLayer(String layerName) {
		bfimgs.remove(layerName);
	}

	public BufferedImage getBaseImage() {
		return bfimgs.get("Base");
	}
	
	public BufferedImage getRackImage() {
		return bfimgs.get("Racks");
	}

	public HashMap<String, BufferedImage> getImages() {
		return bfimgs;
	}

	public Dimension getImageDimension() {
		return imageDimension;
	}

}
