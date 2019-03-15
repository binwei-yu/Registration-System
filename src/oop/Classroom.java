package oop;
import java.util.ArrayList;
import javafx.util.Pair;

public class Classroom {
	String name;
	
	public ArrayList<ArrayList<Pair<TimePeriod, String>>> schedule;
	
	public Classroom(String name) {
		this.name = name;
		
		// Add other information
		this.schedule = new ArrayList<ArrayList<Pair<TimePeriod, String>>>();
		for(int i = 0; i < 7; i++)
			this.schedule.add(new ArrayList<Pair<TimePeriod, String>>());
	}
	
	// Add range
	public boolean addTimeRange(String courseId, TimePeriod period, WeekDay[] days) {
		// Examine conflict
		for(WeekDay day : days) {
			ArrayList<Pair<TimePeriod, String>> periods = schedule.get(day.getValue());
			for(Pair<TimePeriod, String> item : periods) {
				if(item.getKey().endHour <= period.startHour || period.endHour <= item.getKey().startHour) continue;
				else return false;
			}
		}

		// Allocate
		for(WeekDay day : days) {
			schedule.get(day.getValue()).add(new Pair<TimePeriod, String>(period, courseId));
		}
		return true;
	}
}
