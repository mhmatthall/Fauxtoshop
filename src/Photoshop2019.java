import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Photoshop2019 extends Application {
	Parent root;
	Stage stage;
	
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage mainStage) {
		try {
			root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
			
			stage = mainStage;
			stage.setTitle("Photoshop Weyy");
			
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.show();
			
		} catch (Exception ex) {
			System.out.println("-ERROR----------------------------");
			ex.printStackTrace();
		}		
	}
}
