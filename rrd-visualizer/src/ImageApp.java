import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class ImageApp {

	private BufferedImage mapimage;

	public void loadImage() {
		this.loadImage("d:\\transfer\\testimage.png");
	}

	public void loadImage(String path2image) {
		String imagepath = "d:\\transfer\\testimage.png";
		String imagepathalternative = "/home/maskimko/Downloads/testimage.png";
		BufferedReader answer = new BufferedReader(
				new InputStreamReader(System.in));;
		File imagefile;
		String answervalue = "";
		try {
			imagefile = new File(path2image);
			if (!imagefile.canRead()) {
				System.out.println("Cannot read this file: " + path2image);
				System.out.println("Would you like to try load default path? (yes|no)");
				while (!(answervalue.equalsIgnoreCase("yes") || answervalue.equalsIgnoreCase("no"))) {
					answervalue = answer.readLine();
				}

				if (answervalue.equals("no")) {
					System.out.println("Exiting because of impossibility to read image file");
					System.exit(0);
				}
				answervalue = "";
				imagefile = new File(imagepath);
				if (!imagefile.canRead()) {
					System.out.println("Cannot read this file: " + imagepath);
					System.out.println("Would you like to try load default path? (yes|no)");
					while (!(answervalue.equalsIgnoreCase("yes") || answervalue.equalsIgnoreCase("no"))) {
						answervalue = answer.readLine();
					}

					if (answervalue.equals("no")) {
						System.out.println("Exiting because of impossibility to read image file");
						System.exit(0);
					}
					
					imagefile = new File(imagepathalternative);
				}
			}
			answer = null;
			mapimage = ImageIO.read(imagefile);
		} catch (IOException exception) {

			System.out.println("Error: Cannot load image. "
					+ exception.toString());
		}
	}

	public void displayImage(JFrame frame) {
		frame.setBounds(0, 0, mapimage.getWidth(), mapimage.getHeight());
		//CanvasImageApp canvas = new CanvasImageApp(mapimage, 0, 0);
		//frame.add(canvas);
		
		JPanelImageApp jpia = new JPanelImageApp(mapimage, 0, 0);
		jpia.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				System.out.println(event.getPoint());
			}
		});
		frame.add(jpia);
		frame.setResizable(false);
		frame.setVisible(true);
		
		
		
	}

	public void loadAndDisplayImage(JFrame frame) {
		this.loadImage();
		this.displayImage(frame);
	}

	public void loadAndDisplayImage(JFrame frame, String path2image) {
		this.loadImage(path2image);
		this.displayImage(frame);
	}

	public void loadAndDisplayImage(JFrame frame, String path2image, Rack server) {
		this.loadImage(path2image);
		mapimage = server.drawRack(mapimage);
		this.displayImage(frame);
	}

	public void loadAndDisplayImage(JFrame frame, Rack server) {
		this.loadImage();
		mapimage = server.drawRack(mapimage);
		this.displayImage(frame);
	}

	public void loadAndDisplayImage(JFrame frame, String path2image,
			RackCollection rc) {
		this.loadImage(path2image);
		mapimage = rc.drawRackCollection(mapimage);
		this.displayImage(frame);
	}

	public void loadAndDisplayImage(JFrame frame, RackCollection rc) {
		this.loadImage();
		mapimage = rc.drawRackCollection(mapimage);
		this.displayImage(frame);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ImageApp ia = new ImageApp();
		JFrame frame = new JFrame("6floor Server Room");
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});
		// Rack cacti = new Rack("cacti", 25, 8, 29, 31, 100);
		RackCollection racks = RackCollectionTestDrive.createStaticCollection();
		ia.loadAndDisplayImage(frame, racks);
	}

}
