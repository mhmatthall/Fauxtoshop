import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MainViewController {

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

	@FXML
	void btnGammaClicked(MouseEvent event) {
		// Visual effects when tool selected
		lblCurrentToolName.textProperty().set("Gamma Correction");
		resetButtonEffects();
		btnGamma.setOpacity(1);		// Highlight the selected tool icon
		btnGamma.setScaleX(1.1);
		btnGamma.setScaleY(1.1);
		
		// Loading the selected pane's content
		Pane newPane;
		try {
			newPane = FXMLLoader.load(getClass().getResource("fxml/menuGamma.fxml"));	// Load the new pane's layout
			pneToolPane.getChildren().add(newPane);	// Add the nodes to the blank pane
		} catch (IOException ex) {
			System.out.println("FXML FILE FOR THIS MENU NOT FOUND");
			ex.printStackTrace();
		}
	}
	
	@FXML
	void btnGammaStartHover(MouseEvent event) {
		hoverEffect((ImageView) event.getTarget());
	}
	
	@FXML
	void btnGammaEndHover(MouseEvent event) {
		hoverEffect((ImageView) event.getTarget());
	}
	
	@FXML
	void btnContrastClicked(MouseEvent event) {
		
	}

	@FXML
	void btnContrastStartHover(MouseEvent event) {
		hoverEffect((ImageView) event.getTarget());
	}
	
	@FXML
	void btnContrastEndHover(MouseEvent event) {
		hoverEffect((ImageView) event.getTarget());
	}
	
	private void hoverEffect(ImageView img) {
		if (img.getScaleX() == 1) {
			Bloom b = new Bloom();
			b.setThreshold(0.2);

			if (img.getOpacity() == 0.5) {
				img.setOpacity(1);
				img.setEffect(b);
			} else {
				img.setOpacity(0.5);
				img.setEffect(null);
			}
		}
	}

	private void resetButtonEffects() {
		ObservableList<Node> list = pneToolSelector.getChildren();
		List<ImageView> buttons = getNodesOfType(pneToolSelector, ImageView.class);
		for (Node n : list) {
			n.setScaleX(1);
			n.setScaleY(1);
		}
	}
}
