import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class MenuFilterController extends ToolController {
	
    @FXML
    private ComboBox<String> cbbFilters;

    @FXML
    void filterSelected(ActionEvent event) {
    	for (Filter f : Filter.values()) {
    		if (f.getDescription().equals(cbbFilters.getValue())) {
    			selectFilter(f);
    		}
    	}
    }
    
    @FXML
    void initialize() {
    	for (Filter f : Filter.values()) {
    		cbbFilters.getItems().add(f.getDescription());
    	}
    }
    
    private void selectFilter(Filter filter) {
		// Only process the image after the initial UI load
		if (parentController != null) {
			// Then only process the image if one is present
			if (parentController.getImage() != null) {
				processedImage = applyFilter(uneditedImage, filter);
				updateImage();
			}
		}
    }
    
    private Image applyFilter(Image sourceImage, Filter filter) {
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

		// Calculate the amount of pixels surrounding the image to avoid
		int boundary = (int) Math.floor(filter.getSize() / 2);
		
		double kernelValues[][][] = new double[3][height][width];
		double min[] = { Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE };
		double max[] = { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE };

		// Iterate over all pixels
		for (int y = boundary; y < (height - boundary); y++) {
			for (int x = boundary; x < (width - boundary); x++) {
				double r;	// Temp red colour value
				double g;	// Temp green colour value
				double b;	// Temp blue colour value
				int currentMultiplier = 0;	// The index of the kernel's multiplier to be used next
				
				// Iterate over the surrounding pixels in an NxN grid
				for (int i = y - boundary; i <= (y + boundary); i++) {
					for (int j = x - boundary; j <= (x + boundary); j++) {
						r = (reader.getColor(j, i).getRed());
						g = (reader.getColor(j, i).getGreen());
						b = (reader.getColor(j, i).getBlue());
						
						// Add the current pixel's calculation to each channel
						kernelValues[0][y][x] += r * (filter.getPattern()[currentMultiplier]);
						kernelValues[1][y][x] += g * (filter.getPattern()[currentMultiplier]);
						kernelValues[2][y][x] += b * (filter.getPattern()[currentMultiplier]);
						
						currentMultiplier++;
					}
				}
				
				// Check if new value is also the new min/max value
				double currentValue = 0;
				for (int i = 0; i < 3; i++) {
					currentValue = kernelValues[i][y][x];
					if (currentValue > max[i]) {
						max[i] = currentValue;
					}
					
					if (currentValue < min[i]) {
						min[i] = currentValue;
					}
				}
			}
		}
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				for (int i = 0; i < 3; i++) {
					// Normalise the values back into the range 0-1					
					kernelValues[i][y][x] = (kernelValues[i][y][x] - min[i]) / (max[i] - min[i]);
					
					// Prevents values drifting out of range on some filters when value is zero
					if (kernelValues[i][y][x] < 0) {
						kernelValues[i][y][x] = 0;
					}
				}
				
				Color newColour = Color.color(
						kernelValues[0][y][x],
						kernelValues[1][y][x],
						kernelValues[2][y][x]);
				
				writer.setColor(x, y, newColour);
			}
		}
		
		return newImage;
    }
}
