package org.cuacfm.members.model.exceptions;

import java.util.Date;

import org.cuacfm.members.web.support.DisplayDate;

@SuppressWarnings("serial")
public class DateLimitExpirationException extends Exception {

	private Date dateLimit;
	private String trainingName;

	public DateLimitExpirationException(String trainingName, Date dateLimit) {
		super("The training " + trainingName + " expired at " + DisplayDate.dateTimeToString(dateLimit));
		this.trainingName = trainingName;
		this.dateLimit = dateLimit;
	}

	public Date getDateLimit() {
		return dateLimit;
	}

	public String getTrainingName() {
		return trainingName;
	}
}
