package com.brainacad.lms.fastivets.control;

import com.brainacad.lms.fastivets.model.Student;
import com.brainacad.lms.fastivets.util.FileOperations;
import com.brainacad.lms.fastivets.util.QuitException;

public class StudentManager extends PersonManager {
	private static StudentManager instance = new StudentManager();

	{
		thisClassName = Student.class.getSimpleName();
		list = FileOperations.readFromFile(thisClassName);
	}
	
	private StudentManager(){}
	
    public static StudentManager getInstance(){
        return (StudentManager)instance;
    }
	
    @Override
	public void createObject() throws QuitException {
		int id = getNewID();
		Student newStudent = new Student(id);		
		changeFirstName(newStudent);
		changeLastName(newStudent);
		changeAge(newStudent);
		changeCourses(newStudent);
		finishCreation(id, newStudent);
		showLong(id);		
	}
	
	private void changeAge (Student thisStudent) throws QuitException {
		while (true){
			try {
				System.out.print("Age: ");
				String age = scanInput.nextLine();
				QuitException.check(age);
				if (isEmpty(age = age.replaceAll("\\D", ""))) throw new IllegalArgumentException("Age shouldn't be empty");
				int thisAge = Integer.parseInt(age);
				if (thisAge > 100) throw new IllegalArgumentException("Age is bigger than 100 years");
				if (thisAge < 18) throw new IllegalArgumentException("Only students 18+ are legal");
				thisStudent.setAge(thisAge);
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another age.\n", e.getMessage());
			}	
		}	 
	}
	
	@Override
	public void showLong (Integer studentID){
		try {
			Student student = (Student)getObject(studentID);
			System.out.printf("ID: %d\nName: %s\nAge: %d\nCourse(s):\n", student.getID(), student.getName(), student.getAge());
			CourseManager.getInstance().show(student.getCourses());
		} catch (IllegalArgumentException e){
			System.err.println(e.getMessage());
		}
	}
	
	public void transferStudent (Integer studentID, Integer courseID, String action){
		try {
			Student student = (Student)getObject(studentID);	//!!!!
			switch (action){
			case "add":
				addPersonToCourse(student, courseID);
				System.out.printf("Student %s %s was enrolled for course %s\n", student.getFirstName(), student.getLastName(), CourseManager.getInstance().getName(courseID));
				break;
			case "remove":
				removePersonFromCourse(student, courseID);
				System.out.printf("Student %s %s was expelled from course %s\n", student.getFirstName(), student.getLastName(), CourseManager.getInstance().getName(courseID));
				break;
			}
		} catch (IllegalArgumentException e){
			System.err.println(e.getMessage());
		}
	}
	


}
