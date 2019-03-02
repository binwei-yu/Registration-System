package oop;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

public class Course {
	final Integer id;
	String name;
	Integer labId;
	CourseState state;
	private ArrayList<Section> sectionList;
	
	// Constructor
	public Course(String name) {
		// Generate ID
		Random rand = new Random();
		this.id = Math.abs(rand.nextInt());
		
		// Add other information
		this.name = name;
		this.labId = 0;
		this.state = CourseState.Open;
		this.sectionList = new ArrayList<Section>();
	}
	
	public Course(Integer id, String name, Integer labId, CourseState state) {
		this.id = id;
		this.name = name;
		this.labId = labId;
		this.state = state;
		this.sectionList = new ArrayList<Section>();
	}
	
	// Append a section
	public void addSection(Section section) {
		sectionList.add(section);
	}
	
	// Get section from list
	public Section getSection(int idx) {
		return sectionList.get(idx);
	}
	
	// Return true when succeed, otherwise return false
	public boolean deleteSection(int idx) {
		if(idx > 0 && idx < sectionList.size()) {
			sectionList.remove(idx);
			return true;
		}
		else return false;
	}
	
	// Return the number of sections of this class
	public int sectionSize() {
		return sectionList.size();
	}
}
