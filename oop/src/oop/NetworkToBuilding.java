package oop;

public class NetworkToBuilding {
	public void SendRequest(Integer classroomId, TimeRange time) {
		// Connect to server
		// Send request
		// TODO
		System.out.println("Classroom: " + classroomId.toString() + 
				"\nTime Period: " + time.toString() + "\n");
	}
	
	public boolean receiveResponse(boolean approve) {
		return approve;
	}
}
