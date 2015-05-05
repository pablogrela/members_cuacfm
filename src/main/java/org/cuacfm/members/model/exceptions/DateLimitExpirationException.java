package org.cuacfm.members.model.exceptions;

import java.util.Date;

import org.cuacfm.members.web.support.DisplayDate;

/** The Class DateLimitExpirationException. */
@SuppressWarnings("serial")
public class DateLimitExpirationException extends Exception {

   /** The date limit. */
   private final Date dateLimit;

   /** The training name. */
   private final String trainingName;

   /**
    * Instantiates a new date limit expiration exception.
    *
    * @param trainingName
    *           the training name
    * @param dateLimit
    *           the date limit
    */
   public DateLimitExpirationException(String trainingName, Date dateLimit) {
      super("The training " + trainingName + " expired at "
            + DisplayDate.dateTimeToString(dateLimit));
      this.trainingName = trainingName;
      this.dateLimit = dateLimit;
   }

   /**
    * Gets the date limit.
    *
    * @return the date limit
    */
   public Date getDateLimit() {
      return dateLimit;
   }

   /**
    * Gets the training name.
    *
    * @return the training name
    */
   public String getTrainingName() {
      return trainingName;
   }
}
