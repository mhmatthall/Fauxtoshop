import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class MenuGammaController {
	private double gammaValue = 1.0;

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
    private void initialize() {
    	txtGammaValue.setText(String.valueOf(gammaValue));
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
    	this.gammaValue = gammaValue;
    	txtGammaValue.setText(String.valueOf(gammaValue));
    	sldGammaSlider.setValue(gammaValue);
    }
}
