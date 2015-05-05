package org.cuacfm.members.model.exceptions;

/** The Class ExistInscriptionsException. */
@SuppressWarnings("serial")
public class ExistInscriptionsException extends Exception {

   /**
    * Instantiates a new exist inscriptions exception.
    */
   public ExistInscriptionsException() {
      super("Exist Inscriptions in this training.");
   }
}
