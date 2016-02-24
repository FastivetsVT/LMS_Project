package com.brainacad.lms.fastivets;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.brainacad.lms.fastivets.util.QuitException;
import com.brainacad.lms.fastivets.control.CourseManager;
import com.brainacad.lms.fastivets.control.Manager;
import com.brainacad.lms.fastivets.control.StudentManager;
import com.brainacad.lms.fastivets.control.TaskManager;
import com.brainacad.lms.fastivets.control.TrainerManager;

public class CLI {
	public static final Scanner SCAN_INPUT = new Scanner(System.in);	
	private static final Pattern[] COMMANDS = 		{Pattern.compile("\\W*create\\W*course\\W*"),
													Pattern.compile("\\W*show\\W*course\\W*(\\d+)"),													
													Pattern.compile("\\W*create\\W*student\\W*"),
													Pattern.compile("\\W*show\\W*student\\W*(\\d+)"),
													Pattern.compile("\\W*create\\W*trainer\\W*"),
													Pattern.compile("\\W*show\\W*trainer\\W*(\\d+)"),
													Pattern.compile("\\W*show\\W*courses\\W*"),
													Pattern.compile("\\W*show\\W*students\\W*(\\d+)"),													
													Pattern.compile("\\W*transfer\\W*student\\W*(\\d+)\\W*(add|remove)\\W*(\\d+)"),
													Pattern.compile("\\W*create\\W*task\\W*"),
													Pattern.compile("\\W*show\\W*gradebook\\W*(\\d+)"),
													Pattern.compile("\\W*save\\W*gradebook\\W*(\\d+)"),
													Pattern.compile("\\W*(exit|quit)\\W*"),
													Pattern.compile("\\W*help\\W*")};

	public static void main(String[] args) {
		Manager.setScanInput(SCAN_INPUT);
		QuitException.setPattern(COMMANDS[COMMANDS.length-1]);
		CourseManager courseManager = CourseManager.getInstance();
		StudentManager studentManager = StudentManager.getInstance();
		TrainerManager trainerManager = TrainerManager.getInstance();
		TaskManager taskManager = TaskManager.getInstance();
		
		System.out.println("Brain Academy Learning Managment System (LMS) ©Fastivets V.T., 2016");
		help();
		System.out.printf("\nAdded from database:\nCourses: %d\tStudents: %d\tTrainers: %d\tTasks: %d\n", courseManager.getNumberOfObjects(), studentManager.getNumberOfObjects(), trainerManager.getNumberOfObjects(),taskManager.getNumberOfObjects());
		while(true){
			System.out.print("\nPlease, enter the command: ");			
			Matcher matcher = Pattern.compile("").matcher(SCAN_INPUT.nextLine().toLowerCase());
			try {
				for (int i = 0; i <= COMMANDS.length; i++){
					if (i == COMMANDS.length || matcher.usePattern(COMMANDS[i]).matches()) {
						switch(i) {
						case 0:
							courseManager.createObject(); break;
						case 1:
							courseManager.showLong(new Integer(matcher.group(1))); break;
						case 2:
							studentManager.createObject(); break;
						case 3:
							studentManager.showLong(new Integer(matcher.group(1))); break;
						case 4:
							trainerManager.createObject(); break;
						case 5:
							trainerManager.showLong(new Integer(matcher.group(1))); break;							
						case 6:
							courseManager.show(); break;
						case 7:
							courseManager.showStudents(new Integer(matcher.group(1))); break;
						case 8:
							studentManager.transferStudent(new Integer(matcher.group(1)), new Integer(matcher.group(3)), matcher.group(2)); break;
						case 9:
							taskManager.createObject(); break;
						case 10:
							courseManager.showGradeBook(new Integer(matcher.group(1))); break;
						case 11:
							courseManager.saveGradeBook(new Integer(matcher.group(1))); break;
						case 12:
							courseManager.save();
							studentManager.save();
							trainerManager.save();
							taskManager.save();
							System.out.println("\nGoodbye!"); return;
						case 13:
							help(); break;
						default:
							System.err.println("\nUnsupported operation. Please, try again."); break;
						}
						break;
					}
				}
			} catch (QuitException e){
				System.err.println(e.getMessage());
			}
		}
	}
	
	private static void help(){
		System.out.printf("\nSupported commands:\n%s\n", Arrays.toString(COMMANDS).replaceAll(", ", "\n"));
	}
}