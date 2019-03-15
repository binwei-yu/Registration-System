package oop;
import java.util.HashMap;
import mapper.Mapper;

import java.util.ArrayList;

public class CourseProxy {
	private static CourseProxy cp = null;
	HashMap<String, Course> courseRepo;
	
	public static CourseProxy getInstance() {
		if(cp == null) cp = new CourseProxy();
		return cp;
	}
	
	private CourseProxy() {
		courseRepo = new HashMap<String, Course>();
		
		// Fetch data
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				
				ArrayList<Course> res = mapper.getCourse();
				for(Course item : res) {
					courseRepo.put(item.id, item);
					ArrayList<Section> secs = mapper.getSection(item.id);
					for(Section elem : secs) {
						item.addSection(elem);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		// Update resource controller
		for(Course item : courseRepo.values()) {
			for(Section elem : item.sections) {
				ResourceController.getInstance().allocateClassroom(item.id, elem.location[0], 
						elem.location[1], elem.location[2], elem.period, elem.getDays());
			}
		}
	}
	
	// Get course info
	public final Course getCourse(String id) {
		if(courseRepo.containsKey(id)) return courseRepo.get(id);
		else return null;
	}
	
	// Search courses
	public final Course[] searchCourse(HashMap<String, String> conditions) {
		ArrayList<Course> res = new ArrayList<Course>();
		for(Course course : courseRepo.values()) {
			// Filter the result
			boolean isQualified = true;
			for(String key : conditions.keySet()) {
				if(key == "instructor" && !course.instructorName.equals(conditions.get(key))) 
					isQualified = false;
				else if(key == "division" && !course.division.equals(conditions.get(key)))
					isQualified = false;
			}
			if(isQualified)
				res.add(course);
		}
		Course[] tmp = new Course[res.size()];
		return res.toArray(tmp);
	}
	
	public Course createCourse(String name, String division, int state, String instructorId, String instructorName, int sectionSize) {
		CourseIDFactory cif = CourseIDFactory.getInstance();
		String[] info = { division };
		String id = cif.createId(info);
		return new Course(id, name, division, state, instructorId, instructorName, sectionSize);
	}
	
	// Add course
	public void addCourse(final Course course) {
		// Validate basic information
		if(course.sections.size() == 0) {
			System.out.println("[e] invalid course");
			return;
		}
		
		// Validate resource allocation
		for(Section item : course.sections) {
			// Need to reallocate resource
			if(item.location.length < 3) {
				System.out.println("[i] pick up a time for section: ");
				// Get available resource
				ResourceController rc = ResourceController.getInstance();
				HashMap<String, ArrayList<ArrayList<TimePeriod>>> res = rc.getResources();
				for(String location : res.keySet()) {
					System.out.print(location + ": ");
					for(int i = 0; i < 7; i++) {
						System.out.print("[" + WeekDay.getStrValue(i) + "] ");
						for(TimePeriod tp : res.get(location).get(i))
							System.out.print(tp.toString() + " ");
					}
					System.out.print("\n");
				}
				System.out.print("\n");
				
				return;
			}
		}
		
		
		// Push to database
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				
				mapper.addCourse(course.id, course.name, course.state, course.division, 
						course.instructorId, course.instructorName);
				for(Section item : course.sections) {
					String location = item.location[0] + " " + item.location[1] + " " + item.location[2];
					Integer days = 0;
					for(WeekDay day : item.getDays()) {
						days |= (1 << day.getValue());
					}
					mapper.addSection(location, item.period.startHour, item.period.endHour,
							days, item.lab, course.id);
				}
				
				// Add course to repository
				courseRepo.put(course.id, course);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		// Debug statement
		System.out.println("[i] successfully add course " + course.name + "\n");
	}
	
	// Delete course
	public void deleteCourse(String id) {
		
		// Push to database
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {

				mapper.deleteSection(id);
				mapper.deleteCourse(id);

				// Delete course from repository
				courseRepo.remove(id);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		// Debug statement
		System.out.println("[i] successfully delete course " + id + "\n");
	}
	
	// View courses -- Debug
	public void viewCourses() {
		for(Course item : courseRepo.values()) {
			System.out.println("------ ------ ------");
			System.out.println("ID: " + item.id);
			System.out.println("Course: " + item.name);
			System.out.println("Instructor: " + item.instructorName);
			System.out.println("Section: ");
			for(Section elem : item.sections) {
				String str = elem.location[0] + ", " + elem.location[1] 
						+ ", " + elem.location[2] + " ";
				for(WeekDay day : elem.getDays()) {
					str += day.toString() + " ";
				}
				str += elem.period.toString();
				System.out.println(str);
			}
		}
		System.out.println("\n");
	}
	
	// Get random course -- Debug
	public String getCourseID(String name) {
		for(String key : courseRepo.keySet()) {
			if(courseRepo.get(key).name == name)
				return key;
		}
		return null;
	}
}
