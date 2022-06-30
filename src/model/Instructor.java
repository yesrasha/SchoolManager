package model;

import java.util.Random;

public class Instructor extends Person {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String rank;
	private double salary;
	
	private static final String[] RANKS = {"Instructor","Asistant Professor","Associate Professor","Professor"};

	public Instructor(Name name,String rank,double salary) {
		super(name);
		this.rank = rank;
		this.salary = salary;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public static String getRandomRank() {
		return RANKS[new Random().nextInt(RANKS.length)];
	}

	@Override
	public String toString() {
		return "Instructor [rank=" + rank + ", salary=" + salary + ", toString()=" + super.toString() + "]";
	}
	
	
}
