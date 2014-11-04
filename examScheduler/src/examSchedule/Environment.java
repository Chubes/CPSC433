package examSchedule;
import java.util.ArrayList;
import java.util.Vector;

import examSchedule.parser.*;
import examSchedule.parser.Predicate.ParamType;

public class Environment extends PredicateReader implements ExamSchedulePredicates, EnvironmentInterface {
	ArrayList<Student> students = new ArrayList<Student>();
	ArrayList<Instructor> instructors = new ArrayList<Instructor>();
	ArrayList<Room> rooms = new ArrayList<Room>();
	ArrayList<Course> courses = new ArrayList<Course>();
	
	public Environment(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void a_search(String search, String control, Long maxTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SolutionInterface getCurrentSolution() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentSolution(SolutionInterface currentSolution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void a_student(String p) {
		if(e_student(p)){
		Student n = new Student(p); 
		students.add(n);
		}
		
	}

	@Override
	public boolean e_student(String p) {
		Student n = new Student(p);
		if(students.contains(n)){
			return false;
		}
		else{
		return true;
		}
	}

	@Override
	public void a_instructor(String p) {
		if(e_instructor(p)){
			Instructor n = new Instructor(p); 
			instructors.add(n);
			}
		
	}

	@Override
	public boolean e_instructor(String p) {
		Instructor n = new Instructor(p); 
		if(instructors.contains(n)){
			return false;
		}
		else{
		return true;
		}
	}

	@Override
	public void a_room(String p) {
		if(e_room(p)){
			Room n = new Room(p); 
			rooms.add(n);
			}
	}

	@Override
	public boolean e_room(String p) {
		Room n = new Room(p); 
		if(rooms.contains(n)){
			return false;
		}
		else{
		return true;
		}
	}

	@Override
	public void a_course(String p) {
		if(e_course(p)){
			Course n = new Course(p); 
			courses.add(n);
			}
		
	}

	@Override
	public boolean e_course(String p) {
		Course n = new Course(p); 
		if(courses.contains(n)){
			return false;
		}
		else{
		return true;
		}
	}

	@Override
	public void a_session(String p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_session(String p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_day(String p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_day(String p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_lecture(String c, String lec) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_lecture(String c, String lec) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_lecture(String c, String lec, String instructor, Long length) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void a_instructs(String p, String c, String l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_instructs(String p, String c, String l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_examLength(String c, String lec, Long hours) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_examLength(String c, String lec, Long hours) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_roomAssign(String p, String room) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_roomAssign(String p, String room) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_dayAssign(String p, String day) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_dayAssign(String p, String day) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_time(String p, Long time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_time(String p, Long time) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_length(String p, Long length) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_length(String p, Long length) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_at(String session, String day, Long time, Long length) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_at(String session, String day, Long time, Long length) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_session(String session, String room, String day, Long time,
			Long length) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_session(String session, String room, String day,
			Long time, Long length) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_enrolled(String student, String c, String l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_enrolled(String student, String c, String l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_enrolled(String student, Vector<Pair<ParamType, Object>> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void a_capacity(String r, Long cap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_capacity(String r, Long cap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_assign(String c, String lec, String session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_assign(String c, String lec, String session) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//calls the fromFile() declared in PredicateReader.java
	public int fromFile(String fromFile){
	
		return super.fromFile(fromFile);
	
	}
	
	//getter method used by main() in ExamSchedule.java
	public static EnvironmentInterface get(){
	
		Environment singletonEnv = null;
		
		if(singletonEnv==null){
	
			singletonEnv = new Environment("test");
		}
		
		return singletonEnv; 
	}

}
