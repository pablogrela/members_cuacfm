package org.cuacfm.members.web.error;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Throwables;

/** The Class CustomErrorController */
@Controller
class CustomErrorController {

   /** Instantiates a new custom error controller. */
   public CustomErrorController() {
      // Default empty constructor.
   }

   /**
    * Display an error page, as defined in web.xml <code>custom-error</code>
    * element.
    *
    * @param request
    *           the request
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping("generalError")
   public String generalError(HttpServletRequest request, Model model) {
      // retrieve some useful information from the request
      Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
      Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
      // String servletName = (String)
      // request.getAttribute("javax.servlet.error.servlet_name");
      String exceptionMessage = getExceptionMessage(throwable, statusCode);

      String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
      if (requestUri == null) {
         requestUri = "Unknown";
      }

      String message = MessageFormat.format("{0} returned for {1} with message {2}", statusCode,
            requestUri, exceptionMessage);

      model.addAttribute("errorMessage", message);
      return "error/general";
   }

   /**
    * Gets the exception message.
    *
    * @param throwable
    *           the throwable
    * @param statusCode
    *           the status code
    * @return the exception message
    */
   private String getExceptionMessage(Throwable throwable, Integer statusCode) {
      if (throwable != null) {
         return Throwables.getRootCause(throwable).getMessage();
      }
      HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
      return httpStatus.getReasonPhrase();
   }
}
