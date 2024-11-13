package sjsu.edu.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sjsu.edu.application.Models.DbConnection;
import javafx.fxml.FXMLLoader; 


/**
 * JavaFX App
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
		try {
			DbConnection.initializeDatabase(); // this checks if the db already exists
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/main.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setResizable(false);
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
