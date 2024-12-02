package sjsu.edu.application.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sjsu.edu.application.AccountList;
import sjsu.edu.application.Transaction;
import sjsu.edu.application.TransactionList;
import sjsu.edu.application.TransactionTypeList;

public class AccountReportController {
	private Stage stage;
	private Scene scene;
	@FXML
	private ChoiceBox<String> selectAccount;
	@FXML
	private ChoiceBox<String> selectType;
	@FXML
	private Label AccName;
	@FXML
	private TableView<Transaction> transactionTable;
	@FXML
	private TableColumn<Transaction, String> descriptionColumn;
	@FXML
	private TableColumn<Transaction, String> dateColumn;
	
	private AccountList accList = AccountList.getInstance();
	private TransactionTypeList typeList = TransactionTypeList.getInstance();
	private TransactionList transactions = TransactionList.getInstance();
	private ArrayList<Transaction> accountTransactions = new ArrayList<>();
	private String name;
	
	public void initialize() {
		selectAccount.getItems().add("--Select Account--");
		selectAccount.getItems().addAll(accList.getNamesOfAccounts());
		selectAccount.getSelectionModel().selectFirst();
		selectAccount.setOnAction(this::getTransactions);
		selectType.getItems().add("--Select Type--");
		selectType.getItems().addAll(typeList.getList());
		selectType.getSelectionModel().selectFirst();
		selectType.setOnAction(this:: filterType);
	}
	public void getTransactions(ActionEvent event) {
		name = selectAccount.getValue();
		AccName.setText(name);
		loadList(name);
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedDate()));
		loadTable(accountTransactions);
	    filterType(event);
	}
	private void loadTable(ArrayList<Transaction> list) {
		ObservableList<Transaction> transactionList = FXCollections.observableArrayList(list);
	    transactionTable.setItems(transactionList);
	}
	private void loadList(String name) {
		accountTransactions.clear();
		String id = accList.getAccountByName(name).getId();
		for (Transaction t: transactions.getList()) {
			if (t.getAccID().equals(id)) {
				accountTransactions.add(t);
			}
		}
		
		
	}
	public void filterType(ActionEvent event) {
		String type = selectType.getValue();
		int id = typeList.getTransactionTypeIdByName(type);
		if (id == 0) {
			loadList(name);
			loadTable(accountTransactions);
			return;
		}
		
		ArrayList <Transaction> filteredList = new ArrayList<>();
		for (Transaction t: accountTransactions) {
			if (t.getType() == id) {
				filteredList.add(t);
			}
		}
		loadTable(filteredList);
		
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
	
	public void gotoTransactionDetails(MouseEvent event) throws IOException{
		Transaction selectedT = transactionTable.getSelectionModel().getSelectedItem();
		if (selectedT != null) {
			TransactionDetailsController.selectedView = selectedT;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TransactionDetails.fxml"));
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
