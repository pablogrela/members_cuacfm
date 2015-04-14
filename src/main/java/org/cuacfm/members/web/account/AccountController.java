package org.cuacfm.members.web.account;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.accountTypeService.AccountTypeService;
import org.cuacfm.members.model.methodPaymentService.MethodPaymentService;
import org.cuacfm.members.model.userService.UserService;
import org.cuacfm.members.web.profile.ProfileForm;
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
			profileForm.setLogin(account.getLogin());
			profileForm.setEmail(account.getEmail());
			profileForm.setAccountTypeId(account.getAccountType().getId());
			profileForm.setAccountTypes(accountTypeService.getAccountTypes());
			profileForm.setMethodPaymentId(account.getMethodPayment().getId());
			profileForm.setMethodPayments(methodPaymentService
					.getMethodPayments());
			profileForm.setInstallments(account.getInstallments());
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

		boolean modify = false;

		// Name
		String name = profileForm.getName();
		if (profileForm.getOnName() && name != "") {
			account.setName(name);
			modify = true;
		}

		// Password
		boolean modifyPassword = false;
		if (profileForm.getOnPassword()) {
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

		// Login, Queries to database
		Account accountSearch;
		String login = profileForm.getLogin();
		if (profileForm.getOnLogin() && login != "") {
			// check that doesn't exist other login
			accountSearch = accountService.findByLogin(login);
			if (accountSearch != null) {
				if (accountSearch.getId() != account.getId()) {
					errors.rejectValue("login", "profile.existentLogin",
							new Object[] { login }, "login");
				}
			} else {
				account.setLogin(login);
				modify = true;
			}
		}

		// Email
		String email = profileForm.getEmail();
		if (profileForm.getOnEmail() && email != "") {
			// check that doesn't exist other email
			accountSearch = accountService.findByEmail(email);
			if (accountSearch != null) {
				if (accountSearch.getId() != account.getId()) {
					errors.rejectValue("email", "profile.existentEmail",
							new Object[] { email }, "email");
				}
			} else {
				account.setEmail(email);
				modify = true;
			}
		}

		// AccountType
		if (profileForm.getOnAccountType()) {
			account.setAccountType(accountTypeService.findById(profileForm
					.getAccountTypeId()));
			modify = true;
		}

		// MethodPayment
		if (profileForm.getOnMethodPayment()) {
			account.setMethodPayment(methodPaymentService.findById(profileForm
					.getMethodPaymentId()));
			modify = true;
		}

		// Installments
		if (profileForm.getOnInstallments()) {
			account.setInstallments(profileForm.getInstallments());
			modify = true;
		}

		if (errors.hasErrors()) {
			return PROFILE_VIEW_NAME;
		}

		// If correct
		if (modify) {
			accountService.update(account, modifyPassword);
		}

		MessageHelper.addWarningAttribute(ra, "account.successModify",
				account.getName());
		return "redirect:/accountList";
	}

}
