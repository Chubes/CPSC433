package examSchedule;

public class Session {
	private String name;
	private String room;
	private String day;
	private Long time;
	private Long length;
	
	public Session(String n){
		name = n;
	}
	
	public Session(String n, String r, String d, Long t, Long len){
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

	public Long getTime() {
		return time;
	}

	@Override
	public String toString() {
		if(room == null && length == null && day == null && time == null){
			return "Session(" + name +")";
		}
		return "Session(" + name + ", " + room + ", " + day
				+ ", " + time + ", " + length + ")";
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}
}
