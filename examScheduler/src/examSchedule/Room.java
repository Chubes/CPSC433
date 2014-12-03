package examSchedule;

public class Room {
	public String room;
	public long capacity;
	public long remainCap;
	
	public Room(String r){
		room = r;
		capacity = 0;
		remainCap = capacity;
	}

}
