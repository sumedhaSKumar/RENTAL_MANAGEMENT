package model;


public class Suite extends Room{

	public Suite() {
		super();
	}

	public Suite(String roomId, int numberOfRooms, String features, String roomType, String roomStatus, int dd,
			int mm, int yyyy, double fee, double lateFee, String name) {
		super(roomId, numberOfRooms, features, roomType, roomStatus, dd, mm, yyyy, fee, lateFee, name);
		
	}

	
}
