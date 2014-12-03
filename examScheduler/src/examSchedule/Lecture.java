package examSchedule;

public class Lecture {

	public String name;
	public String course;
	public String instructor;
	public Long examLength;
	
	public Lecture(String l, String c){
		name = l;
		course = c;
		instructor = "";
		examLength = new Long(0);
	}
	public Lecture(String l, String c, String i, Long len){
		name = l;
		course = c;
		instructor = i;
		examLength = len;
	}

}
