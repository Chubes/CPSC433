package examSchedule;

import java.util.LinkedList;

import examSchedule.parser.Pair;

public class Instructor {
	public String name;
	public LinkedList<Course> teaches;
	public LinkedList<Lecture> lectures;
	public LinkedList<Pair<Course,Lecture>> instructs;
	
	public Instructor(String i){
		name = i;
		teaches = new LinkedList<Course>();
		lectures = new LinkedList<Lecture>();
		instructs = new LinkedList<Pair<Course,Lecture>>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((instructs == null) ? 0 : instructs.hashCode());
		result = prime * result
				+ ((lectures == null) ? 0 : lectures.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((teaches == null) ? 0 : teaches.hashCode());
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
		Instructor other = (Instructor) obj;
		if (instructs == null) {
			if (other.instructs != null)
				return false;
		} else if (!instructs.equals(other.instructs))
			return false;
		if (lectures == null) {
			if (other.lectures != null)
				return false;
		} else if (!lectures.equals(other.lectures))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (teaches == null) {
			if (other.teaches != null)
				return false;
		} else if (!teaches.equals(other.teaches))
			return false;
		return true;
	}
}
