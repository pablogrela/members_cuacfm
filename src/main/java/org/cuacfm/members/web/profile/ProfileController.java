package org.cuacfm.members.web.profile;

import java.security.Principal;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.accountTypeService.AccountTypeService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.methodPaymentService.MethodPaymentService;
import org.cuacfm.members.model.userService.UserService;
import org.cuacfm.members.web.support.DisplayDate;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class ProfileController. */
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
	
	/** The account Type service. */
	@Autowired
	private AccountTypeService accountTypeService;
	
	/** The account Method Payment service. */
	@Autowired
	private MethodPaymentService methodPaymentService;

	/** The Global variable account. */
	private Account account = null;

	/**
	 * Instantiates a new Profile controller.
	 */
	public ProfileController() {
		// Default empty constructor.
	}

	/**
	 * Profile.
	 * 
	 * @param model
	 *            the model
	 * @param principal
	 *            the actual user
	 * @return the string
	 */
	@RequestMapping(value = "profile")
	public String profile(Model model, Principal principal) {

		account = accountService.findByLogin(principal.getName());
			
		// Show info about user
		ProfileForm profileForm = new ProfileForm();
		profileForm.setName(account.getName());
		profileForm.setDni(account.getDni());
		profileForm.setAddress(account.getAddress());
		profileForm.setLogin(account.getLogin());
		profileForm.setEmail(account.getEmail());
		profileForm.setPhone(account.getPhone());
		profileForm.setMobile(account.getMobile());
		profileForm.setProgramName(account.getProgramName());
		profileForm.setStudent(account.isStudent());
		profileForm.setDateBirth(DisplayDate.dateToString(account.getDateBirth()));
		model.addAttribute(profileForm);

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

	
		if (errors.hasErrors()) {
			return PROFILE_VIEW_NAME;
		}
		
		boolean modify = false;
		
		// Name
		String name = profileForm.getName();
		if (profileForm.isOnName() && name != "") {
			account.setName(name);
			modify = true;
		}

		// Dni
		String dni = profileForm.getDni();
		if (profileForm.isOnDni() && dni != "") {
			account.setDni(dni);
			modify = true;
		}
		
		// Adrress
		String address = profileForm.getAddress();
		if (profileForm.isOnAddress() && address != "") {
			account.setAddress(address);
			modify = true;
		}

		// Password
		boolean modifyPassword = false;
		if (profileForm.isOnPassword()) {
			// check that the password and rePassword are the same
			String password = profileForm.getPassword();
			String rePassword = profileForm.getRePassword();
			if (!password.equals(rePassword)) {
				errors.rejectValue("password", "profile.passwordsDontMatch");
				errors.rejectValue("rePassword", "profile.passwordsDontMatch");
				return PROFILE_VIEW_NAME;
			} else {
				account.setPassword(password);
				modify = true;
				modifyPassword = true;
			}
		}

		// Login, Queries to database
		boolean modifyLogin = false;
		String login = profileForm.getLogin();
		if (profileForm.isOnLogin() && login != "") {
			// check that doesn't exist other login
			account.setLogin(login);
			modify = true;
			modifyLogin = true;	
		}

		// Email
		String email = profileForm.getEmail();
		if (profileForm.isOnEmail() && email != "") {
			// check that doesn't exist other email
			account.setEmail(profileForm.getEmail());
			modify = true;		
		}

		// Phone
		if (profileForm.isOnPhone()){
			account.setPhone(profileForm.getPhone());
			modify = true;
		}
		
		// Mobile
		if (profileForm.isOnMobile()){
			account.setMobile(profileForm.getMobile());
			modify = true;
		}
		
		// ProgramName
		String programName = profileForm.getProgramName();
		if (profileForm.isOnProgramName() && programName != "") {
			account.setProgramName(programName);
			modify = true;
		}
		
		// Student
		if (profileForm.isOnStudent()){
			account.setStudent(profileForm.isStudent());
			modify = true;
		}

		// DateBirth
		if (profileForm.isOnDateBirth()){
			account.setDateBirth(DisplayDate.stringToDate2(profileForm.getDateBirth()));
			modify = true;
		}
		
		// If correct
		if (modify) {
			try {
				accountService.update(account, modifyPassword);
			} catch (UniqueException e) {
				if (e.getAttribute() == "Dni") {
					errors.rejectValue("dni", "signup.existentDni",
							new Object[] { dni }, "dni");
				}
				if (e.getAttribute() == "Login") {
					errors.rejectValue("login", "profile.existentLogin",
							new Object[] { login }, "login");
				}
				if (e.getAttribute() == "Email") {
					errors.rejectValue("email", "profile.existentEmail",
							new Object[] { email }, "email");
				}
			}
			
			if (errors.hasErrors()) {
				return PROFILE_VIEW_NAME;
			}
			
			if (modifyLogin) {
				userService.signin(account);
			}
			MessageHelper.addSuccessAttribute(ra, "profile.success");
		}
		
		return "redirect:/profile";
	}

}
