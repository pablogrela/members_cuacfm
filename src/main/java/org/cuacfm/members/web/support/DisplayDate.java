package org.cuacfm.members.web.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** The Class DisplayDate. */
public class DisplayDate {

	/**
	 * Convert String to date.
	 *
	 * @param date
	 *            the date
	 * @return the date
	 */
	public static Date stringToDate(String date) {
		Date newDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm,yyyy-MM-dd");

		if (!date.equals(",")) {
			try {
				newDate = dateFormat.parse(date);
			} catch (ParseException ex) {
				ex.printStackTrace();

			}
		}
		return newDate;
	}

	/**
	 * Convert String to date2.
	 *
	 * @param date
	 *            the date
	 * @return the date
	 */
	public static Date stringToDate2(String date) {
		Date newDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			newDate = dateFormat.parse(date);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}

		return newDate;
	}

	/**
	 * Convert String to date time.
	 *
	 * @param dateTime
	 *            the date time
	 * @return the date
	 */
	public static Date stringToDateTime(String dateTime) {
		Date newDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

		try {
			newDate = dateFormat.parse(dateTime);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return newDate;
	}

	/**
	 * Convert String paypal to date.
	 *
	 * @param date
	 *            the date
	 * @return the date
	 */
	public static Date stringPaypalToDate(String date) {
		Date newDate = new Date();
		date = date.substring(0, 21);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"HH:mm:ss MMM dd, yyyy", Locale.US);

		try {
			newDate = dateFormat.parse(date);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return newDate;
	}

	/**
	 * Convert Date time to string.
	 *
	 * @param dateTime
	 *            the date time
	 * @return the string
	 */
	public static String dateTimeToString(Date dateTime) {
		String stringDateTime = "";
		if (dateTime != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"HH:mm dd/MM/yyyy");
			stringDateTime = dateFormat.format(dateTime);
		}
		return stringDateTime;
	}

	/**
	 * Convert Date to string.
	 *
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String dateToString(Date date) {
		String stringDate = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			stringDate = dateFormat.format(date);
		}
		return stringDate;
	}

	/**
	 * Time to string.
	 *
	 * @param time
	 *            the time
	 * @return the string
	 */
	public static String timeToString(Date time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		String stringTime = dateFormat.format(time);
		return stringTime;
	}

	/**
	 * Convert String to timetable.
	 *
	 * @param timetable
	 *            the timetable
	 * @return the date
	 */
	public static Date stringToTimetable(String timetable) {
		Date newDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm EEEE");

		try {
			newDate = dateFormat.parse(timetable);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return newDate;
	}
}
