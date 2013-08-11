import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MapApplication {
	private JFrame frame;
	WheatherPanel wheatherPanel;
	RackCollection racks;
	 
	

	
	public static void main(String[] args){
	MapApplication map = new MapApplication();
	map.go();
	}
	
	public void go(){
		frame = new JFrame("Server Room Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton generatorButton = new JButton("Generate new data");
		generatorButton.addActionListener(new GeneratorListener());

		racks = RackCollectionTestDrive.createStaticCollection();
		wheatherPanel = new WheatherPanel();
		
		wheatherPanel.add2Image(racks);
		
				
		frame.getContentPane().add(BorderLayout.SOUTH, generatorButton);
		
	
		frame.getContentPane().add(BorderLayout.CENTER, wheatherPanel);
		
		frame.setSize(900, 600);
	
		frame.setVisible(true);
		
	}
	

	
	class GeneratorListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			racks=RackCollectionTestDrive.createStaticCollection();
			wheatherPanel.clearDrawings();
			wheatherPanel.add2Image(racks);
			frame.repaint();
			
		}
	}
	
	

}


class WheatherPanel extends JPanel {
	private BufferedImage floormap;
	private RackCollection drawings = new RackCollection();
	private int x,y;
	
	public WheatherPanel(){
		super();
		this.x = 0;
		this.y = 0;
		loadImage();
		
		
		
		
	}

	
	public WheatherPanel(File imagefile){
		super();
		this.x = 0;
		this.y = 0;
		loadImage(imagefile);
		
		
		
	}
	
	public WheatherPanel(String path2file){
		super();
		this.x = 0;
		this.y = 0;
		loadImage(path2file);
		
		
		
	}
	
	private void loadImage(File imagefile){
		try {
			floormap = ImageIO.read(imagefile);
			System.out.println("Loading image: " + imagefile.getAbsolutePath());
			this.setBounds(0,  0, floormap.getWidth(), floormap.getHeight());
		} catch (IOException exception) {
			System.out.println("Error: Cannot load an image " + exception);
		}
	}
	
	private void loadImage(String path2image){
		
			File imagefile = new File(path2image);
			loadImage(imagefile);
		
	}
	
	private void loadImage(){
		//System.out.println("Current path: " + System.getProperty("user.dir"));
		File imagefile = new File("src", "testimage.png");
		loadImage(imagefile);
	}
	
	
	//TODO
	//rewrite this method!
    public void add2Image(RackCollection racks){
    	for (Rack currentrack : racks){
    		drawings.add(currentrack);
    	}
    }
    
    public void add2Image(Rack rack){
    	drawings.add(rack);
    }
    
    public void clearDrawings(){
    	drawings = new RackCollection();
    }
   
    
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	Graphics2D g2 =  (Graphics2D) g;
    	g2.drawImage(floormap, null, 0, 0);
    	g2.setComposite(AlphaComposite.SrcOver);
    	for (Rack currentrack : drawings){
    		currentrack.paintRack(g2);
    	}
    	g2.dispose();
    	
    }
    
   

	
	
}
