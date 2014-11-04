package examSchedule;

public class Student {
	private String id;
	
	@Override
	public String toString() {
		return "student(" + id + ")";
	}

	public Student(String s){
		setId(s);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
