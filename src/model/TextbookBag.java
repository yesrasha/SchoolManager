package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Predicate;

public class TextbookBag implements Bag<Textbook>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1482464856244867454L;
	private Textbook[] bookBag;
	private int nElem;
	private final static int DEFAULT_SIZE = 16;

	
	public TextbookBag() {
		this(DEFAULT_SIZE);
	}
	
	
	public TextbookBag(int initialSize) {
		bookBag = new Textbook[initialSize];
		
	}
	
	@Override
	public void addAll(Textbook...tb ) {
		
		for(Textbook b: tb) insert(b);
	}
	
	@Override
	public void insert(Textbook book) {
		// adjust size if required
		adjustSize();
		bookBag[nElem++] = book;

	}

	@Override
	public Textbook[] search(Predicate<Textbook> predicate) {
		// TODO Auto-generated method stub
		Textbook[] searchResults = new Textbook[nElem];
		int idx = 0;
		for(int i = 0; i < nElem; i++) {
			if(predicate.test(bookBag[i])) searchResults[idx++] = bookBag[i];
		}
		
		return Arrays.copyOf(searchResults, idx);
	}

	@Override
	public Textbook[] delete(Predicate<Textbook> predicate) {
		// TODO Auto-generated method stub
		Textbook[] deletedPeople = new Textbook[nElem];
		int idx = 0;
		
		for(int i = 0; i < nElem; i++) {
			if(predicate.test(bookBag[i])) {
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
		for(int i = 0; i < nElem; i++) System.out.println(bookBag[i]);
	}
	
	
	private Textbook delete(int idx) {
		//TODO do some index validation
		Textbook book = bookBag[idx];
		for(int i = idx; i < nElem - 1; i++) bookBag[i] = bookBag[i + 1];
		// decrement nElem and last element is either duplicated or in the edge case we want to delete last element so just set it null
		bookBag[--nElem] = null;
		return book;
	}
	
	private boolean isFull() {
		return bookBag.length == nElem;
	}
	
	private void resize(int size) {
		bookBag = Arrays.copyOf(bookBag, size);
	}
	
	private void adjustSize() {
		if(isFull()) resize(size() * 2);
		
		//TODO reduce size when nElem is 1/4th of length of the arr
	}
}
