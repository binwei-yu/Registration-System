package oop;

public interface IInstructor {
	public void assignGrade(String studentId, String courseId, Integer grade);
	public void updateGrade(String studentId, String courseId, Integer grade);
	public Course[] viewSchedule(String instructorId);
	public Student[] viewRoger(String courseId);
}
