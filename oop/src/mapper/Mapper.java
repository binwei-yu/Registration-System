package mapper;
import oop.*;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

public interface Mapper {
	// Area
	public ArrayList<Area> getAreas() throws Exception;
	
	// Building
	public ArrayList<Building> getBuildings(Integer areaId) throws Exception;
	
	// Classroom
	public ArrayList<Classroom> getClassrooms(Integer buildingId) throws Exception;
	
	// Course
	public ArrayList<Course> getCourses() throws Exception;
	public void addCourses(@Param("course") Course course) throws Exception;
	public void deleteCourse(Integer courseId) throws Exception;
	
	// Student
	public void changePassword(Integer studentId, String password) throws Exception;
	
	// Registration
	public ArrayList<Registration> getStudentReg(Integer studentId) throws Exception;
	public ArrayList<Registration> getCourseReg(Integer courseId) throws Exception;
	public void addReg(Integer id, Integer courseId, Integer studentId, Integer state) throws Exception;
	public void deleteReg(Integer id) throws Exception;
}
