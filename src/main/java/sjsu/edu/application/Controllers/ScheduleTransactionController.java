package sjsu.edu.application.Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sjsu.edu.application.Account;
import sjsu.edu.application.AccountList;
import sjsu.edu.application.ScheduleTransactionList;
import sjsu.edu.application.Transaction;
import sjsu.edu.application.TransactionList;
import sjsu.edu.application.TransactionTypeList;
import sjsu.edu.application.Models.DbConnection;
import sjsu.edu.application.ScheduleTransaction;

public class ScheduleTransactionController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TableView<ScheduleTransaction> ScheduledView;
	@FXML
	private TableColumn<ScheduleTransaction, String> nameColumn;
	@FXML
	private TableColumn<ScheduleTransaction, String> accColumn;
	@FXML
	private TableColumn<ScheduleTransaction, String> typeColumn;
	@FXML
	private TableColumn<ScheduleTransaction, String> paymentAmountColumn;
	@FXML
	private TableColumn<ScheduleTransaction, LocalDate> dateColumn;
	@FXML
	private TableColumn<ScheduleTransaction, Double> freqColumn;
	
	private ScheduleTransactionList transactions = ScheduleTransactionList.getInstance(); 
	private AccountList accList = new AccountList();
	
	public void initialize() {

	    accColumn.setCellValueFactory(cellData -> {
	        ScheduleTransaction transaction = cellData.getValue(); //Change this to whatever the class name is
	        Account account = accList.getAccountById(transaction.getAccID());
	        if (account != null) {
	            return new SimpleStringProperty(account.getBankName());
	        } else {
	            return new SimpleStringProperty("Unknown Account");
	        }
	    });

	    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
	    nameColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
	    dateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

	    paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
	    freqColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));

	    loadTransactions();
	}

	private void loadTransactions() {
	    ObservableList<ScheduleTransaction> transactionList = FXCollections.observableArrayList();

	    try (Connection conn = DbConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM transactions ORDER BY transaction_date DESC");
	         ResultSet rs = stmt.executeQuery()) {

	        this.transactions.getList().clear(); //replace with schedule list name

	        while (rs.next()) {
	            String accountId = rs.getString("account_id");
	            int transactionType = rs.getInt("transaction_type_id");
	            String frequency = rs.getString("frequency");
	            long timestamp = rs.getLong("transaction_date");
	            LocalDate date = Instant.ofEpochMilli(timestamp)
	                                               .atZone(ZoneId.systemDefault())
	                                               .toLocalDate();
	            String name = rs.getString("schedule_name");
	            double paymentAmount = rs.getDouble("payment_amount");

	            double amount;
	            if (paymentAmount > 0) {
	                amount = -paymentAmount;
	            } else {
	                amount = paymentAmount;
	            }
	            if (accList.getAccountById(accountId) != null) {
	            	//public ScheduleTransaction(String schedName, String accID, int type, String frequency, LocalDate date, double paymentAmount)
	                ScheduleTransaction transaction = new ScheduleTransaction(name, accountId, transactionType, frequency, date, amount);
	                this.transactions.getList().add(transaction);
	            }
	        }

	    } catch (SQLException error) {
	        System.out.println("Error loading transactions from database: " + error.getMessage());
	        return;
	    }

	    transactionList.addAll(this.transactions.getList());
	    ScheduledView.setItems(transactionList);
	}
	
	public void switchToMain2(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
}
