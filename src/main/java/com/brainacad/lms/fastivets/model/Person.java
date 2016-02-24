package com.brainacad.lms.fastivets.model;

import java.io.Serializable;
import java.util.TreeSet;

public class Person implements Serializable, Comparable<Person> {
	private static final long serialVersionUID = 1L;
	
	protected final int ID;
	protected String firstName;
	protected String lastName;
	protected TreeSet<Integer> courses = new TreeSet<>();
	
	protected Person(int id) {
		ID = id;
	}
	
	public int getID() {
		return ID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getName(){
		return String.format("%s %s", firstName, lastName);
	}
	
	public TreeSet<Integer> getCourses() {
		return courses;
	}
	
	@Override
	public String toString() {
		return String.format("%d: %s", ID, getName());
	}

	@Override
	public int compareTo(Person person) {
		return ID - person.ID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Person other = (Person) obj;
		if (ID != other.ID)	return false;
		return true;
	}		
}
