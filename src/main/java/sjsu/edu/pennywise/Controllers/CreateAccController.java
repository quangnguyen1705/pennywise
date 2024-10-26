package sjsu.edu.pennywise.Controllers;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreateAccController {

	private Stage stage;
	private Scene scene;
	
	
	public void switchToMain(ActionEvent event) throws IOException {
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void initialize() { //stop people enter on Date Picker
	OpeningDate.getEditor().setDisable(true);
	OpeningDate.getEditor().setOpacity(1);
	}
	
	
	@FXML
	private TextField AccountName;	
	@FXML
	private TextField OpeningBalance;
	@FXML
	private DatePicker OpeningDate;
	@FXML
	private Button myButton;
	@FXML
	private Label MyLabel;
	
	String accName;
	Integer openBalance;
	LocalDate openDate;
	

	public void submit(ActionEvent event) {
		
	    try {
	        // Directly assign the String from the TextField
	        openBalance = Integer.parseInt(OpeningBalance.getText());
	        System.out.println(openBalance);

	        openDate = OpeningDate.getValue();
	        System.out.println(openDate);
	        
	    	accName = AccountName.getText();
	        System.out.println(accName);


	    }
	    catch (Exception e) {
	        MyLabel.setText("Enter Valid");
	    }
	}
}

	
