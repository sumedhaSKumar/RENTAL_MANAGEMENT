package model;


public class StandardRoom extends Room {

	public StandardRoom() {                 
        super();
    }

	public StandardRoom(String roomId, int numberOfRooms, String features, String roomType, String roomStatus,
			double fee, double lateFee, String name) {
		super(roomId, numberOfRooms, features, roomType, roomStatus, fee, lateFee, name);
		
	}

}
