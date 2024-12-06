package sjsu.edu.application.Controllers;

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
import sjsu.edu.application.Account;
import sjsu.edu.application.AccountList;
import sjsu.edu.application.List;
import sjsu.edu.application.Transaction;
import sjsu.edu.application.TransactionList;
import sjsu.edu.application.TransactionTypeList;



public class AddTransactionController {

	private Stage stage;
	private Scene scene;

	@FXML
	private Label errMsg;
	@FXML
	private ChoiceBox<String> chosenAccount;
	@FXML
	private ChoiceBox<String> transactionTypeInput;
	@FXML
	private DatePicker transactionDateInput;
	@FXML
	private TextField descriptionInput;
	@FXML
	private TextField transactionAmount;
	@FXML
	private TextField depositAmount;

	private List<Account> accountList = new List(new AccountList());
	private List<Transaction> transactionLst = new List(new TransactionList());
	private List<String> typeList = new List(new TransactionTypeList());


	public void initialize() {
		// Set the current date as the default value
		transactionDateInput.setValue(LocalDate.now());

		// Prevent users from typing in the DatePicker
		transactionDateInput.getEditor().setDisable(true);
		transactionDateInput.getEditor().setOpacity(1);
		chosenAccount.getItems().addAll(getAccountsByName(accountList.getList()));
		chosenAccount.getSelectionModel().selectFirst();
		transactionTypeInput.getItems().addAll(typeList.getList());
		transactionTypeInput.getSelectionModel().selectFirst();
	}

	private ArrayList<String> getAccountsByName(ArrayList<Account> accList) {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<>();
		for (Account acc : accList) {
			list.add(acc.getBankName());
		}

		return list;
	}

	private String getAccIDByName(String accName) {
		String accID = "";
		for (Account acc : accountList.getList()) {
			if (accName.equals(acc.getBankName()))
				return acc.getId();
		}
		return accID;

	}
	
	private int getTransactionTypeIdByName(String typeName) {
	    return typeList.getList().indexOf(typeName) + 1;
	}

	public void newTransaction(@SuppressWarnings("exports") ActionEvent event) {

		errMsg.setText("");
		try {
			//check accountName
			String accountName = chosenAccount.getValue();
			if (accountName == null) {
				errMsg.setText("Please Fill out Transaction Type fields");
				return;
			}
			
			// check transaction type
			String transactionType = transactionTypeInput.getValue();
			if (transactionType == null) {
				errMsg.setText("Please Fill out Transaction Type fields");
				return;
			}

			// transactionDateInput
			LocalDate transactionDate = transactionDateInput.getValue();
			// check for existing description
			String description = descriptionInput.getText();
			if (description.equals("")) {
				errMsg.setText("Please Fill out Transaction Description fields");
				return;
			}

			// check for existing transaction amount
			//check which box amount we are using
			System.out.println("Entering transaction box phase");
			String transactionAmount = this.transactionAmount.getText();
			String depositAmount = this.depositAmount.getText();
			double transasctionAmountDouble = 0;
			double depositAmountDouble = 0;
			
			//checks if both fields are filled
			if (transactionAmount.length() == 0 && depositAmount.length() == 0) {
				//if both fields are filled return an error
				System.out.println("Transaction: " + transactionAmount + " deposit: " + depositAmount);
				errMsg.setText("Please fill in only one field Transaction Amount or Deposit Amount");
				return;
			}
			else if (transactionAmount.length() > 0 && depositAmount.length() > 0) {
				errMsg.setText("Choose only deposit or transaction");
				return;
			}
			else if (transactionAmount.length() > 0 && depositAmount.length() == 0) {
				//if transaction field is filled, set the transaction double to the price and the deposit field to 0
				transasctionAmountDouble = Double.parseDouble(transactionAmount);
				depositAmountDouble = 0;
			}
			else {
				//if deposit field was filled, set the deposit field to price and the other to 0
				depositAmountDouble = Double.parseDouble(depositAmount);
				transasctionAmountDouble = 0;
				
			}
			//double transasctionAmountDouble = Double.parseDouble(transactionAmount.getText());
			//double depositAmountDouble = Double.parseDouble(depositAmount.getText());
			//checks if values are negative
			if (transasctionAmountDouble < 0 || depositAmountDouble < 0) {
				errMsg.setText("Invalid input for Transaction Amount or Deposit Amount field");
				return;
			}
			else {
				// TODO: if transaction type is depositing money into an account, use deposit
				String accID = getAccIDByName(accountName);
				int transTypeID = getTransactionTypeIdByName(transactionType);
				int lastIndex = transactionLst.getList().size() - 1;
		        int trueIndex = transactionLst.getList().get(lastIndex).getID() + 1;
				transactionLst.add(new Transaction(trueIndex, transTypeID,description, transactionDate, transasctionAmountDouble,
						depositAmountDouble, accID));
				errMsg.setText("Transaction is saved successful");
				switchToView(event);
			}
			
			
			
		}catch(NumberFormatException ex) {
			errMsg.setText("Please enter a number in the transaction amount or deposit amount field");
			//ex.printStackTrace();
		}
		catch (Exception e) {
			//e.printStackTrace();
		}

	}

	public void switchToMain(ActionEvent event) throws IOException {
		transactionLst.reload();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Main.fxml"));
		AnchorPane root = loader.load();


		stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	public void switchToView(ActionEvent event) throws IOException {
		transactionLst.reload();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Transaction.fxml"));
		AnchorPane root = loader.load();


		stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
}
