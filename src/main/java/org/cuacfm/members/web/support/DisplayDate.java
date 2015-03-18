package org.cuacfm.members.web.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DisplayDate {

	public static Date stringToDate(String dateTraining) {
		Date newDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm,yyyy-MM-dd");

		if (!dateTraining.equals(",")) {
			try {
				newDate = dateFormat.parse(dateTraining);
			} catch (ParseException ex) {
				ex.printStackTrace();

			}
		}
		return newDate;
	}
	
	
	public static String dateTimeToString(Date dateTimeTraining) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm,yyyy-MM-dd");
		String stringDateTime = dateFormat.format(dateTimeTraining);
		return stringDateTime;
	}
	
	public static String dateToString(Date dateTraining) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String stringDate = dateFormat.format(dateTraining);
		return stringDate;
	}
	
	public static String timeToString(Date timeTraining) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		String stringTime = dateFormat.format(timeTraining);
		return stringTime;
	}

}
