package org.cuacfm.members.model.exceptions;

/** The Class ExistTransactionIdException. */
@SuppressWarnings("serial")
public class UniqueException extends Exception {

   /** The attribute. */
   private final String attribute;

   /** The attribute. */
   private final String value;

   /**
    * Instantiates a new exist transaction id exception.
    *
    * @param attribute
    *           the id txn
    */
   public UniqueException(String attribute, String value) {
      super("It already exist arribute " + attribute + " with value: " + value);
      this.attribute = attribute;
      this.value = value;
   }

   /**
    * Gets the attribute.
    *
    * @return the attribute
    */
   public String getAttribute() {
      return attribute;
   }

   /**
    * Gets the value.
    *
    * @return the value
    */
   public String getValue() {
      return value;
   }
}
