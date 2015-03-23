package org.cuacfm.members.model.exceptions;


/** The Class UnsubscribeException. */
@SuppressWarnings("serial")
public class UnsubscribeException extends Exception{
	
	/** The name training. */
	private String nameTraining;
	
	/**
	 * Instantiates a new unsubscribe exception.
	 *
	 * @param nameTraining the name training
	 */
	public UnsubscribeException(String nameTraining) {
			super("You have already unsubscribed from this formation " + nameTraining);

			this.nameTraining=nameTraining;
	}

	/**
	 * Gets the name training.
	 *
	 * @return the name training
	 */
	public String getNameTraining() {
		return nameTraining;
	}

}
