package org.cuacfm.members.web.account;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class AccountListController. */
@Controller
public class AccountListController {

	/** The Constant ACCOUNT_VIEW_NAME. */
	private static final String ACCOUNT_VIEW_NAME = "account/accountlist";

	/** The TrainingTypeService. */
	@Autowired
	private AccountService accountService;

	/** The rol admin. */
	private roles rolAdmin = roles.ROLE_ADMIN;

	/**
	 * Instantiates a new account List Controller.
	 */
	public AccountListController() {
		// Default empty constructor.
	}

	/**
	 * Accounts.
	 *
	 * @return the list
	 */
	@ModelAttribute("accounts")
	public List<Account> accounts() {
		return accountService.getAccounts();
	}

	/**
	 * Rol admin.
	 *
	 * @return the roles
	 */
	@ModelAttribute("rolAdmin")
	public roles rolAdmin() {
		return rolAdmin;
	}

	/**
	 * Gets the accounts.
	 *
	 * @param model
	 *            the model
	 * @return the accounts
	 */
	@RequestMapping(value = "accountList")
	public String getAccounts(Model model) {
		return ACCOUNT_VIEW_NAME;
	}

	/**
	 * Unsubscribe the account.
	 *
	 * @param id
	 *            the id
	 * @param ra
	 *            the ra
	 * @return the string
	 */
	@RequestMapping(value = "accountList/accountUnsubscribe/{id}", method = RequestMethod.POST)
	public String unsubscribe(@PathVariable Long id, RedirectAttributes ra) {

		Account account = accountService.findById(id);
		String name = account.getName();
		if (account.getRole() == roles.ROLE_ADMIN) {
			MessageHelper.addErrorAttribute(ra, "account.errorUnsubscribe",
					name);
		} else {
			accountService.Unsubscribe(id);
			MessageHelper.addInfoAttribute(ra, "account.successUnsubscribe",
					name);
		}
		return "redirect:/accountList";
	}

	/**
	 * Subscribe the account.
	 *
	 * @param id
	 *            the id
	 * @param ra
	 *            the ra
	 * @return the string
	 */
	@RequestMapping(value = "accountList/accountSubscribe/{id}", method = RequestMethod.POST)
	public String Subscribe(@PathVariable Long id, RedirectAttributes ra) {

		String name = accountService.findById(id).getName();
		accountService.Subscribe(id);
		MessageHelper.addInfoAttribute(ra, "account.successSubscribe", name);
		return "redirect:/accountList";
	}
}
