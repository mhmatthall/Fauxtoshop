import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainViewController {
	private final String[] ACCEPTED_FILE_EXTENSIONS = { "*.png", "*.jpg", "*.jpeg", "*.bmp", ".gif" };

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
	private AnchorPane pneToolPane;

	@FXML
	private Pane pneToolSelector;

	@FXML
	private ImageView btnGamma;

	@FXML
	private ImageView btnContrast;

	@FXML
	private ImageView imgImageViewer;

	@FXML
	private Label lblCurrentToolName;

	@FXML
	private Font x1;

	@FXML
	private Label lblHistogram;

	@FXML
	private Font x11;

	@FXML
	private AreaChart<?, ?> chtHistogram;

	@FXML
	private Font x3;

	@FXML
	private Color x4;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	// FXML Constructor
	@FXML
	public void initialize() {
		// Select the gamma menu as the default when the program loads
		btnGammaClicked(null);
	}

	// Toolbar menu methods
	@FXML
	void openFile(ActionEvent event) throws FileNotFoundException {
		FileChooser picker = new FileChooser();

		picker.setTitle("Open Image File");

		// Filter out incompatible files from the dialog search
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
				"Image files (*.jpg, *.jpeg, *.png, *.bmp, *.gif)", // Description shown to user
				Arrays.asList(ACCEPTED_FILE_EXTENSIONS)); // File extension mask
		picker.getExtensionFilters().add(filter);

		// Open the dialog
		File chosenImage = picker.showOpenDialog(stage);

		if (!chosenImage.equals(null)) {
			// If the user selects an image
			Image img = new Image(new FileInputStream(chosenImage));
			imgImageViewer.setImage(img); // Load the image onto the main image editor

			stage.setTitle("Photoshop - '" + chosenImage.getName() + "'");
		}
	}

	@FXML
	void closeProgram(ActionEvent event) {
		stage.close();
	}

	// Gamma Correction menu methods
	@FXML
	void btnGammaClicked(MouseEvent event) {
		changeTool("fxml/menuGamma.fxml");
	}

	@FXML
	void btnGammaStartHover(MouseEvent event) {
		ImageView img = (ImageView) event.getTarget();
		/*
		 * TODO create new images for the buttons: +inactive +active +hovered
		 */

	}

	@FXML
	void btnGammaEndHover(MouseEvent event) {
		ImageView img = (ImageView) event.getTarget();
	}

	// Contrast Stretching menu methods
	@FXML
	void btnContrastClicked(MouseEvent event) {
		changeTool("fxml/menuContrast.fxml");
	}

	@FXML
	void btnContrastStartHover(MouseEvent event) {
		ImageView img = (ImageView) event.getTarget();
	}

	@FXML
	void btnContrastEndHover(MouseEvent event) {
		ImageView img = (ImageView) event.getTarget();
	}

	// Other menu methods
	/**
	 * Loads a new tool menu panel form into the tool menu region
	 * 
	 * @param fileLocation The location of the tool's corresponding FXML file
	 */
	private void changeTool(String fileLocation) {
		Pane newPane;	// Create a blank pane
		try {
			// Load the new tool onto the blank pane
			newPane = FXMLLoader.load(getClass().getResource(fileLocation));
			pneToolPane.getChildren().add(newPane); // Add the nodes to the new pane

		} catch (IOException ex) {
			// If no such FXML file could be found
			System.out.println("-ERROR: TOOL FXML FILE NOT FOUND------------------");
			ex.printStackTrace();
		}
	}
}
