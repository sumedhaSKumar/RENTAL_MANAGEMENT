package model;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.scene.control.Alert;
import model.util.DateTime;

public abstract class Room implements MenuInterface
{
	String roomId;
	int beds;
	String featureSummary;
	String roomType;
	String roomStatus;
	ArrayList<RentalRecord> hiringRecords;
	double fee;
	double lateFee;
	DateTime lastMaintenance;
	String roomImage;

	
	
	
	public Room() {
		this.roomId = "";
		this.beds = 0;
		this.featureSummary = "";
		this.roomType = null;
		this.roomStatus = null;
		this.lastMaintenance = null;
		this.fee = 0.0;
		this.lateFee = 0.0;
        this.roomImage = "";
        this.hiringRecords = new ArrayList<RentalRecord>();
	}

	public Room(String roomId, int beds, String featureSummary, String roomType, String roomStatus,
			double fee, double lateFee,String roomImage) {
		super();
		this.roomId = roomId;
		this.beds = beds;
		this.featureSummary = featureSummary;
		this.roomType = roomType;
		this.roomStatus = roomStatus;
		this.fee = fee;
		this.lateFee = lateFee;
        this.roomImage = roomImage;
        this.hiringRecords = new ArrayList<RentalRecord>();

	}

	public Room(String roomId, int beds, String featureSummary, String roomType, String roomStatus, int dd,
			int mm, int yyyy, double fee, double lateFee, String name) {
		super();
		this.roomId = roomId;
		this.beds = beds;
		this.featureSummary = featureSummary;
		this.roomType = roomType;
		this.roomStatus = roomStatus;
		this.lastMaintenance = new DateTime(dd, mm, yyyy);
		this.fee = fee;
		this.lateFee = lateFee;
        this.roomImage = name;
        this.hiringRecords = new ArrayList<RentalRecord>();
		
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public int getBeds() {
		return beds;
	}

	public void setBeds(int beds) {
		this.beds = beds;
	}

	public String getFeatureSummary() {
		return featureSummary;
	}

	public void setFeatureSummary(String featureSummary) {
		this.featureSummary = featureSummary;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}

	public List<RentalRecord> getHiringRecords() {
		return hiringRecords;
	}

	public void setHiringRecords(ArrayList<RentalRecord> hiringRecords) {
		this.hiringRecords = hiringRecords;
	}
	
	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public double getLateFee() {
		return lateFee;
	}

	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}

	public DateTime getLastMaintenance() {
		return lastMaintenance;
	}

	public void setLastMaintenance(DateTime lastMaintenance) {
		this.lastMaintenance = lastMaintenance;
	}

	public String getRoomImage() {
		return roomImage;
	}

	public void setRoomImage(String roomImage) {
		this.roomImage = roomImage;
	}
	
	
	public void addRecords(RentalRecord renRec)
	{
		try 
		{
			hiringRecords.add(renRec);
		} 
		catch (Exception npe) 
		{}
		
	}

