package examSchedule;

public class ExamLength {
	private Course course = new Course();
	private Lecture lecture = new Lecture();
	private int time = 0;
	
	public ExamLength(Course C, Lecture L, int time) {
		this.time = time;
		
		// If Course does not exist, create.
		// If Lecture does not exist, create.
		this.course = C;
		this.lecture = L;
	}
}
