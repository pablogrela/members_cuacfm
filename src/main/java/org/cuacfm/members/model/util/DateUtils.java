/**
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.model.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** The Class DisplayDate. */
public class DateUtils {

	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

	public static final String FORMAT_PAYPAL = "HH:mm:ss MMM dd, yyyy";
	public static final String FORMAT_DISPLAY = "HH:mm dd/MM/yyyy";
	public static final String FORMAT_LOCAL_DATE = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_FILE = "yyyy-MM-dd_HH-mm-ss";
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT_MONTH_YEAR = "yyyy-MM";
	public static final String FORMAT_DISPLAY_MONTH_YEAR = "MMMM yyyy";
	public static final String FORMAT_DATE_DIRECTDEBIT = "yyyyMMdd";
	public static final String FORMAT_TIME_DIRECTDEBIT = "HHmmssSSSSS";
	public static final String FORMAT_TIME = "HH:mm";

	/** Instantiates a new display date. */
	protected DateUtils() {
		super();
	}

	/**
	 * Convert String to date.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static Date stringToDate(String date) {
		Date newDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm,yyyy-MM-dd");

		if (!",".equals(date)) {
			try {
				newDate = dateFormat.parse(date);
			} catch (ParseException ex) {
				ex.getMessage();

			}
		}
		return newDate;
	}

	/**
	 * Format.
	 *
	 * @param date the date
	 * @param format the format
	 * @return the date
	 */
	public static Date format(String date, String format) {
		if (date != null && !date.isEmpty()) {
			Date newDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);

			try {
				newDate = dateFormat.parse(date);
				return newDate;
			} catch (ParseException e) {
				logger.error("format", e);
			}
		}
		return null;
	}

	/**
	 * Format.
	 *
	 * @param date the date
	 * @param format the format
	 * @param locale the locale
	 * @return the date
	 */
	public static Date format(String date, String format, Locale locale) {
		if (date != null && !date.isEmpty()) {
			Date newDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(format, locale);

			try {
				newDate = dateFormat.parse(date);
				return newDate;
			} catch (ParseException e) {
				logger.error("format", e);
			}
		}
		return null;
	}

	/**
	 * Format.
	 *
	 * @param format the format
	 * @return the string
	 */
	public static String format(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date());
	}

	/**
	 * Format.
	 *
	 * @param date the date
	 * @param format the format
	 * @return the string
	 */
	public static String format(Date date, String format) {
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		}
		return null;
	}

	/**
	 * Format.
	 *
	 * @param date the date
	 * @param format the format
	 * @return the string
	 */
	public static String format(LocalDateTime date, String format) {
		if (date != null) {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(format);
			return dateFormat.format(date);
		}
		return null;
	}

}
