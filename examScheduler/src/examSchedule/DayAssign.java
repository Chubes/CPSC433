package examSchedule;

public class DayAssign {
	
	private String day;
	private String session;
	
	public DayAssign(String s, String d) {
		session = s;
		day = d;
	}
	
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}
	
	@Override
	public String toString() {
		return "dayAssign(" + session + ", " + day + ")";
	}
}
