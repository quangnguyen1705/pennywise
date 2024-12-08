package sjsu.edu.application.Controllers;

import java.io.IOException;
import java.util.ArrayList;

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
import sjsu.edu.application.List;
import sjsu.edu.application.ScheduleTransaction;
import sjsu.edu.application.ScheduleTransactionList;
import sjsu.edu.application.Transaction;
import sjsu.edu.application.TransactionTypeList;

public class EditScheduledTransactionController {

	private Stage stage;
	private Scene scene;

	@FXML
	private Label errMsg;
	@FXML
	private ChoiceBox<String> chosenAccount;
	@FXML
	private ChoiceBox<String> transactionTypeInput;
	@FXML
	private TextField dayValue;
	@FXML
	private TextField paymentName;
	@FXML
	private TextField amount;
	@FXML
	private ChoiceBox<String> frequency;

	private List<Account> accountList = new List(new AccountList());
	private List<String> typeList = new List(new TransactionTypeList());
	private List<ScheduleTransaction> scheduleList = new List(new ScheduleTransactionList());
	public static ScheduleTransaction selectedTransaction;

	public ScheduleTransaction getData() {
		return selectedTransaction;
	}

	public void initialize() {
		paymentName.setText(selectedTransaction.getSchedName());

		chosenAccount.getItems().addAll(getAccountsByName(accountList.getList()));
		chosenAccount.getSelectionModel().select(getNameByID(selectedTransaction.getAccID()));
		transactionTypeInput.getItems().addAll(typeList.getList());
		transactionTypeInput.getSelectionModel().select(getTypeName(selectedTransaction.getType()));
		frequency.getItems().add("Monthly");
		frequency.getSelectionModel().selectFirst();
		dayValue.setText(String.valueOf(selectedTransaction.getDate()));
		amount.setText(String.valueOf(selectedTransaction.getPaymentAmount()));
	}
	
	private String getNameByID(String s){
		for(Account acc : accountList.getList()) {
			if (acc.getId().equals(s)) {
				return acc.getBankName();
			}
		}
		return "";
	}
	
	private String getTypeName(int i) {
		try {
			return typeList.getList().get(i - 1);
		}catch(Exception e) {
			return "Unknown Account";
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

	// TODO: create save changes button in frontend relating to this
	// user edits to the scheduled transaction are updated and refreshed
	@FXML
	public void onSaveChanges(ActionEvent event) {

		// check accountName
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
		// check name
		String name = paymentName.getText();
		if (name.length() == 0) {
			errMsg.setText("Please enter payment name");
			return;
		}
		// check frequency
		String freq = frequency.getValue();
		if (freq == null) {
			errMsg.setText("Please choose a frequency");
			return;
		}
		// get due day date
		String date = dayValue.getText();
		if (date.length() == 0) {
			errMsg.setText("Enter valid day");
			return;
		}

		if (!date.matches("\\d+")) {
			errMsg.setText("Enter due date as number only");
			return;
		}

		int transactionDate = Integer.parseInt(date);
		if (transactionDate <= 0 || transactionDate > 31) {
			errMsg.setText("Enter valid day");
			return;
		}

		// check for existing transaction amount
		// check which box amount we are using
		String transactionAmount = amount.getText();
		double transasctionAmountDouble = Double.parseDouble(transactionAmount);

		// double transasctionAmountDouble =
		// Double.parseDouble(transactionAmount.getText());
		// checks if values are negative
		if (transasctionAmountDouble < 0) {
			errMsg.setText("Invalid input for Transaction Amount");
			return;
		} else {
			// TODO: if transaction type is depositing money into an account, use deposit
			String accID = getAccIDByName(accountName);
			int transTypeID = getTransactionTypeIdByName(transactionType);
			// public ScheduleTransaction(String schedName, String accID, int type, String
			// frequency, int date, double paymentAmount)
			
			selectedTransaction.setAccID(accID);
			selectedTransaction.setSchedName(name);
			selectedTransaction.setFrequency(freq);
			selectedTransaction.setDueDate(transactionDate);
			selectedTransaction.setPaymentAmount(transasctionAmountDouble);
			selectedTransaction.setType(transTypeID);
			
			scheduleList.update(selectedTransaction);
			errMsg.setText("Transaction is saved successful");
			try {
				switchToTransactionSearch(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}

	}


	// going back to main page
	// TODO: Back to Main button like other pages should be implemented.
	public void switchToTransactionSearch(ActionEvent event) throws IOException {
		scheduleList.reload();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SearchedScheduled.fxml"));
		AnchorPane root = loader.load();
		stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
}
