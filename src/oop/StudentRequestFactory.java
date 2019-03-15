package oop;

public class StudentRequestFactory implements IRequestFactory {

	@Override
	public Reservation createReservation(TimePeriod time, WeekDay days[], 
			String area, String building, String room, FacilityType[] facilities) {
		
		// Students could only reserve 2 hour once
		if(time.endHour - time.startHour > 2 || days.length > 1) return null;
		
		// Facilities cannot be used
		return new StudentReservation(time, days, area, building, room);
	}

	@Override
	public Application createApplication(String id, String name, String phone, String reason, boolean signature) {
		return new StudentApplication(id, name, phone, reason, signature);
	}

}
