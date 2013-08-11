import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class ImageMaker {
	
	

	public static BufferedImage createImage(Color setedcolor) {
		BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		img.createGraphics();
		Graphics2D g2 = (Graphics2D) img.getGraphics();
		g2.setColor(setedcolor);
		g2.fillRect(0, 0, img.getWidth(), img.getHeight());
		
		for (int i = 1; i < 49 ; i++){
			g2.setColor(new Color(5*i, 5*i, 6+i*3));
			g2.fillRect(2*i, 2*i, 3*i, 3*i);
		}
		return img;
	}
	
	public static BufferedImage createImage(){
		return createImage(Color.YELLOW);
	}
	
	public static void saveImage(String ref, BufferedImage image){
		try {
			String format = (ref.endsWith(".png")) ? "png" : "jpg";
			ImageIO.write(image,  format,  new File("ref"));
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		checkSavedImage(ref);
	}
	
	public static String checkSavedImage(String ref){
		if ((new File(ref).exists())) return "Image was saved!"; else return "Image was not saved!";
	}
	
	public static BufferedImage loadTranslucentImage(BufferedImage loaded, float transparency){
		BufferedImage aimg = new BufferedImage(loaded.getWidth(), loaded.getHeight(), BufferedImage.TRANSLUCENT);
		Graphics2D g2 = aimg.createGraphics();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
		g2.drawImage(loaded,  null, 0, 0);
		g2.dispose();
		return aimg;
	}
	
	public static BufferedImage loadTranslucentImage(String ref, float transparency){
		BufferedImage loaded = null;
		try {
		loaded = ImageIO.read(new File(ref));
		} catch (IOException e) {
			System.out.println("Cannot load image! " + e.toString());
			System.exit(1);
		}
		return loadTranslucentImage(loaded, transparency);
	}
	
	public static BufferedImage makeColorTransparent(BufferedImage loaded, Color color){
		BufferedImage dimg = new BufferedImage(loaded.getWidth(), loaded.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = dimg.createGraphics();
		g2.setComposite(AlphaComposite.Src);
		g2.drawImage(loaded, null, 0, 0);
		g2.dispose();
		for (int i =0; i < dimg.getHeight(); i++){
			for (int j = 0; j < dimg.getWidth(); j++) {
				if (dimg.getRGB(j, i) == color.getRGB()) {
					dimg.setRGB(j, i, 0x00ff0000);
				}
			}
		}
		return dimg;
	}
	
	public static BufferedImage makeColorTransparent(String reference, Color color){
		BufferedImage image = null;
		try {
		image = ImageIO.read(new File(reference));
		} catch (IOException e) {
			System.out.println("Cannot load image! " + e.toString());
			System.exit(1);
		}
		
		return makeColorTransparent(image, color);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BufferedImage ourimage;
		JFrame frame = new JFrame("Image Maker");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event){
				System.exit(0);
			}
		});
		frame.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent event){
				System.out.println(event.getPoint());
			}
		});
		frame.setBounds(0, 0, 200, 200);
		//ourimage = createImage();
		//ourimage = loadTranslucentImage(createImage(), 0.1f);
		ourimage = makeColorTransparent(createImage(), Color.YELLOW);
		saveImage("d:\\some_image.png", ourimage);
		frame.setVisible(true);
		Graphics2D g2 = (Graphics2D) frame.getRootPane().getGraphics();
		g2.drawImage(ourimage, null, 0, 0);
		
		


	}

}
