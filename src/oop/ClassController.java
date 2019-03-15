package oop;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;

//import javafx.util.Pair;
import mapper.Mapper;

public class ClassController extends Controller {

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	// Properties
	private static ClassController cc = null;
	private CourseProxy proxy = null;
	
	// Singleton
	public static ClassController getInstance() {
		if(cc == null) cc = new ClassController();
		return cc;
	}
	
	// Constructor
	private ClassController() {
		proxy = CourseProxy.getInstance();
	}
	
	// Search course
	public Course[] searchCourse(HashMap<String, String> conditions) {
		return proxy.searchCourse(conditions);
	}
	
	// Add course
	public void addClass(String courseId, String courseName, String studentId, String studentName, Integer section) {
		
		// Update
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				mapper.addClass(courseId, courseName, studentId, studentName, section);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
	}
	
	// Drop class
	public void dropCourse(String courseId, String studentId) {
		
		// Update
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				mapper.dropClass(courseId, studentId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
	}
	
	// View classes
	public final ArrayList<Pair<Course, Integer>> viewClasses(String studentId) {

		ArrayList<Pair<Course, Integer>> res = new ArrayList<Pair<Course, Integer>>();
		
		// Update
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				ArrayList<ClassRecord> classes = mapper.viewClasses(studentId);
				for(ClassRecord item : classes) {
					Course course = CourseProxy.getInstance().getCourse(item.courseId);
					Pair<Course, Integer> tmp = new Pair<Course, Integer>(course, item.section);
					res.add(tmp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return res;
	}
	
	/* Dispatch student registration to faculty
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
	*/
}
