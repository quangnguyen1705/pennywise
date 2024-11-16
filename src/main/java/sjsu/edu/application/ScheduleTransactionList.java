package sjsu.edu.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sjsu.edu.application.Models.DbConnection;

public class ScheduleTransactionList {
	private ArrayList<ScheduleTransaction> list = new ArrayList<>();
	
	public ScheduleTransactionList() {
		loadScheduledTransactionDb();
	}
	
    public void addScheduledTransaction(String schedName, String accID, int type, String frequency, int date, double paymentAmount) {
        
    	ScheduleTransaction newScheduledTransaction = new ScheduleTransaction(schedName, accID, type, frequency, date, paymentAmount);
        
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
        String sql = "SELECT schedule_name, account_id, transaction_type_id, frequency, due_date, payment_amount "
        		     + " FROM scheduled_transactions ORDER BY due_date ASC";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String schedName = rs.getString("schedule_name");
                String accID = rs.getString("account_id");
                int type = rs.getInt("transaction_type_id");
                String frequency = rs.getString("frequency");
                int dueDate = rs.getInt("due_date");
                double paymentAmount = rs.getDouble("payment_amount");

                list.add(new ScheduleTransaction(schedName, accID, type, frequency, dueDate, paymentAmount));
            }
            System.out.println("Scheduled transactions loaded successfully.");
            conn.close();
        } catch (SQLException error) {
            System.out.println("Error in loading scheduled transactions: " + error.getMessage());
        }
    }

    public ArrayList<ScheduleTransaction> getList() {
        return new ArrayList<>(list);
    }

}
