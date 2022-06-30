package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Predicate;

public class PersonBag implements Bag<Person>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1243015711340817993L;
	private Person[] personArr;
	private int nElem;
	private final static int DEFAULT_SIZE = 16;
	private int idCount;
	
	public PersonBag() {
		this(DEFAULT_SIZE);
	}
	
	
	public PersonBag(int initialSize) {
		personArr = new Person[initialSize];
		
	}
	
	public Person[] getArr() {
		return personArr;
	}
	
	@Override
	public void addAll(Person...persons ) {
		
		for(Person p: persons) insert(p);
	}
	
	@Override
	public void insert(Person person) {
		// adjust size if required
		adjustSize();
		personArr[nElem++] = person;
		person.setId("" + idCount++);
	}

	@Override
	public Person[] search(Predicate<Person> predicate) {
		// TODO Auto-generated method stub
		Person[] searchResults = new Person[nElem];
		int idx = 0;
		for(int i = 0; i < nElem; i++) {
			if(predicate.test(personArr[i])) searchResults[idx++] = personArr[i];
		}
		
		return Arrays.copyOf(searchResults, idx);
	}

	@Override
	public Person[] delete(Predicate<Person> predicate) {
		// TODO Auto-generated method stub
		Person[] deletedPeople = new Person[nElem];
		int idx = 0;
		
		for(int i = 0; i < nElem; i++) {
			if(predicate.test(personArr[i])) {
				deletedPeople[idx++] = delete(i);
				i--;
			}
		}
		
		return Arrays.copyOf(deletedPeople, idx);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return nElem;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return nElem == 0;
	}
	
	@Override
	public void display() {
		for(int i = 0; i < nElem; i++) System.out.println(personArr[i]);
	}
	
	
	private Person delete(int idx) {
		//TODO do some index validation
		Person person = personArr[idx];
		for(int i = idx; i < nElem - 1; i++) personArr[i] = personArr[i + 1];
		// decrement nElem and last element is either duplicated or in the edge case we want to delete last element so just set it null
		personArr[--nElem] = null;
		return person;
	}
	
	private boolean isFull() {
		return personArr.length == nElem;
	}
	
	private void resize(int size) {
		personArr = Arrays.copyOf(personArr, size);
	}
	
	private void adjustSize() {
		if(isFull()) resize(size() * 2);
		
		//TODO reduce size when nElem is 1/4th of length of the arr
	}
	
	
}
