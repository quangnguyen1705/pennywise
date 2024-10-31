package sjsu.edu.pennywise.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sjsu.edu.pennywise.Account;
import sjsu.edu.pennywise.AccountList;

public class AddTransactionController {
	
	private Stage stage;
	private Scene scene;
	
	@FXML
	private Label errMsg;
	@FXML
	private ChoiceBox<String> chosenAccount;
	@FXML
	private TextField transactionTypeInput;
	@FXML
	private DatePicker transactionDateInput;
	@FXML
	private TextField descriptionInput;
	@FXML
	private TextField transactionAmount;
	@FXML
	private TextField depositAmount;
	
	private AccountList accountList = new AccountList();
	
	public void initialize() {
		// Set the current date as the default value
		transactionDateInput.setValue(LocalDate.now());

        // Prevent users from typing in the DatePicker
		transactionDateInput.getEditor().setDisable(true);
		transactionDateInput.getEditor().setOpacity(1);		
		chosenAccount.getItems().add("--Choose an Account--");
		chosenAccount.getItems().addAll(getAccountsByName(accountList.getList()));
		chosenAccount.getSelectionModel().selectFirst();
	}
	
	private ArrayList<String> getAccountsByName(ArrayList<Account> accList) {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<>();
		for (Account acc: accList) {
			list.add(acc.getBankName());
		}
		
		return list;
	}

	public void switchToMain(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Main.fxml"));
	    AnchorPane root = loader.load();
        
	    //Refreshing accounts list
        MainController mainController = loader.getController();
        mainController.loadAccounts(); 
        
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		
		
		
	}

	public void newTransaction(ActionEvent event) {
		errMsg.setText("");
		//check for existing account
		String accountName = chosenAccount.getValue();
		if (accountName.equals("--Choose an Account--")) {
			errMsg.setText("Please select an Account");
			return;
		}
		//check transaction type
		//TODO: check to see if the type is fetched from a list
		
		//get variable for transaction date
		LocalDate date = transactionDateInput.getValue();
		
		//check for existing description
		String description = descriptionInput.getText();
		if (description.equals("")) {
			errMsg.setText("Please enter description");
			return;
		}
		
		//check for existing transaction amount
		//TODO: if transaction type is depositing money into an account, use deposit amount instead
		
	}

}
