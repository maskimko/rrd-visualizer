import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import Racks.RackCollection;
import Racks.TemperatureLayer;


public class RandomTemperatureMap {
  
	public JPanel mainPanel;
	private ImagePanel impnl = null;
	private BufferedImage tempLayer = null;
	//private ImagePanelScrollable imagePanel = null;
	
	public  JPanel createMainPanel(){
		BorderLayout layout = new BorderLayout();
		mainPanel = new JPanel(layout);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		impnl = new ImagePanel("6 floor");
		//RackCollection racks = RackCollectionTestDrive.createStaticCollection();
		//impnl.add2Image(racks);
		ImagePanelScrollable imagePanel = new ImagePanelScrollable(impnl);
	
		Box controlPanel = new Box(BoxLayout.Y_AXIS);
		JButton generator = new JButton("Generate New");
		generator.addActionListener(new GeneratorListener());
		controlPanel.add(generator);
		mainPanel.add(BorderLayout.EAST, controlPanel);
		mainPanel.add(BorderLayout.CENTER, imagePanel);
		return mainPanel;
	}
	
	
	
	class GeneratorListener implements ActionListener {
		public void actionPerformed(ActionEvent ae){
			System.out.println("\"Generate New\" button has been pressed");
			impnl.clearDrawings();
			RackCollection rc = RackCollectionTestDrive.createStaticCollection();
			
			tempLayer = TemperatureLayer.getLayer(impnl.getWidth(), impnl.getHeight(), rc.getRackProperty("temperature"), rc);
			impnl.setBufferedImage("temperature", tempLayer);
			impnl.add2Image(rc);
			impnl.repaint();
		}
	}
}
