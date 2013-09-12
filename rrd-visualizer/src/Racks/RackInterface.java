package Racks;

import java.awt.Graphics;
import java.util.Calendar;
import java.util.HashMap;

public interface RackInterface {

	public HashMap<String, RackProperty> getRackPropertyMap();
	public void addRackProperty(RackProperty rp);
	public RackProperty getRackProperty(String description);
	public Radiation getRadiation(RackProperty rp, Calendar time);
	public Radiation getRadiation(String propertyDescription, Calendar time);
	public void paintRadiation(RackProperty rp, Calendar time, Graphics g);
	public void paintRadiation(String propertyDescription, Calendar time, Graphics g);
	public void paintRack(Graphics g);
}
