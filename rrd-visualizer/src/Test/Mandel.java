package Test;


	import java.applet.Applet;
	import java.awt.*;

	public final class Mandel extends Applet {
	  private static final int MAX = 128;

	  public void paint(Graphics g) {
	    Dimension size = getSize();
	    for (int y = 0; y < size.height; y++) {
	      for (int x = 0; x < size.width; x++) {
	        // squeeze (x, y) to the proper interval
	        double dx = 2.5 * x / size.width - 2.0;
	        double dy = 1.25 - 2.5 * y / size.height;
	        int value = mandel(dx, dy); // compute value
	        value = value * 255 / MAX; // stretch to 0-255
	        g.setColor(new Color(value, value, value));
	        g.drawLine(x, y, x, y); // draw point
	      }
	    }
	  }

	  private int mandel(double px, double py) {
	    int value = 0;
	    double zx = 0.0, zy = 0.0, zx2 = 0.0, zy2 = 0.0;
	    while (value < MAX && zx2 + zy2 < 4.0) {
	      zy = 2.0 * zx * zy + py;
	      zx = zx2 - zy2 + px;
	      zx2 = zx * zx;
	      zy2 = zy * zy;
	      value++;
	    }
	    return MAX - value;
	  }
	
}
