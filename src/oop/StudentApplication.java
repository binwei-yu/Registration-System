package oop;

public class StudentApplication extends Application {
	boolean signature;
	
	public StudentApplication(String id, String name, String phone, String reason, boolean signature) {
		super(id, name, phone, reason);
		this.signature = signature;
	}
}
