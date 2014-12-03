package examSchedule;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.text.html.HTMLDocument.Iterator;

import examSchedule.parser.*;
import examSchedule.parser.Predicate.ParamType;

public class Environment extends PredicateReader implements ExamSchedulePredicates, EnvironmentInterface {

	public ArrayList<Student> students = new ArrayList<Student>();
	public ArrayList<Instructor> instructors = new ArrayList<Instructor>();
	public ArrayList<Room> rooms = new ArrayList<Room>();
	public ArrayList<Course> courses = new ArrayList<Course>();
	public ArrayList<Session> sessions = new ArrayList<Session>();
	public ArrayList<Lecture> lectures = new ArrayList<Lecture>();
	public ArrayList<String> day = new ArrayList<String>();

	public Environment clone(){
		 Environment envClone = new Environment("clone");
		 envClone.students = (ArrayList<Student>)students.clone();
		 envClone.instructors = (ArrayList<Instructor>)instructors.clone();
		 envClone.rooms = (ArrayList<Room>)rooms.clone();
		 envClone.courses = (ArrayList<Course>)courses.clone();
		 envClone.sessions = (ArrayList<Session>)sessions.clone();
		 envClone.lectures = (ArrayList<Lecture>)lectures.clone();
		 return envClone; 
	 }
	// ArrayList<Day> days = new ArrayList<Day>();
	// ArrayList<Instructs> instructs = new ArrayList<Instructs>();
	// ArrayList<Capacity> capacities = new ArrayList<Capacity>();
	// ArrayList<ExamLength> exams = new ArrayList<ExamLength>();
	// ArrayList<RoomAssign> roomAssigns = new ArrayList<RoomAssign>();
	// ArrayList<DayAssign> dayAssigns = new ArrayList<DayAssign>();
	// ArrayList<Time> times = new ArrayList<Time>();
	// ArrayList<Length> lengths = new ArrayList<Length>();
	// ArrayList<At> atList = new ArrayList<At>();
	// ArrayList<Enrolled> enrollments = new ArrayList<Enrolled>();
	// ArrayList<Assign> assigns = new ArrayList<Assign>();

	public int studentCount(Pair<Course,Lecture> P){
		int count = 0;
		for(Student S : students){
			for(Pair<Course,Lecture> enrl : S.enrolled){
				if(enrl.getKey().equals(P.getKey()) && enrl.getValue().equals(P.getValue())){
					count++;
				}
			}
		}
		return count;
	}
	
	public int softConstraints(){
		return softC1() + softC2() + softC3() + softC4() + softC5() + softC6() + softC7();
	}
	
	public boolean hardConstraints(){
		if((hardC1() && hardC2() && hardC3() && hardC4())){
			return true;
		}
		return false;
	}
	
	private int softC1() {
		// S1: penalty=100/incident. No student writes more than one exam in a timeslot (no direct conflict)
		// FORALL s:STUDENT, c1,c2:COURSE, lec1,lec2:LECTURE | (lec1 /= lect2 \/ c1 /= c2) /\ ENROLLED(s,c1,lec1) /\ ENROLLED(s,c2,lec2) .
		//  FORALL ses1,ses2:SESSION | ASSIGN(c1,lec1,ses1) /\ ASSIGN(c2,lect2,ses2) .
		//   EXISTS d1,d2:DAY, t1,t2,i:int | AT(ses1,d1,t1,?) /\ AT(ses2,d2,t2,?) /\ d1=d2 /\ t1<=t2 /\ EXAMLENGTH(lec1,i) . 
		//    t1+i < t2 
		int penalty = 0;
		for(Student stdnt : students){
			if(stdnt.enrolled.size() > 1){
				for(Pair<Course,Lecture> enr1 : stdnt.enrolled){
					for(Pair<Course,Lecture> enr2 : stdnt.enrolled){
						if(!enr1.getKey().equals(enr2.getKey()) || !enr1.getValue().equals(enr2.getValue())){
							for(Session ses1 : sessions){
								for(Pair<Course,Lecture> assign1 : ses1.assignment){
									if(assign1.getKey().equals(enr1.getKey()) && assign1.getValue().equals(enr1.getValue())){
										for(Session ses2 : sessions){
											for(Pair<Course,Lecture> assign2 : ses2.assignment){
												if(assign2.getKey().equals(enr2.getKey()) && assign2.getValue().equals(enr2.getValue())){
													if((ses1.day.equals(ses2.day)) && (ses1.time <= ses2.time) && ((enr1.getValue().examLength + ses1.time) < ses2.time)){
														penalty += 100;
													}// end-if
												}// end-if
											}// end-if
										}// end-for									
									}// end-if
								}// end-for
							}// end-for
						}// end-if
					}// end-for
				}// end-for
			}// end-if
		}// end-for
		return penalty;
	}

