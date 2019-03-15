package mapper;
import oop.*;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

public interface Mapper {
	// Log
	public Student validateStudent(@Param("id") String id, @Param("password") String password) throws Exception;
	public Faculty validateFaculty(@Param("id") String id, @Param("password") String password) throws Exception;
	
	// Area
	public ArrayList<Area> getAreas() throws Exception;
	
	// Building
	public ArrayList<Building> getBuildings(Integer areaId) throws Exception;
	
	// Classroom
	public ArrayList<Classroom> getClassrooms(Integer buildingId) throws Exception;
	
	// ClassController
	public ArrayList<ClassRecord> viewClasses(@Param("id") String studentId) throws Exception;
	public void addClass(@Param("courseId") String courseId, @Param("courseName")String courseName, 
			@Param("studentId") String studentId, @Param("studentName") String studentName, @Param("section") Integer section);
	public void dropClass(@Param("courseId") String courseId, @Param("studentId") String studentId) throws Exception;
	
	// Student
	public ArrayList<Student> getStudents() throws Exception;
	public void addStudent(@Param("id") String id, @Param("name") String name, @Param("division") String division, @Param("password") String password);
	public void deleteStudent(@Param("id") String id);
	public void changePassword(Integer studentId, String password) throws Exception;
	
	// Faculty
	public ArrayList<Faculty> getFaculties() throws Exception;
	public void addFaculty(@Param("id") String id, @Param("name") String name, @Param("division") String division, @Param("password") String password);
	public void deleteFaculty(@Param("id") String id);
	
	// Registration
	public ArrayList<Registration> getStudentReg(@Param("studentId") String studentId) throws Exception;
	public ArrayList<Registration> getCourseReg(@Param("courseId") String courseId) throws Exception;
	public void addReg(@Param("courseId") String courseId, @Param("courseName") String courseName, 
			@Param("studentId") String studentId, @Param("studentName") String studentName, 
			@Param("section") Integer section, @Param("state") Integer state) throws Exception;
	public void updateReg(@Param("courseId") String courseId, @Param("studentId") String studentId, @Param("state") Integer state) throws Exception;
	public void cancelReg(@Param("courseId") String courseId, @Param("studentId") String studentId) throws Exception;
	public void deleteReg(@Param("courseId") String courseId, @Param("studentId") String studentId) throws Exception;
	
	// Course Proxy
	public ArrayList<Course> getCourse() throws Exception;
	public void addCourse(@Param("id") String id, @Param("name") String name, @Param("state") Integer state, 
			@Param("division") String division, @Param("instructorId") String instructorId, @Param("instructorName") String instructorName) throws Exception;
	public void deleteCourse(@Param("id") String id) throws Exception;
	public ArrayList<Section> getSection(@Param("courseId") String courseId) throws Exception;
	public void addSection(@Param("location") String location, @Param("start") Integer start, @Param("end") Integer end, 
			@Param("days") Integer days, @Param("lab") String lab, @Param("course") String course) throws Exception;
	public void deleteSection(@Param("courseId") String courseId) throws Exception;

	// Cleaner
	public void clearCourse() throws Exception;
	public void clearSection() throws Exception;
}
