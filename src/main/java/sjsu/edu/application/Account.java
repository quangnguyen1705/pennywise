package sjsu.edu.application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Account {
	private String id; 
	private String bankName; 
	private double balance; 
	private LocalDate date; 
	
	public Account(String id, String bankName, double balance, LocalDate date) {
		this.id = id; 
		this.bankName = bankName; 
		this.balance = balance; 
		this.date = date; 
	}
	
	// getters
	public String getId() {
		return id;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public LocalDate getDate() {
		return date; 
	}
	
	
	// setters 
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public void setBalance(double balance) {
		this.balance = balance; 
	}
	
	public void setDate(LocalDate date) {
		this.date = date; 
	}
	
    // format date so that it displays to the user in the same way they inputted it
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yy");
        return date.format(formatter);
    }

}
