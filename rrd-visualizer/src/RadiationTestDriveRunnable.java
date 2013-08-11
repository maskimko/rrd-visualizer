import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RadiationTestDriveRunnable {

	public static void main(String[] args) {
		Runnable running = new Runnable() {
			@Override
			public void run() {
				JFrame mainframe = new JFrame("Radiation Test Drive v.01");
				mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainframe.setBackground(new Color(0x05ffffd0));
				
				RadiationTestDrive rtd = new RadiationTestDrive();

				mainframe.add(rtd);
				mainframe.setBounds(0, 0, rtd.getWidth(), rtd.getHeight());
				// mainframe.pack();
				mainframe.setLocationByPlatform(true);
				mainframe.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(running);
	}
}

class RadiationTestDrive extends JPanel {

	private Radiation radiation;
	private Rack rack;
	Point2D newpoint = new Point2D.Float(0f, 0f);

	public RadiationTestDrive(Rack rack, Radiation radiation) {
		super();
		this.radiation = radiation;
		this.rack = rack;

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				newpoint = event.getPoint();
				repaint();
			}
		});
	}
	public RadiationTestDrive(Rack rack){
		this(rack, rack.getRadiation());
	}
	
	public RadiationTestDrive(){
		this(new Rack("Rack for Test", 0, 0, 1, 1));
	}
	
	

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		try {
			BufferedImage bimg = ImageIO.read(new File(
					"d:\\transfer\\testimage.png"));

			g2.drawImage(bimg, 0, 0, null);

		} catch (IOException e) {
		}
		;
		// g2.draw(rack);
		g2.setComposite(AlphaComposite.SrcOver);

		try {
			rack = new Rack("Somename", newpoint, new Dimension(50, 34), 70f);
			rack.paintRack(g2);
			//radiation.paintRadiation(g2);
			// g2.setPaint(new RadialGradientPaint(newpoint, 50, new float[]
			// {0.0f, 1f}, RadiationColors.getTemperatureColorsArray(),
			// CycleMethod.NO_CYCLE) );
			// //g2.setPaint(radiation.getRadiationPaint());
			// g2.fill(rack);
		} catch (NullPointerException ex) {

		}
		g2.dispose();

	}

}