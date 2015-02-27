package org.cuacfm.members.web.signup;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.cuacfm.members.model.account.*;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.userService.UserService;
import org.cuacfm.members.web.support.*;

/**
 * The Class SignupController.
 */
@Controller
public class SignupController {

    /** The Constant SIGNUP_VIEW_NAME. */
    private static final String SIGNUP_VIEW_NAME = "signup/signup";

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
     * Signup.
     *
     * @param model
     *            the model
     * @return the string
     */
	@RequestMapping(value = "signup")
	public String signup(Model model) {
		model.addAttribute(new SignupForm());
        return SIGNUP_VIEW_NAME;
	}
	
    /**
     * Signup.
     *
     * @param signupForm
     *            the signup form
     * @param errors
     *            the errors
     * @param ra
     *            the ra
     * @return the string
     */
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra) {
		
        // check that doesn't exist other email
        String email = signupForm.getEmail();
        if (accountService.findByEmail(email) != null) {
            errors.rejectValue("email", "signup.existentEmail", new Object[] { email }, "email");
        }
        
        // check that doesn't exist other login
        String login = signupForm.getLogin();
        if (accountService.findByLogin(login) != null) {
        	 errors.rejectValue("login", "signup.existentLogin", new Object[] { login }, "login");
        }
                
        // check that the password and rePassword are the same
        String password = signupForm.getPassword();
        String rePassword = signupForm.getRePassword();
        if (!password.equals(rePassword)) {
        	 errors.rejectValue("rePassword", "signup.passwordsDontMatch");
        }      
        
		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		
		Account account = accountService.save(signupForm.createAccount());
		userService.signin(account);
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
        MessageHelper.addSuccessAttribute(ra, "signup.success");
		return "redirect:/";
	}
}
