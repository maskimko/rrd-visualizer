import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private BufferedImage bfimg = null;
	private FloorMap fm = null;
	public Dimension imageDimension;
	private RackCollection drawings = new RackCollection();
	private int x,y;

	
	
	public ImagePanel(String mapname, int x, int y) {
		super();
		this.x = x;
		this.y = y;
		try {
			fm = new FloorMap(mapname);
			bfimg = fm.getImage();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		imageDimension = new Dimension(bfimg.getWidth(), bfimg.getHeight());
		this.setPreferredSize(imageDimension);

	}
	
	public ImagePanel(String mapname){
		this(mapname, 0, 0);
	}

	@Override
	
	public void paintComponent(Graphics g) {
		// super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (bfimg != null) {
			g2.drawImage(bfimg, null, 0, 0);
		}
		g2.setComposite(AlphaComposite.SrcOver);
		for (Rack currentrack : drawings){
    		currentrack.paintRack(g2);
    	}
    	g2.dispose();
	}

	public void add2Image(RackCollection racks){
    	for (Rack currentrack : racks){
    		drawings.add(currentrack);
    	}
    }
    
    public void add2Image(Rack rack){
    	drawings.add(rack);
    }
    
    public void clearDrawings(){
    	drawings = new RackCollection();
    }
	
	public BufferedImage getImage() {
		return bfimg;
	}

	public Dimension getImageDimension() {
		return imageDimension;
	}

}
