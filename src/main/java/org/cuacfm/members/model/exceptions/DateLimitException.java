package org.cuacfm.members.model.exceptions;

import java.util.Date;

import org.cuacfm.members.web.support.DisplayDate;

@SuppressWarnings("serial")
public class DateLimitException extends Exception {

	private Date dateLimit;
	private Date dateTraining;

	public DateLimitException(Date dateLimit, Date dateTraining) {
		super("The date limit " + DisplayDate.dateTimeToString(dateLimit)
				+ " should be after or equals to date "
				+ DisplayDate.dateTimeToString(dateTraining));

		this.dateLimit = dateLimit;
		this.dateTraining = dateTraining;
	}

	public Date getDateLimit() {
		return dateLimit;
	}

	public Date getDateTraining() {
		return dateTraining;
	}

}
