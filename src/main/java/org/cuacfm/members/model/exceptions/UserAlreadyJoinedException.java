package org.cuacfm.members.model.exceptions;

/** The Class UserAlreadyJoinedException. */
@SuppressWarnings("serial")
public class UserAlreadyJoinedException extends Exception {

   /** The name. */
   private final String name;

   /**
    * Instantiates a new UserAlreadyJoined exception.
    *
    * @param name
    *           the name
    */
   public UserAlreadyJoinedException(String name) {
      super("User " + name + ", already inscription in this formation ");
      this.name = name;
   }

   /**
    * Gets the name.
    *
    * @return the name
    */
   public String getName() {
      return name;
   }

}
