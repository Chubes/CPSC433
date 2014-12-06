package examSchedule.parser;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import examSchedule.*;

/**
 * Set based search.
 *
 */
public class Set {

	static int workingSetSize = 30;
	static int workingSetKeep = 20;
	static int bestSetSize = 10;
	
	static LinkedList<Environment> workingSet;
	static LinkedList<Environment> bestSet;
	static long timeLimit;
	static long startTimer;
	static String inFile;
	
	public Set(Environment env, long maxTime, String outFile){
		this.inFile = outFile;
		this.timeLimit = maxTime;
		this.startTimer = System.currentTimeMillis();
		Environment kontrol = env;
		bestSet = new LinkedList<Environment>();
		workingSet = new LinkedList<Environment>();
		
		while((System.currentTimeMillis() - startTimer) < timeLimit) {
			search(kontrol);
		}
		System.out.println("DONE");
		
		System.out.println("Penalty:" + bestSet.get(0).utility);
		bestSet.get(0).printOutput(outFile);
	}
	
	
	/**
	 * Generates the current working set and best set of possible 
	 * solutions as well as mutates the working set for the next time 
	 * search is invoked.
	 * 
	 * @param kontrol Control environment
	 */
	public static void search(Environment kontrol){

		// fill open slots in the working set
		while(workingSet.size() < workingSetSize){
			Environment E = generateNew(kontrol);
			if(E != null){
				workingSet.add(E);
			}
		}
		
		sortList(workingSet);
		sortList(bestSet);

		// Keeping data for best sets
		for(int i = 0; i < bestSetSize; i++ ){
			bestSet.add(workingSet.get(i));
		}
		// cleanup best set
		sortList(bestSet);
		while(bestSet.size() >= bestSetSize){
			bestSet.remove((bestSet.size()-1));
		}
		
		// Manipulating the working set
		// Add top 5 best 
		for(int i = 0; i < 5; i++){
			workingSet.add(bestSet.get(i));
		}
		sortList(workingSet);
		// Remove near the worst but not worst
		for(int i = (workingSetSize-6); i > workingSetKeep; i-- ){
			workingSet.remove(i);
		}

		// attempt to mutate everything!
		for(int i = (workingSet.size()-1); i>1; i--){
			for(int j = 0; j < 10; j++) {
				if(mutate(workingSet.get(i))){
					break;
				}
			}
		}
	}
	
	//Mike's Generation function
	/**
	 * Generates a new random solution set from the input set.
	 * 
	 * @param B control environment
	 * @return Environment with new session assignments
	 */
	public static Environment generateNew(Environment B){
		
		Environment newGen = new Environment("Gen");
		Environment garbage = new Environment("Garb");
		Random rnd = new Random();
		
		do{ 
			// "B" would have been copied to the "garbage" environment to work from,
			// however issues in cloning an environment, or lack-there-of due to
			// some quirks with java forced us to do this. "B" is "kontrol" is "inFile".
			newGen.fromFile(inFile);
			garbage.fromFile(inFile);
			
			ArrayList<Pair<Course,Lecture>> exams = getLec(garbage.courses);
			ArrayList<Session> slots = garbage.sessions;
			ArrayList<Session> temp = new ArrayList<Session>();
			
			// Find non-conflicting pre-assignments
			for(Session S : slots){ 
				while(S.assignment.size() > 0){
					Pair<Course,Lecture> pair = S.assignment.removeFirst();
					if(S.sessionLength >= pair.getValue().examLength){
						S.room.remainCap -= garbage.studentCount(pair);
						for(int i = 0; i < exams.size(); i++){
							if(exams.get(i).getKey().equals(pair.getKey()) && exams.get(i).getValue().equals(pair.getValue())){
								exams.remove(i);
								break;
							}
						}
					}
				}
			}

			// Randomly assign exams.
			while(slots.size()>0){
				int r =rnd.nextInt(slots.size());
				Session tmp = slots.get(r);
				tmp.room.remainCap = tmp.room.capacity;

				int i = 0;
				while(i < exams.size()){
					if(((tmp.room.remainCap - garbage.studentCount(exams.get(i))) >= 0) && (exams.get(i).getValue().examLength <= tmp.sessionLength)){
						tmp.assignment.add(exams.get(i));
						tmp.room.remainCap -=  garbage.studentCount(exams.get(i));
						exams.remove(i);
					}
					else{
						i++;
					}
				}
				slots.remove(r);
				temp.add(tmp);
			}
			
			// Move them into the working set.
			// Due to the inability to properly clone an Environment,
			// this was the next best method.
			for(int i = 0; i < temp.size(); i++){
				String s;
				String c;
				String lec;
				s = temp.get(i).name;
				for(int j = 0; j < temp.get(i).assignment.size(); j++){
					c =  temp.get(i).assignment.get(j).getKey().name;
					lec = temp.get(i).assignment.get(j).getValue().name;
					newGen.a_lecture(temp.get(i).assignment.get(j).getValue().course, temp.get(i).assignment.get(j).getValue().name, temp.get(i).assignment.get(j).getValue().instructor, temp.get(i).assignment.get(j).getValue().examLength); 
					
					newGen.a_assign(c, lec, s);
				}
				
			}
		}while(!newGen.hardConstraints() && ((System.currentTimeMillis() - startTimer) < timeLimit));
	
		return newGen;
	}
	
