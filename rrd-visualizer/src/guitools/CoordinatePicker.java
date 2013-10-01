package guitools;

import java.applet.Applet;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
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
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class CoordinatePicker {

	private BufferedImage map;
	private JDialog coordinator;
	private JFrame owner;
	

	private Rectangle rackPosition;
	//private JLabel showCoordinates;
	private JLabel resultCoord;
	private int mouseX, mouseY, dragX, dragY, oldX, oldY;
	private JPanel coordP;
	private boolean drag = false, rect = true, oldRect = true, exist = false;
	
	
	private MyCoordinatePicker mcp = null;
	
	public CoordinatePicker(BufferedImage map, JFrame owner){
		this.map = map;
		this.owner = owner;
	}
	
	public void showDialog(){
		coordinator = new JDialog(owner, "Set coordinates");
		coordinator.setModal(true);
		coordP = new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g){
				Graphics2D g2 = (Graphics2D) g;
				
				if (drag) {
					g2.setColor(Color.BLACK);
					g2.setXORMode(Color.WHITE);
					if (oldRect) {
						int x = Math.min(mouseX, oldX);
						int y = Math.min(mouseY,  oldY);
						int w = Math.abs(mouseX - oldX);
						int h = Math.abs(mouseY - oldY);
						g2.drawRect(x, y, w, h);
					}
					if (rect) {
						int x = Math.min(mouseX, dragX);
						int y = Math.min(mouseY,  dragY);
						int w = Math.abs(mouseX - dragX);
						int h = Math.abs(mouseY - dragY);
						g2.drawRect(x, y, w, h);
					}
					oldX = dragX;
					oldY = dragY;
					oldRect = rect;
					drag = false;
				} else {
					g2.setComposite(AlphaComposite.Src);
					g2.drawImage(map, null, 0, 0);
				}
				
				
				g2.dispose();
			}
			
			@Override
			public void update(Graphics g){
				paintComponent(g);
			}
		};
		JPanel bPan  = new JPanel(new FlowLayout());
		resultCoord = new JLabel("Rack coordinates" );
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		okButton.addActionListener(new OKButtonListener());
		cancelButton.addActionListener(new CancelButtonListener());
		bPan.add(resultCoord);
		bPan.add(okButton);
		bPan.add(cancelButton);
		//JPanel coordPanel = new JPanel(new FlowLayout());
		//coordP.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
		//coordP.addMouseListener(new ImageMouseListener());
		//coordP.addMouseMotionListener(new ImageMouseMotionListener());
		JScrollPane mapPanel = new JScrollPane(coordP, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//showCoordinates = new JLabel("Coordinates: ");
		//coordPanel.add(showCoordinates);
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		
		content.add(BorderLayout.PAGE_START, bPan);
		
		mcp = new MyCoordinatePicker();
		mcp.addMouseListener(new ImageMouseListener());
		mcp.addMouseMotionListener(new ImageMouseMotionListener());
		mcp.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
		JScrollPane appletPanel = new JScrollPane(mcp, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		content.add(BorderLayout.CENTER, appletPanel);
		//content.add(BorderLayout.CENTER, mapPanel);
		//content.add(BorderLayout.PAGE_END, coordPanel);
		coordinator.setContentPane(content);
		coordinator.pack();
		coordinator.setVisible(true);
	}
	
	
	public Rectangle getPosition(){
		return rackPosition;
	}
	
	class ImageMouseListener implements MouseListener {
		public void mousePressed(MouseEvent e) {
			Point dot = e.getPoint();
			if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			//showCoordinates.setText("Coordinates: " + dot.x + ":" + dot.y);
			if (exist){
				mcp.clearSelection();
				rect = false;
				mcp.repaint();
				rect = true;
				
				exist = false;
			}
				mouseX = dragX = oldX = dot.x;
			mouseY = dragY = oldY = dot.y;
			
			}
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
			if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0){
				if (mouseX != e.getX() && mouseY != e.getY()) {
					int x = Math.min(mouseX, e.getX());
					int y  = Math.min(mouseY, e.getY());
					int w = Math.abs(mouseX - e.getX());
					int h = Math.abs(mouseY -e.getY());
					rackPosition = new Rectangle(x, y, w, h);
					resultCoord.setText("X: "+x+" Y: "+y+" Width: "+w+" Height: "+h);
					exist = true;
					mcp.showSelection(rackPosition);
				}
			}
			
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
	
	class ImageMouseMotionListener implements MouseMotionListener {
		public void mouseMoved(MouseEvent e) {}
		public void mouseDragged(MouseEvent e) {
					drag = true;
				dragX = e.getX();
				dragY = e.getY();
				//coordP.repaint();
				mcp.repaint();
		}
	}
	
	class OKButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			if (rackPosition != null){
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
	
	class MyCoordinatePicker extends Applet {
		
		Rectangle oldArea = null;
		
		public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		if (drag) {
			g2.setColor(Color.BLACK);
			g2.setXORMode(Color.WHITE);
			if (oldRect) {
				int x = Math.min(mouseX, oldX);
				int y = Math.min(mouseY,  oldY);
				int w = Math.abs(mouseX - oldX);
				int h = Math.abs(mouseY - oldY);
				g2.drawRect(x, y, w, h);
			}
			if (rect) {
				int x = Math.min(mouseX, dragX);
				int y = Math.min(mouseY,  dragY);
				int w = Math.abs(mouseX - dragX);
				int h = Math.abs(mouseY - dragY);
				g2.drawRect(x, y, w, h);
			}
			oldX = dragX;
			oldY = dragY;
			oldRect = rect;
			drag = false;
		} else {
			//g2.setComposite(AlphaComposite.Src);
			g2.drawImage(map, null, 0, 0);
		}
		
		
		g2.dispose();
	}
	
		public void showSelection(Rectangle area){
			oldArea = area;
			Graphics2D g2 = (Graphics2D) this.getGraphics();
			g2.setColor(Color.WHITE);
			g2.setXORMode(new Color(0x88ff0000));
			g2.fillRect(area.x, area.y, area.width, area.height);
		}
		
		public void clearSelection(){
			Graphics2D g2 = (Graphics2D) this.getGraphics();
			g2.setColor(Color.WHITE);
			g2.setXORMode(new Color(0x88ff0000));
			g2.fillRect(oldArea.x, oldArea.y, oldArea.width, oldArea.height);
		}
	
	public void update(Graphics g){
		paint(g);
	}	
	}
}