	public boolean rent(String cust_Id, DateTime rentDate, int days) { 
		if ((this.roomStatus.equals("Rented") == true || this.roomStatus.equals("Maintenance")))
		{
  			 Alert rt = new Alert(Alert.AlertType.INFORMATION);
             rt.setTitle("Input");
             rt.setHeaderText(null);
             rt.setContentText("Room has already been rented.");
             rt.showAndWait();
			return false;
		} else {
			Calendar calender = Calendar.getInstance();
			calender.setTime(new Date(rentDate.getTime()));
			int week = calender.get(Calendar.DAY_OF_WEEK);
			int min = 1;
			if ((this.roomType.equals("StandardRoom")) == true) {
				switch (week) {
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
					 Alert rt = new Alert(Alert.AlertType.INFORMATION);
		             rt.setTitle("Input");
		             rt.setHeaderText(null);
		             rt.setContentText("minDays = 2");
		             rt.showAndWait();
					min = 2;
					break;
				case 6:
				case 7:
					Alert rt1 = new Alert(Alert.AlertType.INFORMATION);
		             rt1.setTitle("Input");
		             rt1.setHeaderText(null);
		             rt1.setContentText("minDays = 3");
		             rt1.showAndWait();
					min = 3;
					break;
				}
			}
			if (min > days) {
				Alert rt = new Alert(Alert.AlertType.INFORMATION);
	             rt.setTitle("Input");
	             rt.setHeaderText(null);
	             rt.setContentText("Minimum stay period for current day of week" + min + ".");
	             rt.showAndWait();
				return false;
			}
			if ((this.roomType.equals("Suite"))) {
				DateTime last = new DateTime(this.lastMaintenance, 10);
				DateTime temp = new DateTime(
						new DateTime(calender.get(Calendar.DAY_OF_MONTH), calender.get(Calendar.MONTH), calender.get(Calendar.YEAR)),
						days);
				int diff = DateTime.diffDays(last, temp);
				if (diff < 0) {
					Alert rt = new Alert(Alert.AlertType.INFORMATION);
		             rt.setTitle("Input");
		             rt.setHeaderText(null);
		             rt.setContentText("Cant rent room for so long...Maintenance issue");
		             rt.showAndWait();
					return false;
				}
			}
			String rId = this.roomId + "_" + cust_Id + "_" + calender.get(Calendar.DAY_OF_MONTH) + calender.get(Calendar.MONTH) + calender.get(Calendar.YEAR);
			RentalRecord hr = new RentalRecord(rId, calender.get(Calendar.DAY_OF_MONTH), calender.get(Calendar.MONTH),calender.get(Calendar.YEAR), days);
			
			this.roomStatus ="Rented";
			this.addRecords(hr);
			return true;
		}
	}

	public boolean returnRoom(DateTime retDate) 
	{
			if ((roomStatus.equals("Rented"))) 
			{
				DateTime rentDate = this.hiringRecords.get(0).rentDate;
				DateTime estReturnDate = this.hiringRecords.get(0).estimatedReturnDate;
				int stays = DateTime.diffDays(retDate, estReturnDate);
			
				
				
				if (stays < 0) 
				{
					Alert rt1 = new Alert(Alert.AlertType.INFORMATION);
		            rt1.setTitle("Input");
		            rt1.setHeaderText(null);
		            rt1.setContentText("Return date cann't be before rent date.");
		            rt1.showAndWait();
					return false;
				} else if (stays == 0) { 
					this.hiringRecords.get(0).actualReturnDate = retDate;
					this.hiringRecords.get(0).rentalFee = this.calculateRentFee(estReturnDate, rentDate);
					this.roomStatus = "Available";
					return true;
				} else { 
					this.hiringRecords.get(0).actualReturnDate = retDate;
					this.hiringRecords.get(0).rentalFee = this.calculateRentFee(estReturnDate, rentDate);
					this.hiringRecords.get(0).lateFee = this.calculateLateFees(retDate, estReturnDate);
					this.roomStatus = "Available";
					return true;
				}
		} else {
			return false;
		}
	}

	public double calculateRentFee(DateTime endDate, DateTime startDate) 
	{ 
		return DateTime.diffDays(endDate, startDate) * fee;
	}

	public double calculateLateFees(DateTime endDate, DateTime startDate) 
	{
		return DateTime.diffDays(endDate, startDate) * lateFee;
	}

