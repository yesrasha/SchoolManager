package utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import model.PersonBag;
import model.TextbookBag;

public class BackUpRestoreTools {

	public static PersonBag restorePersonBag() {
		PersonBag personBag = new PersonBag();
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream("data/Persons.dat")));
			personBag = (PersonBag) ois.readObject();
			ois.close();
			return personBag;
		} catch (IOException e) {

			return null;
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return personBag;
	}

	public static void backupPersonBag(PersonBag personBag) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream("data/Persons.dat", false)));
			oos.writeObject(personBag);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public static TextbookBag restoreTextbookBag() {
		TextbookBag textbookBag = new TextbookBag(1000);

		try {
			ObjectInputStream ois = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream("data/Textbooks.dat")));
			textbookBag = (TextbookBag) ois.readObject();
			ois.close();
		}

		catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return textbookBag;
	}

	public static void backupTextbookBag(TextbookBag textbookBag) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream("data/Textbooks.dat", false)));
			oos.writeObject(textbookBag);
			oos.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}
}