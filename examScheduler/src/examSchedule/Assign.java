package examSchedule;

public class Assign {
	
	private String course;
	private String lecture;
	private String session;
	
	public Assign(String c, String l, String s) {
		course = c;
		lecture = l;
		session = s;		
	}
	
	public String getCourse() {
		return course;
	}
	
	public void setCourse(String course) {
		this.course = course;
	}
	
	public String getLecture() {
		return lecture;
	}
	
	public void setLecture(String lecture) {
		this.lecture = lecture;
	}
	
	public String getSession() {
		return session;
	}
	
	public void setSession(String session) {
		this.session = session;
	}
	
	@Override
	public String toString() {
		return "assign(" + course + ", " + lecture + ", " + session + ")";
	}
}
 