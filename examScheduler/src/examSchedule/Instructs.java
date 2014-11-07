package examSchedule;

public class Instructs {

	private String lecture;
	private String course;
	private String instructor;
		
	public Instructs(String i, String c, String l){
		lecture = l;
		course = c;
		instructor = i;
	}
	
	@Override
	public String toString() {
		return "instructs(" + instructor + ", " + course + ", " + lecture +  ")";
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
}
