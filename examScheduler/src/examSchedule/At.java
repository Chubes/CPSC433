package examSchedule;

public class At {
	private String session;
	private String day;
	private Long time;
	private Long length;
	
	public At(String s, String d, Long t, Long len) {
		time = t;
		length = len;
		day = d;
		session = s;
	}
	
	public String getName() {
		return session;
	}

	public void setName(String session) {
		this.session = session;
	}
	
	public Long getTime() {
		return time;
	}
	
	public void setTime(Long time) {
		this.time = time;
	}
	
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}
	
	@Override
	public String toString() {
		return "at(" + session + ", " + day + ", " + time + ", " + length + ")";
	}
}
