package oop;

import oop.Member;
import java.util.ArrayList;
import mapper.Mapper;

public class Student extends Member {
	ArrayList<Course> courseList;
	ArrayList<Registration> registrationList;
	
	public Student(String name, String division, String password) {
		super(name, division, password);
		
		// Fetch data from 
		courseList = new ArrayList<Course>();
	}
	
	// Change password
	@Override
	public void changePassword(String password) {
		this.password = password;
		DatabaseController ds = DatabaseController.getInstance();
		ds.query((Mapper mapper) -> {
			try {
				mapper.changePassword(id, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	// View available courses
	public void ViewAvailableCourses() {
		// TODO
		CourseController cc = CourseController.getInstance();
		ArrayList<Course> list = cc.getAvailableCourses();
		for(Course item : list) {
			System.out.println("[Course] " + item.name + " [ID] " + item.id);
		}
	}
	
	// Get registration records
	public void ViewRegistration() {
		CourseController cc = CourseController.getInstance();
		registrationList = cc.getStudentRegistrations(this.id);

		for(Registration item : registrationList) {
			String state = "Pending";
			if(item.state == RegistrationState.Approved) state = "Approved";
			else if(item.state == RegistrationState.Declined) state = "Declined";
			System.out.println("[Course] " + item.courseName + " [State] " + state);
		}
	}
	
	// Add registration
	public void AddRegistration(Integer courseId, String courseName) {
		CourseController cc = CourseController.getInstance();
		cc.dispatchRegistration(new Registration(courseId, courseName, this.id));
	}
	
	// Delete registration
	public void DeleteRegistration(Integer registrationId) {
		CourseController cc = CourseController.getInstance();
		cc.deleteRegistration(registrationId);
	}
	
	// View registered courses
	public void ViewCourses() {
		// TODO
	}
	
	public void DropCourse(Integer courseId) {
		// TODO
	}
	
	public void RescheduleCourse() {
		// TODO
	}
}
