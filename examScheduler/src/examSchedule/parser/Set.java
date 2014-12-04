package examSchedule.parser;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import examSchedule.*;

public class Set {

	static int workingSetSize = 15;
	static int workingSetKeep = 9;
	static int bestSetSize = 5;
//	static int workingSetSize = 30;		// number of facts in the working set
//	static int bestSetSize = 10;	// number of facts in the all-time-best set
	
	static LinkedList<Environment> workingSet;
	static LinkedList<Environment> bestSet;
	final long timeLimit;
	static String outFile;
	public Set(Environment env, long maxTime, String outFile){
		this.outFile = outFile;
		this.timeLimit = maxTime;
		long startTime = System.currentTimeMillis();
		Environment kontrol = env;
		bestSet = new LinkedList<Environment>();
		workingSet = new LinkedList<Environment>();
		
		while((System.currentTimeMillis() - startTime)< timeLimit ) {
			search(kontrol);
			long elapsedTime = (System.currentTimeMillis() - startTime);
			//System.out.println("TIME PASSED: " +elapsedTime);
			 
		}
		System.out.println("DONE");
		
//		Environment best = workingSet.getFirst();
//		best.printOutput(outFile + "_best");
		
		System.out.println("Penalty:" + bestSet.get(0).utility);
		bestSet.get(0).printOutput(outFile);
//Debug**
//		mutate(bestSet.get(0));
//		bestSet.get(0).printOutput("mutate1.txt");
//		mutate(bestSet.get(0));
//		bestSet.get(0).printOutput("mutate2.txt");
//		mutate(bestSet.get(0));
//		bestSet.get(0).printOutput("mutate3.txt");
//		mutate(bestSet.get(0));
//		bestSet.get(0).printOutput("mutate4.txt");
//Debug**
	}
	
	
	public static void search(Environment kontrol){
		// placeholder for the next set
		LinkedList<Environment> tempEnvSet = new LinkedList<Environment>();
	
		// ext fill (seed for the first iteration )
		while(workingSet.size() < workingSetSize){
			Environment E = generateNew(kontrol);
			if(E != null){
				workingSet.add(E);
//Debug				
//				E.printOutput("test.txt" + workingSet.size());
			}
		}
		
		sortList(workingSet);
		sortList(bestSet);

		// Keeping data for best sets	
		if(bestSet.size() != 0){
		for(int i = 0; i < 10; i++ ){
			if(bestSet.get(i).utility > workingSet.get(i).utility )
				bestSet.add(i, workingSet.get(i));
		}
		}
		else{
			for(int i = 0; i < 10; i++ ){
				
					bestSet.add(i, workingSet.get(i));
			}
		}
		for(int i = 14; i > workingSetKeep; i-- ){
			workingSet.remove(i);
		}
		//mutate loop
		for(int i = (workingSet.size()-1); i>1; i--){
			mutate(workingSet.get(i));
		}
	}
	//Mike's Generation function
	public static Environment generateNew(Environment B){
		
		Environment newGen = new Environment("Gen");
		Environment garbage = new Environment("Garb");
		Random rnd = new Random();
		
		do{ 
			newGen.fromFile(outFile);
			garbage.fromFile(outFile);
			
			ArrayList<Pair<Course,Lecture>> exams = getLec(garbage.courses);
			
			ArrayList<Session> slots = garbage.sessions;
			ArrayList<Session> temp = new ArrayList<Session>();
			Session tmp = new Session("");
			
			for(Session S : slots){ 
				while(S.assignment.size() > 0){
					Pair<Course,Lecture> pair = S.assignment.removeFirst();
//					System.out.println("C/L: " + pair.getKey().name + "," + pair.getValue().name + "; " + S.assignment.size());
					S.room.remainCap -= garbage.studentCount(pair);
					for(int i = 0; i < exams.size(); i++){
						if(exams.get(i).getKey().equals(pair.getKey()) && exams.get(i).getValue().equals(pair.getValue())){
							exams.remove(i);
							break;
						}
					}
				}
			}
			while(slots.size()>0){
				
				int r =rnd.nextInt(slots.size());
				tmp = slots.get(r);
				
				slots.remove(r);
				int i = 0;

				while(i < exams.size()){
					if(((tmp.room.remainCap - garbage.studentCount(exams.get(i))) >= 0) && exams.get(i).getValue().examLength <= tmp.sessionLength){
						tmp.assignment.add(exams.get(i));
						tmp.room.remainCap -=  garbage.studentCount(exams.get(i));
						exams.remove(i);
					}
					else{
						i++;
					}
				}
				temp.add(tmp);
			}

			for(int i = 0; i < temp.size(); i++){
				String s;
				String c;
				String lec;
				s = temp.get(i).name;
				for(int j = 0; j < temp.get(i).assignment.size(); j++){
					c =  temp.get(i).assignment.get(j).getKey().name;
					lec = temp.get(i).assignment.get(j).getValue().name;
					newGen.a_lecture(temp.get(i).assignment.get(j).getValue().course, temp.get(i).assignment.get(j).getValue().name, temp.get(i).assignment.get(j).getValue().instructor, temp.get(i).assignment.get(j).getValue().examLength); 
//Debug	
//					System.out.println(temp.get(i).assignment.get(j).getValue().course+ " " +temp.get(i).assignment.get(j).getValue().name+ " " +temp.get(i).assignment.get(j).getValue().instructor + " " + temp.get(i).assignment.get(j).getValue().examLength);
					newGen.a_assign(c, lec, s);
//Debug	
//					System.out.println(c + " " + lec + " " + s);
				}
				
			}
			//Debug				
//			newGen.printOutput("test");
//			B.printOutput("B");
		}while(!newGen.hardConstraints());
	
		return newGen;
	}


/*	
	//Landon's Generation function
	public static Environment generateNew(Environment B){
		
		Environment newGen = B.clone();
		
		do{
			Random rnd = new Random();
			ArrayList<Pair<Course,Lecture>> exams = getLec(newGen.courses);
			ArrayList<Session> slots = newGen.sessions;
//Debug		
			System.out.println("S:" + slots.size());
			ArrayList<Session> temp = new ArrayList<Session>();
			Session tmp = null; 

//Added Landon			
			while(exams.size() > 0){
				int r1 = rnd.nextInt(exams.size());
				int r2 = rnd.nextInt(slots.size());
				int attempt = 0;
					
				while(attempt <= slots.size()){
					tmp = slots.get(r2);
					// Find a slot with appropriate capacity.
					if((tmp.room.remainCap - B.studentCount(exams.get(r1))) < 0){
						attempt++;
						r2 = rnd.nextInt(slots.size());
					}
					else{
						// conditional break state.
						attempt = slots.size() + 1;
					}
				}
				tmp.assignment.add(exams.get(r1));
				tmp.room.remainCap -=  B.studentCount(exams.get(r1));
				exams.remove(r1);
				temp.add(tmp);
//Debug		
				System.out.println("T:" +temp.size());
//Debug
				System.out.println("E:" +exams.size());
			}
			newGen.sessions = temp;
		}while(!newGen.hardConstraints());
		return newGen;
	}
*/
	
	
	// Swap two assignments between 2 sessions OR move 1 assignment
	// Returns false if it failed to mutate
	public static boolean mutate(Environment env) {
		Random rnd = new Random();
		Random rnd1 = new Random();
		Random rnd2 = new Random();
		int s1r = rnd1.nextInt(env.sessions.size());
		int s2r = rnd2.nextInt(env.sessions.size());
		boolean swapOrMove = rnd.nextBoolean();

		// Try to ensure that s1r != s2r index since it is the same Environment
		int i = 0;
		while(((i < 10) && (s1r == s2r))){
			s1r = rnd1.nextInt(env.sessions.size());
			s2r = rnd2.nextInt(env.sessions.size());
			i++;
			if((s1r == s2r)){
				System.out.println("Same:"+ s1r + "," + s2r);
			}
			System.out.println(i);
			System.out.println(s1r + "," + s2r);
		}
		Session S1 = env.sessions.get(s1r);
		Session S2 = env.sessions.get(s2r);
		// Ensure they are different and at least one has an assignment that can be swapped.
		if(!S1.name.equals(S2.name) && (S2.assignment.size() > 0)){
			if(swapOrMove){
				// Move from S2 to S1
//				System.out.println("Move");
				s2r = rnd.nextInt(S2.assignment.size());
				if(S2.assignment.get(s2r).getValue().examLength <= S1.sessionLength
						&& env.studentCount(S2.assignment.get(s2r)) <= S1.room.remainCap) {
					
					// Move <c,lec> from S2 to S1 and update remainingCapacities.
					S2.room.remainCap += env.studentCount(S2.assignment.get(s2r));
					S1.room.remainCap -= env.studentCount(S2.assignment.get(s2r));
					S1.assignment.add(S2.assignment.get(s2r));
					S2.assignment.remove(s2r);	
					
					// Swap occurred
					return true;
				}	
			}
			else{
				// Session 1 cannot have nothing assigned to it.
				if(S1.assignment.size() > 0){
					// Session 1 has assignments, swap with Session 2
//					System.out.println("Swap");
					s1r = rnd.nextInt(S1.assignment.size());
					s2r = rnd.nextInt(S2.assignment.size());
					
					// Check exam times fit each other
					if(S1.assignment.get(s1r).getValue().examLength <= S2.sessionLength
							&& S2.assignment.get(s2r).getValue().examLength <= S1.sessionLength) {
						
						// Create potential capacities
						long s1Cap = S1.room.remainCap + env.studentCount(S1.assignment.get(s1r));
						long s2Cap = S2.room.remainCap + env.studentCount(S2.assignment.get(s2r));
						// Check capacity is fine
						if((env.studentCount(S2.assignment.get(s2r)) <= s1Cap) 
								&&(env.studentCount(S1.assignment.get(s1r)) <= s2Cap) ){
								
							// Swap a <c,lec> of S2, S1 and update remainingCapacities.
							S1.room.remainCap = s1Cap - env.studentCount(S2.assignment.get(s2r));
							S2.room.remainCap = s2Cap - env.studentCount(S1.assignment.get(s1r));
							
							Pair<Course,Lecture> tmp1 = S1.assignment.remove(s1r);
							Pair<Course,Lecture> tmp2 = S2.assignment.remove(s2r);
							
							S1.assignment.add(tmp2);
							S2.assignment.add(tmp1);
								
							return true;
						}
					}
				}
			}
		}
		System.out.println("Failed to move or swap...");
		return false;
	}
	
	public static ArrayList<Pair<Course, Lecture>> getLec(ArrayList<Course> courses){
		ArrayList <Pair<Course, Lecture>> examsList = new ArrayList<Pair<Course, Lecture>>();
		for(int i = 0; i < courses.size(); i++){
			for(int j =0; j < courses.get(i).lecture.size(); j++){
				examsList.add(new Pair<Course, Lecture>(courses.get(i), courses.get(i).lecture.get(j)));
			}
		}
		return examsList;
	}
	
	
	public static void sortList(LinkedList<Environment> list){
		for( int i = 0 ; i < list.size() ; i++){
			for( int j = i+1 ; j < list.size() ; j++){
				if(list.get(i).softConstraints() > list.get(j).softConstraints()){
					Environment swap = list.get(j);
					list.set(j,list.get(i));
					list.set(i,swap);			
				}
			}
		}
	}
	
	
}

