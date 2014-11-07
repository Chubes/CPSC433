package examSchedule;

public class Lecture {

	private String lecture;
	private String course;
	private String instructor;
	private Long length;
	
	public Lecture(String c, String l){
		lecture = l;
		course = c;
	}
	
	public Lecture(String c, String l, String i, Long len){
		lecture = l;
		course = c;
		instructor = i;
		length = len;
	}
	
	@Override
	public String toString() {
		if(instructor == null && length == null) {
			return "lecture(" + course + ", " + lecture + ")";
		}
		return "lecture(" + course + ", " + lecture 
				+ ", " + instructor + ", " + length + ")";
	}

	public String getLecture() {
		return lecture;
	}

	public void setLecture(String lecture) {
		this.lecture = lecture;
	}
	
	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
	
	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	
	public Long getLength() {
		return length;
	}
	
	public void setLength(Long length) {
		this.length = length;
	}
}
