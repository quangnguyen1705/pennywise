package sjsu.edu.application.Controllers;

import java.io.IOException;
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
import sjsu.edu.application.Account;
import sjsu.edu.application.AccountList;
import sjsu.edu.application.List;
import sjsu.edu.application.Transaction;
import sjsu.edu.application.TransactionList;
import sjsu.edu.application.TransactionTypeList;

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
	@FXML
	private TableColumn<Transaction, Double> paymentAmountColumn;
	@FXML
	private TableColumn<Transaction, Double> depositAmountColumn;

	
	private List<String> typeList = new List(new TransactionTypeList());
	private List<Transaction> transactions = new List(new TransactionList());
	private List<Account> accList = new List(new AccountList());
	
	public void initialize() {
	    listedTypes.getItems().addAll(typeList.getList());

	    accColumn.setCellValueFactory(cellData -> {
	        Transaction transaction = cellData.getValue();
	        Account account = getAccountById(transaction.getAccID());
	        if (account != null) {
	            return new SimpleStringProperty(account.getBankName());
	        } else {
	            return new SimpleStringProperty("Unknown Account");
	        }
	    });

	    typeColumn.setCellValueFactory(cellData -> {
	    	try {
	    		Transaction transaction = cellData.getValue();
	    		int typeID = transaction.getType();
	    		String typeName = typeList.getList().get(typeID - 1);
	    		return new SimpleStringProperty(typeName);
	    	}catch (Exception e) {
	    		return new SimpleStringProperty("Unkown Type");
	    		
	    	}
	    	
	    });
	    descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
	    dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedDate()));

	    paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
	    depositAmountColumn.setCellValueFactory(new PropertyValueFactory<>("depositAmount"));

	    ObservableList<Transaction> transactionList = FXCollections.observableArrayList(transactions.getList());
	    TransactionView.setItems(transactionList);
	    
	}

	public Account getAccountById(String accountId) {
        for (Account account : accList.getList()) {
            if (account.getId().equals(accountId)) {
                return account;
            }
        }
        return null;
    }
	
	public void switchToMain2(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}

	
	public void gotoSearchTransactions(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/SearchTransaction.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public void switchToScheduledTransaction(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/ScheduledTransactions.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public boolean containsDuplicate(String type) {
		return typeList.getList().contains(type);
	}
	
	public void addTypeOP(ActionEvent event) {
		errMsg.setText("");
		String type = typeInputField.getText();
		if (type.equals("")){
			errMsg.setText("Please enter a valid type");
			return;
		}
		if (!containsDuplicate(type)) {
			typeList.add(type);
			typeInputField.setText("");
			listedTypes.getItems().add(type);
		}
		else {
			errMsg.setText("Duplicate Type Found");
		}
		
		
		
		
	}
	
	

}
