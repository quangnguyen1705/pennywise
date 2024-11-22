package sjsu.edu.application.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import sjsu.edu.application.Account;
import sjsu.edu.application.AccountList;
import sjsu.edu.application.Transaction;
import sjsu.edu.application.TransactionList;
import sjsu.edu.application.TransactionTypeList;

public class EditTransactionController {

    private Stage stage;
    private Scene scene;

    
    // user input fields
    
    @FXML
    private ChoiceBox<String> accInput;
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


    @FXML
    private Label errorLabel; 

    private TransactionList transactionList = TransactionList.getInstance(); 
    private TransactionTypeList transactionTypeList = TransactionTypeList.getInstance();
    private AccountList accList = AccountList.getInstance();
    public static Transaction selectedTransaction; 
    
    
    public Transaction getData() {
    	return selectedTransaction;
    }
    
    // TODO: implement in frontend
    public void initialize() {
    	//get transaction account name
    	String accName = getNameByID(selectedTransaction.getAccID());
    	// Set the current date as the default value
		dateInput.setValue(selectedTransaction.getDate());

		// Prevent users from typing in the DatePicker
		dateInput.getEditor().setDisable(true);
		dateInput.getEditor().setOpacity(1);
		accInput.getItems().addAll(getAccountsByName(accList.getList()));
		accInput.getSelectionModel().select(accName);
		typeInput.getItems().addAll(transactionTypeList.getList());
		typeInput.getSelectionModel().select(selectedTransaction.getTypeName());
		descriptionInput.setText(selectedTransaction.getDescription());
		double payment = selectedTransaction.getPaymentAmount();
		double deposit = selectedTransaction.getDepositAmount();
		
		if (payment > 0) {
			paymentAmountInput.setText(String.valueOf(payment));
		}
		else {
			depositAmountInput.setText(String.valueOf(deposit));
		}

    }
    
    private ArrayList<String> getAccountsByName(ArrayList<Account> accList) {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<>();
		for (Account acc : accList) {
			list.add(acc.getBankName());
		}

		return list;
	}
    
    private String getNameByID(String id) {
    	for (Account acc: accList.getList()) {
    		if (acc.getId().equals(id)) {
    			return acc.getBankName();
    		}
    	}
    	return "";
    }
    
    private String getIDByName(String name) {
    	for (Account acc: accList.getList()) {
    		if (acc.getBankName().equals(name)) {
    			return acc.getId();
    		}
    	}
    	return "";
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
        	String accName = accInput.getValue();
            String description = descriptionInput.getText().trim();
            String paymentAmountText = paymentAmountInput.getText().trim();
            String depositAmountText = depositAmountInput.getText().trim();
            LocalDate date = dateInput.getValue();
            String typeName = typeInput.getValue();

            if (accName.isEmpty() || description.isEmpty() || date == null || typeName == null) { //checks
                errorLabel.setText("Fill in all required info fields!");
                return;
            }

            double paymentAmount = 0, depositAmount = 0;
            try {
            	if ((paymentAmountText.isEmpty() && depositAmountText.isEmpty()) || (!paymentAmountText.isEmpty() && !depositAmountText.isEmpty())) {
            		 errorLabel.setText("Fill in one of the amount fields!");
                     return;
            	}
            	if (!paymentAmountText.isEmpty()){
            		paymentAmount = Double.parseDouble(paymentAmountText);
            	}
            	else {
            		 depositAmount = Double.parseDouble(depositAmountText);
            	}
                
               

                if (paymentAmount < 0 || depositAmount < 0) { // check
                    errorLabel.setText("Amounts must be greater than 0!");
                    return;
                }
                
            } catch (NumberFormatException e) {
                errorLabel.setText("Error with formatting");
                return;
            }
            String originalID = selectedTransaction.getAccID();
            LocalDate originalDate = selectedTransaction.getDate();
            selectedTransaction.setAccID(getIDByName(accName));
            selectedTransaction.setDescription(description);
            selectedTransaction.setPaymentAmount(paymentAmount);
            selectedTransaction.setDepositAmount(depositAmount);
            selectedTransaction.setDate(date);
            selectedTransaction.setTypeName(typeName);

            // db changes
            transactionList.updateTransaction(selectedTransaction, originalID, originalDate);
            switchToTransactionSearch(event);

            errorLabel.setText("Transaction updated successfully!");
        } catch (Exception error) {
            errorLabel.setText("Error while saving changes.");
            //error.printStackTrace();
        }
    }


    
    // going back to main page
    // TODO: Back to Main button like other pages should be implemented. 
	public void switchToTransactionSearch(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Main.fxml"));
		AnchorPane root = loader.load();

		// Refreshing accounts list
		MainController mainController = loader.getController();
		mainController.loadAccounts();
		
		loader = new FXMLLoader(getClass().getResource("/views/SearchTransaction.fxml"));
		root = loader.load();
		stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
    
    
}
