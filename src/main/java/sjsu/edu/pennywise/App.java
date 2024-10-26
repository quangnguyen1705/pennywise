package sjsu.edu.pennywise;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import sjsu.edu.pennywise.Models.DbConnection; 


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
		try {
			DbConnection.initializeDatabase(); // this checks if the db already exists
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

    public static void main(String[] args) {
        launch();
    }

}
