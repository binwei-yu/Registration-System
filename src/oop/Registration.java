package oop;

public class Registration {
	String courseId;
	String courseName;
	String studentId;
	String studentName;
	Integer section;
	Integer state;
	
	public Registration(String courseId, String courseName, String studentId, String studentName, Integer section, Integer state) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.studentId = studentId;
		this.studentName = studentName;
		this.section = section;
		this.state = state;
	}
}
