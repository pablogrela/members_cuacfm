
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

	@RequestMapping(value = "accountListAjax")
	public String getAccounts() {
		return ACCOUNT_VIEW_NAME;
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
			return new ResponseEntity<List<AccountDTO>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<AccountDTO>>(accountsDTO, HttpStatus.OK);
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
			System.out.println("Unable to delete. User with id " + id + " not found");
			return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
		}

		accountService.unsubscribe(id);
		AccountDTO accountDTO = new AccountDTO(account.getId(), account.getLogin(), account.getDni(), account.getEmail(), account.getName(),
				account.getNickName(), account.getAddress(), account.isActive(), account.getRole(), account.getInstallments());
		return new ResponseEntity<AccountDTO>(accountDTO, HttpStatus.OK);
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
			System.out.println("Unable to delete. User with id " + id + " not found");
			return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
		}

		accountService.subscribe(id);
		AccountDTO accountDTO = new AccountDTO(account.getId(), account.getLogin(), account.getDni(), account.getEmail(), account.getName(),
				account.getNickName(), account.getAddress(), account.isActive(), account.getRole(), account.getInstallments());
		return new ResponseEntity<AccountDTO>(accountDTO, HttpStatus.OK);
	}
}