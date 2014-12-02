package examSchedule;

public class Lecture {

	public String name;
	public Course course;
	public Instructor instructor;
	public Long examLength;
	
	public Lecture(String l, String c){
		name = l;
		course = c
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result
				+ ((examLength == null) ? 0 : examLength.hashCode());
		result = prime * result
				+ ((instructor == null) ? 0 : instructor.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (examLength == null) {
			if (other.examLength != null)
				return false;
		} else if (!examLength.equals(other.examLength))
			return false;
		if (instructor == null) {
			if (other.instructor != null)
				return false;
		} else if (!instructor.equals(other.instructor))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
