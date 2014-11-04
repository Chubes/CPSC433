package examSchedule;

public class Instructor {
	private String id;
	
	@Override
	public String toString() {
		return "instructor(" + id + ")";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Instructor(String i){
		id = i;
	}
}
