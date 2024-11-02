package sjsu.edu.pennywise;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sjsu.edu.pennywise.Models.DbConnection;

public class TransactionTypeList {
	private ArrayList<String> list = new ArrayList<>();
	
	public TransactionTypeList() {
		loadTransactionTypeDb();
	}
	public void loadTransactionTypeDb() {
		//TODO
		list.clear();
		String sql = "SELECT type_name FROM transaction_types";
		
		try (Connection conn = DbConnection.getConnection();
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(sql)) {

	            while (rs.next()) {
	                String type = rs.getString("type_name");

	                list.add(type);
	            }
	            System.out.println("Accounts loaded successfully.");
	            conn.close();
	        } catch (SQLException e) {
	            System.out.println("Error in loading accounts: " + e.getMessage());
	        }
		
	}
	
	public ArrayList<String> getList(){
		return new ArrayList<>(list);
	}
	

}
