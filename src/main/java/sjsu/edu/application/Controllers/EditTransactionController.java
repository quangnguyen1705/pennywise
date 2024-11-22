package sjsu.edu.application.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import sjsu.edu.application.Transaction;
import sjsu.edu.application.TransactionList;
import sjsu.edu.application.TransactionTypeList;

public class EditTransactionController {

    private Stage stage;
    private Scene scene;

    //table views
    @FXML
    private TableView<Transaction> transactionTable; 

    @FXML
    private TableColumn<Transaction, String> descriptionColumn; 

    @FXML
    private TableColumn<Transaction, String> typeColumn; 

    @FXML
    private TableColumn<Transaction, Double> paymentAmountColumn; 

    @FXML
    private TableColumn<Transaction, Double> depositAmountColumn; 

    @FXML
    private TableColumn<Transaction, String> dateColumn; 

    
    // user input fields
    @FXML
    private TextField descriptionInput; 

    @FXML
    private TextField paymentAmountInput; 

    @FXML
    private TextField depositAmountInput; 

    @FXML
    private DatePicker dateInput; 

    @FXML
    private ChoiceBox<String> typeInput; 

    //TODO
    // Creating a new transactionType within the editing transaction. Should it just be exiting ones only? 
    @FXML
    private TextField newTypeInput; 

    @FXML
    private Label errorLabel; 

    private TransactionList transactionList = new TransactionList(); 
    private TransactionTypeList transactionTypeList = TransactionTypeList.getInstance(); // singleton design pattern
    private Transaction selectedTransaction = null; 
    
    // TODO: implement in frontend
    public void initialize() {
        try {
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeName"));
            paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
            depositAmountColumn.setCellValueFactory(new PropertyValueFactory<>("depositAmount"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedDate"));

            loadTransactionTable(transactionList.getList());
            loadTransactionTypes();
        } catch (Exception error) {
            errorLabel.setText("Failed to load data.");
            //error.printStackTrace();
        }
    }
    
    // loads transactions into a table view
    private void loadTransactionTable(List<Transaction> transactions) {
        ObservableList<Transaction> observableTransactions = FXCollections.observableArrayList(transactions);
        transactionTable.setItems(observableTransactions);
    }
    
    // load transactionTypes into dropdown selection box
    private void loadTransactionTypes() {
        typeInput.getItems().clear(); 
        typeInput.getItems().addAll(transactionTypeList.getList());
    }
    
    // TODO: create visual in editTransaction.fxml for this. 
    // This may not be needed because adding a transaction type here or defining one possibly violates SRP (single responsibility principle)
    @FXML
    public void onAddNewType(ActionEvent event) {
        String newType = newTypeInput.getText().trim();

        if (newType.isEmpty()) {
            errorLabel.setText("Transaction type cannot be empty.");
            return;
        }

        if (transactionTypeList.addType(newType).equals("Success")) {
            loadTransactionTypes();
            newTypeInput.clear();
            errorLabel.setText("New transaction type added.");
        } else {
            errorLabel.setText("Unable to add transaction type. Possible duplicate.");
        }
    }
    
    
    // TODO: use in frontend
    // load selected transaction details
    @FXML
    public void onSelectTransaction() {
        selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();

        if (selectedTransaction == null) {
            errorLabel.setText("Please select a transaction to edit!");
            return;
        }

        // load selected transaction details
        descriptionInput.setText(selectedTransaction.getDescription());
        paymentAmountInput.setText(String.valueOf(selectedTransaction.getPaymentAmount()));
        depositAmountInput.setText(String.valueOf(selectedTransaction.getDepositAmount()));
        dateInput.setValue(selectedTransaction.getDate());
        typeInput.setValue(selectedTransaction.getTypeName());

        errorLabel.setText(""); 
    }
    
    //TODO: create save changes button in frontend relating to this
    // user edits to the transaction are updated and refreshed
    @FXML
    public void onSaveChanges(ActionEvent event) {
        if (selectedTransaction == null) {
            errorLabel.setText("Please select a transaction!");
            return;
        }

        try {
            selectedTransaction.setDescription(descriptionInput.getText().trim());
            selectedTransaction.setPaymentAmount(Double.parseDouble(paymentAmountInput.getText().trim()));
            selectedTransaction.setDepositAmount(Double.parseDouble(depositAmountInput.getText().trim()));
            selectedTransaction.setDate(dateInput.getValue());
            selectedTransaction.setTypeName(typeInput.getValue());

            transactionList.getList().set(transactionTable.getSelectionModel().getSelectedIndex(), selectedTransaction);
            errorLabel.setText("Transaction updated!");
            loadTransactionTable(transactionList.getList()); 
        } catch (Exception error) {
            errorLabel.setText("Saving changes failed.");
            //error.printStackTrace();
        }
    }
    
    // going back to main page
    // TODO: Back to Main button like other pages should be implemented. 
	public void switchToMain(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Main.fxml"));
		AnchorPane root = loader.load();

		// Refreshing accounts list
		MainController mainController = loader.getController();
		mainController.loadAccounts();

		stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
    
    
}
