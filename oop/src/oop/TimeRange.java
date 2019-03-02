package oop;

public class TimeRange {
	public int startHour;
	public int endHour;
	
	public TimeRange(int startHour, int endHour) {
		this.startHour = startHour;
		this.endHour = endHour;
	}
	
	// Debug
	public String toString() {
		String res = "Time: " + String.valueOf(startHour) + ":00 - " + String.valueOf(endHour) + ":00\n";
		return res;
	}
}
