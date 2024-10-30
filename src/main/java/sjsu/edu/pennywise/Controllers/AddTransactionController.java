package sjsu.edu.pennywise.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddTransactionController {
	
	private Stage stage;
	private Scene scene;
	
	public void switchToMain(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Main.fxml"));
	    AnchorPane root = loader.load();
        
	    //Refreshing accounts list
        MainController mainController = loader.getController();
        mainController.loadAccounts(); 
        
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		
		
		
	}
	

}
