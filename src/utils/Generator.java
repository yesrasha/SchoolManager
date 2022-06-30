package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import model.Instructor;
import model.Name;
import model.PersonBag;
import model.Student;
import model.Textbook;
import model.TextbookBag;

public class Generator {

	private static final int DEFAULT_STUDENT_LIMIT = 1000, DEFAULT_INSTRUCTOR_LIMIT = 500;

	private final List<String> FIRST_NAMES, LAST_NAMES, MAJORS, TITLES, ISBN;

	private final String DATA_P = "data/", FNAMES_P = DATA_P + "names/FirstNames.txt",
			LNAMES_P = DATA_P + "names/LastNames.txt", MAJOR_P = DATA_P + "majors/Majors.txt",
			ISBN_P = DATA_P + "Textbooks/textbook_isbns.txt", TITLES_P = DATA_P + "Textbooks/textbook_titles.txt";

	private final double GPA_LIMIT = 4.0d, LOWER_SALARY = 10_000d, UPPER_SALARY = 100_000d;

	public Generator() {

		FIRST_NAMES = new ArrayList<>();
		LAST_NAMES = new ArrayList<>();
		MAJORS = new ArrayList<>();
		TITLES = new ArrayList<>();
		ISBN = new ArrayList<>();

		readNames();
		readMajors();
		readTitles();
		readISBN();
	}

	public PersonBag getPersonBag() {
		PersonBag bag = new PersonBag();
		bag.addAll(getStudents(DEFAULT_STUDENT_LIMIT));
		bag.addAll(getInstructors(DEFAULT_INSTRUCTOR_LIMIT));

		return bag;
	}

	public TextbookBag getTextbookBag() {
		// TODO
		TextbookBag bag = new TextbookBag();
		System.out.println(TITLES.size() == ISBN.size());
		bag.addAll(getBooks());
		return bag;
	}

	public Textbook[] getBooks() {
		final int N = TITLES.size();
		Textbook[] books = new Textbook[N];
		for (int i = 0; i < N; i++)
			books[i] = new Textbook(TITLES.get(i), ISBN.get(i), getRName(), getRPrice());
		return books;
	}

	private double getRPrice() {
		// TODO Auto-generated method stub
		return randomDouble(0, 200);
	}

	private void readTitles() {

		Scanner sc;
		try {
			sc = new Scanner(new File(TITLES_P));
			while (sc.hasNextLine())
				TITLES.add(sc.nextLine());
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

	private void readISBN() {

		Scanner sc;
		try {
			sc = new Scanner(new File(ISBN_P));
			while (sc.hasNextLine())
				ISBN.add(sc.nextLine());
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/* Instructor generation methods */

	public Instructor[] getInstructors(int N) {
		Instructor[] instructors = new Instructor[N];
		for (int i = 0; i < N; i++)
			instructors[i] = getInstructor();
		return instructors;
	}

	public Instructor getInstructor() {
		return new Instructor(getRName(), Instructor.getRandomRank(), getRSalary());
	}

	private double getRSalary() {
		return randomDouble(LOWER_SALARY, UPPER_SALARY);
	}

	/* Student generation methods */

	public Student[] getStudents(int N) {
		// TODO
		Student[] students = new Student[N];
		for (int i = 0; i < N; i++)
			students[i] = getStudent();
		return students;
	}

	public Student getStudent() {
		// returns a randomly generated instance of Student
		return new Student(getRName(), getRmajor(), getRGpa());
	}

	private String getRmajor() {
		return getRFromList(MAJORS);
	}

	private void readMajors() {

		Scanner sc;
		try {
			sc = new Scanner(new File(MAJOR_P));
			String[] majors = sc.nextLine().split(",");
			for (String m : majors)
				MAJORS.add(m.strip());
			sc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private double getRGpa() {
		return round(randomDouble(0, GPA_LIMIT),1);
	}

	// *********************STUDENT END****************************

	// NAME GENERATION METHODS

	private Name getRName() {
		return new Name(getRFname(), getRLname());
	}

	private String getRLname() {
		// returns a random last name
		return getRFromList(LAST_NAMES);
	}

	private String getRFname() {
		// returns a random first name
		return getRFromList(FIRST_NAMES);
	}

	private String getRFromList(List<String> lst) {
		final int N = lst.size();
		return lst.get(new Random().nextInt(N));

	}

	private void readNames() {
		readFirstNames();
		readLastNames();
	}

	private void readLastNames() {
		Scanner sc;
		try {
			sc = new Scanner(new File(LNAMES_P));
			while (sc.hasNextLine())
				LAST_NAMES.add(sc.nextLine());
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void readFirstNames() {

		Scanner sc;
		try {
			sc = new Scanner(new File(FNAMES_P));
			while (sc.hasNextLine())
				FIRST_NAMES.add(sc.nextLine());
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	// -------------------------------------

	public static double randomDouble(double min, double max) {
		return (ThreadLocalRandom.current().nextDouble() * (max - min)) + min;
	}
	
	public static double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}

	public static void main(String[] args) {
		Generator g = new Generator();
		PersonBag bag = g.getPersonBag();
//		bag.display();
//
//		Person[] students = bag.search(p -> p instanceof Student);
//		Person[] instructors = bag.search(p -> p instanceof Instructor);
//
//		System.out.println(students.length);
//		System.out.println(instructors.length);

		g.getTextbookBag().display();

	}

}
