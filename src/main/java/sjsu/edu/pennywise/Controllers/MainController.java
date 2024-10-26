package sjsu.edu.pennywise.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController{
	
	private Stage stage;
	private Scene scene;
	
	@FXML
	private ListView<String> AccListView;
	
	//dummy list, TODO: get list from DB 
	String[] accounts = {"acc1", "acc2", "acc3"};
	
	
	@FXML
	public void initialize() {
		// TODO Auto-generated method stub
		if (AccListView != null) {
			AccListView.getItems().addAll(accounts);
		}
		
		
	}
	
	public void switchToCreateAcc(ActionEvent event) throws IOException {
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/createAcc.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		
	}

	
	


}
