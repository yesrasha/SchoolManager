package model;

import java.util.function.Predicate;

public interface Bag<T> {
	
	public void insert(T item);
	
	public void addAll(T... items);
	
	public T[] search(Predicate<T> predicate);
	
	public T[] delete(Predicate<T> predicate);
	
	public int size();
	
	public boolean isEmpty();
	
	public void display();

}
