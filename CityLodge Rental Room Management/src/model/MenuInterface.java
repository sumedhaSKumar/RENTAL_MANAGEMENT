package model;

import model.util.DateTime;

public interface MenuInterface {

	
		public void addRecords(RentalRecord renRec);
		public boolean rent(String cust_Id, DateTime rentDate, int days);
		public boolean returnRoom(DateTime retDate);
		public boolean performMaintenance();
		public boolean completeMaintenance(DateTime completion);
		public String getDetails();
		public double calculateRentFee(DateTime endDate, DateTime startDate);
		public double calculateLateFees(DateTime endDate, DateTime startDate);

	

}
