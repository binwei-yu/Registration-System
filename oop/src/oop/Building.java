package oop;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.lang.Math;
import mapper.Mapper;

public class Building {
	final Integer id;
	String name;
	HashMap<String, Classroom> classrooms;
	
	// Constructor
	public Building(String name) {
		Random rand = new Random();
		this.id = Math.abs(rand.nextInt());
		
		this.name = name;
		this.classrooms = new HashMap<String, Classroom>();
	}
	
	// Add classroom with specified name
	public boolean addClassroom(String roomName) {
		if(classrooms.containsKey(roomName)) return false;
		else classrooms.put(roomName, new Classroom(roomName));
		return true;
	}
	
	// Delete classroom by name
	public void deleteClassroom(String roomName) {
		if(classrooms.containsKey(roomName)) {
			classrooms.remove(roomName);
		}
	}
	
	// Find classroom
	public boolean containsClassroom(String roomName) {
		return classrooms.containsKey(name);
	}
	
	// Get classroom
	public Classroom getClassroom(String roomName) {
		if(classrooms.containsKey(roomName))
			return classrooms.get(roomName);
		return null;
	}
	
	// Load classrooms
	public void loadClassrooms() {
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				ArrayList<Classroom> res = mapper.getClassrooms(id);
				for(Classroom room : res) {
					classrooms.put(room.name, room);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
