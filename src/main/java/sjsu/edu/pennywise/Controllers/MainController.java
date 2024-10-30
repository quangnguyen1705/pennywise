package sjsu.edu.pennywise.Controllers;
/**
 * @author Matthew Tran
 * @author Aditya Rao
 */

import java.io.IOException;
import java.time.LocalDate; 
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;

import sjsu.edu.pennywise.Account;
import sjsu.edu.pennywise.AccountList;

public class MainController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private AccountList accountList = new AccountList(); 
	
	@FXML
	private TableView<Account> AccListView;
	@FXML
	private TableColumn<Account, String> AccName;
	@FXML
	private TableColumn<Account, Double> OpBalance;
	@FXML
	private TableColumn<Account, String> OpDate; // we do format this as a date in the initialization
	
	
	//TODO: get the list of accounts
	public void loadAccounts() {
        ObservableList<Account> accounts = FXCollections.observableArrayList(accountList.getList());
        AccListView.setItems(accounts);
	}
			
	@FXML
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		AccName.setCellValueFactory(new PropertyValueFactory<>("bankName"));
		OpBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        OpDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedDate()));
		
		//AccListView.setItems(list);
		loadAccounts(); 
		
	}
	
	public void switchToCreateAcc(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/createAcc.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}

	public void switchToAddTransaction(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/AddTransaction.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}

	


}
