package sjsu.edu.application;

import java.util.ArrayList;

//random id generation
import java.util.UUID;

import sjsu.edu.application.Account;
import sjsu.edu.application.Models.DbConnection;

import java.sql.*;
import java.time.LocalDate;

public class AccountList {
	private ArrayList<Account> accountList = new ArrayList<>();
	private static AccountList accList = new AccountList();
	
	public static AccountList getInstance() {
		return accList;
	}

	public void addAccount(String bankName, double balance, LocalDate date) {
		UUID rawID = UUID.randomUUID();
		String id = rawID.toString();

		Account newAcc = new Account(id, bankName, balance, date); // do this so that separate objects arent created

		accountList.add(newAcc);
		saveAccountDb(newAcc);
	}

	public void saveAccountDb(Account acc) {
		// Ensure the account has an ID
		if (acc.getId() == null || acc.getId().isEmpty()) {
			acc.setId(UUID.randomUUID().toString()); // unique ID
		}

		String sql = "INSERT INTO accounts (id, bank_name, open_balance, open_date) VALUES (?, ?, ?, ?)";

		try (Connection conn = DbConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

			statement.setString(1, acc.getId());
			statement.setString(2, acc.getBankName());
			statement.setDouble(3, acc.getBalance());

			// format for SQL
			Date formattedDate = Date.valueOf(acc.getDate());
			statement.setDate(4, formattedDate);

			statement.executeUpdate();
			System.out.println("Account saved to database successfully.");
			conn.close();
		} catch (SQLException error) {
			System.out.println("Error saving account to database: " + error.getMessage());
		}
	}

	public void loadAccountsDb() {
		accountList.clear();
		String sql = "SELECT id, bank_name, open_balance, open_date FROM accounts ORDER BY open_date DESC";

		try (Connection conn = DbConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				String id = rs.getString("id");
				String bankName = rs.getString("bank_name");
				double balance = rs.getDouble("open_balance");
				LocalDate date = rs.getDate("open_date").toLocalDate();

				accountList.add(new Account(id, bankName, balance, date));
			}
			System.out.println("Accounts loaded successfully.");
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error in loading accounts: " + e.getMessage());
		}
	}

	public AccountList() {
		loadAccountsDb();
	}

	public ArrayList<Account> getList() { // return accounts list
		return new ArrayList<>(accountList);
	}
	
   
    public Account getAccountById(String accountId) {
        for (Account account : accountList) {
            if (account.getId().equals(accountId)) {
                return account;
            }
        }
        return null;
    }

}
