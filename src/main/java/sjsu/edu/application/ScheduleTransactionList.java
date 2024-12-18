package sjsu.edu.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sjsu.edu.application.Models.DbConnection;

public class ScheduleTransactionList implements TransactionListInterface<ScheduleTransaction>{
	private ArrayList<ScheduleTransaction> list = new ArrayList<>();
	
	public ScheduleTransactionList() {
		loadScheduledTransactionDb();
	}
	public void reload() {
		loadScheduledTransactionDb();
	}
	
    public void add(ScheduleTransaction newScheduledTransaction) {
    	list.add(newScheduledTransaction);
        saveScheduledTransaction(newScheduledTransaction);
    }
    
    private void saveScheduledTransaction(ScheduleTransaction scheduledTransaction) {
        
    	String sql = "INSERT INTO scheduled_transactions (schedule_name, account_id, transaction_type_id, frequency, due_date, payment_amount) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, scheduledTransaction.getSchedName());
            statement.setString(2, scheduledTransaction.getAccID());
            statement.setInt(3, scheduledTransaction.getType());
            statement.setString(4, scheduledTransaction.getFrequency());
            statement.setDouble(6, scheduledTransaction.getPaymentAmount());
            statement.setInt(5, scheduledTransaction.getDate());
            
            statement.executeUpdate();
            System.out.println("Scheduled transaction saved to database successfully.");
            conn.close();
        } catch (SQLException error) {
            System.out.println("Error saving scheduled transaction to database: " + error.getMessage());
        }
    }
    
    private void loadScheduledTransactionDb() {
        list.clear();
        String sql = "SELECT st.id,schedule_name, account_id, transaction_type_id, frequency, due_date, payment_amount ,t.type_name AS transaction_type"
        		     + " FROM scheduled_transactions st"
        		     + " INNER JOIN transaction_types t ON t.id = st.transaction_type_id "
        		     + " ORDER BY due_date ASC";

        
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
            	int id = rs.getInt("id");
                String schedName = rs.getString("schedule_name");
                String accID = rs.getString("account_id");
                int type = rs.getInt("transaction_type_id");
                String frequency = rs.getString("frequency");
                int dueDate = rs.getInt("due_date");
                double paymentAmount = rs.getDouble("payment_amount");

                list.add(new ScheduleTransaction(id, schedName, accID, type, frequency, dueDate, paymentAmount));
            }
            System.out.println("Scheduled transactions loaded successfully.");
            conn.close();
        } catch (SQLException error) {
            System.out.println("Error in loading scheduled transactions: " + error.getMessage());
        }
    }

    public void update(ScheduleTransaction scheduledTransaction) {
	    String sql = "UPDATE scheduled_transactions SET account_id = ?, schedule_name = ?, transaction_type_id = ?, frequency = ?, due_date = ?, payment_amount = ? "
	               + "WHERE id =?";
	    try (Connection conn = DbConnection.getConnection();
	        PreparedStatement statement = conn.prepareStatement(sql)) {
	        statement.setString(1, scheduledTransaction.getAccID());  
	        statement.setString(2, scheduledTransaction.getSchedName());
	        statement.setInt(3, scheduledTransaction.getType());
	        statement.setString(4, scheduledTransaction.getFrequency());
	        statement.setInt(5, scheduledTransaction.getDate());
	        statement.setDouble(6, scheduledTransaction.getPaymentAmount());
	        statement.setInt(7, scheduledTransaction.getSchedID());  

	        int changes = statement.executeUpdate();
	        if (changes > 0) {
	            System.out.println("Scheduled Transaction updated successfully.");
	        } else {
	            System.out.println("Scheduled Transaction update failed.");
	        }
	    } catch (SQLException error) {
	        System.out.println("Error updating transaction: " + error.getMessage());
	    }
	}
    public ArrayList<ScheduleTransaction> getList() {
        return new ArrayList<>(list);
    }

}
