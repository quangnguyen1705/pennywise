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
	private static TransactionList tList = new TransactionList();
	
	public static TransactionList getInstance() {
		return tList;
	}

	public TransactionList() {
		//load the data from the transaction list
		loadTransactionDb();
	}
	public void reload() {
		loadTransactionDb();
	}
	
	
	public void addTransaction(int typeId,String description, LocalDate date, double paymentAmount, double depositAmount, String accID) {
		int lastIndex = list.size() - 1;
        int trueIndex = list.get(lastIndex).getID() + 1;
		Transaction newTransaction = new Transaction(trueIndex, typeId, description, date, paymentAmount,depositAmount, accID); // do this so that separate objects arent created

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
        String sql = "SELECT st.id, account_id, transaction_description, transaction_type_id,transaction_date, payment_amount,deposit_amount "
        		     + " FROM transactions st"
        		     + " ORDER BY transaction_date DESC";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
            	int id = rs.getInt("id");
                String accID = rs.getString("account_id");
                String description = rs.getString("transaction_description");
                int type = rs.getInt("transaction_type_id");
                LocalDate date = rs.getDate("transaction_date").toLocalDate();
                double paymentAmount = rs.getDouble("payment_amount");
                double depositAmount = rs.getDouble("deposit_amount");

                list.add(new Transaction(id, type, description, date, paymentAmount,depositAmount, accID));
            }
            System.out.println("Accounts loaded successfully.");
            conn.close();
        } catch (SQLException error) {
            System.out.println("Error in loading accounts: " + error.getMessage());
        }
    }
	
	public void updateTransaction(Transaction transaction, int id) {
	    String sql = "UPDATE transactions SET account_id = ?, transaction_description = ?, transaction_type_id = ?, transaction_date = ?, payment_amount = ?, deposit_amount = ? "
	               + "WHERE id = ?";
	    try (Connection conn = DbConnection.getConnection();
	        PreparedStatement statement = conn.prepareStatement(sql)) {
	        statement.setString(1, transaction.getAccID());  
	        statement.setString(2, transaction.getDescription());
	        statement.setInt(3, transaction.getType());
	        statement.setDate(4, Date.valueOf(transaction.getDate()));
	        statement.setDouble(5, transaction.getPaymentAmount());
	        statement.setDouble(6, transaction.getDepositAmount());
	        statement.setInt(7, id);  
	        

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
