package sjsu.edu.pennywise.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sjsu.edu.pennywise.TransactionTypeList;

public class TransactionController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextField typeInputField;
	@FXML 
	private ListView<String> listedTypes;
	@FXML
	private Label errMsg;
	
	private TransactionTypeList typeList = new TransactionTypeList();
	
	public void initialize() {
		listedTypes.getItems().addAll(typeList.getList());
		
	}
	
	
	public void switchToMain2(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public void addTypeOP(ActionEvent event) {
		String type = typeInputField.getText();
		if (type.equals("")){
			errMsg.setText("Please enter a valid type");
			return;
		}
		String res = typeList.addType(type);
		typeInputField.setText("");
		if(res.equals("Success")) {
			listedTypes.getItems().add(type);
		}
		else {
			errMsg.setText("Duplicate Type Found");
		}
		
		
	}
	
	

}
