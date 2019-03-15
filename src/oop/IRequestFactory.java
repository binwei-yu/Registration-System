package oop;

public interface IRequestFactory {
	// Create reservations
	Reservation createReservation(TimePeriod time, WeekDay days[], 
			String area, String building, String room, FacilityType[] facilities);
	
	// Create applications
	Application createApplication(String id, String name, String phone, String reason, boolean signature);
}
