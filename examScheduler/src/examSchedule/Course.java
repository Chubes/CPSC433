package examSchedule;

import java.util.LinkedList;

public class Course {
	public String name;
	public LinkedList<Lecture> lecture;
	
	public Course(String c){
		name = c;
		lecture = new LinkedList<Lecture>();
	}

}
