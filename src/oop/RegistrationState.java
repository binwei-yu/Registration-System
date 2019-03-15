package oop;

public enum RegistrationState {
	Approved(0), Declined(1), Pending(2), Cancelled(3);

	private final int index;
	RegistrationState(int index) { this.index = index; }
	public int getValue() { return index; }
}
