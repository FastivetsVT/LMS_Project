package com.brainacad.lms.fastivets.control;

import java.text.ParseException;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import com.brainacad.lms.fastivets.model.Course;
import com.brainacad.lms.fastivets.util.QuitException;
import com.brainacad.lms.fastivets.util.DateFormat;
import com.brainacad.lms.fastivets.util.DayOfWeek;
import com.brainacad.lms.fastivets.util.FileOperations;

public class CourseManager extends Manager<Course> {
	private static CourseManager instance = new CourseManager();
	private Random rand = new Random();

	{
		thisClassName = Course.class.getSimpleName();
		list = FileOperations.readFromFile(thisClassName);
	}
	 
	private CourseManager(){}
	
    public static CourseManager getInstance(){
        return instance;
    }
    
    @Override
	public void createObject() throws QuitException {
    	Integer id = getNewID();
		Course newCourse = new Course(id);		
		changeName(newCourse);
		changeDescription(newCourse);
		changeStartDate(newCourse);
		changeEndDate(newCourse);
		changeDays(newCourse);
		finishCreation(id, newCourse);
		showLong(id);
	}
	
	private void changeName (Course thisCourse) throws QuitException {
		while (true){
			try {
				System.out.print("Name: ");
				String name = scanInput.nextLine().trim();			
				if (isEmpty(name)) throw new IllegalArgumentException("Name shouldn't be empty");
				QuitException.check(name);
				if (!isUniqueName(name)) throw new IllegalArgumentException("Name should be unique");
				thisCourse.setName(name);
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another name.\n", e.getMessage());
			}	
		}	 
	}
	
	private boolean isUniqueName(String name){
		for (Course course: list.values()){
			if (course.getName().trim().equalsIgnoreCase(name)) return false;
		}
		return true;
	}
	
	private void changeDescription(Course thisCourse) throws QuitException {
		while (true){
			try {
				System.out.print("Description: ");
				String description = scanInput.nextLine().trim();
				if (isEmpty(description)) throw new IllegalArgumentException("Description shouldn't be empty");
				QuitException.check(description);
				thisCourse.setDescription(description);
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another description.\n", e.getMessage());
			}
		}		
	}
	
	private void changeStartDate(Course thisCourse) throws QuitException {
		while (true){
			try {
				System.out.printf("Start date (%s): ", DateFormat.getPattern());
				String startDateString = scanInput.nextLine();
				if (isEmpty(startDateString)) throw new IllegalArgumentException("Start date shouldn't be empty");
				QuitException.check(startDateString);
				Date startDate = DateFormat.parse(startDateString);
				Date endDate = thisCourse.getEndDate();
				if (!isValidStartEndDates(startDate, endDate)) throw new IllegalArgumentException(String.format("Start date (%s) is bigger than end date (%s)", DateFormat.format(startDate), DateFormat.format(endDate)));
				thisCourse.setStartDate(startDate);
				break;
			} catch (ParseException e) {
				System.err.printf("Illegal date format (%s). Please, try again.\n", DateFormat.getPattern());
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another start date.\n", e.getMessage());
			}
		}
	}
	
	private void changeEndDate(Course thisCourse) throws QuitException {
		while (true){
			try {
				System.out.printf("End date (%s): ", DateFormat.getPattern());
				String endDateString = scanInput.nextLine();
				if (isEmpty(endDateString)) throw new IllegalArgumentException("End date shouldn't be empty");
				QuitException.check(endDateString);
				Date endDate = DateFormat.parse(endDateString);
				Date startDate = thisCourse.getStartDate();
				if (!isValidStartEndDates(startDate, endDate)) throw new IllegalArgumentException(String.format("Start date (%s) is bigger than end date (%s)", DateFormat.format(startDate), DateFormat.format(endDate)));
				thisCourse.setEndDate(endDate);
				break;
			} catch (ParseException e) {
				System.err.printf("Illegal date format (%s). Please, try again.\n", DateFormat.getPattern());
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another end date.\n", e.getMessage());
			}
		}
	}
	
	private boolean isValidStartEndDates (Date startDate, Date endDate){
		return startDate == null || endDate == null || startDate.getTime() <= endDate.getTime();
	}
	
	private void changeDays(Course thisCourse) throws QuitException {
		while (true){
			try {
				System.out.print("Days (mon - SUNDAY): ");
				String daysString = scanInput.nextLine();
				if (isEmpty(daysString)) throw new IllegalArgumentException("Days shouldn't be empty");
				QuitException.check(daysString);
				int days = 0;
				String dayInput = null;
				try (Scanner daysInput = new Scanner(daysString.toUpperCase()).useDelimiter("\\W+");){
					while (daysInput.hasNext()){
						days = DayOfWeek.valueOf(dayInput = daysInput.next()).addToBitMask(days);
					}
				} catch (IllegalArgumentException e) {
					System.err.printf("\"%s\" illegal day of week name\n", dayInput);
				}
				if (days == 0) throw new IllegalArgumentException("Days shouldn't be empty");
				thisCourse.setDays(days);
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another days.\n", e.getMessage());
			}
		}
	}
	
	@Override
	public void showLong(Integer courseID){
		try {
			Course course = getObject(courseID);
			System.out.printf("ID: %d\nName: %s\nDescription: %s\nStart date: %s\nEnd date: %s\nDays: %s\n", course.getID(), course.getName(), course.getDescription(), DateFormat.format(course.getStartDate()), DateFormat.format(course.getEndDate()), DayOfWeek.toStringFromBitMask(course.getDays(), false));
		} catch (IllegalArgumentException e){
			System.err.println(e.getMessage());
		}
	}	
	
