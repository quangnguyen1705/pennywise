package sjsu.edu.application.Models; // check this across other files. 

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    	String schemaPath = "src/main/resources/config/schema.sql";
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement()) {
        	
        	BufferedReader reader = new BufferedReader(new FileReader(schemaPath));
        	
        	StringBuilder schemaBuilder = new StringBuilder();
        	String line;
        	while((line = reader.readLine()) != null) {
        		schemaBuilder.append(line).append("\n");
        	}
        	
        	String schemaSql = schemaBuilder.toString();
            statement.executeUpdate(schemaSql);

            System.out.println("Schema executed successfully!");

        } catch (SQLException error) {
            System.out.println("Error initializing database: " + error.getMessage());
        }
        catch (FileNotFoundException e) {
        	System.out.println("Schema not found"); 
        }
        catch (IOException e) {
        	System.out.println("schema not read properly");
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
