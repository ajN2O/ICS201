package application;


public abstract class Student {

	

	private String name;
	private int id;
	private double GPA;
	
	public Student(String name, int id, double GPA) {
		this.name = name;
		this.id = id;
		this.GPA = GPA;
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getGPA() {
		return GPA;
	}

	public void setGPA(float gPA) {
		GPA = gPA;
	}

	public abstract String getStatus();
	@Override
	public String toString() {
		return "Student name=" + name + ", id=" + id + ", GPA=" + GPA ;
	}
}