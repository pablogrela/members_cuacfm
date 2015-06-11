package org.cuacfm.members.web.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** The Class DisplayDate. */
public class DisplayDate {

   /** Instantiates a new display date.*/
   protected DisplayDate() {
      // Default empty constructor.
   }

   /**
    * Convert String to date.
    *
    * @param date
    *           the date
    * @return the date
    */
   public static Date stringToDate(String date) {
      Date newDate = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm,yyyy-MM-dd");

      if (!date.equals(",")) {
         try {
            newDate = dateFormat.parse(date);
         } catch (ParseException ex) {
            ex.getMessage();

         }
      }
      return newDate;
   }

   /**
    * Convert String to date2.
    *
    * @param date
    *           the date
    * @return the date
    */
   public static Date stringToDate2(String date) {
      Date newDate = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

      try {
         newDate = dateFormat.parse(date);
      } catch (ParseException ex) {
         return null;
      }

      return newDate;
   }

   /**
    * Convert String to month of year.
    *
    * @param date
    *           the date
    * @return the date
    */
   public static Date stringToMonthOfYear(String date) {
      Date newDate = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

      try {
         newDate = dateFormat.parse(date);
      } catch (ParseException ex) {
         ex.getMessage();
      }

      return newDate;
   }

   /**
    * Convert String to date time.
    *
    * @param dateTime
    *           the date time
    * @return the date
    */
   public static Date stringToDateTime(String dateTime) {
      Date newDate = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

      try {
         newDate = dateFormat.parse(dateTime);
      } catch (ParseException ex) {
         ex.getMessage();
      }
      return newDate;
   }

   /**
    * Convert String paypal to date.
    *
    * @param date
    *           the date
    * @return the date
    */
   public static Date stringPaypalToDate(String date) {
      Date newDate = new Date();
      String dateAux = date.substring(0, 21);
      SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss MMM dd, yyyy", Locale.US);

      try {
         newDate = dateFormat.parse(dateAux);
      } catch (ParseException ex) {
         ex.getMessage();
      }
      return newDate;
   }

   /**
    * Convert Date time to string.
    *
    * @param dateTime
    *           the date time
    * @return the string
    */
   public static String dateTimeToString(Date dateTime) {
      String stringDateTime = "";
      if (dateTime != null) {
         SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
         stringDateTime = dateFormat.format(dateTime);
      }
      return stringDateTime;
   }

   /**
    * Convert Date time to string.
    *
    * @param dateTime
    *           the date time
    * @return the string
    */
   public static String dateTimeToStringSp(Date dateTime) {
      String stringDateTime = "";
      if (dateTime != null) {
         SimpleDateFormat dateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH:mm:ss");
         stringDateTime = dateFormat.format(dateTime);
      }
      return stringDateTime;
   }

   /**
    * Convert Date to string.
    *
    * @param date
    *           the date
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
    * Date to direct debit.
    *
    * @param date
    *           the date
    * @return the string
    */
   public static String dateToDirectDebit(Date date) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
      return dateFormat.format(date);
   }

   /**
    * Time to direct debit.
    *
    * @param date
    *           the date
    * @return the string
    */
   public static String timeToDirectDebit(Date date) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmssSSSSS");
      return dateFormat.format(date);
   }

   /**
    * Time to string.
    *
    * @param time
    *           the time
    * @return the string
    */
   public static String timeToString(Date time) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
      return dateFormat.format(time);
   }

   /**
    * Month Of Year to string.
    *
    * @param monthOfYear
    *           the monthOfYear
    * @return the string
    */
   public static String monthOfYearToString(Date monthOfYear) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
      return dateFormat.format(monthOfYear);
   }

   /**
    * Month Of Year to string.
    *
    * @param monthOfYear
    *           the monthOfYear
    * @return the string
    */
   public static String monthOfYearToDisplay(Date monthOfYear) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
      return dateFormat.format(monthOfYear);
   }
}
