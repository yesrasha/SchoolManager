package model;

import java.util.Arrays;

public class PersonBagDemo {
	
	public static void main(String[] args) {
		
		PersonBag bag = new PersonBag();
		
		bag.insert(new Student(
				 new Name("Rasha","Loser"),
				"Computer Science",
				 3.8
				));
		
		bag.insert(new Instructor(
				new Name("Russel","Coe"),
				"Associate Professor",
				 80_000d
				));
		
		bag.insert(new Instructor(
				new Name("Ben","Chen"),
				"Chair of Computer Science",
				 80_000d
				));
		
		//bag.display();
		
		System.out.println(Arrays.toString(bag.delete(person -> person instanceof Instructor)));
		
		bag.display();
		
	}

}
