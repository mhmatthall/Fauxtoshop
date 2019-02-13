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
	private Image image;

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
    void applyChanges(ActionEvent event) {
    	
    }
    
    @FXML
    private void initialize() {
    	//updateGammaValue(gammaValue);
    	// something to do with .addListener
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
    	updateGammaValue(gammaValue + event.getDeltaX() * 0.25);
    }

    @FXML
    void gammaValueUpdated(ActionEvent event) {
    	updateGammaValue(Double.valueOf(txtGammaValue.getText()));
    }
    
    private void updateGammaValue(double gammaValue) {
    	this.gammaValue = round(gammaValue, NUMBER_PRECISION);
    	
    	txtGammaValue.setText(String.valueOf(gammaValue));
    	sldGammaSlider.setValue(gammaValue);
    	
    	if (chkShowChanges.isSelected()) {
    		gammaCorrect(image, gammaValue);    		
    	}
    }
    
    private double round(double x, int decimalPlaces) {
    	x = x * (10 * decimalPlaces);
    	int temp = (int)x;
    	return temp / (10 * decimalPlaces);
    }
    
    public Image gammaCorrect(Image image, double gammaValue) {
		// Find the dimensions of the image to be processed
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();
		
		// Create a new image of that width and height
		WritableImage newImage = new WritableImage(width, height);
		// Get an interface to write to that image memory
		PixelWriter w = newImage.getPixelWriter();
		// Get an interface to read from the original image passed as the
		// parameter to the function
		PixelReader newReader = image.getPixelReader();

		// Pre-calculate the gamma values for each possible value for efficiency
		double gammaLookup[] = new double[256];
		for (int i = 0; i < 256; i++) {
			gammaLookup[i] = Math.pow((double) i / 255, 1.0 / gammaValue);
		}

		// Iterate over all pixels
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// For each pixel, get the colour
				Color color = newReader.getColor(x, y);
				// Do something (in this case invert) - the getColor function
				// returns colours as 0..1 doubles (we could multiply by 255 if
				// we want 0-255 colours)
				color = Color.color(gammaLookup[(int) (color.getRed() * 255)],
						gammaLookup[(int) (color.getGreen() * 255)], gammaLookup[(int) (color.getBlue() * 255)]);

				// Apply the new colour
				w.setColor(x, y, color);
			}
		}
		return newImage;
	}

	public void setParent(MainViewController parent) {
		this.parent = parent;
	}
}
