package com.brainacad;

import java.util.TreeMap;
import java.util.TreeSet;

abstract public class Person implements Storeable {
	private static final long serialVersionUID = 1L;
	public static TreeMap<Integer, Person> personsList = new TreeMap<>();
	
	protected final int PERSON_ID = personsList.isEmpty()?1:personsList.lastKey()+1;
	protected /*final*/ String firstName;
	protected /*final*/ String lastName;
	protected TreeSet<Integer> coursesList;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + PERSON_ID;
		result = prime * result + ((coursesList == null) ? 0 : coursesList.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;		
		if (getClass() == obj.getClass()) {
			Person other = (Person) obj;
			if (PERSON_ID != other.PERSON_ID) return false;
			if (firstName == null) {
				if (other.firstName != null)	return false;
			} else if (!firstName.equals(other.firstName)) return false;
			if (lastName == null) {
				if (other.lastName != null)	return false;
			} else if (!lastName.equals(other.lastName)) return false;
		} else if (obj instanceof String){
			return (firstName + lastName).equals((String)obj);
		} else if (obj instanceof Integer) {
			return ((Integer) obj).intValue() == PERSON_ID;
		}
		return true;
	}
		
}
