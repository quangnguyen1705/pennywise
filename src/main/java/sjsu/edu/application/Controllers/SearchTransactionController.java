package sjsu.edu.application.Controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import sjsu.edu.application.Transaction;
import sjsu.edu.application.TransactionList;

public class SearchTransactionController {
	
	private Stage stage;
	private Scene scene;

	//TODO: fix these appropriately for frontend
    @FXML
    private TextField searchField; // input search

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

    @FXML
    private Label errorLabel;

    private TransactionList transactionList = new TransactionList();

    
    // load transaction data 
    private void loadTransactionTable(List<Transaction> transactions) {
        ObservableList<Transaction> observableTransactions = FXCollections.observableArrayList(transactions);
        transactionTable.setItems(observableTransactions);
    }
    
    public void initialize() {
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeName"));
        paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        depositAmountColumn.setCellValueFactory(new PropertyValueFactory<>("depositAmount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedDate"));
        
        loadTransactionTable(transactionList.getList());
    }



    
    @FXML
    // TODO: use onSearch with a search button in searchtransaction.fxml
    public void onSearch(ActionEvent event) {
    	try {
        String query = searchField.getText().trim();
        
        if (query.isEmpty()) {
            errorLabel.setText("Search field cannot be empty!");
            return;
        }

        List<Transaction> filteredTransactions = transactionList.getList()
        	    .stream()
        	    .filter(transaction -> transaction.getDescription().toLowerCase().contains(query.toLowerCase())
        	            || transaction.getTypeName().toLowerCase().contains(query.toLowerCase())
        	            || transaction.getFormattedDate().contains(query))
        	    .collect(Collectors.toList());


        if (filteredTransactions.isEmpty()) {
            errorLabel.setText("No transactions found!");
            transactionTable.setItems(FXCollections.observableArrayList());
        } else {
            errorLabel.setText("");
            loadTransactionTable(filteredTransactions);
        } 
    } catch (Exception error) {
            errorLabel.setText("Unexpected error while searching");	
        }
    }
    
    
    // going back to main page
    // TODO: Back to Main button like other pages should be implemented. 
	public void switchToTransaction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Main.fxml"));
		AnchorPane root = loader.load();

		// Refreshing accounts list
		MainController mainController = loader.getController();
		mainController.loadAccounts();
		
		loader = new FXMLLoader(getClass().getResource("/views/Transaction.fxml"));
		root = loader.load();

		stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	
	public void gotoEditTransaction(MouseEvent event) throws IOException{
		Transaction selectedT = transactionTable.getSelectionModel().getSelectedItem();
		
		if (selectedT != null) {
			EditTransactionController.selectedTransaction = selectedT;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditTransaction.fxml"));
			Scene scene = new Scene(loader.load());
			
			stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		}
		else {
			System.out.println("No transaction selected");
			
		}
		
	}
}
