import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

	public Image getImage() {
		return imgImageViewer.getImage();
	}
	
	public void setImage(Image newImage) {
		imgImageViewer.setImage(newImage);
	}
	
	// FXML Constructor
	@FXML
	public void initialize() {
		// Open the gamma correction menu as the default
		btnGammaClicked(null);
		
		// Disable the controls until the user loads a file
		pneMainSplit.disableProperty().set(true);
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

		if (chosenImage != null) {
			// If the user actually selects an image, then get it
			Image img = new Image(new FileInputStream(chosenImage));
			imgImageViewer.setImage(img);

			// Update the window title
			stage.setTitle("Photoshop - '" + chosenImage.getName() + "'");
			
			// Re-enable the UI
			pneMainSplit.disableProperty().set(false);
			
			
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
	 * @param fileLocation
	 *            The location of the tool's corresponding FXML file
	 */
	private void changeTool(String fileLocation) {
		if (!pneMainSplit.isDisable()) {
			Pane newPane; // Create a blank pane
			try {
				// Load the new tool onto the blank pane
				FXMLLoader loader = new FXMLLoader(getClass().getResource(fileLocation));				
				newPane = loader.load();	// It's not that it can't load the file, it's that it can't find the parent yet
				ToolController controller = loader.getController();
				controller.setParentController(this);

				// Add the nodes to the new pane
				pneToolPane.getChildren().add(newPane);
				
			} catch (IOException ex) {
				// If no such FXML file could be found
				System.out.println("-ERROR: TOOL FXML FILE NOT FOUND------------------");
				ex.printStackTrace();
			}
		}
	}
}
