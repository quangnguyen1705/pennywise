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
	
	
	public void addTransaction(int typeId, String transactionName ,String description, LocalDate date, double paymentAmount, double depositAmount, String accID) {
		
		Transaction newTransaction = new Transaction(typeId, transactionName,description, date, paymentAmount,depositAmount, accID); // do this so that separate objects arent created

        /* if (type <= 0) {
            throw new IllegalArgumentException("Transaction type must be a positive integer.");
        }

        if (paymentAmount <= 0 && depositAmount <= 0) {
            throw new IllegalArgumentException("Either paymentAmount or depositAmount must be greater than 0.");
        } */

		
		list.add(newTransaction);
		saveTransaction(newTransaction);
	
	}
	
	private void saveTransaction(Transaction transaction) {
		
		String sql = "INSERT INTO transactions (account_id, transaction_description, transaction_type_id, transaction_date, payment_amount,deposit_amount) VALUES (?, ?, ?, ?, ?,?)";
		
		try (Connection conn = DbConnection.getConnection();
	             PreparedStatement statement = conn.prepareStatement(sql)) {
	             
	            statement.setString(1, transaction.getAccID());
	            statement.setString(2, transaction.getDescription());
	            statement.setInt(3, transaction.getType());
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
        String sql = "SELECT account_id, transaction_description, transaction_type_id,  tt.type_name AS transaction_type_name ,transaction_date, payment_amount,deposit_amount "
        		     + " FROM transactions t INNER JOIN transaction_types tt ON tt.id =t.transaction_type_id "
        		     + " ORDER BY transaction_date DESC";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String accID = rs.getString("account_id");
                String description = rs.getString("transaction_description");
                String transactionTypeName = rs.getString("transaction_type_name");
                int type = rs.getInt("transaction_type_id");
                LocalDate date = rs.getDate("transaction_date").toLocalDate();
                double paymentAmount = rs.getDouble("payment_amount");
                double depositAmount = rs.getDouble("deposit_amount");

                list.add(new Transaction(type, transactionTypeName ,description, date, paymentAmount,depositAmount, accID));
            }
            System.out.println("Accounts loaded successfully.");
            conn.close();
        } catch (SQLException error) {
            System.out.println("Error in loading accounts: " + error.getMessage());
        }
    }
	
	public void updateTransaction(Transaction transaction) {
	    String sql = "UPDATE transactions SET account_id = ?, transaction_description = ?, transaction_type_id = ?, transaction_date = ?, payment_amount = ?, deposit_amount = ? "
	               + "WHERE account_id = ? AND transaction_date = ?";
	    try (Connection conn = DbConnection.getConnection();
	        PreparedStatement statement = conn.prepareStatement(sql)) {
	        statement.setString(1, transaction.getAccID());  
	        statement.setString(2, transaction.getDescription());
	        statement.setInt(3, transaction.getType());
	        statement.setDate(4, Date.valueOf(transaction.getDate()));
	        statement.setDouble(5, transaction.getPaymentAmount());
	        statement.setDouble(6, transaction.getDepositAmount());
	        statement.setString(7, transaction.getAccID());  
	        statement.setDate(8, Date.valueOf(transaction.getDate()));

	        int changes = statement.executeUpdate();
	        if (changes > 0) {
	            System.out.println("Transaction updated successfully.");
	        } else {
	            System.out.println("Transaction update failed.");
	        }
	    } catch (SQLException error) {
	        System.out.println("Error updating transaction: " + error.getMessage());
	    }
	}

	
	
	public ArrayList<Transaction> getList(){
		return new ArrayList<>(list);
	}
	

}
