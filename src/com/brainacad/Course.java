package com.brainacad;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

import static com.brainacad.LMS_Main.*;

public class Course implements Storeable {	
	static {
		coursesList = new TreeMap<>();
		readFromFile();
	}		
	private static final long serialVersionUID = 1L;
	public static TreeMap<Integer, Course> coursesList;
		
	public TreeSet<Integer> studentsList;
	private final int COURSE_ID = coursesList.isEmpty()?1:coursesList.lastKey()+1;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private byte days;	//as bit mask
	
	private Course(){}
	
	public static void create() throws EscException {
		Course newCourse = new Course();
		newCourse.setName();
		newCourse.setDescription();
		newCourse.setStartDate();
		newCourse.setEndDate();
		newCourse.setDays();
		coursesList.put(newCourse.COURSE_ID, newCourse);
		System.out.printf("\nNew course has been successfully created:\n%s\n",newCourse);
		saveToFile();
		newCourse.studentsList = new TreeSet<>();
	}
	
	public void setName() throws EscException {
		while (true){
			try {
				System.out.print("Course name: ");		
				setName(SCAN_INPUT.nextLine());
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another name.\n", e.getMessage());
			}
		}		
	}

	public void setName(String name) throws EscException, IllegalArgumentException {
		if (name == null || (name = name.trim()).isEmpty()) throw new IllegalArgumentException("Course name shouldn't be empty");
		escCheck(name);
		for (Course course: coursesList.values()){
			if (course.equals(name)) throw new IllegalArgumentException("Course name should be unique");
		}	
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setDescription() throws EscException {
		while (true){
			try {
				System.out.print("Course description: ");
				setDescription(SCAN_INPUT.nextLine());
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another description.\n", e.getMessage());
			}
		}	
	}
	
	public void setDescription(String description) throws EscException, IllegalArgumentException {
		if (description == null || (description = description.trim()).isEmpty()) throw new IllegalArgumentException("Course description shouldn't be empty");
		escCheck(description);	
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setStartDate() throws EscException {
		while (true){
			try {
				System.out.print("Start date: ");
				setStartDate(SCAN_INPUT.nextLine());
				break;
			} catch (ParseException e) {
				System.err.printf("Illegal date format (%s). Please, try again.\n", DATE_FORMAT.toPattern());
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another start date.\n", e.getMessage());
			}
		}
	}
	
	public void setStartDate(String startDate) throws EscException, ParseException, IllegalArgumentException {
		if (startDate == null) throw new IllegalArgumentException("Start date shouldn't be empty");
		escCheck(startDate);
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
		while (true){
			try {
				System.out.print("End date: ");
				setEndDate(SCAN_INPUT.nextLine());
				break;
			} catch (ParseException e) {
				System.err.printf("Illegal date format (%s). Please, try again.\n", DATE_FORMAT.toPattern());
			} catch (IllegalArgumentException e) {
				System.err.printf("%s. Please, enter another end date.\n", e.getMessage());
			}	
		}
	}
	
	public void setEndDate(String endDate) throws EscException, ParseException, IllegalArgumentException {
		if (endDate == null) throw new IllegalArgumentException("End date shouldn't be empty");
		escCheck(endDate);
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
		while (true){ 
			try {
				System.out.print("Days: ");
				setDays(SCAN_INPUT.nextLine());
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, try again.\n", e.getMessage());
			}			
		}		
	}
	
	public void setDays(String days) throws EscException, IllegalArgumentException {
		if (days == null || days.trim().isEmpty()) throw new IllegalArgumentException("Days shouldn't be empty");
		escCheck(days);
		this.days = 0;
		Scanner daysInput = new Scanner(days.toUpperCase()).useDelimiter("\\W+");
		String dayInput = null;
		while (daysInput.hasNext()){
			try {
				addDay(DayOfWeek.valueOf(dayInput = daysInput.next()));
			} catch (IllegalArgumentException e) {
				System.err.printf("\"%s\" illegal day of week name\n", dayInput);
			}
		}
		if (this.days == 0) throw new IllegalArgumentException("Days shouldn't be empty");
	}
	
	public void addDay (DayOfWeek day) throws IllegalArgumentException{
		if (day == null) throw new IllegalArgumentException("Day shouldn't be empty");
		addDay(day.getValue());
	}
	
	public void addDay (int day) throws IllegalArgumentException{
		if (day > 7 || day < 1) throw new IllegalArgumentException(String.format("\"%d\"Illegal day of week", day));
		days = (byte)(days | 1<<(day-1));
	}
	
	public String getDays() {
		String binaryString = Integer.toBinaryString(days);
		StringBuilder daysFormat = new StringBuilder();
		for (int i = binaryString.length()-1; i >= 0; i--)
			if (binaryString.charAt(i) == '1') daysFormat.append(DayOfWeek.of(binaryString.length()-i).getDisplayName(TextStyle.SHORT, LOCALE)).append(", ");		
		return daysFormat.delete(daysFormat.length()-2, daysFormat.length()).toString();
	}
		
	@Override
	public String toString() {
		return String.format("Course ID: %d\nCourse name: %s\nCourse description: %s\nStart date: %s\nEnd date: %s\nDays: %s", COURSE_ID, name, description, DATE_FORMAT.format(startDate), DATE_FORMAT.format(endDate), getDays());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + COURSE_ID;
		result = prime * result + days;
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
			if (COURSE_ID != other.COURSE_ID) return false;
			if (name == null) {
				if (other.name != null)	return false;
			} else if (!name.equalsIgnoreCase(other.name)) return false;			
		} else if (obj instanceof String){
			return name.equalsIgnoreCase((String)obj);
		} else if (obj instanceof Integer) {
			return ((Integer) obj).intValue() == COURSE_ID;
		}
		return true;
	}

	public static void show(int id){
		Course course = coursesList.get(id);
		if (course == null){
			System.err.printf("Course %d doesnТt exist.\n", id);
		} else {
			System.out.println(course);
		}		
	}
	
	public static void showAll (){
		if (coursesList.isEmpty()){
			System.err.println("No courses available yet");
		} else {
			StringBuilder courses  = new StringBuilder();
			for (Course course: coursesList.values()) courses.append(course.COURSE_ID).append(": ").append(course.name).append("\n");
			System.out.println(courses.deleteCharAt(courses.length()-1));
		}
	}

	public static void saveToFile() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Course.ser"))) {
			for (Course course : coursesList.values()) out.writeObject(course);
		} catch (IOException e) {e.printStackTrace();}
	}	

	public static void readFromFile() {		
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Course.ser"))) {
			Course course;
			while (true) coursesList.put((course = (Course)in.readObject()).COURSE_ID, course);
		} catch (IOException e) {return;
		} catch (Exception e) {e.printStackTrace();}
	}

	public class GradeBook{
		//количество полученных баллов каждым студентом по каждой задаче в рамках курса
		public class Task{
			//private final int ID = 0;
			//private String title;			
			}
		}
}