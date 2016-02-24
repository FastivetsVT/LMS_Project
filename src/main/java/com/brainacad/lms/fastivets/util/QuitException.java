package com.brainacad.lms.fastivets.util;

import java.util.regex.Pattern;

public class QuitException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static Pattern pattern;

	public QuitException() {}
	public QuitException(String message) {
		super(message);
	}
	
	public static Pattern getPattern() {
		return pattern;
	}
	public static void setPattern(Pattern pattern) {
		QuitException.pattern = pattern;
	}
	public static void check(String s) throws QuitException{
		
		if (pattern.matcher(s.toLowerCase()).matches()) throw new QuitException("Last action cancelled.");
	}

}
