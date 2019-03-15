package oop;

public enum WeekDay {
	Sunday(0), Monday(1), Tuesday(2), Wednesday(3), Thursday(4), Friday(5), Saturday(6);
	private final int index;
	WeekDay(int index) { this.index = index; }
	public int getValue() { return index; }
	public static String getStrValue(int index) {
		switch(index) {
		case 0: return "Sunday"; 
		case 1: return "Monday"; 
		case 2: return "Tuesday"; 
		case 3: return "Wednesday"; 
		case 4: return "Thursday"; 
		case 5: return "Friday"; 
		case 6: return "Saturday"; 
		default: return "Unknown";
		}
	}
}
