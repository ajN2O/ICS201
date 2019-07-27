package application;


public class Graduate extends Student {

	public Graduate(String name, int id, double GPA) {
		super(name, id, GPA);
	}
	
	@Override
	public String getStatus() {
		String standing = "Good";
		
		if (getGPA() < 3.0)
			standing = "Probation";
		
		return standing;
	}
}