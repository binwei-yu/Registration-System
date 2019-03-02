package oop;

import java.util.Random;

public class Registration {
	Integer id;
	Integer courseId;
	String courseName;
	Integer studentId;
	RegistrationState state;
	
	public Registration(Integer courseId, String courseName, Integer studentId) {
		// Generate ID
		Random rand = new Random();
		this.id = Math.abs(rand.nextInt());
		
		// Add other information
		this.courseId = courseId;
		this.courseName = courseName;
		this.studentId = studentId;
		this.state = RegistrationState.Pending;
	}
	
	public Registration(Integer id, Integer courseId, String courseName, Integer studentId, RegistrationState state) {
		this.id = id;
		this.courseId = courseId;
		this.courseName = courseName;
		this.studentId = studentId;
		this.state = state;
	}
}
