package com.brainacad;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import static com.brainacad.LMS_Main.*;

public class Course implements Storeable {	
	static {
		coursesList = new ArrayList<Course>();
		readFromFile();
	}
	private static final long serialVersionUID = 1L;	
	public static List<Course> coursesList;
	
	private final int ID = coursesList.size()+1;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private Set<DayOfWeek> days;
	
	private Course(){}
	
	public static void createCourse() throws EscException {
		Course newCourse = new Course();
		newCourse.setName();
		newCourse.setDescription();
		newCourse.setStartDate();
		newCourse.setEndDate();
		newCourse.setDays();
		coursesList.add(newCourse);		
		saveToFile();
		System.out.printf("\nNew course has been successfully created:\n%s",newCourse);
	}
	
		public void setName() throws EscException {
		name = null;
		while (name == null){
			try {
				System.out.print("Course name: ");		
				setName(SCAN_INPUT.nextLine());
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another name.\n", e.getMessage());
			}
		}		
	}

	public void setName(String name) throws EscException, IllegalArgumentException {
		if (name == null || (name = name.trim()).isEmpty()) throw new IllegalArgumentException("Course name shouldn't be empty");
		if (ESC_COMMAND.matcher(name.toLowerCase()).matches()) throw new EscException("Last action cancelled.");
		for (Course course: coursesList){
			if (course.equals(name)) throw new IllegalArgumentException("Course name should be unique");
		}			
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setDescription() throws EscException {
		description = null;
		while (description == null){
			try {
				System.out.print("Course description: ");
				setDescription(SCAN_INPUT.nextLine());
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another description.\n", e.getMessage());
			}
		}	
	}
	
	public void setDescription(String description) throws EscException, IllegalArgumentException {
		if (description == null || (description = description.trim()).isEmpty()) throw new IllegalArgumentException("Course description shouldn't be empty");
		if (ESC_COMMAND.matcher(description.toLowerCase()).matches()) throw new EscException("Last action cancelled.");		
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setStartDate() throws EscException {
		startDate = null;
		while (startDate == null){
			try {
				System.out.print("Start date: ");
				setStartDate(SCAN_INPUT.nextLine());
			} catch (ParseException e) {
				System.err.printf("Illegal date format (%s). Please, try again.\n", DATE_FORMAT.toPattern());
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another start date.\n", e.getMessage());
			}
		}
	}
	
	public void setStartDate(String startDate) throws EscException, ParseException, IllegalArgumentException {
		if (startDate == null) throw new IllegalArgumentException("Start date shouldn't be empty");
		if (ESC_COMMAND.matcher(startDate.toLowerCase()).matches()) throw new EscException("Last action cancelled.");
		setStartDate(DATE_FORMAT.parse(startDate));
	}
	
	public void setStartDate(Date startDate) throws IllegalArgumentException {
		if (startDate == null) throw new IllegalArgumentException("Start date shouldn't be empty");
		if (endDate != null && startDate.getTime() > endDate.getTime()) throw new IllegalArgumentException(String.format("Start date (%s) is bigger than end date (%s)", DATE_FORMAT.format(startDate), DATE_FORMAT.format(endDate)));
		this.startDate = startDate;
	}
	
	public Date getStartDate() {
		return startDate;
	}	
	
	public void setEndDate() throws EscException {
		endDate = null;
		while (endDate == null){
			try {
				System.out.print("End date: ");
				setEndDate(SCAN_INPUT.nextLine());	
			} catch (ParseException e) {
				System.err.printf("Illegal date format (%s). Please, try again.\n", DATE_FORMAT.toPattern());
			} catch (IllegalArgumentException e) {
				System.err.printf("%s. Please, enter another end date.\n", e.getMessage());
			}	
		}
	}
	
	public void setEndDate(String endDate) throws EscException, ParseException, IllegalArgumentException {
		if (endDate == null) throw new IllegalArgumentException("End date shouldn't be empty");
		if (ESC_COMMAND.matcher(endDate.toLowerCase()).matches()) throw new EscException("Last action cancelled.");
		setEndDate(DATE_FORMAT.parse(endDate));
	}

	public void setEndDate(Date endDate) throws IllegalArgumentException {
		if (endDate == null) throw new IllegalArgumentException("End date shouldn't be empty");
		if (startDate != null && startDate.getTime() > endDate.getTime()) throw new IllegalArgumentException(String.format("Start date (%s) is bigger than end date (%s)", DATE_FORMAT.format(startDate), DATE_FORMAT.format(endDate)));
		this.endDate = endDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setDays() throws EscException {
		days = null;
		while (days == null){
			try {
				System.out.print("Days: ");
				setDays(SCAN_INPUT.nextLine());
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, try again.\n", e.getMessage());
			}			
		}		
	}
	
	public void setDays(String days) throws EscException, IllegalArgumentException {
		if (days == null || days.trim().isEmpty()) throw new IllegalArgumentException("Days shouldn't be empty");
		if (ESC_COMMAND.matcher(days.toLowerCase()).matches()) throw new EscException("Last action cancelled.");
		Set<DayOfWeek> daysSet = new TreeSet<>();
		Scanner daysInput = new Scanner(days.toUpperCase()).useDelimiter("\\W+");
		String dayInput = null;		
		while (daysInput.hasNext()){
			try {
				daysSet.add(DayOfWeek.valueOf(dayInput = daysInput.next()));					
			} catch (IllegalArgumentException e) {
				System.err.printf("\"%s\" illegal day of week name\n", dayInput);
			}
		}
		setDays(daysSet);
	}
	
	public void setDays(Set<DayOfWeek> days) throws IllegalArgumentException {
		if (days == null || days.isEmpty()) throw new IllegalArgumentException("Days shouldn't be empty");
		this.days = days;
	}
	
	public Set<DayOfWeek> getDays() {
		return days;
	}
	
	@Override
	public String toString() {
		StringBuilder daysFormat = new StringBuilder();
		for (DayOfWeek dow: days) daysFormat.append(dow.getDisplayName(TextStyle.SHORT, LOCALE)).append(", ");
		return String.format("Course ID: %d\nCourse name: %s\nCourse description: %s\nStart date: %s\nEnd date: %s\nDays: %s", ID, name, description, DATE_FORMAT.format(startDate), DATE_FORMAT.format(endDate), daysFormat.delete(daysFormat.length()-2, daysFormat.length()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((days == null) ? 0 : days.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;		
		if (getClass() == obj.getClass()) {
			Course other = (Course) obj;
			if (ID != other.ID) return false;
			if (name == null) {
				if (other.name != null)	return false;
			} else if (!name.equals(other.name)) return false;			
		} else if (obj instanceof String){
			return name.toLowerCase().equals(obj.toString().toLowerCase());
		} else if (obj instanceof Integer) {
			return ((Integer) obj).intValue() == ID;
		}
		return true;
	}

	public static void show(int id){
		for (Course course: Course.coursesList){
			if (course.equals(id)) {				
				System.out.println(course);
				return;
			}
		}
		System.err.printf("Course with ID %d doesnТt exist!\n", id);
	}
	
	public static void showAll (){
		if (coursesList.isEmpty()){
			System.err.println("No courses available yet");
		} else {
			StringBuilder courseNames  = new StringBuilder();
			for (Course course: coursesList) courseNames.append(course.ID).append(": ").append(course.name).append("\n");
			System.out.println(courseNames.deleteCharAt(courseNames.length()-1));
		}
	}	

	public static void saveToFile() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Course.ser"))) {
			for (Course course : coursesList) out.writeObject(course);
		} catch (IOException e) {e.printStackTrace();}
	}
	

	public static void readFromFile() {		
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Course.ser"))) {
			while (true) coursesList.add((Course)in.readObject());
		} catch (IOException e) {return;
		} catch (Exception e) {e.printStackTrace();}
	}

	public class GradeBook{
		//количество полученных баллов каждым студентом по каждой задаче в рамках курса
	}
}