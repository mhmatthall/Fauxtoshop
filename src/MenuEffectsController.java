import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Simple effects tool for Fauxtoshop
 * <p>
 * I declare that the following is my own work.
 * 
 * @author Matt Hall (961500)
 */
public class MenuEffectsController extends ToolController {
	@FXML
	private Button btnInvert;

	@FXML
	private Button btnGreyscale;

	@FXML
	private Button btnEqualise;

	@FXML
	void invertButtonClicked(ActionEvent event) {
		// Only process the image after the initial UI load
		if (parentController != null) {
			// Then only process the image if one is present
			if (parentController.getImage() != null) {
				processedImage = invertColours(uneditedImage);
				updateImage();
				applyChanges();
			}
		}
	}

	@FXML
	void greyscaleButtonClicked(ActionEvent event) {
		// Only process the image after the initial UI load
		if (parentController != null) {
			// Then only process the image if one is present
			if (parentController.getImage() != null) {
				processedImage = greyscale(uneditedImage);
				updateImage();
				applyChanges();
			}
		}
	}

	@FXML
	void equaliseButtonClicked(ActionEvent event) {
		// Only process the image after the initial UI load
		if (parentController != null) {
			// Then only process the image if one is present
			if (parentController.getImage() != null) {
				processedImage = equaliseHistogram(uneditedImage);
				updateImage();
				applyChanges();
			}
		}
	}

	@FXML
	public void initialize() {
		// This tool doesn't make sense to use a preview and Apply button, so it is
		// bypassed here

		// Force changes to be immediately shown to the user
		chkShowChanges.setSelected(true);

		// Hide the button and checkbox from the user
		chkShowChanges.setVisible(false);
		btnApply.setVisible(false);
	}

	/**
	 * Invert the colours in the image
	 * 
	 * @param sourceImage The original, unedited image
	 * @return The finished, edited image
	 */
	private Image invertColours(Image sourceImage) {
		// Find the dimensions of the source image
		int width = (int) sourceImage.getWidth();
		int height = (int) sourceImage.getHeight();

		// Create a new image
		WritableImage newImage = new WritableImage(width, height);
		// Get an interface to write to that image memory
		PixelWriter writer = newImage.getPixelWriter();
		// Get an interface to read from the original image passed as the
		// parameter to the function
		PixelReader reader = sourceImage.getPixelReader();

		// Iterate over all pixels
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// For each pixel, get the colour
				Color color = reader.getColor(x, y);

				// Flip the colour values
				color = Color.color(1.0 - color.getRed(), 1.0 - color.getGreen(), 1.0 - color.getBlue());

				// Apply the new colour
				writer.setColor(x, y, color);
			}
		}
		return newImage;
	}

	/**
	 * Make the image black and white
	 * 
	 * @param sourceImage The original, unedited image
	 * @return The finished, edited image
	 */
	private Image greyscale(Image sourceImage) {
		// Find the dimensions of the source image
		int width = (int) sourceImage.getWidth();
		int height = (int) sourceImage.getHeight();

		// Create a new image
		WritableImage newImage = new WritableImage(width, height);
		// Get an interface to write to that image memory
		PixelWriter writer = newImage.getPixelWriter();
		// Get an interface to read from the original image passed as the
		// parameter to the function
		PixelReader reader = sourceImage.getPixelReader();

		// Iterate over all pixels
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// For each pixel, get the colour
				Color color = reader.getColor(x, y);

				// Get the average of all three colour channels
				double greyValue = ((color.getRed() + color.getGreen() + color.getBlue()) / 3);

				color = Color.color(greyValue, greyValue, greyValue);

				// Apply the new colour
				writer.setColor(x, y, color);
			}
		}
		return newImage;
	}

	/**
	 * Balance the contrast of the image through histogram equalisation
	 * 
	 * @param sourceImage The original, unedited image
	 * @return The finished, edited image
	 */
	private Image equaliseHistogram(Image sourceImage) {
		// Find the dimensions of the source image
		int width = (int) sourceImage.getWidth();
		int height = (int) sourceImage.getHeight();
		int size = width * height;

		double[] equalisedValues = new double[256];

		for (int i = 0; i < parentController.distribution[0].length; i++) {
			if (i != 0) {
				// Add the previous value's tally to the present
				equalisedValues[i] += (equalisedValues[i - 1] * size);
			}
			
			// Tally this value
			equalisedValues[i] += parentController.distribution[3][i];

			// Creates a mapping between contrast levels to adjust the image
			equalisedValues[i] = equalisedValues[i] / size;
		}

		// Create a new image
		WritableImage newImage = new WritableImage(width, height);
		// Get an interface to write to that image memory
		PixelWriter writer = newImage.getPixelWriter();
		// Get an interface to read from the original image passed as the
		// parameter to the function
		PixelReader reader = sourceImage.getPixelReader();

		// Iterate over all pixels
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// For each pixel, get the colour
				Color color = reader.getColor(x, y);

				// Get a greyscale image
				int oldValue = (int) (((color.getRed() + color.getGreen() + color.getBlue()) * 255) / 3);

				// Map the old greyscale values of the image to the new equalised values 
				color = Color.color(equalisedValues[oldValue], equalisedValues[oldValue], equalisedValues[oldValue]);

				// Apply the new colour
				writer.setColor(x, y, color);
			}
		}
		return newImage;
	}
}