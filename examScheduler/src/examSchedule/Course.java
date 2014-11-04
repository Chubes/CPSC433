package examSchedule;

public class Course {
	private String course;
	
	public Course(String c){
		course = c;
	}

	@Override
	public String toString() {
		return "Course (" + course + ")";
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
}
