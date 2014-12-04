package examSchedule.parser;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import examSchedule.*;

public class Set {

	static int workingSetSize = 10;
	static int bestSetSize = 5;
//	static int workingSetSize = 30;		// number of facts in the working set
//	static int bestSetSize = 10;	// number of facts in the all-time-best set
	
	static LinkedList<Environment> workingSet;
	static LinkedList<Environment> bestSet;
	final long timeLimit;

	public Set(Environment env, long maxTime, String outFile){
		
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
		 
		 bestSet.get(0).printOutput(outFile);
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
				E.printOutput("test.txt" + workingSet.size());
			}
		}
		
//Temporary removal
//		sortList(workingSet);
		
/*bestSet.
		for(int i = 0, i < bestSetSize; i++){
			bestSet.
		}
		sortList(bestSet);
*/
		
	}
	//Mike's Generation function
	public static Environment generateNew(Environment B){
		
		Environment newGen = new Environment("clone");
		Random rnd = new Random();
		
		//do{ 
			newGen = B.clone();
			newGen.printOutput("A");
			//System.out.println(newGen.sessions.size());
			
			ArrayList<Pair<Course,Lecture>> exams = getLec(newGen.courses);
			ArrayList<Session> slots = newGen.sessions;
			System.out.println(slots.size());
			ArrayList<Session> temp = new ArrayList<Session>();
			Session tmp = new Session("");
//Debug		
			System.out.println("S:" + slots.size());			
			while(slots.size()>0){
				
				int r =rnd.nextInt(slots.size());
				System.out.println("slots r Room Space: " +  slots.get(r).room.remainCap);
				slots.get(r).room.remainCap = slots.get(r).room.capacity;
				tmp = slots.get(r);
				slots.remove(r);
				
				int i = 0;
				
				while(i < exams.size()){
				
//					System.out.println("hi");
					try{
					System.out.println("tmp Course Space: " +  tmp.assignment.get(0).getKey().name);
					}catch(IndexOutOfBoundsException e){
						System.out.println("empty");
					}
					System.out.println("tmp Room Space left: " +  (tmp.room.remainCap- B.studentCount(exams.get(i))));	
					if(((tmp.room.remainCap - B.studentCount(exams.get(i))) >= 0) && exams.get(i).getValue().examLength <= tmp.sessionLength){
						System.out.println("HEY" +  exams.size());
						tmp.assignment.add(exams.get(i));
						tmp.room.remainCap -=  B.studentCount(exams.get(i));
						exams.remove(i);
						
						System.out.println("E:" +exams.size());
					}
					else{
						//System.out.println("HEY" +  i);
						i++;
					}
					
					
				}
				System.out.println("tmp Course Space: " +  tmp.assignment.get(0).getKey().name);			
				temp.add(tmp);

//Debug		
				System.out.println("T:" +temp.size());
//Debug
				
			}
			newGen.sessions = B.sessions;
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
			
			newGen.printOutput("test");
		//}while(!newGen.hardConstraints());
	
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

