package examSchedule;

public class Room {
	private String room;
	
	public Room(String r){
		room = r;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return "room(" + room + ")";
	}
}
