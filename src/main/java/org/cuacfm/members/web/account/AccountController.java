package org.cuacfm.members.web.account;

import java.security.Principal;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class AccountController.
 */
@Controller
@Secured("ROLE_USER")
class AccountController {

	/** The account service. */
	@Autowired
	private AccountService accountService;

	/**
	 * Instantiates a new account controller.
	 */
	public AccountController() {
		// Default empty constructor.

	}

	/**
	 * Accounts.
	 *
	 * @param principal
	 *            the principal
	 * @return the account
	 */
	@RequestMapping(value = "account/current", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Account accounts(Principal principal) {
		Assert.notNull(principal);
		return accountService.findByLogin(principal.getName());
	}
}
