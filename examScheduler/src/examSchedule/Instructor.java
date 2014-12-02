package examSchedule;

import java.util.LinkedList;

import examSchedule.parser.Pair;

public class Instructor {
	public String name;
//	public LinkedList<Course> teaches;
//	public LinkedList<Lecture> lectures;
	public LinkedList<Pair<Course,Lecture>> instructs;
	
	public Instructor(String i){
		name = i;
//		teaches = new LinkedList<Course>();
//		lectures = new LinkedList<Lecture>();
		instructs = new LinkedList<Pair<Course,Lecture>>();
	}

}
