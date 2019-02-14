import com.sun.javafx.property.adapter.PropertyDescriptor.Listener;

import javafx.scene.image.Image;

public class ImageConfig {
	private static Image image;
	private static Image uneditedImage;
	
	public static Image getImage() {
		return image;
	}
	public static void setImage(Image image) {
		ImageConfig.image = image;
		
	}
	public static Image getUneditedImage() {
		return uneditedImage;
	}
	public static void setUneditedImage(Image uneditedImage) {
		ImageConfig.uneditedImage = uneditedImage;
	}
}