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
			System.out.println("TIME PASSED: " +elapsedTime);
			 
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
//Removed Landon
/*			
			while(slots.size()>0){
				
				int r = rnd.nextInt(slots.size());
				tmp = slots.get(r);
				slots.remove(r);
				int i = 0;
				
				while(i< exams.size()){
				
					System.out.println("hi");
					
					if((tmp.room.remainCap - B.studentCount(exams.get(i))) >=0){
						tmp.assignment.add(exams.get(i));
						tmp.room.remainCap -=  B.studentCount(exams.get(i));
						exams.remove(i);
						i--;
					}
					
					i++;
					
				}
								
				temp.add(tmp);
			}
*/			newGen.sessions = temp;
		}while(!newGen.hardConstraints());
		return newGen;
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

