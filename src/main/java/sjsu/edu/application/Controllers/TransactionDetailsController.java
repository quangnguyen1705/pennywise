package sjsu.edu.application.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sjsu.edu.application.Account;
import sjsu.edu.application.AccountList;
import sjsu.edu.application.List;
import sjsu.edu.application.Transaction;
import sjsu.edu.application.TransactionTypeList;

public class TransactionDetailsController {
	@FXML
	private Text descLabel;
	@FXML
	private Text accLabel;
	@FXML
	private Text typeLabel;
	@FXML
	private Text dateLabel;
	@FXML
	private Text transactionLabel;
	@FXML
	private Text depositLabel;
	private Stage stage;
	private Scene scene;
	public static Transaction selectedView;
	private List<Account> accList = new List(new AccountList());
	private List<String> typeList = new List(new TransactionTypeList());
	
	public void initialize() {
		descLabel.setText("Transaction: " + selectedView.getDescription());
		String name = getAccountById(selectedView.getAccID()).getBankName();
		accLabel.setText(name);
		String type = getNameByID(selectedView.getType());
		typeLabel.setText(type);
		dateLabel.setText(selectedView.getFormattedDate());
		transactionLabel.setText(String.valueOf(selectedView.getPaymentAmount()));
		depositLabel.setText(String.valueOf(selectedView.getDepositAmount()));
	}
	
	private Account getAccountById(String accountId) {
        for (Account account : accList.getList()) {
            if (account.getId().equals(accountId)) {
                return account;
            }
        }
        return null;
    }
    
	private String getNameByID(int index) {
		return typeList.getList().get(index - 1);
	}
	
	
	public void switchToAccReports(ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AccountReport.fxml"));
		AnchorPane root = loader.load();


		stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
