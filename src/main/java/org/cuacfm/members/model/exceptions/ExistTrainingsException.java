package org.cuacfm.members.model.exceptions;

@SuppressWarnings("serial")
public class ExistTrainingsException extends Exception{

	public ExistTrainingsException() {
			super("Exist Traininings in this Training Type.");
	}
}
