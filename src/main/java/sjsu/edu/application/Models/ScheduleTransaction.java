package sjsu.edu.application.Models;

public class ScheduleTransaction {
	
	private String scheduleName;
	private int accountID;
	private int transactionTypeID;
	private String frequency;
	private int dueDate;
	private double paymentAmount; 
	
	public ScheduleTransaction() {}
	
	public ScheduleTransaction(String scheduleName, int accountID, int transsactionTypeID, String frequency, int dueDate, double paymentAmount) {
		setScheduleName(scheduleName);
		setAccountID(accountID);
		setTransactionTypeID(transsactionTypeID);
		setFrequency(frequency);
		setDueDate(dueDate);
		setPaymentAmount(paymentAmount);
	}

	private double getPaymentAmount() {
		return paymentAmount;
	}
	private void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	private int getDueDate() {
		return dueDate;
	}
	private void setDueDate(int dueDate) {
		this.dueDate = dueDate;
	}
	private String getFrequency() {
		return frequency;
	}
	private void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	private int getTransactionTypeID() {
		return transactionTypeID;
	}
	private void setTransactionTypeID(int transactionTypeID) {
		this.transactionTypeID = transactionTypeID;
	}
	private int getAccountID() {
		return accountID;
	}
	private void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	private String getScheduleName() {
		return scheduleName;
	}
	private void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}


	
}
