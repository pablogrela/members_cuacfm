package org.cuacfm.members.web.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;

/** General error handler for the application. */
@ControllerAdvice
class ExceptionHandler {

   /** Instantiates a new exception handler. */
   public ExceptionHandler() {
      // Default empty constructor.
   }

   /**
    * Handle exceptions thrown by handlers.
    *
    * @param exception
    *           the exception
    * @return the model and view
    */
   @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
   public ModelAndView exception(Exception exception) {
      ModelAndView modelAndView = new ModelAndView("error/general");
      modelAndView.addObject("errorMessage", Throwables.getRootCause(exception));
      return modelAndView;
   }
}