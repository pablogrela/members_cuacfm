
package org.cuacfm.members.web.account;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.AccountDTO;
import org.cuacfm.members.model.accountservice.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The Class AccountListAjaxController.
 */
//@RestController
@Controller
public class AccountListAjaxController {

	@Autowired
	private AccountService accountService;

	private static final String ACCOUNT_VIEW_NAME = "account/accountlistajax";

	/**
	 * Gets the accounts.
	 *
	 * @return the accounts
	 */
	@RequestMapping(value = "accountListAjax")
	public String getAccounts() {
		return ACCOUNT_VIEW_NAME;
	}

	/**
	 * Gets the accounts ajax pagination.
	 *
	 * @return the accounts ajax pagination
	 */
	@RequestMapping(value = "accountListAjaxPagination")
	public String getAccountsAjaxPagination() {
		return "account/accountlistajaxpagination";
	}

	/**
	 * List all users.
	 *
	 * @return the response entity
	 */
	@RequestMapping(value = "/accountListAjax/", method = RequestMethod.GET)
	public ResponseEntity<List<AccountDTO>> listAllUsers() {

		List<AccountDTO> accountsDTO = accountService.getAccountsDTO();

		if (accountsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(accountsDTO, HttpStatus.OK);
	}

	/**
	 * Unsubscribe.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@RequestMapping(value = "accountListAjax/unsubscribe/{id}", method = RequestMethod.POST)
	public ResponseEntity<AccountDTO> unsubscribe(@PathVariable("id") long id) {

		Account account = accountService.findById(id);
		if (account == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		accountService.unsubscribe(account);
		AccountDTO accountDTO = new AccountDTO(account.getId(), account.getLogin(), account.getDni(), account.getEmail(), account.getName(),
				account.getNickName(), account.getAddress(), account.isActive(), account.getRole(), account.getInstallments());
		return new ResponseEntity<>(accountDTO, HttpStatus.OK);
	}

	/**
	 * Subscribe.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@RequestMapping(value = "accountListAjax/subscribe/{id}", method = RequestMethod.POST)
	public ResponseEntity<AccountDTO> subscribe(@PathVariable("id") long id) {

		Account account = accountService.findById(id);
		if (account == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		accountService.subscribe(account);
		AccountDTO accountDTO = new AccountDTO(account.getId(), account.getLogin(), account.getDni(), account.getEmail(), account.getName(),
				account.getNickName(), account.getAddress(), account.isActive(), account.getRole(), account.getInstallments());
		return new ResponseEntity<>(accountDTO, HttpStatus.OK);
	}
}