	/**
	 * Swap two assignments between 2 sessions OR move 1 assignment.
	 * Returns false if it failed to mutate.
	 * 
	 * @param env Environment to be manipulated
	 * @return mutated environment
	 */
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
		}
		Session S1 = env.sessions.get(s1r);
		Session S2 = env.sessions.get(s2r);
		// Ensure they are different and at least one has an assignment that can be swapped.
		if(!S1.name.equals(S2.name) && (S2.assignment.size() > 0)){
			if(swapOrMove){
				// Move from S2 to S1
				s2r = rnd.nextInt(S2.assignment.size());
				if((S2.assignment.get(s2r).getValue().examLength <= S1.sessionLength)
						&& (env.studentCount(S2.assignment.get(s2r)) <= S1.room.remainCap)) {
					
					// Move <c,lec> from S2 to S1 and update remainingCapacities.
					S2.room.remainCap += env.studentCount(S2.assignment.get(s2r));
					S1.room.remainCap -= env.studentCount(S2.assignment.get(s2r));
					S1.assignment.add(S2.assignment.get(s2r));
					S2.assignment.remove(s2r);	
					
					// Swap occurred
					if(env.hardConstraints()){
						return true;
					}
				}	
			}
			else{
				// Session 1 cannot have nothing assigned to it.
				if(S1.assignment.size() > 0){
					// Session 1 has assignments, swap with Session 2
					s1r = rnd.nextInt(S1.assignment.size());
					s2r = rnd.nextInt(S2.assignment.size());
					
					// Check exam times fit each other
					if((S1.assignment.get(s1r).getValue().examLength <= S2.sessionLength)
							&& (S2.assignment.get(s2r).getValue().examLength <= S1.sessionLength)) {
						
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
							
							// Swap occurred
							if(env.hardConstraints()){
								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}
	
	/**
	 * Generates all of the course/lecture pairs.
	 * 
	 * @param courses
	 * @return list
	 */
	public static ArrayList<Pair<Course, Lecture>> getLec(ArrayList<Course> courses){
		ArrayList <Pair<Course, Lecture>> examsList = new ArrayList<Pair<Course, Lecture>>();
		for(int i = 0; i < courses.size(); i++){
			for(int j =0; j < courses.get(i).lecture.size(); j++){
				examsList.add(new Pair<Course, Lecture>(courses.get(i), courses.get(i).lecture.get(j)));
			}
		}
		return examsList;
	}
	
	/**
	 * Sorts a list of Environments based on soft constraint penalties.
	 * Lowest penalty Environment is at the head of the list.
	 * 
	 * @param list List of Environments.
	 */
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

