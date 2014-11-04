package examSchedule;

public class Session {
	private String name;
	private String room;
	private String day;
	private int time;
	private int length;
	
	public Session(String n){
		name = n;
	}
	
	public Session(String n, String r, String d, int t, int len){
		name = n;
		room = r;
		day = d;
		time = t;
		length = len;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
