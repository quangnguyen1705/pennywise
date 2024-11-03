package sjsu.edu.pennywise.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sjsu.edu.pennywise.Account;
import sjsu.edu.pennywise.AccountList;
import sjsu.edu.pennywise.Transaction;
import sjsu.edu.pennywise.TransactionList;
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
	@FXML 
	private TableView<Transaction> TransactionView;
	@FXML
	private TableColumn<Transaction, String> accColumn;
	@FXML
	private TableColumn<Transaction, String> typeColumn;
	@FXML
	private TableColumn<Transaction, String> descColumn;
	@FXML
	private TableColumn<Transaction, String> dateColumn;
	@FXML
	private TableColumn<Transaction, Double> amtColumn;
	
	private TransactionTypeList typeList = new TransactionTypeList();
	private TransactionList transactions = new TransactionList();
	private AccountList accList = new AccountList();
	
	public void initialize() {
		listedTypes.getItems().addAll(typeList.getList());
		//initialize cells
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedDate()));
		accColumn.setCellValueFactory(cellData -> {
			Transaction t = cellData.getValue();
			//fetch account by ID
			//get name from account object
		});
		
		loadTransactions();
		
	}
	
	private void loadTransactions() {
		ObservableList<Transaction> transactions = FXCollections.observableArrayList(this.transactions.getList());
		TransactionView.setItems(transactions);
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
