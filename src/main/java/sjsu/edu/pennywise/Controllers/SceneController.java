package sjsu.edu.pennywise.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneController {
	
	private Stage stage;
	private Scene scene;
	
	public void switchToCreateAcc(ActionEvent event) throws IOException {
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/createAcc.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		
	}
	
public void switchToMain(ActionEvent event) throws IOException{
	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
	stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
	scene = new Scene(root);
	stage.setScene(scene);
	stage.show();
		
	}

}
