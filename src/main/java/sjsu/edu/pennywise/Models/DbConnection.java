package src.main.java.sjsu.edu.pennywise.Models; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbConnection {
    private static final String DB_PATH = "lib/pennywise.db";
    
    public static Connection connect() {
        String url = "jdbc:sqlite:" + DB_PATH;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite is successful.");
            return conn;
        } catch (SQLException e) {
            System.out.println("Connection Error: " + e.getMessage());
            return null;
        }
    }

    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS accounts (" +
                                "name TEXT PRIMARY KEY, " +
                                "open_balance DOUBLE NOT NULL, " +
                                "open_date TEXT NOT NULL);";
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Accounts table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
        // Test connection and table existence
        try (Connection conn = connect()) {
            if (conn != null) {
                var meta = conn.getMetaData();
                System.out.println("Driver version: " + meta.getDriverVersion());
                
                // Test if table exists
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='accounts'");
                if (rs.next()) {
                    System.out.println("accounts table exists");
                } else {
                    System.out.println("accounts table not found");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error testing connection: " + e.getMessage());
        }
    }
}
