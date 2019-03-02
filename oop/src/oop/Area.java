package oop;
import java.util.ArrayList;
import java.util.HashMap;
import mapper.Mapper;

public class Area {
	final Integer id;
	String name;
	HashMap<String, Building> buildings;
	
	// Constructor
	public Area(Integer id, String name) {
		this.id = id;
		this.name = name;
		this.buildings = new HashMap<String, Building>();
	}

	// Add building with specified name
	public boolean addBuilding(String buildingName) {
		if(buildings.containsKey(buildingName)) return false;
		else buildings.put(buildingName, new Building(buildingName));
		return true;
	}
	
	// Delete building by name
	public void deleteBuilding(String buildingName) {
		if(buildings.containsKey(buildingName)) {
			buildings.remove(buildingName);
		}
	}
	
	// Find building
	public boolean containsBuilding(String buildingName) {
		return buildings.containsKey(name);
	}
	
	// Get building
	public Building getBuilding(String buildingName) {
		if(buildings.containsKey(buildingName))
			return buildings.get(buildingName);
		return null;
	}
	
	public void loadBuildings() {
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				ArrayList<Building> res = mapper.getBuildings(id);
				for(Building b : res) {
					buildings.put(b.name, b);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		// Load classrooms for each building
		for(Building bld : buildings.values()) {
			bld.loadClassrooms();
		}
	}
}
