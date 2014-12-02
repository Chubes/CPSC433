package examSchedule;

import java.util.LinkedList;

import examSchedule.parser.Pair;

public class Session {
	public String name;
	public String day;
	public long time;
	public long sessionLength;
	public Room room;
	public LinkedList<Lecture> lectures;
	public LinkedList<Pair<Course,Lecture>> assignment;
	
	public Session(String n){
		name = n;
		lectures = new LinkedList<Lecture>();
		assignment = new LinkedList<Pair<Course,Lecture>>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((assignment == null) ? 0 : assignment.hashCode());
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result
				+ ((lectures == null) ? 0 : lectures.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result
				+ (int) (sessionLength ^ (sessionLength >>> 32));
		result = prime * result + (int) (time ^ (time >>> 32));
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
		Session other = (Session) obj;
		if (assignment == null) {
			if (other.assignment != null)
				return false;
		} else if (!assignment.equals(other.assignment))
			return false;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
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
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (sessionLength != other.sessionLength)
			return false;
		if (time != other.time)
			return false;
		return true;
	}
}
