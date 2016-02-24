package com.brainacad.lms.fastivets.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {
	private static SimpleDateFormat DATE_FORMAT_INPUT = new SimpleDateFormat("dd.MM.yy");
	private static SimpleDateFormat DATE_FORMAT_OUTPUT = new SimpleDateFormat("dd.MM.yyyy");
	
	static {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2000);
		DATE_FORMAT_INPUT.set2DigitYearStart(c.getTime());
	}
	
	public static Date parse (String date) throws ParseException{
		return DATE_FORMAT_INPUT.parse(date);
	}
	
	public static String format (Date date){
		return DATE_FORMAT_OUTPUT.format(date);
	}
	
	public static String getPattern(){
		return DATE_FORMAT_INPUT.toPattern().toLowerCase();
	}
}
