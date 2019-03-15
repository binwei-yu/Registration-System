package oop;
import java.util.ArrayList;

public class Course {
	String id;
	String name;
	int state;
	String division;
	String instructorId;
	String instructorName;
	public ArrayList<Section> sections;
	
	// Constructor for database initializer
	public Course(String id, String name, Integer state, String division, String instructorId, String instructorName) {	
		this.id = id;
		this.name = name;
		this.division = division;
		this.state = state;
		this.instructorId = instructorId;
		this.instructorName = instructorName;
		sections = new ArrayList<Section>();
	}
	
	public Course(String id, String name, String division, int state, String instructorId, 
			String instructorName, int sectionSize) {
		this.id = id;
		this.name = name;
		this.state = state;
		this.division = division;
		this.instructorId = instructorId;
		this.instructorName = instructorName;
		sections = new ArrayList<Section>();
		for(int i = 0; i < sectionSize; i++)
			this.addEmptySection();
	}
	
	public void addEmptySection() {
		String[] location = {};
		this.addSection(location, 0, 0, 0, "");
	}
	
	public void addSection(final Section section) {
		sections.add(section);
	}
	
	public void addSection(String[] location, Integer start, Integer end, Integer days, String lab) {
		TimePeriod tp = new TimePeriod(start, end);
		sections.add(new Section(location, tp, days, lab));
	}
	
	public void setSection(int index, String[] location, Integer start, Integer end, Integer days, String lab) {
		TimePeriod tp = new TimePeriod(start, end);
		if(index > -1 && index < sections.size()) {
			sections.get(index).location = location;
			sections.get(index).period = tp;
			sections.get(index).days = days;
			sections.get(index).lab = lab;
		}
	}

	/*
	public final Section getSection(int index) {
		if(index > -1 && index < sections.size())
			return sections.get(index);
		else return null;
	}
	
	public void deleteSection(int index) {
		if(index > -1 && index < sections.size())
			sections.remove(index);
	}
	
	public int getSectionSize() {
		return sections.size();
	}
	*/
}
