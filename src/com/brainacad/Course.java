package com.brainacad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Course implements Storeable {
	
	static {
		list = new ArrayList<Course>();
		readFromFile();
		setCurrentID();
	}
	
	private static int currentID;
	public static List<Course> list;
	
	private /*final*/ int id = ++currentID;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private Set<DayOfWeek> days = new TreeSet<DayOfWeek>();
	
	private Course(){}
	
	private static void setCurrentID(){
		if (list.isEmpty()) {
			currentID = 0;
		} else {
			//if courses can be removed, else better to use list.size()-1
			int maxID = list.get(0).id;
			for (Course course: list){
				if (course.id > maxID) maxID = course.id;
			}
			currentID = maxID;
		}
	}
	
	public static void create(){
		Course course = new Course();
		course.setName();
		course.setDescription();
		course.setDates();
		course.setDays();	
		if (list.add(course)) System.out.printf("\n\nNew course has been successfully created:\n");
		saveToFile();
		course.show();
	}
	
	public void setName() {
		boolean isIllegal = true;		
		while (isIllegal){
			System.out.print("Course name: ");		
			this.name =  LMS_Main.SCAN_INPUT.nextLine();
			if (this.name.equals("")) {
				System.err.println("Course name shouldn't be empty. Please, enter another name.");	
			} else {
				isIllegal = false;
				String thisName = this.name.toLowerCase();
				for (Course c: Course.list){
					if (thisName.equals(c.name.toLowerCase())){
						isIllegal = true;
						System.err.println("Course name should be unique. Please, enter another name.");	
						break;
					}
				}				
			}					
		}		
	}

	public void setDescription() {
		boolean isIllegal = true;
		while (isIllegal){
			System.out.print("Course description: ");
			this.description =  LMS_Main.SCAN_INPUT.nextLine();
			if (this.description.equals("")) System.err.println("Course description shouldn't be empty. Please, enter another name."); else isIllegal = false;
		}	
	}
	
	public void setDates() {
		boolean isIllegalStartDate = true;
		boolean isIllegalEndDate = true;
		while (isIllegalStartDate || isIllegalEndDate){
			try {
				if (isIllegalStartDate) {
					System.out.print("Start date: ");
					this.startDate = LMS_Main.DATE_FORMAT.parse(LMS_Main.SCAN_INPUT.nextLine());
					isIllegalStartDate = false;
				}
				if (isIllegalEndDate) {
					System.out.print("End date: ");
					this.endDate = LMS_Main.DATE_FORMAT.parse(LMS_Main.SCAN_INPUT.nextLine());
					if (this.startDate.getTime() > this.endDate.getTime()) throw new RuntimeException();
					isIllegalEndDate = false;
				}				
			} catch (ParseException e) {
				System.err.println("Illegal date format(dd.mm.yyyy), please, try again!");
			} catch (RuntimeException e) {
				System.err.println("Start date is bigger than end date. Please, enter another end date.");
			}	
		}
	}

	public void setDays() {
		this.days = new TreeSet<DayOfWeek>();
		boolean isIllegal = true;
		while (isIllegal){
			System.out.print("Days: ");
			Scanner daysInput = new Scanner(LMS_Main.SCAN_INPUT.nextLine()).useDelimiter("\\W+");
			String dayInput = null;
			while (daysInput.hasNext()){				
				try {
					dayInput = daysInput.next().toUpperCase();
					this.days.add(DayOfWeek.valueOf(dayInput));					
				} catch (IllegalArgumentException e) {
					System.err.printf("\"%s\" illegal day of week name\n", dayInput);
				}				
			}
			if (this.days.isEmpty()) System.err.println("Days shouldn't be empty. Please, try again."); else isIllegal = false;
		}		
	}

	public static void show(int i){
		//if courses can be removed, else better to use get(i-1) from collection
		for (Course course: Course.list){
			if (course.id == i) {
				StringBuilder daysFormat = new StringBuilder();
				for (DayOfWeek dow: course.days){
					if (daysFormat.length() != 0) daysFormat.append(", ");
					daysFormat.append(dow.getDisplayName(TextStyle.SHORT, LMS_Main.LOCALE));
				}
				System.out.printf("Course ID: %d\nCourse name: %s\nCourse description: %s\nStart date: %s\nEnd date: %s\nDays: %s\n", course.id, course.name, course.description, LMS_Main.DATE_FORMAT.format(course.startDate), LMS_Main.DATE_FORMAT.format(course.endDate), daysFormat);
				return;
			}
		}
		System.err.printf("Course with id %d doesnТt exist!\n", i);
	}
	
	public void show(){
		show(this.id);
	}
	
	public static void showAll (){
		StringBuilder courseNames  = new StringBuilder();
		for (Course course: Course.list){
			if (courseNames.length() != 0) courseNames.append(", ");
			courseNames.append(course.name);
		}
		if (courseNames.length() == 0 ) System.err.println("Courses have not been created yet"); else System.out.printf("List of course names: %s\n", courseNames);
	}	

	public static void saveToFile() {
		//not the final version
		try (PrintWriter out = new PrintWriter(new File("Courses.txt"))) {
			for (Course course: list){
				StringBuilder daysFormat = new StringBuilder();
				for (DayOfWeek dow: course.days){
					if (daysFormat.length() != 0) daysFormat.append("|");
					daysFormat.append(dow.getValue());
				}
				out.printf("%d|%s|%s|%d|%d|%s", course.id, course.name, course.description, course.startDate.getTime(),course.endDate.getTime(),daysFormat);
				out.println();
			}			
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void readFromFile() {
		//not the final version
		try (BufferedReader in = new BufferedReader(new FileReader("Courses.txt"))) {
			String object; 
			while ((object = in.readLine()) != null) {
				Scanner objectFields = new Scanner(object).useDelimiter("\\|");
				Course course = new Course();
				course.id = objectFields.nextInt();
				course.name = objectFields.next();
				course.description = objectFields.next();
				course.startDate = new Date(objectFields.nextLong());
				course.endDate = new Date(objectFields.nextLong());
				while (objectFields.hasNext()){
					course.days.add(DayOfWeek.of(objectFields.nextInt()));
				}
				list.add(course);
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class GradeBook{
		//количество полученных баллов каждым студентом по каждой задаче в рамках курса
	}
}
