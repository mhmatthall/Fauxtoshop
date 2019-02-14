import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuGammaController {
	private final int NUMBER_PRECISION = 2;
	
	private double gammaValue = 1.0;
	private Image processedImage;

    @FXML
    private AnchorPane pneGammaToolPane;

    @FXML
    private TextField txtGammaValue;

    @FXML
    private Label lblToolDescription;

    @FXML
    private Label lblToolName;

    @FXML
    private Font x1;

    @FXML
    private Slider sldGammaSlider;
    
    @FXML
    private Button btnApply;
    
    @FXML
    private CheckBox chkShowChanges;
    
    @FXML
    private void initialize() {
    	updateGammaValue(gammaValue);
    }
    
    @FXML
    void showChangesBoxChanged(ActionEvent event) {
    	if (chkShowChanges.isSelected()) {
    		System.out.println("I'm doing things when selected");
    		ImageConfig.setUneditedImage(ImageConfig.getImage());
    		ImageConfig.setImage(processedImage);
    	} else {
    		System.out.println("I'm doing things when UNselected");
    		ImageConfig.setImage(ImageConfig.getUneditedImage());
    	}
    }
    
    @FXML
    void applyChanges(ActionEvent event) {
    	ImageConfig.setImage(processedImage);
    	System.out.println("I have set the processed image");
    }
    
    @FXML
    void gammaSliderShifted(KeyEvent event) {
    	updateGammaValue(sldGammaSlider.getValue());
    }

    @FXML
    void gammaSliderUpdated(MouseEvent event) {
    	updateGammaValue(sldGammaSlider.getValue());
    }

    @FXML
    void gammaValueScrolled(ScrollEvent event) {
    	updateGammaValue(gammaValue + event.getDeltaY() * 0.0125);
    }

    @FXML
    void gammaValueUpdated(ActionEvent event) {
    	updateGammaValue(Double.valueOf(txtGammaValue.getText()));
    }
    
    private void updateGammaValue(double gammaValue) {
    	this.gammaValue = Math.floor(gammaValue * 100) / 100;
    	
    	// Update the UI elements with the new data
    	txtGammaValue.setText(String.valueOf(gammaValue));
    	sldGammaSlider.setValue(gammaValue);
    	
    	// Only process the image if present
    	if (ImageConfig.getImage() != null) {
    		System.out.println("I found the image");
    		processedImage = gammaCorrection(ImageConfig.getImage(), gammaValue);    		
    	} else {
    		System.out.println("There's no image");
    	}
    }
    
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
		System.out.println("I have corrected the gamma.");
		return newImage;
	}
}
