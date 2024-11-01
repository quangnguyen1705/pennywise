package sjsu.edu.pennywise;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import sjsu.edu.pennywise.Models.DbConnection;

public class TransactionList {
	private ArrayList<Transaction> list = new ArrayList<>();

	public TransactionList() {
		//load the data from the transaction list
	}
	
	
	public void addTransaction(String type, String description, LocalDate date, double amount, String accID) {
		
		Transaction newTransaction = new Transaction(type, description, date, amount, accID); // do this so that separate objects arent created

		list.add(newTransaction);
		saveTransaction(newTransaction);
	
	}
	
	private void saveTransaction(Transaction transaction) {
		
		String sql = "INSERT INTO transactions (accID, description, type, date, amount) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = DbConnection.getConnection();
	             PreparedStatement statement = conn.prepareStatement(sql)) {
	             
	            statement.setString(1, transaction.getAccID());
	            statement.setString(2, transaction.getDescription());
	            statement.setString(3, transaction.getType());
	            statement.setDouble(5, transaction.getAmount());

	            //format for SQL
	            Date formattedDate = Date.valueOf(transaction.getDate());
	            statement.setDate(4, formattedDate);
	            statement.executeUpdate();
	            System.out.println("Account saved to database successfully.");
	        } catch (SQLException error) {
	            System.out.println("Error saving account to database: " + error.getMessage());
	        }
		
	}
	
	private void loadAccountsDb() {
        list.clear();
        String sql = "SELECT id, bank_name, open_balance, open_date FROM accounts ORDER BY open_date DESC";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String accID = rs.getString("accID");
                String description = rs.getString("description");
                String type = rs.getString("type");
                LocalDate date = rs.getDate("date").toLocalDate();
                double amount = rs.getDouble("amount");

                list.add(new Transaction(type, description, date, amount, accID));
            }
            System.out.println("Accounts loaded successfully.");
        } catch (SQLException e) {
            System.out.println("Error in loading accounts: " + e.getMessage());
        }
    }
	

}
