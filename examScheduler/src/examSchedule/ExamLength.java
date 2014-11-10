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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((lecture == null) ? 0 : lecture.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		ExamLength other = (ExamLength) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (lecture == null) {
			if (other.lecture != null)
				return false;
		} else if (!lecture.equals(other.lecture))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
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
