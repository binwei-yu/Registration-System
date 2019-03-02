package oop;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;
import mapper.Mapper;

public class ResourceController {
	public static ResourceController rc = null;
	HashMap<String, Area> areas;
	
	// Singleton
	public static ResourceController getInstance() {
		if(rc == null) rc = new ResourceController();
		return rc;
	}
	
	// Constructor
	private ResourceController() {
		areas = new HashMap<String, Area>();
		
		// Fetch area data
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				ArrayList<Area> res = mapper.getAreas();
				for(Area area : res) {
					areas.put(area.name, area);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		// Fetch resources
		for(Area area : areas.values()) {
			area.loadBuildings();
		}
	}
	
	// Return resource
	public final HashMap<String, ArrayList<Pair<Integer, TimeRange>>> getResources() {
		HashMap<String, ArrayList<Pair<Integer, TimeRange>>> res = 
				new HashMap<String, ArrayList<Pair<Integer, TimeRange>>>();
		for(Area area : areas.values()) {
			for(Building bld : area.buildings.values()) {
				for(Classroom room : bld.classrooms.values()) {
					String location = area.name + " " + bld.name + " " + room.name;
					res.put(location, new ArrayList<Pair<Integer, TimeRange>>());
					for(int i = 0; i < 7; i++) {
						int end = 0;
						for(Pair<TimeRange, Integer> tr : room.schedule.get(i)) {
							if(tr.getKey().startHour > end+1) {
								res.get(location).add(new Pair<Integer, TimeRange>(i, new TimeRange(end, tr.getKey().startHour)));
							}
							end = tr.getKey().endHour;
						}
						if(end < 22)
							res.get(location).add(new Pair<Integer, TimeRange>(i, new TimeRange(end, 22)));
					}
				}
			}
		}
		return res;
	}
	
	// Allocate
	public ClassroomAllocationResult allocateClassroom(Integer courseId, String areaName, String buildingName, 
			String roomName, TimeRange range, WeekDay[] days) {
		
		// Time before 6 am and after 10 pm is unavailable
		if(range.startHour < 6 || range.endHour > 22) 
			return ClassroomAllocationResult.InvalidTime;
		
		// Validate course
		CourseController cc = CourseController.getInstance();
		if(!cc.containsCourse(courseId))
			return ClassroomAllocationResult.InvalidCourse;
		
		// Validate address
		if(!areas.containsKey(areaName))
			return ClassroomAllocationResult.InvalidArea;
		Building bld = areas.get(areaName).getBuilding(buildingName);
		if(bld == null)
			return ClassroomAllocationResult.InvalidBuilding;
		Classroom room = bld.getClassroom(roomName);
		if(room == null)
			return ClassroomAllocationResult.InvalidRoom;
		
		// Validate availability
		if(room.addTimeRange(courseId, range, days))
			return ClassroomAllocationResult.Success;
		else return ClassroomAllocationResult.InUse;
	}
}
