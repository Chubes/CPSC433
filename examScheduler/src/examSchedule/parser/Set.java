package examSchedule.parser;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import examSchedule.*;

public class Set {

	static int workingSetSize = 30;		// number of facts in the working set
	static int bestSetSize = 10;	// number of facts in the all-time-best set
	
	static LinkedList<Environment> workingSet;
	static LinkedList<Environment> bestSet;
	final long timeLimit;
	







	public Set(Environment env, long timeLimit, String outFile){
		
		this.timeLimit = timeLimit;
		long startTime = System.currentTimeMillis();
		Environment kontrol = env;
		
		while ((System.currentTimeMillis() - startTime)< timeLimit ) {
			 
			 search(kontrol);
			 long elapsedTime = (System.currentTimeMillis() - startTime);
			 System.out.println("TIME PASSED: " +elapsedTime);
			 
		 }
		 
		 System.out.println("DONE");
		 
		 bestSet.get(0).printOutput(outFile);
	 }
	
public static void search(Environment kontrol){
		
		// temp holder for the next set
		LinkedList<Environment> tempSet = new LinkedList<Environment>();
		
		
		// ext fill ( seed for the first iteration )
		while(workingSet.size() < workingSetSize){
			Environment candidate = generateNew(kontrol);
			if(candidate != null){
				workingSet.add(candidate);
			}
		}
		
		
		orderList(workingSet);
		orderList(bestSet);
		
		
		
	}
	
public static Environment generateNew(Environment base){
	Environment newgenerate = base.clone();
	Random rnd = new Random();
	
	ArrayList<Pair<Course,Lecture>> avaible = getPair(base.courses);
	
	
	
}

public static ArrayList<Pair<Course, Lecture>> getLec(ArrayList<Course> courses){
	
	for(int i = 0; i < courses.size(); i++){
		
	}
			
	
	return null;
	
}
}

