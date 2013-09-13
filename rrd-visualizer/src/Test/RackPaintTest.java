package Test;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Racks.Rack;
import Racks.RackProperty;
import Racks.Radiation;
import Racks.RadiationTemp;

public class RackPaintTest {

	private BufferedImage floor6;
	private String path2Image;
	int firsta = 50, firstb = 50, seconda = 300, secondb = 300;
	ImageArea ima = null;
	RackProperty temperature = null;
	
	public void go() {
		JFrame window = new JFrame("Test of one rack");
		File imgFile = new File(path2Image);
		if (imgFile.canRead()) {
			try {
				floor6 = ImageIO.read(imgFile);
			} catch (IOException ioe) {
				System.err.println(ioe.getMessage());
			}
		} else {
			System.err.println("Cannot read image file");
		}
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = (JPanel) window.getContentPane();

		if (floor6 != null) {
			ima = new ImageArea();
			ima.setSize(floor6.getWidth(), floor6.getHeight());
			mainPanel.add(BorderLayout.CENTER, ima);
			temperature = new RackProperty(
					"Testing of RackProperty class",
					RadiationTemp.RadiationParametersTemp,
					Calendar.getInstance(), Calendar.getInstance()) {

				public float getValue(Calendar cal)
						throws IllegalArgumentException, NullPointerException {

					return 100;
				}

				public Radiation getRadiation(Rack rack, Calendar cal)
						throws IllegalArgumentException, NullPointerException {
					Radiation rad = new Radiation(rack, this.description,
							this.stop, this.radParam);
					return rad;
				}

				public String getDescription() {

					return description;
				}
			};

			
			
			
			
			Rack r = new Rack("Test rack", firsta, firstb, 100, 100, temperature);
			Rack r2 = new Rack("Test rack2", 330, 270, 50, 40, temperature);
			Rack r3 = new Rack("Test rack2", 330, 30, 50, 40, temperature);
			Rack r4 = new Rack("Test rack2", 160, 400, 50, 40, temperature);
			Rack rt = new Rack("Test rack tween", seconda, secondb, 100, 100, temperature);
			ima.addRack(r);
			ima.addRack(rt);
			
			
			
			//ima.addRack(r2);
			//ima.addRack(r3);
			//ima.addRack(r4);

		} else {
			System.err.println("Image has been not loaded");
		}
		window.setSize(floor6.getWidth(), floor6.getHeight());
		window.setVisible(true);
		Thread move = new Thread(new ImageRunner());
		move.run();
	}
	
	class ImageRunner implements Runnable {
		Rack r, rt;
		
		public void run(){
			try {
				while(firsta < 300){
					r =  new Rack("Test rack", firsta++, firstb++, 100, 100, temperature);
					rt = new Rack("Test rack", seconda--, secondb--, 100, 100, temperature);
					
					ima.clearRacks();
					ima.addRack(r);
					ima.addRack(rt);
					ima.repaint();
					//System.out.println(firsta + ":"+ firstb);
					
					
						Thread.sleep(100);
					
				}
				
				} catch (InterruptedException ie){
					System.err.println("Was interrupted!");
				}
		}
	}
	

	class ImageArea extends JPanel {

		private ArrayList<Rack> racks = new ArrayList<Rack>();
		
		public void addRack(Rack rack){
			racks.add(rack);
		}
		
		public void clearRacks(){
			racks = new ArrayList<Rack>();
		}
		
		private BufferedImage getLayer(int width, int height, ArrayList<Rack> racks){
			BufferedImage layer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D lGr2 = layer.createGraphics();
			
			
			
			if (racks.size() != 0) {
				for (Rack rack : racks) {
					//rack.paintRack(g2);
					rack.paintRadiation("Testing of RackProperty class", Calendar.getInstance(), lGr2);
				}
				float dot, hue;
				Color point = null;
				int  dotRGB, dotRGBA;
				for (int k = 0; k < layer.getWidth(); k++){
					for (int j = 0; j < layer.getHeight(); j++){
						point = new Color(layer.getRGB(k, j), true);
						hue = point.getAlpha();
						hue /= 256;
						hue = 1 - hue;
						hue  = Math.min(hue, 0.55f);
						dotRGB = Color.HSBtoRGB( hue , 1f, 1f);
						dotRGBA = dotRGB + 0x33000000;
						dotRGB = 0;
						layer.setRGB(k, j, dotRGBA);
					}
				}
			}
			return layer;
		}
		
		
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setComposite(AlphaComposite.SrcOver);
			
			
			
			
			if (racks.size() != 0) {
				for (Rack rack : racks) {
					rack.paintRack(g2);
					
				}
				
			}
			
			g2.drawImage(floor6, null, 0, 0);
			g2.drawImage(getLayer(floor6.getWidth(), floor6.getHeight(), racks), null, 0, 0);
			g2.dispose();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length != 1) {
			System.out.println("Usage: RackPaintTest <png image path>");
		} else {
			RackPaintTest rpt = new RackPaintTest();
			rpt.path2Image = args[0];
			rpt.go();
		}
	}

}
