import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class CanvasImageApp extends Canvas {
	
	private BufferedImage image;
	private int x,y;
	
	public CanvasImageApp(BufferedImage image, int x, int y){
		super();
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		super.paint(g2);
		g2.drawImage(image, null, x, y);
	}

}
