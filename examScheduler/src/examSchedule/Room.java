package examSchedule;

public class Room {
	private String room;
	private int capacity;
	
	public Room(String r){
		room = r;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}
