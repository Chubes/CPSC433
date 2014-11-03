package examSchedule;

public class At {
	private Session session = new Session();
	private int time = 0;
	private int length = 0;
	
	public At(Session S, int time, int length) {
		// There needs to be an if-clause to check for Session.dayName
		// If Session.dayName is null, this object should not be created.
		
		this.time = time;
		this.length = length;

		// If Session.sessionName does not exist, create.
		// if Session.dayName does not exist, create.
		this.session = S;
	}
}
