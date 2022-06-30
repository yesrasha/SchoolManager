package model;

import java.io.Serializable;

public class Name implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5417612793001141926L;
	
	private String lastName,firstName;

	public Name(String firstName, String lastName) {
		
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
	
	
	
	

}
