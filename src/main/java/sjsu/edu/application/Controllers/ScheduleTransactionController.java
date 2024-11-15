package sjsu.edu.application.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sjsu.edu.application.Models.ScheduleTransaction;

public class ScheduleTransactionController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TableView<ScheduleTransaction> TransactionView;
	@FXML
	private TableColumn<ScheduleTransaction, String> nameColumn;
	@FXML
	private TableColumn<ScheduleTransaction, String> accColumn;
	@FXML
	private TableColumn<ScheduleTransaction, String> typeColumn;
	@FXML
	private TableColumn<ScheduleTransaction, String> paymentAmountColumn;
	@FXML
	private TableColumn<ScheduleTransaction, Double> dateColumn;
	@FXML
	private TableColumn<ScheduleTransaction, Double> amountColumn;
	
	public void switchToMain2(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
		stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
}
