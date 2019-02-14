import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;

public class ToolController {
	protected MainViewController parentController;
	protected Image processedImage;
	protected Image uneditedImage;

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
	 * Sets the parent controller of this controller in order to integrate it
	 * into the program.
	 * 
	 * @param parentController
	 *            The origin controller
	 */
	protected void setParentController(MainViewController parentController) {
		this.parentController = parentController;
	}
	
	/**
	 * Saves the new image when the user is happy with the changes they have
	 * made.
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
			} else {
				parentController.setImage(uneditedImage);
			}
		}
	}
}
