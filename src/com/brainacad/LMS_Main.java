package com.brainacad;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LMS_Main {
	
	public static final Locale LOCALE = Locale.ENGLISH;;
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	public static final Scanner SCAN_INPUT = new Scanner(System.in);	
	private static final Pattern[] COMMANDS = 		{Pattern.compile("\\W*(exit|esc)\\W*"),
													Pattern.compile("\\W*create\\W*course\\W*"),
													Pattern.compile("\\W*show\\W*course\\W*\\d+"),
													Pattern.compile("\\W*show\\W*courses\\W*"),
													Pattern.compile("\\W*create\\W*student\\W*")};

	public static void main(String[] args) {
		System.out.println("Brain Academy Learning Managment System (LMS) ©Fastivets V.T., 2016");
		System.out.println("\nSupported commands:\ncreate course\nshow course %id\t\tshow courses\ncreate student\nesc\t\t\texit");
		System.out.printf("\nAdded from database:\nCourses: %d\n", Course.coursesList.size());
		while(true){
			System.out.print("\nPlease, enter the command: ");
			String input = SCAN_INPUT.nextLine().toLowerCase();
			try {
				if (COMMANDS[0].matcher(input).matches()){
					Course.saveToFile();
					System.out.println("\nGoodbye!");
					return;
				} else if (COMMANDS[1].matcher(input).matches()){
					Course.create();
				} else if (COMMANDS[2].matcher(input).matches()){
					Course.show(new Scanner(input).useDelimiter("\\W*show\\W*course\\W*").nextInt());
				} else if (COMMANDS[3].matcher(input).matches()){
					Course.showAll();
				} else if (COMMANDS[4].matcher(input).matches()){
					Student.create();
				} else {
					System.err.println("\nUnsupported operation. Please, try again.");
				}
			} catch (EscException e){
				System.err.println(e.getMessage());
			}
		}
	}
	
	public static void escCheck(String s) throws EscException{
		if (COMMANDS[0].matcher(s.toLowerCase()).matches()) throw new EscException("Last action cancelled.");
	}
	
	public static class EscException extends RuntimeException{
		public EscException() {}
		public EscException(String message) {
			super(message);
		}	
	}
}