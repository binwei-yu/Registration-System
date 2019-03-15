package oop;

public class TA extends Student implements IInstructor {

	public TA(String name, String division, String password) {
		super(name, division, password);
	}

	@Override
	public void assignGrade(String studentId, String courseId, Integer grade) {
	}

	@Override
	public void updateGrade(String studentId, String courseId, Integer grade) {

	}

	@Override
	public Course[] viewSchedule(String instructorId) {
		return null;
	}

	@Override
	public Student[] viewRoger(String courseId) {
		return null;
	}

}
