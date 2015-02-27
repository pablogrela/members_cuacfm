package org.cuacfm.members.web.support;

import static org.cuacfm.members.web.support.Message.MESSAGE_ATTRIBUTE;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class MessageHelper.*/
public final class MessageHelper {

   /**
    * Instantiates a new message helper.
    */
	public MessageHelper() {
       // Default empty constructor.
   }

   /**
    * Adds the success attribute.
    *
    * @param ra
    *            the ra
    * @param message
    *            the message
    * @param args
    *            the args
    */
   public static void addSuccessAttribute(RedirectAttributes ra,
           String message, Object... args) {
       addAttribute(ra, message, Message.Type.SUCCESS, args);
   }

   /**
    * Adds the error attribute.
    *
    * @param ra
    *            the ra
    * @param message
    *            the message
    * @param args
    *            the args
    */
   public static void addErrorAttribute(RedirectAttributes ra, String message,
           Object... args) {
       addAttribute(ra, message, Message.Type.DANGER, args);
   }

   /**
    * Adds the info attribute.
    *
    * @param ra
    *            the ra
    * @param message
    *            the message
    * @param args
    *            the args
    */
   public static void addInfoAttribute(RedirectAttributes ra, String message,
           Object... args) {
       addAttribute(ra, message, Message.Type.INFO, args);
   }

   /**
    * Adds the warning attribute.
    *
    * @param ra
    *            the ra
    * @param message
    *            the message
    * @param args
    *            the args
    */
   public static void addWarningAttribute(RedirectAttributes ra,
           String message, Object... args) {
       addAttribute(ra, message, Message.Type.WARNING, args);
   }

   /**
    * Adds the attribute.
    *
    * @param ra
    *            the ra
    * @param message
    *            the message
    * @param type
    *            the type
    * @param args
    *            the args
    */
   private static void addAttribute(RedirectAttributes ra, String message,
           Message.Type type, Object... args) {
       ra.addFlashAttribute(MESSAGE_ATTRIBUTE,
               new Message(message, type, args));
   }

   /**
    * Adds the success attribute.
    *
    * @param model
    *            the model
    * @param message
    *            the message
    * @param args
    *            the args
    */
   public static void addSuccessAttribute(Model model, String message,
           Object... args) {
       addAttribute(model, message, Message.Type.SUCCESS, args);
   }

   /**
    * Adds the error attribute.
    *
    * @param model
    *            the model
    * @param message
    *            the message
    * @param args
    *            the args
    */
   public static void addErrorAttribute(Model model, String message,
           Object... args) {
       addAttribute(model, message, Message.Type.DANGER, args);
   }

   /**
    * Adds the info attribute.
    *
    * @param model
    *            the model
    * @param message
    *            the message
    * @param args
    *            the args
    */
   public static void addInfoAttribute(Model model, String message,
           Object... args) {
       addAttribute(model, message, Message.Type.INFO, args);
   }

   /**
    * Adds the warning attribute.
    *
    * @param model
    *            the model
    * @param message
    *            the message
    * @param args
    *            the args
    */
   public static void addWarningAttribute(Model model, String message,
           Object... args) {
       addAttribute(model, message, Message.Type.WARNING, args);
   }

   /**
    * Adds the attribute.
    *
    * @param model
    *            the model
    * @param message
    *            the message
    * @param type
    *            the type
    * @param args
    *            the args
    */
   private static void addAttribute(Model model, String message,
           Message.Type type, Object... args) {
       model.addAttribute(MESSAGE_ATTRIBUTE, new Message(message, type, args));
   }
}
