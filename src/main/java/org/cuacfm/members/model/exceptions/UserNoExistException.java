package org.cuacfm.members.model.exceptions;

/** The Class UserNoExistException. */
@SuppressWarnings("serial")
public class UserNoExistException extends Exception {

   /** The name. */
   private final String name;

   /**
    * Instantiates a new UserNoExist exception.
    *
    * @param name
    *           the name
    */
   public UserNoExistException(String name) {
      super("User " + name + ", no exist");
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
