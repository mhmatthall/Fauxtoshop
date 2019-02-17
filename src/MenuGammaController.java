import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

/**
 * The gamma correction module for the CS-255 Photoshop coursework.
 * 
 * @author Matt Hall (961500)
 */
public class MenuGammaController extends ToolController {
	private double gammaValue = 1.0;

	@FXML
	private TextField txtGamma;

	@FXML
	private Slider sldGamma;

	@FXML
	private void initialize() {
		updateGammaValue(gammaValue);
	}

	@FXML
	void gammaSliderUpdated(MouseEvent event) {
		updateGammaValue(sldGamma.getValue());
	}

	@FXML
	void gammaTextScrolled(ScrollEvent event) {
		updateGammaValue(gammaValue + event.getDeltaY() * 0.0125);
	}

	@FXML
	void gammaTextUpdated(ActionEvent event) {
		updateGammaValue(Double.valueOf(txtGamma.getText()));
	}

	/**
	 * Changes the value of gamma used in the correction calculations
	 * 
	 * @param gammaValue The new gamma value
	 */
	private void updateGammaValue(double gammaValue) {
		if (gammaValue < 0) {
			// Ensure gamma is never negative to avoid div0
			gammaValue = 0;
		} else {
			// Round the number to however many decimal places
			this.gammaValue = Math.floor(gammaValue * (10 * NUMBER_PRECISION)) / (10 * NUMBER_PRECISION);
		}

		// Update the UI elements with the new data
		txtGamma.setText(String.valueOf(gammaValue));
		sldGamma.setValue(gammaValue);

		// Only process the image after the initial UI load
		if (parentController != null) {
			// Then only process the image if one is present
			if (parentController.getImage() != null) {
				processedImage = gammaCorrection(uneditedImage, gammaValue);
				updateImage();
			}
		}
	}

	/**
	 * Apply gamma correction to an image
	 * 
	 * @param sourceImage The original, uncorrected image
	 * @param gammaValue  The value of gamma used in the calculation
	 * @return The corrected image
	 */
	private Image gammaCorrection(Image sourceImage, double gammaValue) {
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

		// Pre-calculate the gamma values for each possible value for efficiency
		double gammaLookup[] = new double[256];
		for (int i = 0; i < 256; i++) {
			gammaLookup[i] = Math.pow((double) i / 255, 1.0 / gammaValue);
		}

		// Iterate over all pixels
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// For each pixel, get the colour
				Color color = reader.getColor(x, y);

				color = Color.color(gammaLookup[(int) (color.getRed() * 255)],
						gammaLookup[(int) (color.getGreen() * 255)], gammaLookup[(int) (color.getBlue() * 255)]);

				// Apply the new colour
				writer.setColor(x, y, color);
			}
		}
		return newImage;
	}
}
