package oop;

//import static org.junit.Assert.*;

import org.junit.Test;

import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class test {
	@Test
	// VIEW RESOURCES
	public void test01() throws Exception {
		System.out.println("@test1");
		ResourceController rc = ResourceController.getInstance();
		rc.viewResources();
		
		System.out.print("\n\n\n");
	}

	@Test
	// ALLOCATE RESOURCES
	public void test02() throws Exception {
		System.out.println("@test2");
		ResourceController rc = ResourceController.getInstance();
		// Initialize CourseProxy
		CourseProxy.getInstance();
		
		// Check resources
		rc.viewAvailableResources("East, Crarer Library, TES21");
		
		WeekDay[] days = { WeekDay.Monday, WeekDay.Wednesday, WeekDay.Friday };
		AllocationResult[] res = new AllocationResult[4];
		
		// Wrong address
		res[0] = rc.allocateClassroom("CPT510236", "North", "Crarer Library", 
				"TES21", new TimePeriod(6, 10), days);
		
		// Wrong time
		res[1] = rc.allocateClassroom("CPT510236", "East", "Crarer Library", 
				"TES21", new TimePeriod(3, 10), days);
		
		// Correct
		res[2] = rc.allocateClassroom("CPT510236", "East", "Crarer Library", 
				"TES21", new TimePeriod(8, 10), days);
		WeekDay[] days2 = { WeekDay.Tuesday, WeekDay.Friday };
		rc.allocateClassroom("CPT510236", "East", "Crarer Library", 
				"TES21", new TimePeriod(10, 15), days2);
		
		// In use
		res[3] = rc.allocateClassroom("CPT510236", "East", "Crarer Library", 
				"TES21", new TimePeriod(7, 9), days);
		
		for(int i = 0; i < 4; i++) {
			System.out.print("res" + i + ": ");
			switch(res[i]) {
			case Success: System.out.println("sucess"); break;
			case InvalidTime: System.out.println("invalid time"); break;
			case InvalidArea: System.out.println("invalid area"); break;
			case InvalidBuilding: System.out.println("invalid building"); break;
			case InvalidRoom: System.out.println("invalid classroom"); break;
			case InUse: System.out.println("in use"); break;
			case Other: System.out.println("other"); break;
			}
		}
		System.out.print("\n");
		
		// Check resources
		rc.viewAvailableResources("East, Crarer Library, TES21");
		
		System.out.print("\n\n\n");
	}
	
	@Test
	// ADD COURSE
	public void test03() throws Exception {
		System.out.println("@test3");
		CourseProxy cp = CourseProxy.getInstance();

		// Create an instance of course (wrong information - no section)
		Course c1 = cp.createCourse("Network", "CPT", 0, "2019053462", "William Jafferson", 0);
		cp.addCourse(c1);
		
		// Create an instance of course (lack of resources)
		Course c2 = cp.createCourse("Object Oriented Programming", "CPT", 0, "2019057812", "Mark Shacklette", 3);
		cp.addCourse(c2);
		
		// Allocate resources
		String[] location = new String[] {"East", "Crarer Library", "TES21"};
		Integer[] schedules =  new Integer[] { 18, 8, 1 }; 
		TimePeriod[] periods = new TimePeriod[] { new TimePeriod(10, 12), new TimePeriod(17, 20), new TimePeriod(17, 20) };
		for(int i = 0; i < 3; i++) {
			c2.setSection(i, location, periods[i].startHour, periods[i].endHour, schedules[i], "");
		}
		cp.addCourse(c2);
		
		
		System.out.print("\n\n\n");
	}
	
	@Test
	// DELETE COURSE
	public void test04() throws Exception {
		System.out.println("@test4");
		CourseProxy cp = CourseProxy.getInstance();
		cp.viewCourses();
		
		// Delete course
		String id = cp.getCourseID("Object Oriented Programming");	// This function is only used for test
		if(id != null) cp.deleteCourse(id);
		cp.viewCourses();
		

		System.out.print("\n\n\n");
	}
	
	@Test
	// LOG IN
	public void test05() throws Exception {
		System.out.println("@test5");
		
		// Wrong password
		Student s1 = Student.getValidated("2019159852", "toor");
		if(s1 != null) s1.viewClasses();
		
		// Right password
		Student s2 = Student.getValidated("2019159852", "root");
		if(s2 != null) s2.viewClasses();
		

		System.out.print("\n\n\n");
	}
	
	@Test
	// SEARCH COURSES WITH KEYWORDS
	public void test06() throws Exception {
		System.out.println("@test6");
		
		// Log in
		Student s1 = Student.getValidated("2019159852", "root");
		if(s1 != null) {
			
			// Search without conditions
			HashMap<String, String> emptyCond = new HashMap<String, String>();
			s1.searchCourse(emptyCond);
			
			// Search with specified division
			HashMap<String, String> divCond = new HashMap<String, String>();
			divCond.put("division", "CPT");
			s1.searchCourse(divCond);
			
			// Search with specified instructor
			HashMap<String, String> instrCond = new HashMap<String, String>();
			instrCond.put("instructor", "Akira Morton");
			s1.searchCourse(instrCond);
			
			// Search with multiple conditions
			HashMap<String, String> mixCond = new HashMap<String, String>();
			mixCond.put("division", "CPT");
			mixCond.put("instructor", "Andrew Siegel");
			s1.searchCourse(mixCond);
		}
		

		System.out.print("\n\n\n");
	}
	
	@Test
	// SEND REGISTRATION (STUDENT) + PROCESS REGISTRATION (FACULTY)
	public void test07() throws Exception {
		System.out.println("@test7");
		
		// Student log in
		Student s = Student.getValidated("2019159852", "root");
		if(s != null) {
			
			// Send registration
			Registration reg = s.createReg("CPT034253", "Advanced Programming", s.id, s.name, 0);
			Registration reg2 = s.createReg("CPT396487", "Compiler", s.id, s.name, 0);
			RegistrationMediator.dispatch(reg);
			RegistrationMediator.dispatch(reg2);
			
			
			System.out.print(".........................\n\n");
		}
		
		// Faculty log in
		Faculty f = Faculty.getValidated("2019067854", "123456");
		if(f != null) {
			f.processRegistration("CPT034253", RegistrationState.Approved);
			
			System.out.print(".........................\n\n");
		}
		
		Faculty f2 = Faculty.getValidated("2019053462", "123456");
		if(f2 != null) {
			f2.processRegistration("CPT396487", RegistrationState.Declined);
			
			System.out.print(".........................\n\n");
		}
		
		// Student view schedule -- Debug
		if(s != null)
			s.viewClasses();
		

		System.out.print("\n\n\n");
	}
	
	// DROP CLASS
	@Test
	public void test08() throws Exception {
		System.out.println("@test08");
		
		// Student log in
		Student s = Student.getValidated("2019159852", "root");
		if(s != null) {
			s.dropClass("CPT034253");
			s.viewClasses();
		}
		System.out.print("\n\n\n");
	}
	
	// ADD RESERVATION
	@Test
	public void test09() throws Exception {
		System.out.println("@test09");
		
		// Student log in
		Student s = Student.getValidated("2019159852", "root");
		if(s != null) {
			s.reserve();
		}
		System.out.print("\n\n\n");
	}
	
	// HUMAN MANAGEMENT
	@Test
	public void test10() throws Exception {
		System.out.println("@test10");
		
		// View all students -- Debug
		ResourceController rc = ResourceController.getInstance();
		rc.viewStudents();
		
		// Add student
		rc.addStudent("Michael Jordan", "HST", "root");
		
		// Get student id -- Debug
		String id = rc.getStudentId("Michael Jordan");
		if(id != null) {
			
			// View single student to check information
			rc.viewStudent(id);
			
			// Delete student
			rc.deleteStudent(id);
			
		}

		System.out.print("\n\n\n");
	}
}
