package Racks;

import java.awt.image.BufferedImage;
import java.util.Calendar;

public interface RackAddable {

	public void addRack(Rack rack);
	public Calendar getStartTime();
	public Calendar getEndTime();
	public BufferedImage getBaseLayer();
}
