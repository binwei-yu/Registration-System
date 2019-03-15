package oop;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;

public class Student {
	String id;
	String name;
	String division;
	protected String password;
	static Logger log = new Logger();
	ArrayList<String> courses;
	ArrayList<Registration> registrations;
	
	static Student getValidated(String username, String password) {
		return log.validateStudent(username, password);
	};
	
	public Student(String name, String division, String password) {
		// Initialization
		this.id = StudentIDFactory.getInstance().createId(null);
		this.name = name;
		this.division = division;
		this.password = password;
		
		// Fetch data from  database
		courses = new ArrayList<String>();
		registrations = new ArrayList<Registration>();
	}
	
	
	
	// View classes
	public void viewClasses() {
		
		// Request data from ClassController
		final ArrayList<Pair<Course, Integer>> res = ClassController.getInstance().viewClasses(this.id);
		for(Pair<Course, Integer> item : res) {
			System.out.print("[" + item.getKey().name + "] ");
			final Section sec = item.getKey().sections.get(item.getValue());
			System.out.print(sec.location[0] + ", " + sec.location[1] + ", " + sec.location[2] + " ");
			System.out.println(sec.period.toString());
		}

		System.out.print("\n");
	}

	
	// Search courses
	public void searchCourse(HashMap<String, String> conditions) {
		System.out.println("............ RESEARCH RESULT ............");
		Course[] list = ClassController.getInstance().searchCourse(conditions);
		for(Course item : list) {
			System.out.println("------ ------ ------");
			System.out.println("ID: " + item.id);
			System.out.println("Course: " + item.name);
			System.out.println("Instructor: " + item.instructorName);
			System.out.println("Section: ");
			for(Section elem : item.sections) {
				String str = elem.location[0] + ", " + elem.location[1] 
						+ ", " + elem.location[2] + " ";
				for(WeekDay day : elem.getDays()) {
					str += day.toString() + " ";
				}
				str += elem.period.toString();
				System.out.println(str);
			}
		}
		System.out.print("\n");
	}
	
	// Create registration
	public final Registration createReg(String courseId, String courseName, String studentId, String studentName, Integer section) {
		return new Registration(courseId, courseName, studentId, studentName, section, 2);
	}
	
	// Drop class
	public void dropClass(String courseId) {
		ClassController.getInstance().dropCourse(courseId, id);
	}
	
	// Create reservation
	public void reserve() {
		StudentRequestFactory sf = new StudentRequestFactory();
		Application sp = sf.createApplication(id, name, "7737988434", "Presentation", true);
		WeekDay[] days = new WeekDay[1];
		days[0] = WeekDay.Monday;
		Reservation sr = sf.createReservation(new TimePeriod(13, 14), days, "East", "Regenstein Library", "N101", null);
		AllocationResult res = RegistrationMediator.dispatchRev(sp, sr);
		System.out.println(res.toString());
	}

}
