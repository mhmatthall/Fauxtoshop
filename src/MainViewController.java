import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainViewController {
	private final String[] ACCEPTED_FILE_EXTENSIONS = { "*.png", "*.jpg", "*.jpeg", "*.bmp", ".gif" };

	private Image[][] icons = new Image[Tool.values().length][ButtonStatus.values().length];
	private ToolController currentToolController;
	private Stage stage;

	@FXML
	private MenuItem menuFileNew;

	@FXML
	private MenuItem menuFileOpen;

	@FXML
	private MenuItem menuFileClose;

	@FXML
	private MenuItem menuFileSave;

	@FXML
	private MenuItem menuFileSaveAs;

	@FXML
	private MenuItem menuFileQuit;

	@FXML
	private MenuItem menuEditUndo;

	@FXML
	private MenuItem menuEditRedo;

	@FXML
	private Menu menuAbout;

	@FXML
	private SplitPane pneMainSplit;

	@FXML
	private Pane pneToolSelector;

	@FXML
	private AnchorPane pneToolPane;

	@FXML
	private ImageView btnEffects;

	@FXML
	private ImageView btnGamma;

	@FXML
	private ImageView btnContrast;

	@FXML
	private ImageView btnBlur;

	@FXML
	private ImageView imgImageViewer;

	@FXML
	private AreaChart<?, ?> chtHistogram;

	@FXML
	private CheckBox chkHistogramShowRGB;

	@FXML
	private CheckBox chkHistogramShowBlue;

	@FXML
	private CheckBox chkHistogramShowGreen;

	@FXML
	private CheckBox chkHistogramShowRed;

	@FXML
	private Label txtLeftStatus;

	// FXML Constructor
	@FXML
	public void initialize() {
		// Disable the controls until the user loads a file
		pneMainSplit.disableProperty().set(true);

		// Load the tool icons from file
		loadIcons();
	}

	// Getters and setters
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Image getImage() {
		return imgImageViewer.getImage();
	}

	public void setImage(Image newImage) {
		imgImageViewer.setImage(newImage);
	}

	@FXML
	void fileOpenClicked(ActionEvent event) {
		openFile();
	}

	@FXML
	void fileCloseClicked(ActionEvent event) {
		closeFile();
	}

	@FXML
	void fileQuitClicked(ActionEvent event) {
		closeProgram();
	}

	@FXML
	void btnEffectsClicked(MouseEvent event) {
		changeTool(Tool.EFFECTS, event);
	}

	@FXML
	void btnGammaClicked(MouseEvent event) {
		changeTool(Tool.GAMMA_CORRECTION, event);
	}

	@FXML
	void btnContrastClicked(MouseEvent event) {
		changeTool(Tool.CONTRAST_STRETCHING, event);
	}

	@FXML
	void btnBlurClicked(MouseEvent event) {
		changeTool(Tool.BLUR, event);
	}

	@FXML
	void btnEffectsStartHover(MouseEvent event) {
		hoverEffect(Tool.EFFECTS, event);
	}

	@FXML
	void btnEffectsEndHover(MouseEvent event) {
		hoverEffect(Tool.EFFECTS, event);
	}

	@FXML
	void btnGammaStartHover(MouseEvent event) {
		hoverEffect(Tool.GAMMA_CORRECTION, event);
	}

	@FXML
	void btnGammaEndHover(MouseEvent event) {
		hoverEffect(Tool.GAMMA_CORRECTION, event);
	}

	@FXML
	void btnContrastStartHover(MouseEvent event) {
		hoverEffect(Tool.CONTRAST_STRETCHING, event);
	}

	@FXML
	void btnContrastEndHover(MouseEvent event) {
		hoverEffect(Tool.CONTRAST_STRETCHING, event);
	}

	@FXML
	void btnBlurStartHover(MouseEvent event) {
		hoverEffect(Tool.BLUR, event);
	}

	@FXML
	void btnBlurEndHover(MouseEvent event) {
		hoverEffect(Tool.BLUR, event);
	}

	@FXML
	void histogramShowBlueBoxChanged(ActionEvent event) {

	}

	@FXML
	void histogramShowGreenBoxChanged(ActionEvent event) {

	}

	@FXML
	void histogramShowRGBBoxChanged(ActionEvent event) {

	}

	@FXML
	void histogramShowRedBoxChanged(ActionEvent event) {

	}

	private void loadIcons() {
		try {
			for (Tool t : Tool.values()) {
				// For each of the different tools
				for (ButtonStatus b : ButtonStatus.values()) {
					// For each of the different button statuses
					String path = "ico/ico" + t.getInternalToolName() + b.getFilename() + ".png";

					icons[t.getIndex()][b.getValue()] = new Image(getClass().getResource(path).toString());
				}
			}
		} catch (NullPointerException ex) {
			// If an icon image file could not be found
			System.out.println("-ERROR: ONE OR MORE TOOL ICON FILES NOT FOUND------------------");
			ex.printStackTrace();
		}
	}

	private void closeProgram() {
		stage.close();
	}

	private void closeFile() {
		// Reset the tool panels
		resetUI();

		// Clear the tool's stored image cache
		if (currentToolController != null) {
			// Only if the user has selected a tool
			currentToolController.clearImages(true);
		}

		// Remove the displayed image
		imgImageViewer.setImage(null);

		// Update the window title
		stage.setTitle("Photoshop");

		// Disable the UI
		pneMainSplit.disableProperty().set(true);
	}

	private void openFile() {
		FileChooser picker = new FileChooser();

		picker.setTitle("Open Image File");

		// Filter out incompatible files from the dialog search
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
				"Image files (*.jpg, *.jpeg, *.png, *.bmp, *.gif)", // Description shown to user
				Arrays.asList(ACCEPTED_FILE_EXTENSIONS)); // File extension mask
		picker.getExtensionFilters().add(filter);

		// Open the dialog
		File chosenImage = picker.showOpenDialog(stage);

		if (chosenImage != null) {
			if (imgImageViewer != null) {
				// If another file is loaded, close that one first
				closeFile();
			}
			try {
				// If the user actually selects an image, then get it
				Image img = new Image(new FileInputStream(chosenImage));
				imgImageViewer.setImage(img);

				// Update the window title
				stage.setTitle("Photoshop - '" + chosenImage.getName() + "'");

				// Re-enable the UI
				pneMainSplit.disableProperty().set(false);

			} catch (FileNotFoundException ex) {
				// If no such image file could be found, or it could not be accessed
				System.out.println("-ERROR: SELECTED IMAGE FILE INACCESSIBLE------------------");
				ex.printStackTrace();
			}
		}
	}

	private void hoverEffect(Tool toolType, MouseEvent event) {
		// Obtain the button being actioned upon
		ImageView img = (ImageView) event.getTarget();

		if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
			// If the mouse is starting hovering over the button
			if (!img.getImage().equals(icons[toolType.getIndex()][ButtonStatus.SELECTED.getValue()])) {
				// Don't show the wrong graphic if the tool is in use
				img.setImage(icons[toolType.getIndex()][ButtonStatus.HOVERED.getValue()]);
				txtLeftStatus.setText(toolType.getInternalToolName() + " tool");
			}
		} else {
			// If the mouse is stopping hovering over the button
			if (!img.getImage().equals(icons[toolType.getIndex()][ButtonStatus.SELECTED.getValue()])) {
				// Don't show the wrong graphic if the tool is in use
				img.setImage(icons[toolType.getIndex()][ButtonStatus.DESELECTED.getValue()]);
				txtLeftStatus.setText("");
			}
		}
	}

	/**
	 * Loads a new tool menu panel form into the tool menu region
	 * 
	 * @param fileLocation The location of the tool's corresponding FXML file
	 */
	private void changeTool(Tool toolType, MouseEvent event) {
		if (!pneMainSplit.isDisable()) {
			resetUI();

			// Obtain the button being actioned upon
			ImageView img = (ImageView) event.getTarget();

			// Retrieve and set the correct 'tool selected' image from the image cache
			img.setImage(icons[toolType.getIndex()][ButtonStatus.SELECTED.getValue()]);

			Pane newPane; // Create a blank pane
			try {
				// Load the new tool onto the blank pane
				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("fxml/menu" + toolType.getInternalToolName() + ".fxml"));
				newPane = loader.load();
				currentToolController = loader.getController();
				currentToolController.setParentController(this);
				currentToolController.initialiseImages();

				// Add the nodes to the new pane
				pneToolPane.getChildren().add(newPane);

			} catch (IOException ex) {
				// If no such FXML file could be found
				System.out.println("-ERROR: TOOL FXML FILE NOT FOUND------------------");
				ex.printStackTrace();
			}
		}
	}

	private void resetUI() {
		// Reset the tool icon images
		for (Tool t : Tool.values()) {
			Node currentTool = pneToolSelector.getChildren().get(t.getIndex());
			String path = "ico/ico" + t.getInternalToolName() + "Deselected.png";
			((ImageView) currentTool).setImage(new Image(getClass().getResource(path).toString()));
		}

		// Remove all of the nodes of the current tool
		pneToolPane.getChildren().clear();
	}
}