	private int softC2() {
		//S2: penalty=20/incident. No instructor invigulates in more than one room at the same time (no direct conflict)
		// FORALL s:INSTRUCTOR, c1,c2:COURSE, lec1,lec2:LECTURE | (lec1 /= lect2 \/ c1 /= c2) /\ INSTRUCTS(s,c1,lec1) /\ INSTRUCTS(s,c2,lec2) .
		//  FORALL ses1,ses2:SESSION | ASSIGN(c1,lec1,ses1) /\ ASSIGN(c2,lect2,ses2) .
		//   EXISTS d1,d2:DAY, t1,t2,i:int | AT(ses1,d1,t1,?) /\ AT(ses2,d2,t2,?) /\ d1=d2 /\ t1<=t2 /\ EXAMLENGTH(lec1,i) .
		//    (t1+i < t2) => ses1=ses2 
		int penalty = 0;
		for(Instructor I : instructors){
			for(Pair<Course,Lecture> ins1 : I.instructs){
				for(Pair<Course,Lecture> ins2 : I.instructs){
					if(!ins1.getKey().equals(ins2.getKey()) || !ins1.getValue().equals(ins2.getValue())){
						for(Session ses1 : sessions){
							for(Pair<Course,Lecture> assign1 : ses1.assignment){
								if(assign1.getKey().equals(ins1.getKey()) && assign1.getValue().equals(ins1.getValue())){
									for(Session ses2 : sessions){
										for(Pair<Course,Lecture> assign2 : ses2.assignment){
											if(assign2.getKey().equals(ins2.getKey()) && assign2.getValue().equals(ins2.getValue())){
												if((ses1.day.equals(ses2.day)) && (ses1.time <= ses2.time) && ((ins1.getValue().examLength + ses1.time) < ses2.time)){
													penalty+=20;
												}//end-if
											}// end-if
										}// end-if
									}// end-for									
								}// end-if
							}// end-for
						}// end-for
					}//end-if
				}//end-for
			}//end-for
		}//end-for

		return penalty;
	}

	private int softC3() {
		//S3: penalty=50/incident. Every lecture for the same course should have the same exam timeslot
		// FORALL c:COURSE .
		//  EXISTS1 day:Day, time:int .
		//   FORALL lec:LECTURE, ses:SESSION | LECTURE(c,lec) /\ ASSIGN(c,lec,ses) .
		//    AT(ses,day,time,?) 
		int penalty = 0;
		for (Course crs : courses) {
			ArrayList<Pair<Course, Lecture>> CL = new ArrayList<Pair<Course, Lecture>>();
			for (Lecture L : crs.lecture) {
				Pair<Course, Lecture> pair = new Pair<Course, Lecture>(crs, L);
				CL.add(pair);
			}//end-for
			if(CL.size() > 1) {
				for(Pair<Course,Lecture> CL1 : CL){
					for(Pair<Course,Lecture> CL2 : CL){
						if(!CL1.getKey().equals(CL2.getKey()) || !CL1.getValue().equals(CL2.getValue())){
							for(Session ses1 : sessions){
								for(Pair<Course,Lecture> assign1 : ses1.assignment){
									if(assign1.getKey().equals(CL1.getKey()) && assign1.getValue().equals(CL1.getValue())){
										for(Session ses2 : sessions){
											for(Pair<Course,Lecture> assign2 : ses2.assignment){
												if(assign2.getKey().equals(CL2.getKey()) && assign2.getValue().equals(CL2.getValue())){
													if((ses1.day.equals(ses2.day)) && (ses1.time == ses2.time)) {
														penalty+=0;
													}//end-if
													else {
														penalty += 50;
													}
												}// end-if
											}// end-if
										}// end-for									
									}// end-if
								}// end-for
							}
						}// end-if
					}// end-for
				}// end-for
			}// end-if
		}// end-for
		return penalty;
	}

