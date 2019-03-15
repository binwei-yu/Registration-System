package oop;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;
import mapper.Mapper;

public class ResourceController {
	public static ResourceController rc = null;
	private HashMap<String, Area> areas;
	private HashMap<String, Faculty> faculties;
	private HashMap<String, Student> students;
 	
	// Singleton
	public static ResourceController getInstance() {
		if(rc == null) rc = new ResourceController();
		return rc;
	}
	
	// Constructor
	private ResourceController() {
		areas = new HashMap<String, Area>();
		faculties = new HashMap<String, Faculty>();
		students = new HashMap<String, Student>();
		
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				// Fetch area data
				
				ArrayList<Area> res = mapper.getAreas();
				for(Area area : res) areas.put(area.name, area);

				// Fetch faculties data
				ArrayList<Faculty> res2 = mapper.getFaculties();
				for(Faculty f : res2) faculties.put(f.id, f);

				
				// Fetch students data
				ArrayList<Student> res3 = mapper.getStudents();
				for(Student s : res3) students.put(s.id, s);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		// Fetch resources
		for(Area area : areas.values()) {
			area.loadBuildings();
		}
	}
	
	// Return available resource
	public final HashMap<String, ArrayList<ArrayList<TimePeriod>>> getResources() {
		HashMap<String, ArrayList<ArrayList<TimePeriod>>> res = 
				new HashMap<String, ArrayList<ArrayList<TimePeriod>>>();
		for(Area area : areas.values()) {
			for(Building bld : area.buildings.values()) {
				for(Classroom room : bld.classrooms.values()) {
					
					// Create resource list for each location
					String location = area.name + ", " + bld.name + ", " + room.name;
					ArrayList<ArrayList<TimePeriod>> schedule = new ArrayList<ArrayList<TimePeriod>>(7);
					for(int i = 0; i < 7; i++)
						schedule.add(new ArrayList<TimePeriod>());
					
					for(int i = 0; i < 7; i++) {
						int end = 6;
						for(Pair<TimePeriod, String> tr : room.schedule.get(i)) {
							if(tr.getKey().startHour > end+1) {
								schedule.get(i).add(new TimePeriod(end, tr.getKey().startHour));
							}
							end = tr.getKey().endHour;
						}
						if(end < 22)
							schedule.get(i).add(new TimePeriod(end, 22));
					}
					
					res.put(location, schedule);
				}
			}
		}
		return res;
	}
	
	// Allocate
	public AllocationResult allocateClassroom(String courseId, String areaName, String buildingName, 
			String roomName, TimePeriod period, WeekDay[] days) {
		
		// Time before 6 AM and after 10 PM is unavailable
		if(period.startHour < 6 || period.endHour > 22) 
			return AllocationResult.InvalidTime;
		
		// Validate address
		if(!areas.containsKey(areaName))
			return AllocationResult.InvalidArea;
		Building bld = areas.get(areaName).getBuilding(buildingName);
		if(bld == null)
			return AllocationResult.InvalidBuilding;
		Classroom room = bld.getClassroom(roomName);
		if(room == null)
			return AllocationResult.InvalidRoom;
		
		// Validate availability
		if(room.addTimeRange(courseId, period, days))
			return AllocationResult.Success;
		else return AllocationResult.InUse;
	}
	
	// Student management
	public void viewStudent(String studentId) {
		if(students.containsKey(studentId)) {
			final Student s = students.get(studentId);
			System.out.println("[id] " + s.id + " [name] " + s.name);
		}
		else System.out.println("[i] no data");
	}
	
	public void addStudent(String name, String division, String password) {
		Student s = new Student(name, division, password);
		DatabaseController.getInstance().query((Mapper mapper) -> {

			try {
				
				// Atomic
				mapper.addStudent(s.id, name, division, password);
				students.put(s.id, s);
				System.out.println("[i] successfully add student " + name);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		});
		
	}
	
	public void deleteStudent(String id) {

		DatabaseController.getInstance().query((Mapper mapper) -> {

			try {

				// Atomic
				mapper.deleteStudent(id);
				System.out.println("[i] successfully delete " + students.get(id).name);
				students.remove(id);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		});
		
	}

	// Faculty management
	public void viewFaculty(String facultyId) {
		if(faculties.containsKey(facultyId)) {
			final Faculty f = faculties.get(facultyId);
			System.out.println("[id] " + f.id + " [name] " + f.name);
		}
		else System.out.println("[i] no data");
	}
	
	public void addFaculty(String name, String division, String password) {
		
		Faculty f = new Faculty(name, division, password);
		DatabaseController.getInstance().query((Mapper mapper) -> {

			try {
				
				// Atomic
				mapper.addStudent(f.id, name, division, password);
				faculties.put(f.id, f);
				System.out.println("[i] successfully add faculty " + name);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		});
		
	}
	
	public void deleteFaculty(String id) {
		
		DatabaseController.getInstance().query((Mapper mapper) -> {

			try {

				// Atomic
				mapper.deleteFaculty(id);
				faculties.remove(id);
				System.out.println("[i] successfully delete faculty " + faculties.get(id).name);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		});
		
	}
	
	// Process reservation
	public AllocationResult acceptReservation(Application ap, Reservation rs) {
		return this.allocateClassroom(ap.reason, rs.area, rs.building, rs.room, rs.time, rs.days);
	}
	
	// View resource -- Debug
	public void viewResources() {
		for(Area area : areas.values()) {
			System.out.println("---" + area.name + "---");
			for(Building bld : area.buildings.values()) {
				System.out.print(bld.name + ": ");
				for(Classroom room : bld.classrooms.values()) 
					System.out.print("[" + room.name + "] ");
				System.out.print("\n");
			}
			System.out.print("\n");
		}
	}
	
	// View available resources -- Debug
	public void viewAvailableResources(String location) {
		HashMap<String, ArrayList<ArrayList<TimePeriod>>> res = getResources();
		for(String key : res.keySet()) {
			if(key.equals(location)) {
				System.out.println(key);
				for(int i = 0; i < 7; i++) {
					System.out.print("[" + WeekDay.getStrValue(i) + "] ");
					for(TimePeriod tp : res.get(location).get(i))
						System.out.print(tp.toString() + " ");
					System.out.print("\n");
				}
				System.out.print("\n");
				break;
			}
		}
	}
	
	// Get student id -- Debug
	public String getStudentId(String name) {
		for(String id : students.keySet()) {
			if(students.get(id).name.equals(name))
				return id;
		}
		return null;
	}
	
	// Get faculty id -- Debug
	public String getFacultyId(String name) {
		for(String id : faculties.keySet()) {
			if(faculties.get(id).name.equals(name))
				return id;
		}
		return null;
	}
	
	// View students -- Debug
	public void viewStudents() {
		if(students.isEmpty()) return;
		for(String id : students.keySet())
			this.viewStudent(id);
		System.out.println("\n");
	}
	

	// View faculties -- Debug
	public void viewFaculties() {
		if(faculties.isEmpty()) return;
		for(String id : faculties.keySet())
			this.viewFaculty(id);
		System.out.println("\n");
	}
	
}
