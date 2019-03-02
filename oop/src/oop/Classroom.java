package oop;
import java.util.ArrayList;
import java.lang.Math;
import javafx.util.Pair;

import java.util.Random;

public class Classroom {
	final int id;
	String name;
	
	public ArrayList<ArrayList<Pair<TimeRange, Integer>>> schedule;
	
	public Classroom(String roomName) {
		// Generate ID
		Random rand = new Random();
		this.id = Math.abs(rand.nextInt());
		
		this.name = roomName;
		
		// Add other information
		this.schedule = new ArrayList<ArrayList<Pair<TimeRange, Integer>>>(7);
	}
	
	// Add range
	public boolean addTimeRange(Integer courseId, TimeRange range, WeekDay[] days) {
		// Examine conflict
		for(WeekDay day : days) {
			ArrayList<Pair<TimeRange, Integer>> TimeRangeList = schedule.get(day.getValue());
			for(Pair<TimeRange, Integer> item : TimeRangeList) {
				if(item.getKey().endHour <= range.startHour || range.endHour <= item.getKey().startHour) continue;
				else return false;
			}
		}

		// Allocate
		for(WeekDay day : days) {
			schedule.get(day.getValue()).add(new Pair<TimeRange, Integer>(range, courseId));
		}
		return true;
	}
}
