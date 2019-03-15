package oop;

public class Application {
	String applicant_id;
	String applicant_name;
	String applicant_phone;

	String reason;
	
	public Application(String id, String name, String phone, String reason) {
		this.applicant_id = id;
		this.applicant_name = name;
		this.applicant_phone = phone;
		
		this.reason = reason;
	}
}
