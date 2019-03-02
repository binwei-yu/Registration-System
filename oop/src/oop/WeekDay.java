package oop;

public enum WeekDay {
	Sunday(0), Monday(1), Tuesday(2), Wednesday(3), Thursday(4), Friday(5), Saturday(6);
	private final int index;
	WeekDay(int index) { this.index = index; }
	public int getValue() { return index; }
}
