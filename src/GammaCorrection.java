import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class GammaCorrection extends AnchorPane {
	
	public GammaCorrection() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/menuGamma.fxml"));
		
		try {
			fxmlLoader.load();
		} catch (Exception ex) {
			System.out.println("-ERROR----------------------------");
			ex.printStackTrace();
		}
	}
}