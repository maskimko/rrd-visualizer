import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class JPanelImageApp extends JPanel{
	
	private BufferedImage image;
	private int x,y;
	
	public JPanelImageApp(BufferedImage image, int x, int y){
		super();
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void paintComponent(Graphics g){
		//Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g);
		g.drawImage(image,  0, 0, null);
	}
	
	

}
