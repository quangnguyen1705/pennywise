package sjsu.edu.pennywise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sjsu.edu.pennywise.Models.DbConnection;

public class TransactionTypeList {
	private ArrayList<String> list = new ArrayList<>();
	
	
	public String addType(String type) {
		list.add(type);
		return saveTypeDb(type);
	}
	
	public String saveTypeDb(String type) {
		String sql = "INSERT INTO transaction_types (type_name) VALUES (?)";
		try (Connection conn = DbConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

			statement.setString(1, type);
			statement.executeUpdate();
			System.out.println("Account saved to database successfully.");
			conn.close();
		} catch (SQLException error) {
			System.out.println("Error saving account to database: " + error.getMessage());
			return "Error";
		}
		return "Success";
	}
	
	
	public void loadTransactionTypeDb() {
		//TODO
		list.clear();
		System.out.println("list cleared");
		String sql = "SELECT type_name FROM transaction_types";
		
		System.out.println("Attempting connection");

		try (Connection conn = DbConnection.getConnection();
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(sql)) {
			System.out.println("Connection Success");

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
	
	public TransactionTypeList() {
		loadTransactionTypeDb();
		
	}
	

}