	public boolean performMaintenance() 
	{ 
		if (this.roomType.equals("Suite")) 
		{
			int numberOfDays = DateTime.diffDays(new DateTime(), this.lastMaintenance);
			Alert rt = new Alert(Alert.AlertType.INFORMATION);
            rt.setTitle("Input");
            rt.setHeaderText(null);
            rt.setContentText("Last maintenance: " + numberOfDays);
            rt.showAndWait();
			if (numberOfDays >= 10) 
			{
				if (this.roomStatus.equals("Available")) {
					this.roomStatus = "Maintenance";
					this.lastMaintenance = new DateTime();
					Alert rt1 = new Alert(Alert.AlertType.INFORMATION);
		            rt1.setTitle("Input");
		            rt1.setHeaderText(null);
		            rt1.setContentText("Room " + this.roomId + "is maintenence now");
		            rt1.showAndWait();
					return true;
				} else {
					Alert rt1 = new Alert(Alert.AlertType.INFORMATION);
		            rt1.setTitle("Input");
		            rt1.setHeaderText(null);
		            rt1.setContentText("Room cann't maintenance right now");
		            rt1.showAndWait();
					return false;
				}
			} else {
				Alert rt1 = new Alert(Alert.AlertType.INFORMATION);
	            rt1.setTitle("Input");
	            rt1.setHeaderText(null);
	            rt1.setContentText("10 days, not yet over since last maintenance.");
	            rt1.showAndWait();
				return false;
			}
		} else 
		{ 
			if (this.roomStatus.equals("Available")) 
			{ 
				this.roomStatus = "Maintenance";
				this.lastMaintenance = new DateTime();
				Alert rt1 = new Alert(Alert.AlertType.INFORMATION);
	            rt1.setTitle("Input");
	            rt1.setHeaderText(null);
	            rt1.setContentText("Room " + this.roomId + "is under maintenence now");
	            rt1.showAndWait();
				return true;
			} else {
				Alert rt1 = new Alert(Alert.AlertType.INFORMATION);
	            rt1.setTitle("Input");
	            rt1.setHeaderText(null);
	            rt1.setContentText("Room cannot undergo maintenance right now");
	            rt1.showAndWait();
				return false;
			}
		}
	}

	public boolean completeMaintenance(DateTime completion) { 
		Alert rt1 = new Alert(Alert.AlertType.INFORMATION);
        rt1.setTitle("Input");
        rt1.setHeaderText(null);
        rt1.setContentText("Last MainTenance: " + this.lastMaintenance+"\nComplete Maintenace : " + completion);
        rt1.showAndWait();
        
        
		int numberOfDays = DateTime.diffDays(completion, this.lastMaintenance);
		if (numberOfDays < 0) {
			
			Alert rr = new Alert(Alert.AlertType.ERROR);
            rr.setTitle("Error");
            rr.setHeaderText(null);
            rr.setContentText("Wrong date input! Please enter completion date again");
            rr.showAndWait();
			return false;
		} else {
			this.roomStatus = "Available";
			this.lastMaintenance = completion; 
			return true;
		}
	}

	public String toString() {
		String result = "";
		if (this.roomType.equals("StandardRoom")) {
			result = this.roomId + ":"+ this.beds + ":" + this.featureSummary +":"+ this.roomStatus+":"+this.roomImage+":"+this.roomType;
		} else {
			result = this.roomId + ":"+ this.beds + ":" + this.featureSummary +":"+ this.roomStatus+":"+this.lastMaintenance+":"+this.roomImage+":"+this.roomType;
		}
		return result;
	}

	public String getDetails() 
	{
		String result = "";

		result = "Room ID : " + this.roomId;
		result = result + "\nNumber of Beds : "+ this.beds; 
		result = result + "\nType : " + this.roomType;
		result = result + "\nStatus : " + this.roomStatus;
		if (this.roomType.equals("Suite")) {
			result = result + "\nLast Maintenance Date : " + this.lastMaintenance;
		}
		if (this.hiringRecords.size() == 0) {
			result = result + "\nRENTAL RECORD : empty";
		} else {
			result = result + "\nRENTAL RECORD";
			try {
				int i = 0;
				while (i < this.hiringRecords.size()) {
					result = result + this.hiringRecords.get(i).getDetails();
					result = result + "\n***********************************";
					i += 1;
				}
			} catch (NullPointerException npe) {
				result = result + "\n";
			}
		}
		return result;
	}

	

}
