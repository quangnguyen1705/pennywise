package sjsu.edu.pennywise;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
	private String type;
	private String accID;
	private String description;
	private LocalDate date;
	private double amount;
	
	public Transaction(String type, String description, LocalDate date, double amount, String accID) {
		this.type = type;
		this.accID = accID;
		this.description = description;
		this.date = date;
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccID() {
		return accID;
	}

	public void setAccID(String accID) {
		this.accID = accID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yy");
        return date.format(formatter);
    }
	
	
	

}
