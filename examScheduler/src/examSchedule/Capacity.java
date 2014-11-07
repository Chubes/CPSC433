package examSchedule;

public class Capacity {
	private String room;
	private Long capacity;
	
	public Capacity(String r, Long c){
		room = r;
		capacity = c;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Long getCapacity() {
		return capacity;
	}

	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "capacity(" + room + ", " + capacity + ")";
	}
}
