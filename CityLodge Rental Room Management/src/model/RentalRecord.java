package model;

import model.util.DateTime;

public class RentalRecord {
	String recordId;
	DateTime rentDate;
	DateTime estimatedReturnDate;
	DateTime actualReturnDate;
	double rentalFee;
	double lateFee;

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public DateTime getRentDate() {
		return rentDate;
	}

	public void setRentDate(DateTime rentDate) {
		this.rentDate = rentDate;
	}

	public DateTime getEstimatedReturnDate() {
		return estimatedReturnDate;
	}

	public void setEstimatedReturnDate(DateTime estimatedReturnDate) {
		this.estimatedReturnDate = estimatedReturnDate;
	}

	public DateTime getActualReturnDate() {
		return actualReturnDate;
	}

	public void setActualReturnDate(DateTime actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}

	public double getRentalFee() {
		return rentalFee;
	}

	public void setRentalFee(double rentalFee) {
		this.rentalFee = rentalFee;
	}

	public double getLateFee() {

		return lateFee;
	}

	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}

	public RentalRecord() {       
        this.recordId = "";
        this.rentalFee = 0.0;
        this.lateFee = 0.0;
    }

    public RentalRecord(String recordId, int dd, int mm, int yyyy, int days) {
        this.recordId = recordId;
        this.rentDate = new DateTime(dd, mm, yyyy);
        this.estimatedReturnDate = new DateTime(this.rentDate, days);
    }

    protected void setReturnDate(int dd, int mm, int yyyy) {
        this.actualReturnDate = new DateTime(dd, mm, yyyy);
    }

    public String toString() {
        String result = "";
        if (this.rentalFee == 0.0) {
        	result = this.recordId + ":" + this.rentDate + ":" + DateTime.diffDays(this.estimatedReturnDate, this.rentDate);
        } else {
        	result = this.recordId + ":" + this.rentDate + ":" + this.estimatedReturnDate + ":" + this.actualReturnDate + ":" + this.rentalFee + ":" + this.lateFee;
        }
        return result;
    }

    String getDetails() {
        String result = "";
        try {
            if (this.recordId != null) {
                if (this.rentalFee == 0) {
                	result = "\nRecord ID : " + this.recordId;
                	result = result + "\nRent Date : " + this.rentDate;
                	result = result + "\nEstimated Return Date : " + this.estimatedReturnDate;
                } else {
                	result = "\nRecord ID : " + this.recordId;
                	result = result + "\nRent Date : " + this.rentDate;
                	result = result + "\nEstimated Return Date : " + this.estimatedReturnDate;
                	result = result + "\nActual Return Date : " + this.actualReturnDate;
                	result = result + "\nRental Fee : " + Math.round(this.rentalFee * 100.0) / 100.0;
                	result = result + "\nLate Fee : " + Math.round(this.lateFee * 100.0) / 100.0;
                }
            }
        } catch (NullPointerException npe) {
            return "\nempty";
        }
        return result;
    }


}
