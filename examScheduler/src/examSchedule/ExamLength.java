package examSchedule;

public class ExamLength {
	private String course;
	private String lecture;
	private Long time;
	
	public ExamLength(String c, String l, Long t) {
		time = t;
		course = c;
		lecture = l;
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
	
	public Long geTime() {
		return time;
	}
	
	public void setTime(Long time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return "examLength(" + course + ", " + lecture + ", " + time + ")";
	}
	
}
