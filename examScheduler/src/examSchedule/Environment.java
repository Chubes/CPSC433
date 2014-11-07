package examSchedule;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.text.html.HTMLDocument.Iterator;

import examSchedule.parser.*;
import examSchedule.parser.Predicate.ParamType;

public class Environment extends PredicateReader implements ExamSchedulePredicates, EnvironmentInterface {
	ArrayList<Student> students = new ArrayList<Student>();
	ArrayList<Instructor> instructors = new ArrayList<Instructor>();
	ArrayList<Room> rooms = new ArrayList<Room>();
	ArrayList<Course> courses = new ArrayList<Course>();
	ArrayList<Session> sessions = new ArrayList<Session>();
	ArrayList<Day> days = new ArrayList<Day>();
	ArrayList<Lecture> lectures = new ArrayList<Lecture>();
	ArrayList<Instructs> instructs = new ArrayList<Instructs>();
	ArrayList<Capacity> capacities = new ArrayList<Capacity>();
	ArrayList<ExamLength> exams = new ArrayList<ExamLength>();
	
	ArrayList<RoomAssign> roomAssigns = new ArrayList<RoomAssign>();
	ArrayList<DayAssign> dayAssigns = new ArrayList<DayAssign>();
	ArrayList<Time> times = new ArrayList<Time>();
	ArrayList<Length> lengths = new ArrayList<Length>();
	ArrayList<At> atList = new ArrayList<At>();
	ArrayList<Enrolled> enrollments = new ArrayList<Enrolled>();
	ArrayList<Assign> assigns = new ArrayList<Assign>();
	
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
		if(e_session(p)){
			Session n = new Session(p); 
			sessions.add(n);
		}
	}

	@Override
	public boolean e_session(String p) {
		Session n = new Session(p); 
		if(sessions.contains(n)){
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public void a_day(String p) {
		if(e_day(p)){
			Day n = new Day(p); 
			days.add(n);
		}
	}
	

	@Override
	public boolean e_day(String p) {
		Day n = new Day(p); 
		if(days.contains(n)){
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public void a_lecture(String c, String lec) {
		if(e_lecture(c, lec)){
			if(e_course(c)){
				a_course(c);
			}
			Lecture n = new Lecture(c, lec);
			lectures.add(n);
		}
		
		
	}

	@Override
	public boolean e_lecture(String c, String lec) {
		Lecture n = new Lecture(c, lec); 
		if(lectures.contains(n)){
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public void a_lecture(String c, String lec, String instructor, Long length) {
		if(e_lecture(c, lec, instructor, length)){
			if(e_course(c)){
				a_course(c);
			}
			if(e_instructor(instructor)){
				a_instructor(instructor);
			}
			Lecture n = new Lecture(c, lec, instructor, length);
			lectures.add(n);
		}
		
		
	}

	public boolean e_lecture(String c, String lec, String instructor, Long length){
		Lecture n = new Lecture(c, lec, instructor, length); 
		if(lectures.contains(n)){
			return false;
		}
		else{
			return true;
		}
	}
	@Override
	public void a_instructs(String p, String c, String l) {
		if(e_instructs(p, c, l)){
			if(e_course(c)){
				a_course(c);
			}
			if(e_instructor(p)){
				a_instructor(p);
			}
			if(e_lecture(c, l)){
				a_lecture(c, l);
			}
			Instructs n = new Instructs(p, c, l);
			instructs.add(n);
		}
		
	}

	@Override
	public boolean e_instructs(String p, String c, String l) {
		Instructs n = new Instructs(p, c, l); 
		if(instructs.contains(n)){
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public void a_examLength(String c, String lec, Long hours) {
		if(e_examLength(c, lec, hours)) {
			if(e_course(c)) {
				a_course(c);
			}
			if(e_lecture(c, lec)){
				a_lecture(c, lec);
			}
			ExamLength n = new ExamLength(c, lec, hours);
			exams.add(n);
		}
	}

	@Override
	public boolean e_examLength(String c, String lec, Long hours) {
		ExamLength n = new ExamLength(c, lec, hours); 
		if(exams.contains(n)) {
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public void a_roomAssign(String p, String room) {
		if(e_roomAssign(p, room)) {
			if(e_session(p)) {
				a_session(p);
			}
			if(e_room(room)){
				a_room(room);
			}
			RoomAssign n = new RoomAssign(p, room);
			roomAssigns.add(n);
		}
		
	}

	@Override
	public boolean e_roomAssign(String p, String room) {
		RoomAssign n = new RoomAssign(p, room); 
		if(roomAssigns.contains(n)) {
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public void a_dayAssign(String p, String day) {
		if(e_dayAssign(p, day)) {
			if(e_session(p)) {
				a_session(p);
			}
			if(e_day(day)) {
				a_day(day);
			}
			DayAssign n = new DayAssign(p, day);
			dayAssigns.add(n);
		}
	}

	@Override
	public boolean e_dayAssign(String p, String day) {
		DayAssign n = new DayAssign(p, day);
		if(dayAssigns.contains(n)) {
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public void a_time(String p, Long time) {
		if(e_time(p, time)) {
			if(e_session(p)) {
				a_session(p);
			}
			Time n = new Time(p, time);
			times.add(n);
		}
	}

	@Override
	public boolean e_time(String p, Long time) {
		Time n = new Time(p, time);
		if(times.contains(n)) {
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public void a_length(String p, Long length) {
		if(e_length(p, length)) {
			if(e_session(p)) {
				a_session(p);
			}
			Length n = new Length(p, length);
			lengths.add(n);
		}
	}

	@Override
	public boolean e_length(String p, Long length) {
		Length n = new Length(p, length);
		if(lengths.contains(n)) {
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public void a_at(String session, String day, Long time, Long length) {
		if(e_at(session, day, time, length)) {
			if(e_session(session)) {
				a_session(session);
			}
			if(e_day(day)) {
				a_day(day);
			}
			At n = new At(session, day, time, length);
			atList.add(n);
		}
	}

	@Override
	public boolean e_at(String session, String day, Long time, Long length) {
		At n = new At(session, day, time, length);
		if(atList.contains(n)) {
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public void a_session(String session, String room, String day, Long time, Long length) {
		if(e_session(session, room, day, time, length)){
			if(e_room(room)){
				a_room(room);
			}
			if(e_day(day)){
				a_day(day);
			}
			Session n = new Session(session, room, day, time, length); 
			sessions.add(n);
		}
	}
	

	@Override
	public boolean e_session(String session, String room, String day, Long time, Long length) {
			Session n = new Session(session, room, day, time, length); 
			if(lectures.contains(n)){
				return false;
			}
			else{
				return true;
		}
	}
	

	@Override
	public void a_enrolled(String student, String c, String l) {
		if(e_enrolled(student, c, l)){
// Removed due to duplicate check in e_lecture -- Landon
			/*if(e_course(c)) {
				a_course(c);
			} */
			if(e_lecture(c, l)) {
				a_lecture(c, l);
			}
			if(e_student(student)) {
				a_student(student);
			}
			
			Enrolled n = new Enrolled(student, c, l); 
			enrollments.add(n);
		}
	}

	@Override
	public boolean e_enrolled(String student, String c, String l) {
		Enrolled n = new Enrolled(student, c, l); 
		if(enrollments.contains(n)){
			return false;
		}
		else{
			return true;
		}
	}
//This section is incomplete. -- Landon
//trying to figure out how this works --Mike
	@Override
	public void a_enrolled(String student, Vector<Pair<ParamType, Object>> list) {
		/*java.util.Iterator<Pair<ParamType, Object>> itr = list.iterator();
		while(itr.hasNext()){
			itr.next().			
			if(e_enrolled(student, itr.next()., itr.next().getValue())){
				// Removed due to duplicate check in e_lecture -- Landon
							/*if(e_course(c)) {
								a_course(c);
							} 
							if(e_lecture(c, l)) {
								a_lecture(c, l);
							}
							if(e_student(student)) {
								a_student(student);
							}
							
							Enrolled n = new Enrolled(student, c, l); 
							enrollments.add(n);
						}
		}*/
		
		
	}

	@Override
	public String toString() {
		String instr ="";
		instr += "//Instructors\n";
		for(int i = 0; i<instructors.size(); i++){
			instr += instructors.get(i).toString() + "\n";	
		}
		String std = "\n//Students\n";
		for(int i = 0; i<students.size(); i++){
			std += students.get(i).toString() + "\n";	
		}
		String rms = "\n//Rooms\n";
		for(int i = 0; i<rooms.size(); i++){
			rms += rooms.get(i).toString() + "\n";	
		}
		String crs = "\n//Courses\n";
		for(int i = 0; i<courses.size(); i++){
			crs += courses.get(i).toString() + "\n";	
		}
		String ds = "\n//Days\n";
		for(int i = 0; i<days.size(); i++){
			ds += days.get(i).toString() + "\n";	
		}
		String lecs = "\n//Lectures\n";
		for(int i = 0; i < lectures.size(); i++){
			lecs += lectures.get(i).toString() + "\n";	
		}
		String caps = "\n//Capacities\n";
		for(int i = 0;i < capacities.size(); i++){
			caps += capacities.get(i).toString() + "\n";	
		}
		String sess = "\n//Sessions\n";
		for(int i = 0;i <  sessions.size(); i++){
			sess += sessions.get(i).toString() + "\n";	
		}
		String instrc = "\n//Instructs\n";
		for(int i = 0;i <  instructs.size(); i++){
			instrc += instructs.get(i).toString() + "\n";	
		}
		String exms = "\n//Exams\n";
		for(int i = 0;i <  exams.size(); i++){
			exms += exams.get(i).toString() + "\n";	
		}
		String rmas = "\n//Room Assigns\n";
		for(int i = 0;i <  roomAssigns.size(); i++){
			rmas += roomAssigns.get(i).toString() + "\n";	
		}
		String dass = "\n//Day Assigns\n";
		for(int i = 0;i <  dayAssigns.size(); i++){
			dass += dayAssigns.get(i).toString() + "\n";	
		}
		String t = "\n//Times\n";
		for(int i = 0;i <  times.size(); i++){
			t += times.get(i).toString() + "\n";	
		}
		String lens = "\n//Lengths\n";
		for(int i = 0;i <  lengths.size(); i++){
			lens += lengths.get(i).toString() + "\n";	
		}
		String ats = "\n//At\n";
		for(int i = 0;i <  atList.size(); i++){
			ats += atList.get(i).toString() + "\n";	
		}
		String enrls = "\n//Enrolments\n";
		for(int i = 0;i <  enrollments.size(); i++){
			enrls += enrollments.get(i).toString() + "\n";	
		}
		String assns = "\n//Assigns\n";
		for(int i = 0;i <  assigns.size(); i++){
			assns += assigns.get(i).toString() + "\n";	
		}
		return instr + std +  rms + crs +  ds + lecs + caps + sess + instrc + exms + rmas + dass + t + lens + ats + enrls + assns ;
	}

	@Override
	public void a_capacity(String r, Long cap) {
		if(e_capacity(r, cap)){
			if(e_room(r)) {
				a_room(r);
			}
			
			Capacity n = new Capacity(r, cap); 
			capacities.add(n);
		}
		
	}

	@Override
	public boolean e_capacity(String r, Long cap) {
		Capacity n = new Capacity(r, cap); 
		if(capacities.contains(n)){
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public void a_assign(String c, String lec, String session) {
		if(e_assign(c, lec, session)){
// Removed due to duplicate check in e_lecture -- Landon
			/*if(e_course(c)) {
				a_course(c);
			} */
			if(e_lecture(c, lec)) {
				a_lecture(c, lec);
			}
			if(e_session(session)) {
				a_session(session);
			}
			
			Assign n = new Assign(c, lec, session); 
			assigns.add(n);
		}
	}

	@Override
	public boolean e_assign(String c, String lec, String session) {
		Assign n = new Assign(c, lec, session); 
		if(assigns.contains(n)){
			return false;
		}
		else{
			return true;
		}
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
