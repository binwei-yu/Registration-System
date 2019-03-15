package oop;

public class FacultyRequestFactory implements IRequestFactory {

	@Override
	public Reservation createReservation(TimePeriod time, WeekDay[] days, String area, String building, String room,
			FacilityType[] facilities) {
		// No limitation for faculty request
		return new FacultyReservation(time, days, area, building, room, facilities);
	}

	@Override
	public Application createApplication(String id, String name, String phone, String reason, boolean signature) {
		return new FacultyApplication(id, name, phone, reason);
	}

}
