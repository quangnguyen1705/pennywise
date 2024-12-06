package sjsu.edu.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sjsu.edu.application.Models.DbConnection;

public class TransactionTypeList implements ListInterface<String>{
	private ArrayList<String> list = new ArrayList<>();
	private static TransactionTypeList typeList = new TransactionTypeList();
	
	public static TransactionTypeList getInstance() {
		return typeList;
	}
	public void reload() {
		loadTransactionTypeDb();
	}
	
	
	public void add(String type) {
		list.add(type);
		saveTypeDb(type);
	}
	public String getNameByID(int index) {
		return list.get(index - 1);
	}
	
	public String saveTypeDb(String type) {
		String sql = "INSERT INTO transaction_types (type_name) VALUES (?)";
		try (Connection conn = DbConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

			statement.setString(1, type);
			statement.executeUpdate();
			System.out.println("Account saved to database successfully.");
			conn.close();
		} catch (SQLException error) {
	        if (error.getMessage().contains("UNIQUE")) {
	            System.out.println("Error: Duplicate transaction type name found.");
	            return "Duplicate Type Found";
	        } else {
	            System.out.println("Error saving transaction type to database: " + error.getMessage());
	            return "Error";
	        }
	    }
	    return "Success";
	}
	
	public int getTransactionTypeIdByName(String typeName) {
	    return list.indexOf(typeName) + 1;
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