	private int softC4() {
		//S4: penalty=50/incident. No student writes for longer than 5 hours in a single day
		// FORALL s:STUDENT .
		//  FORALL d:DAY .
		//   (SUM i:int | (FORALL c:COURSE, lec:LECTURE, ses:SESSION | ENROLLED(s,c,lec) /\ ASSIGN(c,lec,ses) .       
		//    dayAssign(ses,d)) . 
		//     EXAMLENGTH(lec,i)) <= 5 
		int penalty = 0;
		for (Student stdnt : students) {
			if (stdnt.enrolled.size() > 1) {
				ArrayList<Pair<Session, Lecture>> set = new ArrayList<Pair<Session, Lecture>>();
				for (Pair<Course, Lecture> enr1 : stdnt.enrolled) {
					for (Session ses : sessions) {
						for(Pair<Course,Lecture> assign : ses.assignment){
							if(assign.getKey().equals(enr1.getKey()) && assign.getValue().equals(enr1.getValue())){
								Pair<Session, Lecture> pair = new Pair<Session, Lecture>(ses, enr1.getValue());
								set.add(pair);
							}
						}
					}// end-for
				}// end-for
				for (Pair<Session, Lecture> ses1 : set) {
					long sum = ses1.getValue().examLength;
					for(Pair<Session,Lecture> ses2 : set){
						if(ses1.getKey().day.equals(ses2.getKey().day)){
							sum += ses2.getValue().examLength;
						}
					}
					if (sum > 5) {
						penalty += 50;
					}
				}
			}// end-if
		}// end-for
		return penalty;
	}

	private int softC5() {
		//S5: penalty=50/incident. No student should write exams with no break between them
		// FORALL s:STUDENT, c1,c2:COURSE, lec1,lec2:LECTURE | (lec1 /= lect2 \/ c1 /= c2) /\ ENROLLED(s,c1,lec1) /\ ENROLLED(s,c2,lec2) .
		//  FORALL ses1,ses2:SESSION | ASSIGN(c1,lec1,ses1) /\ ASSIGN(c2,lect2,ses2) .
		//   EXISTS d1,d2:DAY, t1,t2,i:int | AT(ses1,d1,t1,?) /\ AT(ses2,d2,t2,?) /\ d1=d2 /\ t1<=t2 /\ EXAMLENGTH(lec1,i) .
		//    t1+i /= t2 
		int penalty = 0;
		for(Student stdnt : students){
			if(stdnt.enrolled.size() > 1){
				for(Pair<Course,Lecture> enr1 : stdnt.enrolled){
					for(Pair<Course,Lecture> enr2 : stdnt.enrolled){
						if(!enr1.getKey().equals(enr2.getKey()) || !enr1.getValue().equals(enr2.getValue())){
							for(Session ses1 : sessions){
								for(Pair<Course,Lecture> assign1 : ses1.assignment){
									if(assign1.getKey().equals(enr1.getKey()) && assign1.getValue().equals(enr1.getValue())){
										for(Session ses2 : sessions){
											for(Pair<Course,Lecture> assign2 : ses2.assignment){
												if(assign2.getKey().equals(enr2.getKey()) && assign2.getValue().equals(enr2.getValue())){
													if((ses1.day.equals(ses2.day)) && (ses1.time <= ses2.time) && ((enr1.getValue().examLength + ses1.time) != ses2.time)){
														penalty += 50;
													}// end-if
												}// end-if
											}// end-if
										}// end-for									
									}// end-if
								}// end-for
							}
						}// end-if
					}// end-for
				}// end-for
			}// end-if
		}// end-for
		return penalty;
	}

	private int softC6() {
		//S6: penalty=20/session. All the exams taking place in a particular session should have the same length
		// FORALL ses:SESSIONS .
		//  EXISTS c1,c2: COURSE, lec1,lect1:LECTURE | ASSIGN(c1,lec1,ses) /\ ASSIGN(c2,lec2,ses) .
		//   EXISTS i:int . EXAMLENGTH(lec1, i) /\ EXAMLENGTH(lect2,i)
		int penalty = 0;
		for(Session S : sessions){
			for(Pair<Course, Lecture> assign1 : S.assignment) {
				for(Pair<Course, Lecture> assign2 : S.assignment) {
					if(!assign1.getKey().equals(assign2.getKey()) || !assign1.getValue().equals(assign2.getValue())){
						if(assign1.getValue().examLength != assign2.getValue().examLength){
							penalty+=20;
						}
					}
				}
			}
		}
		return penalty;
	}

	private int softC7() {
		//S7: penalty=5/session. Every exam in a session should take up the full time of the session
		// FORALL ses:SESSIONS .
		//  EXISTS c: COURSE, lec:LECTURE | ASSIGN(c,lec,ses) .
		//   EXISTS i:int . EXAMLENGTH(lec, i) /\ AT(ses,?,?,i)
		int penalty = 0;
		for (Session S : sessions) {
			for (Pair<Course, Lecture> assign1 : S.assignment) {
				if (assign1.getValue().examLength != S.sessionLength) {
					penalty += 5;
				}
			}
		}
		return penalty;
	}

