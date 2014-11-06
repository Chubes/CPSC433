package examSchedule;
	// I am not sure about the following requirement for "Enrolled" - Landon.
	// "student-name, vector-of-course-names-and-lecture-names"

public class Enrolled {
	
	private String student;
	private String course;
	private String lecture;
		
	public Enrolled(String s, String c, String l){
		student= s;
		course = c;
		lecture = l;
	}

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}
	
	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
	
	@Override
	public String toString() {
		return "enrolled(" + student + ", " + course + ", " + lecture + ")";
	}
}
