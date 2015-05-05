package org.cuacfm.members.web.home;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** The Class HomeController. */
@Controller
public class HomeController {

   /**
    * Version preliminar
    * 
    * @RequestMapping(value = "/", method = RequestMethod.GET) public String
    *                       index(Principal principal) { return principal !=
    *                       null ? "home/homeSignedIn" : "home/homeNotSignedIn";
    *                       }
    **/

   /**
    * Index.
    *
    * @param principal
    *           the principal
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String index(Principal principal) {

      if (principal != null) {
         return "home/homeSignedIn";
      } else {
         return "home/homeNotSignedIn";
      }
   }
}
