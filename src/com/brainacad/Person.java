package com.brainacad;

import java.util.List;

abstract public class Person {
	private static int currentID = 0;
	public static List<Person> personsList;	
	
	private /*final*/ int id = ++currentID;
	private /*final*/ String firstName;
	private /*final*/ String lastName;
	private List<Course> coursesList;	
	
}
