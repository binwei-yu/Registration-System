package oop;

import mapper.Mapper;

public class Logger {
	
	public Student validateStudent(String id, String password) {
		final Student res = new Student("", "", "");
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				// Get password
				final Student s = mapper.validateStudent(id, password);
				if(s == null) 
					System.out.println("[i] incorrect id or password");
				else {
					System.out.println("[i] welcome, " + s.name);
					res.id = id;
					res.name = s.name;
					res.division = s.division;
					res.password = password;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		if(res.name == "") return null;
		else return res;
	}
	

	public Faculty validateFaculty(String id, String password) {
		final Faculty res = new Faculty("", "", "");
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				// Get password
				final Faculty f = mapper.validateFaculty(id, password);
				if(f == null) 
					System.out.println("[i] incorrect id or password");
				else {
					System.out.println("[i] welcome, " + f.name);
					res.id = id;
					res.name = f.name;
					res.division = f.division;
					res.password = password;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		if(res.name == "") return null;
		else return res;
	}
}
