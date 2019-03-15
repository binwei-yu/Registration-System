
package oop;
import java.util.ArrayList;

import mapper.Mapper;

public class Faculty {
	String id;
	String name;
	String division;
	protected String password;
	static Logger log = new Logger();
	
	static Faculty getValidated(String username, String password) {
		return log.validateFaculty(username, password);
	};

	public Faculty(String name, String division, String password) {
		// Initialization
		this.id = FacultyIDFactory.getInstance().createId(null);
		this.name = name;
		this.division = division;
		this.password = password;
		
		// TODO Auto-generated constructor stub
	}
	
	public void processRegistration(String courseId, RegistrationState state) {
		ArrayList<Registration> regs = new ArrayList<Registration>();
		
		// Fetch data
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				ArrayList<Registration> tmp = mapper.getCourseReg(courseId);
				for(Registration item : tmp)
					regs.add(item);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		// Processing
		for(Registration item : regs) {
			if(state == RegistrationState.Approved || state == RegistrationState.Declined) {
				item.state = state.getValue();
				RegistrationMediator.feedback(item);
			}
		}
	}


	// Reserve (Template Pattern)
	/*
	public final void reserve(TimePeriod time, WeekDay days[], Area area, Building building, 
			Classroom room, FacilityType[] facilities, String id, String name, String phone, 
			String reason, boolean signature) {
		Reservation rsv = getReservation(time, days, area, building, room, facilities);
		Application app = getApplication(id, name, phone, reason, signature);
		sendReservation(rsv);
		sendApplication(app);
	}
	
	abstract Reservation getReservation(TimePeriod time, WeekDay days[], Area area, Building building, 
			Classroom room, FacilityType[] facilities);
	
	abstract Application getApplication(String id, String name, String phone, String reason, 
			boolean signature);
	
	abstract void sendReservation(Reservation rsv);
	
	abstract void sendApplication(Application app);
	*/
}
