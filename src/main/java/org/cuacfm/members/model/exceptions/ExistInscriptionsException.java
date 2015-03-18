package org.cuacfm.members.model.exceptions;

@SuppressWarnings("serial")
public class ExistInscriptionsException extends Exception{

	public ExistInscriptionsException() {
			super("Exist Inscriptions in this training.");
	}
}
