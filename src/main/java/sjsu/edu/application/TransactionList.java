package sjsu.edu.application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import sjsu.edu.application.Models.DbConnection;

public class TransactionList {
	private ArrayList<Transaction> list = new ArrayList<>();

	public TransactionList() {
		//load the data from the transaction list
		loadTransactionDb();
	}
	
	
	public void addTransaction(String type, String description, LocalDate date, double paymentAmount, double depositAccmount, String accID) {
		
		Transaction newTransaction = new Transaction(type, description, date, paymentAmount,depositAccmount, accID); // do this so that separate objects arent created

		list.add(newTransaction);
		saveTransaction(newTransaction);
	
	}
	
	private void saveTransaction(Transaction transaction) {
		
		String sql = "INSERT INTO transactions (account_id, transaction_description, transaction_type, transaction_date, payment_amount,deposit_amount) VALUES (?, ?, ?, ?, ?,?)";
		
		try (Connection conn = DbConnection.getConnection();
	             PreparedStatement statement = conn.prepareStatement(sql)) {
	             
	            statement.setString(1, transaction.getAccID());
	            statement.setString(2, transaction.getDescription());
	            statement.setString(3, transaction.getType());
	            statement.setDouble(5, transaction.getPaymentAmount());
	            statement.setDouble(6, transaction.getDepositAmount());

	            //format for SQL
	            Date formattedDate = Date.valueOf(transaction.getDate());
	            statement.setDate(4, formattedDate);
	            statement.executeUpdate();
	            System.out.println("Transaction saved to database successfully.");
	            conn.close();
	        } catch (SQLException error) {
	            System.out.println("Error saving account to database: " + error.getMessage());
	        }
		
	}
	
	private void loadTransactionDb() {
        list.clear();
        String sql = "SELECT account_id, transaction_description, transaction_type, transaction_date, payment_amount,deposit_amount FROM transactions ORDER BY transaction_date DESC";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String accID = rs.getString("account_id");
                String description = rs.getString("transaction_description");
                String type = rs.getString("transaction_type");
                LocalDate date = rs.getDate("transaction_date").toLocalDate();
                double paymentAmount = rs.getDouble("payment_amount");
                double depositAmount = rs.getDouble("deposit_amount");

                list.add(new Transaction(type, description, date, paymentAmount,depositAmount, accID));
            }
            System.out.println("Accounts loaded successfully.");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in loading accounts: " + e.getMessage());
        }
    }
	
	public ArrayList<Transaction> getList(){
		return new ArrayList<>(list);
	}
	

}
