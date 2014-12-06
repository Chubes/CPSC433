package examSchedule;

import java.util.LinkedList;

import examSchedule.parser.Pair;

public class Instructor {
	public String name;
	public LinkedList<Pair<Course,Lecture>> instructs;
	
	public Instructor(String i){
		name = i;
		instructs = new LinkedList<Pair<Course,Lecture>>();
	}

}
