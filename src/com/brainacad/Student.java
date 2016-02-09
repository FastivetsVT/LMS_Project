package com.brainacad;

import static com.brainacad.LMS_Main.*;
import java.util.Scanner;
import java.util.TreeSet;

import com.brainacad.LMS_Main.EscException;

public class Student extends Person {	
	private /*final*/ int age;
	
	private Student(){}
	
	public static void create() throws EscException {
		Student newStudent = new Student();
		newStudent.setFirstName();
		newStudent.setLastName();
		newStudent.setAge();
		newStudent.setCourses();
		personsList.put(newStudent.PERSON_ID, newStudent);
		System.out.printf("\nNew student has been successfully created:\n%s",newStudent);
		//saveToFile();
	}
	
	public void setFirstName() throws EscException {
		while (true){
			try {
				System.out.print("First name: ");		
				setFirstName(SCAN_INPUT.nextLine());
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another first name.\n", e.getMessage());
			}
		}		
	}
	
	public void setFirstName(String firstName) throws EscException, IllegalArgumentException {
		if (firstName == null || (firstName = firstName.replaceAll("[^a-zA-Z]", "")).isEmpty()) throw new IllegalArgumentException("First name shouldn't be empty");
		escCheck(firstName);
		firstName = String.format("%.1S%s", firstName,firstName.substring(1).toLowerCase());
		if (lastName != null) {
			final String FIO = firstName + lastName;
			System.out.println("in");
			for (Person person: personsList.values()){
				System.out.println("for");
				if (person.equals(FIO)) throw new IllegalArgumentException("Student name should be unique");
			}
		}
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setLastName() throws EscException {
		while (true){
			try {
				System.out.print("Last name: ");		
				setLastName(SCAN_INPUT.nextLine());
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another last name.\n", e.getMessage());
			}
		}		
	}
	
	public void setLastName(String lastName) throws EscException, IllegalArgumentException {
		if (lastName == null || (lastName = lastName.replaceAll("[^a-zA-Z]", "")).isEmpty()) throw new IllegalArgumentException("First name shouldn't be empty");
		escCheck(firstName);
		lastName = String.format("%.1S%s", lastName,lastName.substring(1).toLowerCase());
		if (firstName != null) {
			final String FIO = firstName + lastName;
			for (Person person: personsList.values()){
				if (person.equals(FIO)) throw new IllegalArgumentException("Student name should be unique");
			}
		}
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setAge(){
		while (true){
			try {
				System.out.print("Age: ");		
				setAge(SCAN_INPUT.nextLine());
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another age.\n", e.getMessage());
			}
		}
	}
	
	public void setAge(String age) throws EscException, IllegalArgumentException {
		if (age == null || age.isEmpty()) throw new IllegalArgumentException("Age shouldn't be empty");
		escCheck(age);
		if ((age = age.replaceAll("\\D", "")).isEmpty()) throw new IllegalArgumentException("Age shouldn't be empty");
		setAge(Integer.parseInt(age));
	}
	
	public void setAge(int age) throws IllegalArgumentException{
		if (age > 100) throw new IllegalArgumentException("Age is bigger than 100 years");
		if (age < 18) throw new IllegalArgumentException("Only students 18+ are legal");
		this.age = age;
	}

	public int getAge() {
		return age;
	}
	
	public void setCourses(){
		while (true){
			try {
				System.out.print("Course(s): ");		
				setCourses(SCAN_INPUT.nextLine());
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another course(s).\n", e.getMessage());
			}
		}
	}
	
	public void setCourses(String courses) throws EscException, IllegalArgumentException {
		if (courses == null || (courses = courses.trim()).isEmpty()) throw new IllegalArgumentException("Course(s) shouldn't be empty");
		Scanner coursesInput = new Scanner(courses).useDelimiter("\\D+");
		coursesList = new TreeSet<>();
		while (coursesInput.hasNext()){
			try {
				addCourse(coursesInput.nextInt());
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
			}			
		}
		if (coursesList.isEmpty()) throw new IllegalArgumentException("Course(s) shouldn't be empty");
	}
	
	public void addCourse(int id) throws IllegalArgumentException{
		Course course = Course.coursesList.get(id);
		if (course == null) throw new IllegalArgumentException(String.format("Course %d doesnТt exist", id));
		if (coursesList.contains(id)) throw new IllegalArgumentException(String.format("Student \"%s %s\" already registred to course %d", firstName, lastName, id));
		if (course.studentsList.size() == 12) throw new IllegalArgumentException(String.format("Course %d cannot have more than 12 students ", id));
		course.studentsList.add(PERSON_ID);
		coursesList.add(id);
	}
	
	public String getCourses(){
		StringBuilder courses  = new StringBuilder();
		for (Integer courseId: this.coursesList)
			courses.append(courseId).append(": ").append(Course.coursesList.get(courseId).getName()).append("\n");
		return courses.toString();
	}

	@Override
	public String toString() {
		return String.format("Student ID: %d\nName: %s %s\nAge: %d\nCourses:\n%s", PERSON_ID, firstName, lastName, age, getCourses());
	}
	//список задач полученных в рамках каждого курса
}