package sjsu.edu.application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import sjsu.edu.application.Transaction;
import sjsu.edu.application.TransactionList;
import sjsu.edu.application.TransactionTypeList;
import sjsu.edu.application.Models.DbConnection;

import java.time.Instant;
import java.time.ZoneId;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	
	private TransactionTypeList typeList = TransactionTypeList.getInstance();
	private TransactionList transactions = TransactionList.getInstance();
	private AccountList accList = AccountList.getInstance();
	
	public void initialize() {
	    listedTypes.getItems().addAll(typeList.getList());

	    accColumn.setCellValueFactory(cellData -> {
	        Transaction transaction = cellData.getValue();
	        Account account = accList.getAccountById(transaction.getAccID());
	        if (account != null) {
	            return new SimpleStringProperty(account.getBankName());
	        } else {
	            return new SimpleStringProperty("Unknown Account");
	        }
	    });

	    typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeName"));
	    descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
	    dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedDate()));

	    paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
	    depositAmountColumn.setCellValueFactory(new PropertyValueFactory<>("depositAmount"));

	    loadTransactions();
	}

	private void loadTransactions() {
	    ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

	    try (Connection conn = DbConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement("SELECT account_id, transaction_type_id ,tt.type_name as transaction_type ,transaction_date, transaction_description, payment_amount, deposit_amount "
	         												+ " FROM transactions t INNER JOIN transaction_types tt ON  tt.id = t.transaction_type_id "
	         												+ " ORDER BY transaction_date DESC");
	         ResultSet rs = stmt.executeQuery()) {

	        this.transactions.getList().clear();

	        while (rs.next()) {
	            String accountId = rs.getString("account_id");
	            int transactionTypeID = rs.getInt("transaction_type_id");
	            String transactionType = rs.getString("transaction_type");
	            long timestamp = rs.getLong("transaction_date");
	            LocalDate transactionDate = Instant.ofEpochMilli(timestamp)
	                                               .atZone(ZoneId.systemDefault())
	                                               .toLocalDate();

	            String description = rs.getString("transaction_description");
	            double paymentAmount = rs.getDouble("payment_amount");
	            double depositAmount = rs.getDouble("deposit_amount");

	            double amount;
	            if (paymentAmount > 0) {
	                amount = -paymentAmount;
	            } else {
	                amount = depositAmount;
	            }

	            if (accList.getAccountById(accountId) != null) {
	                Transaction transaction = new Transaction(transactionTypeID, transactionType,description, transactionDate, paymentAmount, depositAmount, accountId);
	                this.transactions.getList().add(transaction);
	            }
	        }

	    } catch (SQLException error) {
	        System.out.println("Error loading transactions from database: " + error.getMessage());
	    }

	    transactionList.addAll(this.transactions.getList());
	    TransactionView.setItems(transactionList);
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
	
	public void addTypeOP(ActionEvent event) {
		errMsg.setText("");
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
