package oop;
import mapper.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.InputStream;
import org.apache.ibatis.io.*;
import org.apache.ibatis.session.*;

public class test {
	
	@Test
	public void AddRegistration() throws Exception {
		ResourceController rc = ResourceController.getInstance();
		for(Area area : rc.areas.values()) {
			System.out.println(area.name);
			for(Building bld : area.buildings.values()) {
				System.out.println(bld.name);
				for(Classroom room : bld.classrooms.values()) 
					System.out.println(room.name);
			}
		}
	}
}
