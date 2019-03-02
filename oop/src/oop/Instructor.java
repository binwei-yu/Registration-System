package oop;
import java.util.ArrayList;

public class Instructor extends Member {
	ArrayList<Registration> registrationList;
	
	public Instructor(String name, String division, String password) {
		super(name, division, password);
	}
	
	public void GetRegistrations(Integer courseId) {
		CourseController cc = CourseController.getInstance();
		registrationList = cc.getCourseRegistrations(courseId);
		// TODO
		System.out.println("View Registrations");
		for(Registration reg : registrationList) {
			System.out.println("[Student ID] " + reg.studentId + " [Registration ID] " + reg.id);
		}
	}
	
	// Debug
	public Integer ReadRegistration() {
		if(registrationList.size() > 0) return registrationList.get(0).id;
		else return -1;
	}
	
	public void ProcessRegistration(Integer id, boolean isApproved) {
		for(Registration reg : registrationList) {
			if(reg.id == id) registrationList.remove(reg);
		}
		
		// Update database
		MySQLModule mysqldb = MySQLModule.getInstance();
		
		Integer state = 0;
		if(!isApproved) state = 1;
		String sql = "UPDATE registrations SET state = " + state.toString() + " WHERE id = " + id.toString();
		mysqldb.update(sql);
		mysqldb.closeQuery();
	}
}
