package org.cuacfm.members.web.profile;

import java.security.Principal;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.userService.UserService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class ProfileController.*/
@Controller
public class ProfileController {

	/** The Constant PROFILE_VIEW_NAME. */
	private static final String PROFILE_VIEW_NAME = "profile/profile";

	/** The account service. */
	@Autowired
	private AccountService accountService;

	/** The user service. */
	@Autowired
	private UserService userService;

	/**
	 * Instantiates a new Profile controller.
	 */
	public ProfileController() {
		// Default empty constructor.
	}

	
////////////////////////////////////////////////////////////////////////////////
	/**
	 * Profile.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 * @RequestMapping(value = "profile") public String profile(Model model) {
	 *                       model.addAttribute(new ProfileForm()); return
	 *                       PROFILE_VIEW_NAME; }
	 */
////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Profile.
	 * 
	 * @param model
	 *            the model
	 * @param principal
	 *            the actual user
	 * @return the string
	 */
	@RequestMapping(value = "profile", method = RequestMethod.GET)
	public String profile(Model model, Principal principal) {
		model.addAttribute(new ProfileForm());

		// Show info about user
		Account account = accountService.findByLogin(principal.getName());
		model.addAttribute("name", account.getName());
		model.addAttribute("login", account.getLogin());
		model.addAttribute("email", account.getEmail());
		model.addAttribute("role", account.getRole());
		return PROFILE_VIEW_NAME;
	}

	/**
	 * Profile.
	 *
	 * @param signupForm
	 *            the signup form
	 * @param errors
	 *            the errors
	 * @param ra
	 *            the RedirectAttributes
	 * @param model
	 *            the model
	 * @param principal
	 *            the actual user
	 * @return the string
	 */
	@RequestMapping(value = "profile", method = RequestMethod.POST)
	public String profile(@Valid @ModelAttribute ProfileForm profileForm,
			Errors errors, RedirectAttributes ra, Principal principal,
			Model model) {

		// Show info about user
		Account account = accountService.findByLogin(principal.getName());
		model.addAttribute("name", account.getName());
		model.addAttribute("login", account.getLogin());
		model.addAttribute("email", account.getEmail());
		model.addAttribute("role", account.getRole());

		if (errors.hasErrors()) {
			return PROFILE_VIEW_NAME;
		}

		boolean modify = false;

		String name = profileForm.getName();
		if (name != null && name != "") {
			account.setName(name);
			modify = true;
		}

		boolean modifyLogin = false;
		String login = profileForm.getLogin();
		if (login != null && login != "") {
			// check that doesn't exist other login
			if (accountService.findByLogin(login) != null) {
				errors.rejectValue("login", "profile.existentLogin",
						new Object[] { login }, "login");
			} else {
				account.setLogin(login);
				modify = true;
				modifyLogin = true;
			}
		}

		String email = profileForm.getEmail();
		if (email != null && email != "") {
			// check that doesn't exist other email
			if (accountService.findByEmail(email) != null) {
				errors.rejectValue("email", "profile.existentEmail",
						new Object[] { email }, "email");
			} else {
				account.setEmail(email);
				modify = true;
			}
		}

		boolean modifyPassword = false;
		if (profileForm.getPassword() != null) {
			// check that the password and rePassword are the same
			String password = profileForm.getPassword();
			String rePassword = profileForm.getRePassword();
			if (!password.equals(rePassword)) {
				errors.rejectValue("password", "profile.passwordsDontMatch");
				errors.rejectValue("rePassword", "profile.passwordsDontMatch");
			} else {
				account.setPassword(password);
				modify = true;
				modifyPassword = true;
			}
		}

		if (errors.hasErrors()) {
			return PROFILE_VIEW_NAME;
		}

		// If correct
		if (modify == true) {
			accountService.update(account, modifyPassword);
			if (modifyLogin == true) {
				userService.signin(account);
			}
			MessageHelper.addSuccessAttribute(ra, "profile.success");
			return "redirect:/profile";
		}

		return PROFILE_VIEW_NAME;
	}

}
