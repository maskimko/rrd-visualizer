import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class ImagePanelScrollable extends JScrollPane {

		
		private ImagePanel imagePanel = null;
		
		
		public ImagePanelScrollable(ImagePanel imagePanel) {
			super(imagePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			this.imagePanel = imagePanel;
		
		}
	
	public BufferedImage getImage(){
		return imagePanel.getImage();
	}
	
	public Dimension getImageDimension(){
		return imagePanel.imageDimension;
	}
	
			
		
	

}
