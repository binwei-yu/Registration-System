package oop;

import mapper.Mapper;

public class RegistrationMediator {
	public static void dispatch(Registration reg) {

		// Update
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				mapper.addReg(reg.courseId, reg.courseName, reg.studentId, reg.studentName, reg.section, reg.state);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		System.out.println("[i] successfully dispatch registration");
	}
	
	public static AllocationResult dispatchRev(Application ap, Reservation rs) {
		return ResourceController.getInstance().acceptReservation(ap, rs);
	}
	
	public static void feedback(Registration reg) {

		// Update
		DatabaseController dc = DatabaseController.getInstance();
		dc.query((Mapper mapper) -> {
			try {
				mapper.updateReg(reg.courseId, reg.studentId, reg.state);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		// Inform student
		String strState = "";
		if(reg.state == 0) strState = "approved";
		else if(reg.state == 1) strState = "declined";
		System.out.println("[i] your registration for " + reg.courseName + " is " + strState + ", " + reg.studentName);
		
		// Update Class Controller
		if(reg.state == 0)
			ClassController.getInstance().addClass(reg.courseId, reg.courseName, reg.studentId, reg.studentName, reg.section);
		
	}
}
