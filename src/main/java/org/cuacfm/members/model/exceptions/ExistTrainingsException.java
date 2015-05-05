package org.cuacfm.members.model.exceptions;

/** The Class ExistTrainingsException. */
@SuppressWarnings("serial")
public class ExistTrainingsException extends Exception {

   /**
    * Instantiates a new exist trainings exception.
    */
   public ExistTrainingsException() {
      super("Exist Traininings in this Training Type.");
   }
}
