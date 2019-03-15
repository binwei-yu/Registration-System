package oop;

public class StudentReservation extends Reservation {
	// Constructor
	public StudentReservation(TimePeriod time, WeekDay[] days, String area, String building, String room) {
		super(time, days, area, building, room);
	}
}
