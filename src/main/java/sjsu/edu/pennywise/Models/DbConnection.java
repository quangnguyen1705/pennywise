package sjsu.edu.pennywise.Models; // check this across other files. 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

/* UPDATE: testing db connection removed for simplicity
 * Use mvn compile exec:java -Dexec.mainClass="src.main.java.sjsu.edu.pennywise.Models.DbConnection" to test the dbconnection */ 

public class DbConnection {
    private static final String DB_PATH = "jdbc:sqlite:lib/pennywise.db";
    

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_PATH);
        } catch (SQLException error) {
            System.out.println("Connection Error: " + error.getMessage());
            return null;
        }
    }
    
    


    public static void initializeDatabase() {
        String createTable = "CREATE TABLE IF NOT EXISTS accounts (" +
				                "id TEXT PRIMARY KEY, " +              
				                "bank_name TEXT NOT NULL, " +
				                "open_balance DOUBLE NOT NULL, " +
				                "open_date DATE NOT NULL);";
        
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement()) {
        	statement.execute(createTable);
            System.out.println("Accounts table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
        
        createTable = "CREATE TABLE IF NOT EXISTS transaction_types (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +              
                "type_name TEXT UNIQUE NOT NULL); ";

		try (Connection conn = getConnection();
			Statement statement = conn.createStatement()) {
			statement.execute(createTable);
			System.out.println("Accounts table created or already exists.");
		} catch (SQLException e) {
			System.out.println("Error initializing database: " + e.getMessage());
		}
    }
    
    //Make sure dates are in a standardized format
    public static void updateDateFormat() {
        try (Connection c = getConnection();
             Statement stmt = c.createStatement()) {
            
            String sql = "UPDATE accounts SET open_date = DATE(open_date / 1000, 'unixepoch') " +
                         "WHERE typeof(open_date) = 'integer'";
            stmt.executeUpdate(sql);
            System.out.println("Date formated updated.");
        } catch (SQLException error) {
            System.out.println("Error updating format for date: " + error.getMessage());
        }
    }
    
    public static void main(String[] args) {
        initializeDatabase();
        
        // tests below if needed
    }
}
