package sjsu.edu.pennywise.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TransactionController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	public void switchToMain2(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}

}
