import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageUtil {

	public static BufferedImage loadImage(String imagePath) {
		BufferedImage img = null;
		File imageFile = new File(imagePath);
		if (!imageFile.exists())
			System.out.println("File " + imagePath + " does not exist!");
		if (!imageFile.canRead())
			System.out.println("Cannot read file " + imagePath);
		try {
			img = ImageIO.read(imageFile);
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}

		return img;
	}
}
