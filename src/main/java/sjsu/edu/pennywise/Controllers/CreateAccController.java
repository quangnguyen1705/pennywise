package sjsu.edu.pennywise.Controllers;

import java.io.IOException;
import sjsu.edu.pennywise.AccountList;

import java.time.LocalDate; 



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.fxml.FXMLLoader;


public class CreateAccController {

	private Stage stage;
	private Scene scene;
	
    @FXML
    private TextField accountNameInput;

    @FXML
    private TextField balanceInput;

    @FXML
    private DatePicker openDateInput;

    private AccountList accountList = new AccountList();

    //create new account
    @FXML
    public void createAccount(ActionEvent event) {
        try {
            String bankName = accountNameInput.getText();
            double balance = Double.parseDouble(balanceInput.getText());
            LocalDate openDate = openDateInput.getValue();

            if (bankName.isEmpty() || openDate == null) {
                System.out.println("Not all fields were filled out!");
                return;
            }

            // add account to the arraylist
            accountList.addAccount(bankName, balance, openDate);
            System.out.println("Account created successfully!");

            // switch back to the main screen 
            // TODO: after account creation, decide whether to auto-switc to main screen or not
            switchToMain(event);

        } catch (NumberFormatException error) {
            System.out.println("Invalid amount entered for opening balance");
        } catch (IOException error) {
            System.out.println("Failed to load the main page: " + error.getMessage());
        }
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
}
