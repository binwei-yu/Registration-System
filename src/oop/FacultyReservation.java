package oop;

public class FacultyReservation extends Reservation {
	FacilityType[] facilities;
	
	public FacultyReservation(TimePeriod time, WeekDay days[], 
			String area, String building, String room, FacilityType[] facilities) {
		super(time, days, area, building, room);
		this.facilities = facilities;
	}
}
