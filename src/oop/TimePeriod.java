package oop;

public class TimePeriod {
	public int startHour;
	public int endHour;
	
	public TimePeriod(int startHour, int endHour) {
		this.startHour = startHour;
		this.endHour = endHour;
	}
	
	// Debug
	public String toString() {
		String res = String.valueOf(startHour) + ":00-" + String.valueOf(endHour) + ":00";
		return res;
	}
}
