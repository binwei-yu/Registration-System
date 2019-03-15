package oop;

public class Reservation {
	// Time
	TimePeriod time;
	WeekDay[] days;
	
	// Location
	String area;
	String building;
	String room;
	
	public Reservation(TimePeriod time, WeekDay[] days, String area, String building, String room) {
		this.time = time;
		this.days = days;
		this.area = area;
		this.building = building;
		this.room = room;
	}
}
