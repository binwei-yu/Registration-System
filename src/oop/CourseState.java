package oop;

public enum CourseState {
	Open(0), Closed(1), Cancelled(2);
	
	private final int index;
	CourseState(int index) { this.index = index; }
	public int getValue() { return index; }
}
