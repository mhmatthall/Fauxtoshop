import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane pneToolPane;
	
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
		GammaCorrection gc = new GammaCorrection();
		pneToolPane = gc;
		System.out.println(pneToolPane.getChildren().toString());
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
		Bloom b = new Bloom();
		b.setThreshold(0.25);
		
		if (img.getOpacity() == 0.5) {
			img.setOpacity(1);
			img.setEffect(b);
		} else {
			img.setOpacity(0.5);
			img.setEffect(null);
		}
	}
}
