package sjsu.edu.application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter; 

public class ScheduleTransaction {
	private String schedName; 
	private String accID; 
	private int type;
	private String frequency; 
	private LocalDate date; 
	private double paymentAmount; 
	
    public ScheduleTransaction(String schedName, String accID, int type, String frequency, LocalDate date, double paymentAmount) {
        this.schedName = schedName;
        this.accID = accID;
        this.type = type;
        this.frequency = frequency;
        setDueDate(date); 
        setPaymentAmount(paymentAmount);
    }
    
    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

	public String getAccID() {
		return accID;
	}

	public void setAccID(String accID) {
		this.accID = accID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDueDate(LocalDate dueDate) {
        if (dueDate != null) {
            this.date = dueDate;
        } else {
            throw new IllegalArgumentException("Date cannot be null");
        }
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        if (paymentAmount > 0) {
            this.paymentAmount = paymentAmount;
        } else {
            throw new IllegalArgumentException("Payment amount cannot be less than 0");
        }
    }

	public String getFormattedDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yy");
		return date.format(formatter);
	}
	

}
