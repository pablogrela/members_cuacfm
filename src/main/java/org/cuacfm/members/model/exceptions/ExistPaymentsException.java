package org.cuacfm.members.model.exceptions;



/** The Class ExistPaymentsException. */
@SuppressWarnings("serial")
public class ExistPaymentsException extends Exception {


   /**
    * Instantiates a new exist payments exception.
    */
   public ExistPaymentsException() {
      super("Exist payments in this programs.");
   }
}
