package sjsu.edu.pennywise;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
	private String type;
	private String accID;
	private String description;
	private LocalDate date;
	private double depositAmount;
	private double paymentAmount;

	public Transaction(String type, String description, LocalDate date, double paymentAmount, double depositAmount,String accID) {
		this.type = type;
		this.accID = accID;
		this.description = description;
		this.date = date;
		this.setPaymentAmount(paymentAmount);
		this.depositAmount = depositAmount;
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

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}


	public String getFormattedDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yy");
		return date.format(formatter);
	}

	public double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}

}
