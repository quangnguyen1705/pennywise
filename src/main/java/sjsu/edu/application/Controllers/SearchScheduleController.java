package sjsu.edu.application.Controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
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
import sjsu.edu.application.Account;
import sjsu.edu.application.ScheduleTransaction;
import sjsu.edu.application.ScheduleTransactionList;
import sjsu.edu.application.Transaction;
import sjsu.edu.application.TransactionList;
import sjsu.edu.application.TransactionTypeList;

public class SearchScheduleController {
	
	private Stage stage;
	private Scene scene;

	//TODO: fix these appropriately for frontend
    @FXML
    private TextField searchField; // input search

    @FXML
    private TableView<ScheduleTransaction> transactionTable;

    @FXML
    private TableColumn<ScheduleTransaction, String> descriptionColumn;

    @FXML
    private TableColumn<ScheduleTransaction, String> typeColumn;

    @FXML
    private TableColumn<ScheduleTransaction, Double> amountColumn;
    @FXML
    private TableColumn<ScheduleTransaction, String> frequencyColumn;


    @FXML
    private TableColumn<ScheduleTransaction, Integer> dateColumn;

    @FXML
    private Label errorLabel;

    private ScheduleTransactionList transactionList = ScheduleTransactionList.getInstance();
    private TransactionTypeList typeList = TransactionTypeList.getInstance();

    
    // load transaction data 
    private void loadTransactionTable(List<ScheduleTransaction> transactions) {
        ObservableList<ScheduleTransaction> observableTransactions = FXCollections.observableArrayList(transactions);
        transactionTable.setItems(observableTransactions);
    }
    
    public void initialize() {
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("schedName"));
        typeColumn.setCellValueFactory(cellData -> {
	        ScheduleTransaction transaction = cellData.getValue();
	        int typeID = transaction.getType();
	        return new SimpleStringProperty(getTypeByID(typeID));
	    });
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        frequencyColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        
        loadTransactionTable(transactionList.getList());
        
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            onSearch(newValue.trim());
        });
        
    }
    
    private String getTypeByID(int i) {
    	try {
    		return typeList.getList().get(i);
    	}
    	
    	catch(Exception e) {
    		return "Unknown Type";
    	}
    }

    
    @FXML
    public void onSearch(String query) {
    	try {
        
        if (query.isEmpty()) {
            errorLabel.setText("");
            loadTransactionTable(transactionList.getList());
            return;
        }

        List<ScheduleTransaction> filteredTransactions = transactionList.getList()
        	    .stream()
        	    .filter(transaction -> transaction.getSchedName().toLowerCase().contains(query.toLowerCase())) // removed other fields for search, since only description is needed
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
	public void switchToSchedule(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Main.fxml"));
		AnchorPane root = loader.load();

		// Refreshing accounts list
		MainController mainController = loader.getController();
		mainController.loadAccounts();
		
		loader = new FXMLLoader(getClass().getResource("/views/ScheduledTransactions.fxml"));
		root = loader.load();

		stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	
	public void gotoEditScheduledTransaction(MouseEvent event) throws IOException{
		ScheduleTransaction selectedT = transactionTable.getSelectionModel().getSelectedItem();
		
		if (selectedT != null) {
			EditScheduledTransactionController.selectedTransaction = selectedT;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditScheduledTransaction.fxml"));
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
