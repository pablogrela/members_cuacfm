package org.cuacfm.members.model.exceptions;

@SuppressWarnings("serial")
public class UnsubscribeException extends Exception{
	
	private String nameTraining;
	public UnsubscribeException(String nameTraining) {
			super("You have already unsubscribed from this formation " + nameTraining);

			this.nameTraining=nameTraining;
	}

	public String getNameTraining() {
		return nameTraining;
	}

}
