package sjsu.edu.application.Controllers;
/**
 * @author Matthew Tran
 * @author Aditya Rao
 */

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import sjsu.edu.application.Account;
import sjsu.edu.application.AccountList;
import sjsu.edu.application.ScheduleTransaction;
import sjsu.edu.application.ScheduleTransactionList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;

public class MainController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private AccountList accountList = AccountList.getInstance();
	private ScheduleTransactionList duePayments = ScheduleTransactionList.getInstance();
	
	@FXML
	private TableView<Account> AccListView;
	@FXML
	private TableColumn<Account, String> AccName;
	@FXML
	private TableColumn<Account, Double> OpBalance;
	@FXML
	private TableColumn<Account, String> OpDate; // we do format this as a date in the initialization
	@FXML
	private MenuButton TransactionPortal;
	@FXML
	private MenuButton SchedulePortal;
	@FXML
	private TableView<ScheduleTransaction> dueTable;
	@FXML
	private TableColumn<ScheduleTransaction, String> duePayment;
	@FXML
	private TableColumn<ScheduleTransaction, Double> Amount;
	
	
	//TODO: get the list of accounts
	public void loadAccounts() {
        ObservableList<Account> accounts = FXCollections.observableArrayList(accountList.getList());
        AccListView.setItems(accounts);
	}
	public void loadDueItems() {
		ObservableList<ScheduleTransaction> dueList = FXCollections.observableArrayList(duePayments.getList());
        for (ScheduleTransaction t: dueList) {
        	if (t.getDate() == LocalDate.now().getDayOfMonth()) {
        		dueTable.getItems().add(t);
        	}
        }
	}
			
	@FXML
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		AccName.setCellValueFactory(new PropertyValueFactory<>("bankName"));
		OpBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        OpDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedDate()));
		loadAccounts();
		duePayment.setCellValueFactory(new PropertyValueFactory<>("schedName"));
		Amount.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
		loadDueItems();
	}
	
	public void gotoAccReport() {
		
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
		stage = (Stage) TransactionPortal.getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public void switchToTransactions(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/Transaction.fxml"));
		stage = (Stage) TransactionPortal.getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	public void switchToAddSchedule(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/AddScheduledTransaction.fxml"));
		stage = (Stage) SchedulePortal.getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	public void switchToSchedule(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/ScheduledTransactions.fxml"));
		stage = (Stage) SchedulePortal.getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}


}