	private boolean hardC1(){
		//H1: every lecture is assigned an exam session (completeness) 
		// FORALL c:COURSE, lec:LECTURE | LECTURE(c,lec) . 
		//  EXISTS ses:SESSION . 
		//   ASSIGN(c,lec,ses) 
		ArrayList<Pair<Course, Lecture>> CL = new ArrayList<Pair<Course, Lecture>>();
		ArrayList<Pair<Course, Lecture>> assigns = new ArrayList<Pair<Course, Lecture>>();
		for (Course crs : courses) {
			for (Lecture lec : crs.lecture) {
				Pair<Course, Lecture> pair = new Pair<Course, Lecture>(crs, lec);
				CL.add(pair);
			}//end-for
		}
		for(Session ses : sessions){
			for(Pair<Course, Lecture> assign : ses.assignment){
				assigns.add(assign);
			}//end-for
		}
		
		boolean exists = false;
		for(Pair<Course, Lecture> CL1 :CL){
			exists = false;
			for(Pair<Course, Lecture> A : assigns){
				if(A.getKey().equals(CL1.getKey()) && A.getValue().equals(CL1.getValue())){
					exists = true;
					break;
				}
			}
		}
		return exists;
	}
//Landon		
	private boolean hardC2(){
		//H2: no lecture is assigned more than one exam session
		// FORALL c:COURSE, lec:LECTURE, ses1,ses2:SESSION | LECTURE(c,lec) . 
		//  (ASSIGN(lec,ses1) /\ ASSIGN(lec,ses2)) => ses1=ses2
		for(Course crs : courses) {
			for(Lecture lec : crs.lecture){
				for(Session ses1 : sessions){
					for(Pair<Course, Lecture> assign1 : ses1.assignment){
						if(assign1.getKey().equals(crs) && assign1.getValue().equals(lec)){
							for(Session ses2 : sessions){
								if(!ses1.equals(ses2)){
									for(Pair<Course, Lecture> assign2 : ses2.assignment){
										if(assign2.getKey().equals(crs) && assign2.getValue().equals(lec)){
											return false;			
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	
	private boolean hardC3(){
		//H3: the number of students writing an exam in a particular exam session may not exceed the capacity of the room
		// FORALL ses:SESSION, r:ROOM | ROOMASSIGN(ses,r) .
		//  EXISTS cap:int | CAPACITY(r,cap) .
		//   #{s:STUDENT | FORALL c:COURSE, lec: LECTURE | ASSIGN(c,lec,ses) . ENROLLED(s,c,lec)} <= cap 
		for(Session S : sessions){
			long capacity = S.room.capacity;
			long counter = 0;
			for(Pair<Course, Lecture> assign1 : S.assignment) {
				for(Student stdnt : students){
					for(Pair<Course,Lecture> enr1 : stdnt.enrolled){
						if(assign1.getKey().equals(enr1.getKey()) && assign1.getValue().equals(enr1.getValue())){
							counter++;
							if(counter > capacity){
								return false;
							}
						}
					}
				}
			}
		}
		return true;		
	}
	private boolean hardC4(){
		//H4: every lecture's required time must be less than the session length
		// FORALL ses:SESSION, c:COURSE, lec:LECTURE | ASSIGN(c,lec,ses) .
		//  EXISTS slen, llen:int | AT(ses,?,?,slen) /\ EXAMLENGTH(c,lec,llen) . llen <= slen 
		for(Session S : sessions){
			for(Pair<Course, Lecture> assign1 : S.assignment) {
				if(assign1.getValue().examLength > S.sessionLength){
					return false;
				}
			}
		}
		return true;
	}
	
	public Environment(String name) {
		super(name);
		students = new ArrayList<Student>();
		instructors = new ArrayList<Instructor>();
		rooms = new ArrayList<Room>();
		courses = new ArrayList<Course>();
		sessions = new ArrayList<Session>();
		lectures = new ArrayList<Lecture>();
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
		if (!e_student(p)) {
			Student n = new Student(p);
			students.add(n);
		}

	}

	@Override
	public boolean e_student(String p) {

		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).name.equals(p))
				return true;
		}

		return false;

	}

	@Override
	public void a_instructor(String p) {
		if (!e_instructor(p)) {
			Instructor n = new Instructor(p);
			instructors.add(n);
		}
	}

	@Override
	public boolean e_instructor(String p) {

		for (int i = 0; i < instructors.size(); i++) {
			if (instructors.get(i).name.equals(p))
				return true;
		}

		return false;

	}

	@Override
	public void a_room(String p) {
		if (!e_room(p)) {
			Room n = new Room(p);
			rooms.add(n);
		}
	}

	@Override
	public boolean e_room(String p) {
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).room.equals(p))
				return true;
		}

		return false;

	}

	@Override
	public void a_course(String p) {
		if (!e_course(p)) {
			Course n = new Course(p);
			courses.add(n);
		}

	}

	@Override
	public boolean e_course(String p) {
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).name.equals(p))
				return true;
		}

		return false;

	}

	@Override
	public void a_session(String p) {
		if (!e_session(p)) {
			Session n = new Session(p);
			sessions.add(n);
		}
	}

	@Override
	public boolean e_session(String p) {
		for (int i = 0; i < sessions.size(); i++) {
			if (sessions.get(i).name.equals(p))
				return true;
		}

		return false;

	}

	@Override
	public void a_day(String p) {
		if (!e_day(p)) {
			day.add(p);
		}
	}

	@Override
	public boolean e_day(String p) {
		for (int i = 0; i < day.size(); i++) {
			if (day.get(i).equals(p))
				return true;
		}
		return false;
	}

	@Override
	public void a_lecture(String c, String lec) {
		if (!e_lecture(c, lec)) {
			if (!e_course(c)) {
				a_course(c);
			}

			Lecture n = new Lecture(c, lec);
			lectures.add(n);
			for (int i = 0; i < courses.size(); i++) {
				if (courses.get(i).name.equals(c)) {
					courses.get(i).lecture.add(n);
					break;
				}
			}
		}

	}

	@Override
	public boolean e_lecture(String c, String lec) {
		for (int i = 0; i < lectures.size(); i++) {
			if (lectures.get(i).name.equals(lec)
					&& lectures.get(i).course.equals(c))
				return true;
		}

		return false;

	}

	@Override
	public void a_lecture(String c, String lec, String instructor, Long length) {
		Lecture n = null;
		if (!e_lecture(c, lec, instructor, length)) {
			// take care of second if first was simple version
			if (!e_lecture(c, lec)) {
				if (!e_course(c)) {
					a_course(c);
				}
				if (!e_instructor(instructor)) {
					a_instructor(instructor);
				}
				n = new Lecture(lec, c, instructor, length);
				lectures.add(n);
				// add lecture into list of lectures in the course
				for (int i = 0; i < courses.size(); i++) {
					if (courses.get(i).name.equals(c)) {
						courses.get(i).lecture.add(n);
						break;
					}
				}
			} else {
				for (int i = 0; i < lectures.size(); i++) {
					if (lectures.get(i).name.equals(lec)
							&& lectures.get(i).course.equals(c)) {
						lectures.get(i).instructor = instructor;
						lectures.get(i).examLength = length;
						n = lectures.get(i);
						break;
					}
				}
			}
			// add course/lecture pair for instructor
			Course key = null;
			for (int i = 0; i < instructors.size(); i++) {
				if (instructors.get(i).name.equals(instructor)) {
					for (int j = 0; j < courses.size(); j++) {
						if (courses.get(j).name.equals(c)) {
							key = courses.get(j);
							break;
						}
					}
					Pair<Course, Lecture> pair = new Pair<Course, Lecture>(key,
							n);
					instructors.get(i).instructs.add(pair);
					break;
				}
			}
		}

	}

	public boolean e_lecture(String c, String lec, String instructor,
			Long length) {
		for (int i = 0; i < lectures.size(); i++) {
			if (lectures.get(i).name.equals(lec)
					&& lectures.get(i).course.equals(c)
					&& lectures.get(i).instructor.equals(instructor)
					&& lectures.get(i).examLength == length)
				return true;
		}

		return false;

	}

	@Override
	public void a_instructs(String p, String c, String l) {
		if (!e_instructs(p, c, l)) {
			if (!e_course(c)) {
				a_course(c);
			}
			if (!e_instructor(p)) {
				a_instructor(p);
			}
			if (!e_lecture(c, l)) {
				a_lecture(c, l);
			}
			Course key = null;
			Lecture value = null;
			for (int i = 0; i < instructors.size(); i++) {
				if (instructors.get(i).name.equals(p)) {
					for (int j = 0; j < courses.size(); j++) {
						if (courses.get(j).name.equals(c)) {
							key = courses.get(j);
							break;
						}
					}
					if(key == null)
						break;
					for (int k = 0; k < key.lecture.size(); k++) {
						if (key.lecture.get(k).name.equals(l)) {
							value = key.lecture.get(k);
							break;
						}
					}
					Pair<Course, Lecture> pair = new Pair<Course, Lecture>(key,
							value);
					instructors.get(i).instructs.add(pair);
					break;
				}
			}

		}

	}


	@Override
	public boolean e_instructs(String p, String c, String l) {
		
		
		
		for (int i = 0; i < instructors.size(); i++) {
			if (instructors.get(i).name.equals(p)) {
				for(int j = 0; j < instructors.get(i).instructs.size(); j++){
					if(instructors.get(i).instructs.get(j).getKey().name.equals(c) &&instructors.get(i).instructs.get(j).getValue().name.equals(l)){
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void a_examLength(String c, String lec, Long hours) {
		if (!e_examLength(c, lec, hours)) {
			if (!e_course(c)) {
				a_course(c);
			}
			if (!e_lecture(c, lec)) {
				a_lecture(c, lec);
			}
			for(int i = 0; i < lectures.size(); i++){
				if(lectures.get(i).course.equals(c)){
					lectures.get(i).examLength = hours;
					break;
				}
					
			}
		}
	}

	@Override
	public boolean e_examLength(String c, String lec, Long hours) {
		for(int i = 0; i < lectures.size(); i++){
			if(lectures.get(i).course.equals(c)){
				if(lectures.get(i).examLength == hours){
					return true;
				}
				
			}
		}
		return false;
	}

	@Override
	public void a_roomAssign(String p, String room) {
		if (!e_session(p)) {
				a_session(p);
			}
			if (!e_room(room)) {
				a_room(room);
			}
			if (!e_roomAssign(p, room)) {
			
			for(int i = 0; i < sessions.size(); i++){
				if(sessions.get(i).name.equals(p)){
					for(int j =0; j < rooms.size(); j++){
						if(rooms.get(j).room.equals(room)){
							sessions.get(i).room = rooms.get(j);
							break;
						}
					}
					break;
				}
			}
		}

	}

	@Override
	public boolean e_roomAssign(String p, String room) {
		for(int i = 0; i < sessions.size(); i++){
			if(sessions.get(i).name.equals(p)){
				if(sessions.get(i).room.room.equals(room)){
					return true;
				}
			}
		}
		return false;
	}
		

	@Override
	public void a_dayAssign(String p, String day) {
		a_day(day);
		if (!e_session(p)) {
				a_session(p);
			}
		if (!e_dayAssign(p, day)) {
			
			
			for(int i = 0; i < sessions.size(); i++){
				if(sessions.get(i).name.equals(p)){
					sessions.get(i).day = day;
					break;
				}
			}
					
		}
	}

	@Override
	public boolean e_dayAssign(String p, String day) {
		for(int i = 0; i < sessions.size(); i++){
			if(sessions.get(i).name.equals(p)){
				if(sessions.get(i).day.equals(day)){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void a_time(String p, Long time) {
		if (!e_session(p)) {
				a_session(p);
			}
		if (!e_time(p, time)) {
			for(int i = 0; i < sessions.size(); i++){
				if(sessions.get(i).name.equals(p)){
					sessions.get(i).time = time;
					break;
				}
			}
			
		}
	}

	@Override
	public boolean e_time(String p, Long time) {
		for(int i = 0; i < sessions.size(); i++){
			if(sessions.get(i).name.equals(p)){
				if(sessions.get(i).time == time){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void a_length(String p, Long length) {
		if (!e_session(p)) {
				a_session(p);
			}
		if (!e_length(p, length)) {
			
			for(int i = 0; i < sessions.size(); i++){
				if(sessions.get(i).name.equals(p)){
					sessions.get(i).sessionLength = length;
					break;
				}
			}
			
		}
		
	}

	@Override
	public boolean e_length(String p, Long length) {
		for(int i = 0; i < sessions.size(); i++){
			if(sessions.get(i).name.equals(p)){
				if(sessions.get(i).sessionLength == length){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void a_at(String session, String day, Long time, Long length) {
			a_day(day);
			if (!e_session(session)) {
				a_session(session);
			}
			if (!e_at(session, day, time, length)) {
			for(int i = 0; i < sessions.size(); i++){
				if(sessions.get(i).name.equals(session)){
					sessions.get(i).day = day;
					sessions.get(i).time = time;
					sessions.get(i).sessionLength = length;
					break;
				}
			}
		}
	}

	@Override
	public boolean e_at(String session, String day, Long time, Long length) {
		for(int i = 0; i < sessions.size(); i++){
			if(sessions.get(i).name.equals(session)){
				if(sessions.get(i).day.equals(day) && sessions.get(i).time == time && sessions.get(i).sessionLength == length){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void a_session(String session, String room, String day, Long time,
			Long length) {
		a_day(day);
		if (!e_session(session, room, day, time, length)) {
			if (!e_session(session)) {
				a_session(session);
			}
			if (e_room(room)) {
				a_room(room);
			}
			a_roomAssign(session, room);
			a_at(session, day, time, length);

		}
	}

	@Override
	public boolean e_session(String session, String room, String day,
			Long time, Long length) {
		for (int i = 0; i < sessions.size(); i++) {
			if (sessions.get(i).name.equals(session)) {
				if (sessions.get(i).room.room.equals(room)
						&& sessions.get(i).day.equals(day)
						&& sessions.get(i).time == time
						&& sessions.get(i).sessionLength == length) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void a_enrolled(String student, String c, String l) {

		if (!e_lecture(c, l)) {
			a_lecture(c, l);
		}
		if (!e_student(student)) {
			a_student(student);
		}
		if (!e_enrolled(student, c, l)) {
			Course key = null;
			Lecture value = null;
			for (int i = 0; i < students.size(); i++) {
				if (students.get(i).name.equals(student)) {
					for (int j = 0; j < courses.size(); j++) {
						if (courses.get(j).name.equals(c)) {
							key = courses.get(j);
							break;
						}
					}
					if (key == null)
						break;
					for (int k = 0; k < key.lecture.size(); k++) {
						if (key.lecture.get(k).name.equals(l)) {
							value = key.lecture.get(k);
							break;
						}
					}
					Pair<Course, Lecture> pair = new Pair<Course, Lecture>(key,
							value);
					students.get(i).enrolled.add(pair);
					break;
				}
			}

		}

	}

	@Override
	public boolean e_enrolled(String student, String c, String l) {

		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).name.equals(student)) {
				for (int j = 0; j < students.get(i).enrolled.size(); j++) {
					if (students.get(i).enrolled.get(j).getKey().name.equals(c)
							&& students.get(i).enrolled.get(j).getValue().name
									.equals(l)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void a_enrolled(String student, Vector<Pair<ParamType, Object>> list) {
		java.util.Iterator<Pair<ParamType, Object>> itr = list.iterator();
		while (itr.hasNext()) {
			Pair<ParamType, Object> c = itr.next();
			Pair<ParamType, Object> lec = itr.next();
			String course = (String) c.getValue();
			String lecture = (String) lec.getValue();

			
			
			if (!e_enrolled(student, course, lecture)) {
				a_enrolled(student, course, lecture);
			}
		}

	}
	
	@Override
	public void a_capacity(String r, Long cap) {
		if (!e_room(r)) {
			a_room(r);
		}
		if (!e_capacity(r, cap)) {
			for (int i = 0; i < rooms.size(); i++) {
				if (rooms.get(i).room.equals(r)) {
					rooms.get(i).capacity = cap;
					break;
				}
			}

		}

	}

	@Override
	public boolean e_capacity(String r, Long cap) {
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).room.equals(r)) {
				if (rooms.get(i).capacity == cap) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void a_assign(String c, String lec, String session) {

		// Removed due to duplicate check in e_lecture -- Landon
		/*
		 * if(e_course(c)) { a_course(c); }
		 */
		if (!e_lecture(c, lec)) {
			a_lecture(c, lec);
		}
		if (!e_session(session)) {
			a_session(session);
		}
		if (!e_assign(c, lec, session)) {
			Course key = null;
			Lecture value = null;
			for (int i = 0; i < sessions.size(); i++) {
				if (sessions.get(i).name.equals(session)) {
					for (int j = 0; j < courses.size(); j++) {
						if (courses.get(j).name.equals(c)) {
							key = courses.get(j);
							break;
						}
					}
					if (key == null)
						break;
					for (int k = 0; k < key.lecture.size(); k++) {
						if (key.lecture.get(k).name.equals(lec)) {
							value = key.lecture.get(k);
							break;
						}
					}
					Pair<Course, Lecture> pair = new Pair<Course, Lecture>(key,
							value);
					sessions.get(i).assignment.add(pair);
					break;
				}
			}

		}

	}

	@Override
	public boolean e_assign(String c, String lec, String session) {
		for (int i = 0; i < sessions.size(); i++) {
			if (sessions.get(i).name.equals(session)) {
				for (int j = 0; j < sessions.get(i).assignment.size(); j++) {
					if (sessions.get(i).assignment.get(j).getKey().name.equals(c)
							&& sessions.get(i).assignment.get(j).getValue().name
									.equals(lec)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void printOutput(String outFileName) {
		try {
			outFileName += ".out";
			PrintWriter out = new PrintWriter(outFileName);
			
			String instr = "//Instructors\n";
			for (int i = 0; i < instructors.size(); i++) {
				instr += "instructor("+ instructors.get(i).name + ")\n";
			}
			out.println(instr);
			
			String stdnts = "//Students\n";
			for (int i = 0; i < students.size(); i++) {
				stdnts += "student("+ students.get(i).name + ")\n";
			}
			out.println(stdnts);
			
			String rms = "//Rooms\n";
			for (int i = 0; i < rooms.size(); i++) {
				rms += "room("+ rooms.get(i).room + ")\n";
			}
			out.println(rms);
			
			String crs = "//Courses\n";
			for (int i = 0; i < courses.size(); i++) {
				crs += "course("+ courses.get(i).name + ")\n";
			}
			out.println(crs);
			
			String ds = "//Days\n";
			for (int i = 0; i < day.size(); i++) {
				ds += "day("+ day.get(i) + ")\n";
			}
			out.println(ds);
			
			String lecs = "//Lectures\n";
			for (int i = 0; i < lectures.size(); i++) {
				lecs += "lecture("+ lectures.get(i).course + "," + lectures.get(i).name + ")\n";
			}
			out.println(lecs);
			
			String caps = "//Capacities\n";
			for (int i = 0; i < rooms.size(); i++) {
				caps += "capacity("+ rooms.get(i).room + ","+ rooms.get(i).capacity +")\n";
			}
			out.println(caps);
			
			String sess = "//Sessions\n";
			for (int i = 0; i < sessions.size(); i++) {
				sess += "session(" + sessions.get(i).name + ")\n";
			}
			out.println(sess);
			
			String instrct = "//Instructs\n";
			for (int i = 0; i < instructors.size(); i++) {
				String instructorName = instructors.get(i).name;
				for(Pair<Course, Lecture> ins : instructors.get(i).instructs){
					String crsName = ins.getKey().name;
					String lecName = ins.getValue().name;
					instrct += "instructs("+ instructorName + "," + crsName + "," + lecName+ ")\n";
				}
			}
			out.println(instrct);
			
			String exms = "//Exams\n";
			for (int i = 0; i < lectures.size(); i++) {
				exms += "examLength("+ lectures.get(i).course + "," + lectures.get(i).name + "," + lectures.get(i).examLength + ")\n";
			}
			out.println(exms);
			
			String rmas = "//Room Assigns\n";
			for (int i = 0; i < sessions.size(); i++) {
				rmas += "roomAssign(" + sessions.get(i).name + "," + sessions.get(i).room.room + ")\n";
			}
			out.println(rmas);
			
			String dass = "\n//Day Assigns\n";
			for (int i = 0; i < sessions.size(); i++) {
				dass += "dayAssign(" + sessions.get(i).name + "," + sessions.get(i).day + ")\n";
			}
			out.println(dass);
			
			String t = "//Times\n";
			for (int i = 0; i < sessions.size(); i++) {
				t += "time(" + sessions.get(i).name + "," + sessions.get(i).time + ")\n";
			}
			out.println(t);
			
			String lens = "//Lengths\n";
			for (int i = 0; i < sessions.size(); i++) {
				lens += "length(" + sessions.get(i).name + "," + sessions.get(i).sessionLength + ")\n";
			}
			out.println(lens);
			
			String ats = "//At\n";
			for (int i = 0; i < sessions.size(); i++) {
				ats += "at(" + sessions.get(i).name + "," + sessions.get(i).day + "," + sessions.get(i).time +"," + sessions.get(i).sessionLength + ")\n";
			}
			out.println(ats);
			
			String enrls = "//Enrolments\n";
			for (int i = 0; i < students.size(); i++) {
				String studentName = students.get(i).name;
				for(Pair<Course, Lecture> enr : students.get(i).enrolled){
					String crsName = enr.getKey().name;
					String lecName = enr.getValue().name;
					enrls += "enrolled("+ studentName + "," + crsName + "," + lecName+ ")\n";
				}
			}
			out.println(enrls);
			
			String assign = "//Assignments\n";
			for (int i = 0; i < sessions.size(); i++) {
				String sessionName = sessions.get(i).name;
				for(Pair<Course, Lecture> asgn : sessions.get(i).assignment){
					String crsName = asgn.getKey().name;
					String lecName = asgn.getValue().name;
					assign += "assign("+ crsName + "," + lecName + "," + sessionName +")\n";
				}
			}
			out.println(assign);
			
			out.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// calls the fromFile() declared in PredicateReader.java
	public int fromFile(String fromFile) {

		return super.fromFile(fromFile);

	}

	// getter method used by main() in ExamSchedule.java
	public static Environment get() {
		Environment singletonEnv = null;

		if (singletonEnv == null) {
			singletonEnv = new Environment("test");
		}

		return singletonEnv;
	}

}
