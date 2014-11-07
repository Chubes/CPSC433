package examSchedule;

public class Day {

	private String day;
	
	public Day(String d){
		day = d;
	}

	@Override
	public String toString() {
		return "day(" + day + ")";
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
}
