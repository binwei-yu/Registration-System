package oop;
import java.util.Random;

public class Section {
	final int sectionID;
	Integer classroomID;
	TimeRange timeRange;
	
	public Section() {
		Random rand = new Random();
		this.sectionID = Math.abs(rand.nextInt());
	}
}
