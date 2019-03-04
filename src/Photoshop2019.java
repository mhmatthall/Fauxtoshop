import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Photoshop2019 extends Application {
	Parent root;
	static Stage stage;
	
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage mainStage) {
		try {
			// Retrieve the FXML file for the main window
			FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
			// Try and load the file
			root = loader.load();
			
			// Assign and customise the stage
			stage = mainStage;
			stage.setTitle("Photoshop");
			
			// Load the program icons of various sizes
			stage.getIcons().add(new Image("ico/logo256.png"));
			stage.getIcons().add(new Image("ico/logo48.png"));
			stage.getIcons().add(new Image("ico/logo32.png"));
			stage.getIcons().add(new Image("ico/logo24.png"));
			stage.getIcons().add(new Image("ico/logo16.png"));
			
			// Pass the stage into the controller so we can easily use it
			MainViewController controller = loader.getController();
			controller.setStage(stage);
			
			// Creating the main scene and displaying it
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException ex) {
			// If no such FXML file could be found
			System.out.println("-ERROR: MAIN FXML FILE NOT FOUND------------------");
			ex.printStackTrace();
		}		
	}
}
