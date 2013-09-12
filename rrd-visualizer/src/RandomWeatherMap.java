import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


public class RandomWeatherMap {
  
	public JPanel mainPanel;
	private ImagePanel impnl = null;
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
			impnl.add2Image(RackCollectionTestDrive.createStaticCollection());
			impnl.repaint();
		}
	}
}
