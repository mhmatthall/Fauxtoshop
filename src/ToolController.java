import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;

/**
 * The root of all tool controllers, allowing them to integrate with the rest of
 * the program. All tool controllers must have an 'Accept' button and 'Show
 * changes' checkbox on their forms.
 * 
 * @author Matt Hall (961500)
 */
public class ToolController {
	/**
	 * The number of decimal places that values will be rounded to.
	 */
	protected final int NUMBER_PRECISION = 2;
	/**
	 * The parent controller from which this controller is created
	 */
	protected MainViewController parentController;
	/**
	 * The source image stored to restore image to pre-processed state
	 */
	protected Image uneditedImage;
	/**
	 * The image with effects applied
	 */
	protected Image processedImage;
	
	@FXML
	protected Button btnApply;

	@FXML
	protected CheckBox chkShowChanges;

	@FXML
	void showChangesBoxChanged(ActionEvent event) {
		updateImage();
	}

	@FXML
	void applyButtonClicked(ActionEvent event) {
		applyChanges();
	}

	/**
	 * Sets the parent controller of this controller.
	 * 
	 * @param parentController The origin controller
	 */
	protected void setParentController(MainViewController parentController) {
		this.parentController = parentController;
	}

	/**
	 * Load the images once the parent controller is established
	 */
	public void initialiseImages() {
		Image img = parentController.getImage();
		uneditedImage = img;
		processedImage = img;
	}
	
	/**
	 * Saves the new image when the user is happy with the changes they have made.
	 */
	protected void applyChanges() {
		parentController.setImage(processedImage);
		uneditedImage = processedImage;
	}

	/**
	 * Refresh the image being displayed, and show the image if the user has
	 * selected to view its changes
	 */
	protected void updateImage() {
		// Ignore if the image is unchanged
		if (uneditedImage != null) {
			// If the user wants to show changes as they're being made
			if (chkShowChanges.isSelected()) {
				parentController.setImage(processedImage);
				parentController.refreshHistogram();
			} else {
				parentController.setImage(uneditedImage);
			}
		}
	}

	/**
	 * Resets the image to the unedited version
	 */
	public void clearImages(boolean isClosingImage) {
		if (!isClosingImage) {
			parentController.setImage(uneditedImage);
		}
		uneditedImage = null;
		processedImage = null;
	}
}