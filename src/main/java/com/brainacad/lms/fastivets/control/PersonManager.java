package com.brainacad.lms.fastivets.control;

import java.util.Scanner;
import com.brainacad.lms.fastivets.model.Person;
import com.brainacad.lms.fastivets.model.Student;
import com.brainacad.lms.fastivets.model.Trainer;
import com.brainacad.lms.fastivets.util.QuitException;

abstract class PersonManager extends Manager<Person> {

	protected PersonManager(){}
	
	protected void changeFirstName (Person thisPerson) throws QuitException {
		while (true){
			try {
				System.out.print("First name: ");
				String firstName = scanInput.nextLine();
				QuitException.check(firstName);
				if (isEmpty(firstName = firstName.replaceAll("[^a-zA-Z]", ""))) throw new IllegalArgumentException("First name shouldn't be empty");
				firstName = String.format("%.1S%s", firstName,firstName.substring(1).toLowerCase());
				if (!isUniqueName(firstName, thisPerson.getLastName())) throw new IllegalArgumentException(String.format("%s name should be unique", thisClassName));
				thisPerson.setFirstName(firstName);
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another first name.\n", e.getMessage());
			}	
		}	 
	}
	
	protected void changeLastName (Person thisPerson) throws QuitException {
		while (true){
			try {
				System.out.print("Last name: ");
				String lastName = scanInput.nextLine();
				QuitException.check(lastName);
				if (isEmpty(lastName = lastName.replaceAll("[^a-zA-Z]", ""))) throw new IllegalArgumentException("Last name shouldn't be empty");
				lastName = String.format("%.1S%s", lastName,lastName.substring(1).toLowerCase());
				if (!isUniqueName(thisPerson.getFirstName(), lastName)) throw new IllegalArgumentException(String.format("%s name should be unique", thisClassName));
				thisPerson.setLastName(lastName);
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another last name.\n", e.getMessage());
			}	
		}	 
	}
	
	private boolean isUniqueName(String firstName, String lastName){
		if (lastName == null) return true;
		for (Person person: list.values()){
			if (person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName)) return false;
		}
		return true;
	}	
	
	protected void changeCourses (Person thisPerson) throws QuitException {
		while (true){
			try {
				System.out.print("Course(s): ");
				String coursesString = scanInput.nextLine();			
				if (isEmpty(coursesString)) throw new IllegalArgumentException("Course(s) shouldn't be empty");
				QuitException.check(coursesString);
				Scanner coursesScanner = new Scanner(coursesString).useDelimiter("\\D+");
				while (coursesScanner.hasNext()){
					try{
						addPersonToCourse(thisPerson, coursesScanner.nextInt());
					} catch (IllegalArgumentException e) {
						System.err.println(e.getMessage());
					}
				}
				if (isEmpty(thisPerson.getCourses())) throw new IllegalArgumentException("Course(s) shouldn't be empty");
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another course(s).\n", e.getMessage());
			}	
		}	 
	}
	
	protected void addPersonToCourse (Person person, Integer courseID) throws IllegalArgumentException{
		if (person instanceof Student) {
			CourseManager.getInstance().addStudentToCourse(person.getID(), courseID);	
		} else if (person instanceof Trainer) {
			CourseManager.getInstance().setTrainerToCourse(person.getID(), courseID);
		}
				
		person.getCourses().add(courseID);
	}
	
	protected void removePersonFromCourse (Person person, Integer courseID) throws IllegalArgumentException{
		if (person instanceof Student) {
			CourseManager.getInstance().removeStudentFromCourse(person.getID(), courseID);	
		} else if (person instanceof Trainer) {
			CourseManager.getInstance().removeTrainerFromCourse(person.getID(), courseID);	
		}				
		person.getCourses().remove(courseID);
	}
	
	@Override
	protected String getName(Integer personID){
		return getObject(personID).getName();
	}
}