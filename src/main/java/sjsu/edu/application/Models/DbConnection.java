package sjsu.edu.application.Models; // check this across other files. 

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
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement()) {

            // Create the accounts table
            String createAccountsTable = "CREATE TABLE IF NOT EXISTS accounts (" +
                                         "id TEXT PRIMARY KEY, " +
                                         "bank_name TEXT NOT NULL, " +
                                         "open_balance DOUBLE NOT NULL, " +
                                         "open_date DATE NOT NULL);";
            statement.execute(createAccountsTable);
            System.out.println("Accounts table created or already exists.");

            // Create the transaction_types table
            String createTransactionTypesTable = "CREATE TABLE IF NOT EXISTS transaction_types (" +
                                                 "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                 "type_name TEXT UNIQUE NOT NULL);";
            statement.execute(createTransactionTypesTable);
            System.out.println("Transaction Types table created or already exists.");

            // Create the transactions table
            String createTransactionsTable = "CREATE TABLE IF NOT EXISTS transactions (" +
                                             "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                             "account_id TEXT NOT NULL, " +
                                             "transaction_type TEXT NOT NULL, " +
                                             "transaction_date DATE NOT NULL, " +
                                             "transaction_description TEXT NOT NULL, " +
                                             "payment_amount DOUBLE NOT NULL DEFAULT 0, " +
                                             "deposit_amount DOUBLE NOT NULL DEFAULT 0, " +
                                             "CHECK ((payment_amount = 0 AND deposit_amount > 0) OR " +  // remove if needed
                                             "       (payment_amount > 0 AND deposit_amount = 0)), " +
                                             "FOREIGN KEY (account_id) REFERENCES accounts(id), " +
                                             "FOREIGN KEY (transaction_type) REFERENCES transaction_types(type_name));";
            statement.execute(createTransactionsTable);
            System.out.println("Transactions table created or already exists.");

        } catch (SQLException error) {
            System.out.println("Error initializing database: " + error.getMessage());
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
