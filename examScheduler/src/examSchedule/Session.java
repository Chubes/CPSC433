package examSchedule;

import java.util.LinkedList;

import examSchedule.parser.Pair;

public class Session {
	public String name;
	public String day;
	public long time;
	public long sessionLength;
	public Room room;
//	public LinkedList<Lecture> lectures;
	public LinkedList<Pair<Course,Lecture>> assignment;
	
	public Session(String n){
		name = n;
//		lectures = new LinkedList<Lecture>();
		assignment = new LinkedList<Pair<Course,Lecture>>();
		day = "";
		time = new Long(0);
		sessionLength = new Long(0);
		room = new Room("");
			
	}

}
