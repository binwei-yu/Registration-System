package oop;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;
import mapper.Mapper;

public class CourseController {

	// Properties
	private static CourseController cc = null;
	private HashMap<Integer, Course> courses;
	
	// Singleton
	public static CourseController getInstance() {
		if(cc == null) cc = new CourseController();
		return cc;
	}
	
	// Constructor
	private CourseController() {
		courses = new HashMap<Integer, Course>();
		
		// Fetch data
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				ArrayList<Course> res = mapper.getCourses();
				for(Course course : res) {
					courses.put(course.id, course);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	// Add course
	public void addCourse(Course course) {
		courses.put(course.id, course);
		
		// Update
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				mapper.addCourses(course);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	// Delete course
	public void deleteCourse(Integer courseId) {
		if(courses.containsKey(courseId))
			courses.remove(courseId);
		
		// Update
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				mapper.deleteCourse(courseId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	// Get available courses
	public ArrayList<Course> getAvailableCourses() {
		ArrayList<Course> res = new ArrayList<Course>();
		for(Course course : courses.values()) {
			// Only return open courses
			if(course.state == CourseState.Open) {
				res.add(course);
			}
		}
		return res;
	}
	
	// Dispatch student registration to faculty
	public void dispatchRegistration(Registration reg) {
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				mapper.addReg(reg.id, reg.courseId, reg.studentId, reg.state.getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	// Get one's registrations results with student id
	public ArrayList<Registration> getStudentRegistrations(Integer studentId) {
		ArrayList<Registration> res = new ArrayList<Registration>();
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				ArrayList<Registration> tmp = mapper.getStudentReg(studentId);
				for(Registration reg : tmp) {
					res.add(reg);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return res;
	}
	
	// Get all registrations of one course
	public ArrayList<Registration> getCourseRegistrations(Integer courseId) {
		ArrayList<Registration> res = new ArrayList<Registration>();
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				ArrayList<Registration> tmp = mapper.getCourseReg(courseId);
				for(Registration reg : tmp) {
					res.add(reg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return res;
	}
	
	// Process student's request of deleting registration
	public void deleteRegistration(Integer regId) {
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				mapper.deleteReg(regId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	// Find course by id
	public boolean containsCourse(Integer id) {
		return courses.containsKey(id);
	}
	
	// View available resource
	public HashMap<String, ArrayList<Pair<Integer, TimeRange>>> viewAvailableClassrooms() {
		ResourceController rc = ResourceController.getInstance();
		return rc.getResources();
	}
	
	// Request resource
	public ClassroomAllocationResult requestClassrooms(Integer courseId, String areaName, String buildingName, 
			String roomName, TimeRange range, WeekDay[] days) {
		ResourceController rc = ResourceController.getInstance();
		return rc.allocateClassroom(courseId, areaName, buildingName, roomName, range, days);
	}
}
