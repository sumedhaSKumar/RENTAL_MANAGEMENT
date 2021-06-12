package model.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import model.Room;

public class Validation {

	public static boolean checkIfIdExist(List<Room> roomList, String id) {
		boolean result = true;
		for (Room room : roomList) {
			if (room.getRoomId().equals(id)) {
				result = false;
				break;
			}
		}
		return result;
	}

	public static boolean checkWordsInFeatureSummary(String summary, int maxLimit) {
		boolean result = true;
		String words[] = summary.split(" ");
		if (words.length > maxLimit)
			result = false;
		return result;
	}

	public static boolean validateBedsForStandard(int beds) {
		boolean result = false;
		if (beds == 1 || beds == 2 || beds == 4)
			result = true;
		return result;
	}

	public static Room getRoom(List<Room> roomList, String id) {
		Room result = null;
		for (Room room : roomList) {
			if (room.getRoomId().equals(id)) {
				result = room;
				break;
			}
		}
		return result;
	}

	public static DateTime addDays(DateTime date, int plusDays) {
		LocalDate dateLocal = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate resultLocal = dateLocal.plusDays(plusDays);
		DateTime result = new DateTime(resultLocal.getDayOfMonth(), resultLocal.getMonthValue(), resultLocal.getYear());
		return result;
	}

}
