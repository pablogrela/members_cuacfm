package org.cuacfm.members.web.account;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.accountTypeService.AccountTypeService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.methodPaymentService.MethodPaymentService;
import org.cuacfm.members.model.userService.UserService;
import org.cuacfm.members.web.profile.ProfileForm;
import org.cuacfm.members.web.support.DisplayDate;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class ProfileController. */
@Controller
public class AccountController {

	/** The Constant PROFILE_VIEW_NAME. */
	private static final String PROFILE_VIEW_NAME = "account/account";

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
	private Account account;

	/**
	 * Instantiates a new Profile controller.
	 */
	public AccountController() {
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
	@RequestMapping(value = "account")
	public String profile(Model model) {
		if (account != null) {
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
			profileForm.setDateBirth(DisplayDate.dateToString(account
					.getDateBirth()));
			if (account.getAccountType() != null) {
				profileForm.setAccountTypeId(account.getAccountType().getId());
			}
			profileForm.setAccountTypes(accountTypeService.getAccountTypes());
			if (account.getMethodPayment() != null) {
				profileForm.setMethodPaymentId(account.getMethodPayment()
						.getId());
			}
			profileForm.setMethodPayments(methodPaymentService
					.getMethodPayments());
			profileForm.setInstallments(account.getInstallments());
			profileForm.setObservations(account.getObservations());
			profileForm.setRole(String.valueOf(account.getRole()));
			profileForm.setRoles(java.util.Arrays.asList(roles.values()));
			model.addAttribute(profileForm);
			return PROFILE_VIEW_NAME;
		}
		// If not have account, redirect to accounts
		else {
			return "redirect:/accountList";
		}
	}

	/**
	 * Modify account.
	 *
	 * @param id
	 *            the id
	 * @param ra
	 *            the ra
	 * @return the string
	 */
	@RequestMapping(value = "account/{id}", method = RequestMethod.POST)
	public String modifyAccount(@PathVariable Long id, RedirectAttributes ra) {
		account = accountService.findById(id);
		return "redirect:/account";
	}

	/**
	 * Account.
	 *
	 * @param profileForm
	 *            the profile form
	 * @param errors
	 *            the errors
	 * @param ra
	 *            the RedirectAttributes
	 * @param principal
	 *            the actual user
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "account", method = RequestMethod.POST)
	public String profile(@Valid @ModelAttribute ProfileForm profileForm,
			Errors errors, RedirectAttributes ra, Model model) {

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
			// check that doesn't exist other email
			account.setDni(dni);
			modify = true;

		}

		// Adrress
		String address = profileForm.getAddress();
		if (profileForm.isOnAddress() && address != "") {
			account.setAddress(address);
			modify = true;
		}

		// Login, Queries to database
		String login = profileForm.getLogin();
		if (profileForm.isOnLogin() && login != "") {
			account.setLogin(login);
			modify = true;
		}

		// Email
		String email = profileForm.getEmail();
		if (profileForm.isOnEmail() && email != "") {
			account.setEmail(email);
			modify = true;
		}

		// Phone
		if (profileForm.isOnPhone()) {
			account.setPhone(profileForm.getPhone());
			modify = true;
		}

		// Mobile
		if (profileForm.isOnMobile()) {
			account.setMobile(profileForm.getMobile());
			modify = true;
		}

		// // ProgramName
		String programName = profileForm.getProgramName();
		if (profileForm.isOnProgramName() && programName != "") {
			account.setProgramName(programName);
			modify = true;
		}

		// Student
		if (profileForm.isOnStudent()) {
			account.setStudent(profileForm.isStudent());
			modify = true;
		}

		// DateBirth
		if (profileForm.isOnDateBirth()) {
			account.setDateBirth(DisplayDate.stringToDate2(profileForm
					.getDateBirth()));
			modify = true;
		}

		// AccountType
		if (profileForm.isOnAccountType()) {
			account.setAccountType(accountTypeService.findById(profileForm
					.getAccountTypeId()));
			modify = true;
		}

		// MethodPayment
		if (profileForm.isOnMethodPayment()) {
			account.setMethodPayment(methodPaymentService.findById(profileForm
					.getMethodPaymentId()));
			modify = true;
		}

		// Installments
		if (profileForm.isOnInstallments()) {
			account.setInstallments(profileForm.getInstallments());
			modify = true;
		}

		// Observations
		if (profileForm.isOnObservations()) {
			account.setObservations(profileForm.getObservations());
			modify = true;
		}

		// Role
		if (profileForm.isOnRole()) {
			account.setRole(roles.valueOf(profileForm.getRole()));
			modify = true;
		}

		// If correct
		if (modify) {
			try {
				accountService.update(account, false);
			} catch (UniqueException e) {
				if (e.getAttribute() == "Dni") {
					errors.rejectValue("dni", "signup.existentDni",
							new Object[] { dni }, "dni");
				}
				if (e.getAttribute() == "Login") {
					errors.rejectValue("login", "signup.existentLogin",
							new Object[] { login }, "login");
				}
				if (e.getAttribute() == "Email") {
					errors.rejectValue("email", "signup.existentEmail",
							new Object[] { email }, "email");
				}
			}

			if (errors.hasErrors()) {
				return PROFILE_VIEW_NAME;
			}
		}

		MessageHelper.addWarningAttribute(ra, "account.successModify",
				account.getName());
		return "redirect:/accountList";
	}

}
