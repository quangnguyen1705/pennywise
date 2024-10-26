package sjsu.edu.pennywise.Controllers;
/**
 * @author Matthew Tran
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sjsu.edu.pennywise.Account;

public class MainController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	
	@FXML
	private TableView<Account> AccListView;
	@FXML
	private TableColumn<Account, String> AccName;
	@FXML
	private TableColumn<Account, Double> OpBalance;
	@FXML
	private TableColumn<Account, String> OpDate;
	
	//Test Data
	//TODO: get the list of accounts
	ObservableList<Account> list = FXCollections.observableArrayList(
			
			new Account("ahvdhwad", "TestChecking", 23.33, "12/12/2024")
			
			);
			
	@FXML
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		AccName.setCellValueFactory(new PropertyValueFactory<>("bankName"));
		OpBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
		OpDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		AccListView.setItems(list);
		
	}
	
	public void switchToCreateAcc(ActionEvent event) throws IOException {
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/createAcc.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		
	}

	
	


}
