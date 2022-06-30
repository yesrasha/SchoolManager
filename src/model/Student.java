package model;

public class Student extends Person {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5205696566630422262L;
	private double gpa;
	private String major;

	public Student(Name name,String major, double gpa) {
		super(name);
		this.major = major;
		this.gpa = gpa;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Override
	public String toString() {
		return "Student [gpa=" + gpa + ", major=" + major + ", toString()=" + super.toString() + "]";
	}
	
	

}
