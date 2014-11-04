package examSchedule;
/*Landon, we can create objects if they do not exist in the environment class, and add them to the lists in there. I also don't think that we need to make the 
 * Course and Lecture Objects, as they  can just be strings, we can still compare the fields
 * --Mike
 */
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
