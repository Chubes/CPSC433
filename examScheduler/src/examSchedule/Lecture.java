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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result
				+ ((instructor == null) ? 0 : instructor.hashCode());
		result = prime * result + ((lecture == null) ? 0 : lecture.hashCode());
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lecture other = (Lecture) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (instructor == null) {
			if (other.instructor != null)
				return false;
		} else if (!instructor.equals(other.instructor))
			return false;
		if (lecture == null) {
			if (other.lecture != null)
				return false;
		} else if (!lecture.equals(other.lecture))
			return false;
		if (length == null) {
			if (other.length != null)
				return false;
		} else if (!length.equals(other.length))
			return false;
		return true;
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
