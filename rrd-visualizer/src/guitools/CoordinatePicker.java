package guitools;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CoordinatePicker {

	private BufferedImage map;
	private JDialog coordinator;
	private JFrame owner;
	private String crd = "", dim = "";
	private int x1, x2, y1, y2;
	private Rectangle rackPosition;
	private JLabel showCoordinates;
	
	public CoordinatePicker(BufferedImage map, JFrame owner){
		this.map = map;
		this.owner = owner;
	}
	
	public void showDialog(){
		coordinator = new JDialog(owner, "Set coordinates");
		coordinator.setModal(true);
		JPanel imagePanel = new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g){
				Graphics2D g2 = (Graphics2D) g;
				g2.setComposite(AlphaComposite.Src);
				g2.drawImage(map, null, 0, 0);
				g2.dispose();
			}
		};
		JPanel buttonPanel  = new JPanel(new FlowLayout());
		JLabel resultCoord = new JLabel("Rack coordinates" + crd + dim);
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		okButton.addActionListener(new OKButtonListener());
		cancelButton.addActionListener(new CancelButtonListener());
		JPanel coordPanel = new JPanel(new FlowLayout());
		JScrollPane mapPanel = new JScrollPane(imagePanel);
		mapPanel.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
		showCoordinates = new JLabel("Coordinates: " + crd);
		JPanel content = new JPanel();
		content.add(BorderLayout.NORTH, buttonPanel);
		content.add(BorderLayout.CENTER, mapPanel);
		content.add(BorderLayout.SOUTH, coordPanel);
		coordinator.add(content);
		coordinator.setVisible(true);
	}
	
	
	public Rectangle getPosition(){
		return rackPosition;
	}
	
	class ImageMouseListener implements MouseListener {
		public void mousePressed(MouseEvent e) {
			Point dot = e.getPoint();
			crd = dot.x + ":" + dot.y;
		}

		public void mouseReleased(MouseEvent e) {
			//System.out.println("Painting ...");
			// Iterator<Rack> rcIter = rackColl.iterator();
			// while(rcIter.hasNext()){
			// rcIter.next().paintRadiation(RackTempProperty.rackTempDescription,
			// currentCal, imagePanel.getGraphics());
			// }
			
			/*
			RackProperty prop2Show = propList.getSelectedValue();
			 
			BufferedImage tmpLayer = getLayer(prop2Show);
			System.out.println(prop2Show.getDescription() + " is selected");
			imagePanel.setBufferedImage(prop2Show.getDescription(), tmpLayer);
			imagePanel.repaint();*/
		}

		public void mouseEntered(MouseEvent e) {
			// System.out.println("Mouse Entered");
		}

		public void mouseExited(MouseEvent e) {
			// System.out.println("Mouse Exited");
		}

		public void mouseClicked(MouseEvent e) {
			// System.out.println("Mouse Clicked");
		}
	}
	
	class OKButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			if (x1 != x2 && y1 != y2){
				rackPosition = new Rectangle(Math.min(x1, x2), Math.min(y1,  y2), Math.abs(x1 - x2), Math.abs(y1 -y2));
				coordinator.setVisible(false);
			}
		}
	}
	
	class CancelButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			rackPosition = null;
			coordinator.setVisible(false);
		}
	}
}
