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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((enrolled == null) ? 0 : enrolled.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (enrolled == null) {
			if (other.enrolled != null)
				return false;
		} else if (!enrolled.equals(other.enrolled))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
