package examSchedule;

public class RoomAssign {

	private String session;
	private String room;
	
	public RoomAssign(String s, String r) {
		session = s;
		room = r;
	}
	
	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	
	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}
	
	@Override
	public String toString() {
		return "roomAssign(" + session + ", " + room  + ")";
	}
}
