import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FloorMap {

	private BufferedImage floorimage;
	private static String floor6ImagePath = "src/testimage.png";

	public FloorMap(String mapname) throws Exception {
		switch (mapname) {
		case "6 floor":
			loadImage(floor6ImagePath);
			break;
		default:
			throw new Exception("There is no such predefined map");

		}
	}
	
	public BufferedImage getImage() throws Exception {
		if (floorimage != null ) return floorimage;
		else throw new Exception("Image was not loaded");
	}

	private void loadImage(String image) throws IOException {
		File imagefile = new File(image);
		floorimage = ImageIO.read(imagefile);

	}
}