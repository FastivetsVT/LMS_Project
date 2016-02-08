package com.brainacad;

import java.util.List;

abstract public class Person {
	public static List<Person> personsList;	
	
	protected final int ID = personsList.size()+1;
	protected /*final*/ String firstName;
	protected /*final*/ String lastName;
	protected List<Course> coursesList;	
	
}
