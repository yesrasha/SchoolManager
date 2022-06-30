package model;

import java.io.Serializable;

public abstract class Person implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4687645054790453186L;
	private Name name;
	private static int idNumber;
	private String id;
	
	
	public Person(Name name) {
		this.name = name;
		this.id = ("" + idNumber++);
	}


	public Name getName() {
		return name;
	}


	public void setName(Name name) {
		this.name = name;
	}


	public String getId() {
		return id;
	}
	
	protected void setId(String id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Person [name=" + name + ", id=" + id + "]";
	}


	
	
	 

}
