package sjsu.edu.application.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sjsu.edu.application.Account;
import sjsu.edu.application.AccountList;
import sjsu.edu.application.ScheduleTransactionList;
import sjsu.edu.application.TransactionTypeList;

public class AddScheduleTransactionController {
	private Stage stage;
	private Scene scene;

	@FXML
	private Label errMsg;
	@FXML
	private ChoiceBox<String> chosenAccount;
	@FXML
	private ChoiceBox<String> transactionTypeInput;
	@FXML
	private ChoiceBox<String> frequency;
	@FXML
	private TextField amount;
	@FXML
	private TextField paymentName;
	@FXML
	private TextField dayValue;

	private AccountList accountList = new AccountList();
	private ArrayList<String> types = new ArrayList<>();
	private ScheduleTransactionList scheduleTransactionLst= new ScheduleTransactionList();
	private TransactionTypeList typeList = new TransactionTypeList();
	private ArrayList<String> frequencyLs = new ArrayList<>(Arrays.asList("Monthly"));

	public void initialize() {
		// Set the current date as the default value

		chosenAccount.getItems().addAll(getAccountsByName(accountList.getList()));
		chosenAccount.getSelectionModel().selectFirst();
		transactionTypeInput.getItems().addAll(typeList.getList());
		transactionTypeInput.getSelectionModel().selectFirst();
		
		frequency.getItems().addAll(frequencyLs);
		frequency.getSelectionModel().selectFirst();
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
	public void newTransaction(@SuppressWarnings("exports") ActionEvent event) {
		errMsg.setText("");
		
		try {
			//check accountName
			String accountName = chosenAccount.getValue();
			if (accountName == null) {
				errMsg.setText("Please Fill out Transaction Type fields");
				return;
			}
			
			// check 
			String billName = paymentName.getText();
			if (billName.equals("") ) {
				errMsg.setText("Please Fill out Schedule Transaction Name");
				return;
			}
			String paymentAmountStr = amount.getText();
			if (paymentAmountStr.equals("") ) {
				errMsg.setText("Please Fill out Payment Amount fields");
				return;
			}
			double paymentAmount = Double.parseDouble(paymentAmountStr);
			if (paymentAmount < 0) {
				errMsg.setText("Please enter a number in the Payment Amount field");
				return;
			}
			
			String dueDateStr = dayValue.getText();
			if (dueDateStr.equals("") ) {
				errMsg.setText("Please Fill out Due Date fields");
				return;
			}
			int dueDate = Integer.parseInt(dueDateStr);
			if (dueDate < 0) {
				errMsg.setText("Please enter a number in the Due Date field");
				return;
			}
			
			String transactionType = transactionTypeInput.getValue();
			if(transactionType == null) {
				errMsg.setText("Please Fill out Transaction Type fields");
				return;
			}
			
			String accID = getAccIDByName(accountName);
			int transTypeID = typeList.getTransactionTypeIdByName(transactionType);
			scheduleTransactionLst.addScheduledTransaction(billName, accID, transTypeID,"","Monthly", dueDate, paymentAmount);
			switchToMain(event);
			
		}catch(NumberFormatException ex) {
			errMsg.setText("Please enter a number in the Payment Amount or Due Date field");
			ex.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		

	}

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
