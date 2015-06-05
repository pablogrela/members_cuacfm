package org.cuacfm.members.model.util;

/** The Class States. */
public class States {

   /** Instantiates a new states.*/
   protected States() {
      // Default empty constructor.
   }

   /**
    * The Enum states.
    */
   public static enum states {

      /** The no pay. */
      NO_PAY,

      /** The pay. */
      PAY,

      /** The management. */
      MANAGEMENT,

      /** The return bill. */
      RETURN_BILL
   }

   /**
    * The Enum method.
    */
   public static enum methods {

      /** The no pay. */
      NO_PAY,

      /** The paypal. */
      PAYPAL,

      /** The directdebit. */
      DIRECTDEBIT,

      /** The cash. */
      CASH,

   }
}