	public void showStudents(Integer courseID){
		try {
			Course course = getObject(courseID);
			if (isEmpty(course.getStudents())) throw new IllegalArgumentException(String.format("No students enrolled to the course %d yet", courseID));
			System.out.printf("Course: %s\n", course.getName());
			StudentManager.getInstance().show(course.getStudents());
		} catch (IllegalArgumentException e){
			System.err.println(e.getMessage());
		}
	}
	
	public void setTrainerToCourse (Integer trainerID, Integer courseID) throws IllegalArgumentException {
		Course course = getObject(courseID);
		Integer currentID = course.getTrainer();
		if (!isNull(currentID) && currentID.equals(trainerID)) throw new IllegalArgumentException(String.format("Trainer %d has been already registered to course %d yet", trainerID, courseID));
		course.setTrainer(trainerID);
	}
	
	public void addStudentToCourse (Integer studentID, Integer courseID) throws IllegalArgumentException {
		Course course = getObject(courseID);
		TreeSet<Integer> students = course.getStudents();
		if (students.contains(studentID)) throw new IllegalArgumentException(String.format("Student %d has been already registered to course %d yet", studentID, courseID));
		if (students.size() == 12) throw new IllegalArgumentException(String.format("Course %d cannot have more than 12 students ", courseID));
		students.add(studentID);
		TreeMap<Integer, TreeMap<Integer, Integer>> gradebook = course.getGradebook();
		for (Integer taskID: gradebook.keySet()){
			gradebook.get(taskID).put(studentID, getNewMark());
		}		
	}
	
	public void removeTrainerFromCourse (Integer trainerID, Integer courseID) throws IllegalArgumentException {
		Course course = getObject(courseID);
		Integer currentID = course.getTrainer();
		if (!isNull(currentID) && course.getTrainer().equals(trainerID)) throw new IllegalArgumentException(String.format("Trainer %d hasn't been registered to course %d yet", trainerID, courseID));
		course.setTrainer(null);
	}
	
	public void removeStudentFromCourse (Integer studentID, Integer courseID) throws IllegalArgumentException {
		Course course = getObject(courseID);
		TreeSet<Integer> students = course.getStudents();
		if (!students.contains(studentID)) throw new IllegalArgumentException(String.format("Student %d hasn't been registered to course %d yet", studentID, courseID));
		students.remove(studentID);
		TreeMap<Integer, TreeMap<Integer, Integer>> gradebook = course.getGradebook();
		for (Integer taskID: gradebook.keySet()){
			gradebook.get(taskID).remove(studentID);
		}	
	}
	
	protected String getName(Integer courseID){
		return getObject(courseID).getName();
	}
	
	public void addTaskToCourse(Integer courseID, Integer taskID) throws IllegalArgumentException {
		Course course = getObject(courseID);
		TreeMap<Integer, TreeMap<Integer, Integer>> gradebook = course.getGradebook();
		if (gradebook.containsKey(taskID)) throw new IllegalArgumentException(String.format("Course %d already have task %d", courseID, taskID));
		TreeMap<Integer, Integer> taskMarks = new TreeMap<>();
		TreeSet<Integer> students = getObject(courseID).getStudents();
		for (Integer id: students){
			taskMarks.put(id, getNewMark());
		}		
		gradebook.put(taskID, taskMarks);
	}
	
	private Integer getNewMark (){
		return rand.nextInt(101);
	}
	
	public void showGradeBook (Integer courseID) {
		System.out.println(getGradeBook(courseID));
	}
	
	public void saveGradeBook(Integer courseID) {
		FileOperations.saveGradeBookToFile(String.format("%s\\gradebook_%s.txt", System.getProperty("user.dir"), getName(courseID)), getGradeBook(courseID));
	}
	
	private String getGradeBook (Integer courseID) {
		StringBuilder sb = new StringBuilder();
		try {
			Course course = getObject(courseID);
			if (isEmpty(course.getStudents())) throw new IllegalArgumentException(String.format("No students enrolled to the course %d yet", courseID));
			TreeMap<Integer, TreeMap<Integer, Integer>> gradebook = course.getGradebook();
			if (gradebook.isEmpty()) throw new IllegalArgumentException(String.format("No tasks added to the course %d yet", courseID));			
			sb.append(String.format("Course: %s\nTasks:", course.getName()));
			Manager<?> taskManager = TaskManager.getInstance();
			Manager<?> studentManager = StudentManager.getInstance();
			for (Integer taskID: gradebook.keySet()){
				sb.append(String.format("\n%d: %s\n", taskID, taskManager.getName(taskID)));
				TreeMap<Integer, Integer> taskMarks = gradebook.get(taskID);
				Float sum = 0f;
				for (Integer studentID: taskMarks.keySet()){
					Integer mark = taskMarks.get(studentID);
					sb.append(String.format("\t%s: %d%%\n", studentManager.getName(studentID), mark));
					sum+= mark==null?0:mark;
				}
				sb.append(String.format("\t--Average:--%.0f%%", sum/taskMarks.size()));		
			}			
		} catch (IllegalArgumentException e){
			System.err.println(e.getMessage());
		}
		return sb.toString();
	}
	
	
}
