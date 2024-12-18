package sjsu.edu.application;



public class ScheduleTransaction {
	private String schedName; 
	private String accID; 
	private int type;
	private String frequency; 
	private int dueDate; 
	private double paymentAmount;
	private int schedID;
	
    public ScheduleTransaction(int id, String schedName, String accID, int type, String frequency, int date, double paymentAmount) {
        this.schedName = schedName;
        this.accID = accID;
        this.type = type;
        this.frequency = frequency;
        setDueDate(date); 
        setPaymentAmount(paymentAmount);
        schedID = id;
    }
    
    public String getSchedName() {
        return schedName;
    }
    public int getSchedID() {
    	return schedID;
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

    public int getDate() {
        return dueDate;
    }

    public void setDueDate(int dueDate) {
       this.dueDate = dueDate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
       this.paymentAmount = paymentAmount;
    }


	

}
