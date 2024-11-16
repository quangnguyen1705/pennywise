package sjsu.edu.application.Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import sjsu.edu.application.ScheduleTransaction;
import sjsu.edu.application.ScheduleTransactionList;
import sjsu.edu.application.Models.DbConnection;

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
	private TableColumn<ScheduleTransaction, String> amountColumn;
	@FXML
	private TableColumn<ScheduleTransaction, Integer> dateColumn;
	@FXML
	private TableColumn<ScheduleTransaction, Double> freqColumn;
	
	//private TransactionList transactions = new TransactionList(); change to scheduledList
	private ScheduleTransactionList scheduleTransaction = new ScheduleTransactionList();
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
	    nameColumn.setCellValueFactory(new PropertyValueFactory<>("schedule_name"));
	    dateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

	    amountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
	    freqColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));

	   loadTransactions();
	}

	private void loadTransactions() {
	    ObservableList<ScheduleTransaction> scheduleTransactionLst = FXCollections.observableArrayList();

	    try (Connection conn = DbConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement("SELECT id, schedule_name, st.account_id, st.transaction_type_id, frequency, due_date, payment_amount, c.bank_name, t.type AS transaction_type "
	         												+ " FROM scheduled_transactions st "
	         												+ " INNER JOIN transactions t ON t.id = st.transaction_type_id "
	         												+ " INNER JOIN account c ON c.id= st.bank_name "
	         												+ " ORDER BY due_date DESC");
	         ResultSet rs = stmt.executeQuery()) {

	        this.scheduleTransaction.getList().clear(); //replace with schedule list name

	        while (rs.next()) {
	            String accountId = rs.getString("account_id");
	            String transactionType = rs.getString("transaction_type");
	            int transactionTypeID = rs.getInt("transaction_type_id");
	            String name = rs.getString("schedule_name");
	            String frequency = rs.getString("frequency");
	            int dueDate = rs.getInt("due_date");
	            
	            double paymentAmount = rs.getDouble("payment_amount");

	            if (accList.getAccountById(accountId) != null) {
	         
	                ScheduleTransaction scheduleTrans = new ScheduleTransaction(name,accountId,transactionTypeID,frequency,dueDate,paymentAmount);
	                this.scheduleTransaction.getList().add(scheduleTrans);
	            }
	        }

	    } catch (SQLException error) {
	        System.out.println("Error loading transactions from database: " + error.getMessage());
	    }

	    scheduleTransactionLst.addAll(this.scheduleTransaction.getList());
	    ScheduledView.setItems(scheduleTransactionLst);
	}
	
	public void switchToMain2(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	public void switchToAddSchedule(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/AddScheduledTransaction.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
}
