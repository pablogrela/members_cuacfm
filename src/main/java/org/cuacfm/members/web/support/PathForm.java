package org.cuacfm.members.web.support;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Size;

/** The Class PathForm. */
public class PathForm {

   /** The Constant NOT_BLANK_MESSAGE. */
   private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
   
   /** The Constant MAX_CHARACTERS. */
   private static final String MAX_CHARACTERS = "{max.characters}";

   /** The Path. */
   @NotBlank(message = PathForm.NOT_BLANK_MESSAGE)
   @Size(max = 50, message = PathForm.MAX_CHARACTERS)
   private String path;

   /** The File. */
   @NotBlank(message = PathForm.NOT_BLANK_MESSAGE)
   @Size(max = 50, message = PathForm.MAX_CHARACTERS)
   private String file;

   /**
    * Instantiates a new PathForm.
    */
   public PathForm() {
      // Default empty constructor.
   }

   /**
    * Gets the path.
    *
    * @return the path
    */
   public String getPath() {
      return path;
   }

   /**
    * Sets the path.
    *
    * @param path
    *           the new path
    */
   public void setPath(String path) {
      this.path = path;
   }

   /**
    * Gets the file.
    *
    * @return the file
    */
   public String getFile() {
      return file;
   }

   /**
    * Sets the file.
    *
    * @param file
    *           the new file
    */
   public void setFile(String file) {
      this.file = file;
   }

}
