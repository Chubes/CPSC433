package examSchedule;

public class Length {

	private String session;
	private Long length;
	
	public Length(String s, Long len) {
		length = len;
		session = s;
	}
	
	public Long getLength() {
		return length;
	}
	
	public void setLength(Long length) {
		this.length = length;
	}
	
	public String getSession() {
		return session;
	}
	
	public void setSession(String session) {
		this.session = session;
	}
	
	@Override
	public String toString() {
		return "length(" + session + ", " + length + ")";
	}
}
