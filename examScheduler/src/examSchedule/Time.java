package examSchedule;

public class Time {

	private String session;
	private Long time;
	
	public Time(String s, Long t) {
		time = t;
		session = s;
	}
	
	public Long getTime() {
		return time;
	}
	
	public void setTime(Long time) {
		this.time = time;
	}
	
	public String getSession() {
		return session;
	}
	
	public void setSession(String session) {
		this.session = session;
	}
	
	@Override
	public String toString() {
		return "time(" + session + ", " + time + ")";
	}
}
