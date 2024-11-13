package sjsu.edu.application.Controllers;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sjsu.edu.application.Account;
import sjsu.edu.application.AccountList;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;


public class CreateAccController {

	private Stage stage;
	private Scene scene;

	@FXML
	private TextField accountNameInput;

	@FXML
	private Label errMSG;

	@FXML
	private TextField balanceInput;

	@FXML
	private DatePicker openDateInput;

	private AccountList accountList = new AccountList();

	public void initialize() {
		// Set the current date as the default value
		openDateInput.setValue(LocalDate.now());

		// Prevent users from typing in the DatePicker
		openDateInput.getEditor().setDisable(true);
		openDateInput.getEditor().setOpacity(1);
	}

	// create new account
	@FXML
	public void createAccount(ActionEvent event) {

		errMSG.setText("");
		try {

			String bankName = accountNameInput.getText();
			double balance = Double.parseDouble(balanceInput.getText());
			LocalDate openDate = openDateInput.getValue();

			if (bankName.isEmpty() || openDate == null) {
				errMSG.setText("Fill out required fields");
				return;
			}
			if (nameExists(bankName)) {
				errMSG.setText("Account already exists");
				return;
			}

			// add account to the arraylist
			accountList.addAccount(bankName, balance, openDate);
			System.out.println("Account created successfully!");

			// switch back to the main screen
			// TODO: after account creation, decide whether to auto-switc to main screen or
			// not
			switchToMain(event);

		} catch (NumberFormatException error) {
			errMSG.setText("Invalid amount entered for opening balance");
		} catch (IOException error) {
			System.out.println("Failed to load the main page: " + error.getMessage());
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

	private boolean nameExists(String name) {
		for (Account acc : accountList.getList()) {
			if (acc.getBankName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
