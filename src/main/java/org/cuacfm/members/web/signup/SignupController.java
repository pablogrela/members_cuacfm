package org.cuacfm.members.web.signup;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.userservice.UserService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class SignupController.*/
@Controller
public class SignupController {

   /** The Constant SIGNUP_VIEW_NAME. */
   private static final String SIGNUP_VIEW_NAME = "signup/signup";

   /** The ConfigurationService. */
   @Autowired
   private ConfigurationService configurationService;
   
   /** The description rul. */
   private String descriptionRul;
   
   /** The account service. */
   @Autowired
   private AccountService accountService;

   /** The user service. */
   @Autowired
   private UserService userService;

   /**
    * Instantiates a new signup Controller.
    */
   public SignupController() {
      // Default empty constructor.
   }

   /**
    * Description rul.
    *
    * @return the string
    */
   @ModelAttribute("descriptionRul")
   public String descriptionRul() {
      return descriptionRul;
   }
   
   /**
    * Signup.
    *
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "signup")
   public String signup(Model model) {
      descriptionRul = configurationService.getConfiguration().getDescriptionRul();
      model.addAttribute("descriptionRul", descriptionRul);
      model.addAttribute(new SignupForm());
      return SIGNUP_VIEW_NAME;
   }

   /**
    * Signup.
    *
    * @param signupForm
    *           the signup form
    * @param errors
    *           the errors
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "signup", method = RequestMethod.POST)
   public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors,
         RedirectAttributes ra) {

      // check that the password and rePassword are the same
      String password = signupForm.getPassword();
      String rePassword = signupForm.getRePassword();
      if (!password.equals(rePassword)) {
         errors.rejectValue("rePassword", "signup.passwordsDontMatch");
      }

      // check that rule = true
      boolean rule = signupForm.getRule();
      if (!rule) {
         errors.rejectValue("rule", "signup.existentRule", new Object[] { "rule" }, "rule");
      }

      if (errors.hasErrors()) {
         return SIGNUP_VIEW_NAME;
      }

      try {
         Account account;
         account = accountService.save(signupForm.createAccount());
         account.setProgramName(signupForm.getProgramName());
         accountService.update(account, false);
         userService.signin(account);
      } catch (UniqueException e) {
         if (e.getAttribute() == "Dni") {
            errors.rejectValue("dni", "signup.existentDni", new Object[] { e.getValue() }, "dni");
         }
         if (e.getAttribute() == "Login") {
            errors.rejectValue("login", "signup.existentLogin", new Object[] { e.getValue() },
                  "login");
         }
         if (e.getAttribute() == "Email") {
            errors.rejectValue("email", "signup.existentEmail", new Object[] { e.getValue() },
                  "email");
         }
      }

      if (errors.hasErrors()) {
         return SIGNUP_VIEW_NAME;
      }

      // see /WEB-INF/i18n/messages.properties and
      // /WEB-INF/views/homeSignedIn.html
      MessageHelper.addSuccessAttribute(ra, "signup.success");
      return "redirect:/";
   }
}
