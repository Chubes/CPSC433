package examSchedule;

import java.util.LinkedList;

import examSchedule.parser.Pair;

public class Student {
	public String name;
	public LinkedList<Pair<Course,Lecture>> enrolled;
		
	public Student(String s){
		name = s;
		enrolled = new LinkedList<Pair<Course,Lecture>>();
	}

}
