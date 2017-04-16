/**
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.model.accountservice;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.account.AccountDTO;
import org.cuacfm.members.model.account.AccountRepository;
import org.cuacfm.members.model.bankaccount.BankAccount;
import org.cuacfm.members.model.bankaccount.BankAccountRepository;
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.model.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** The Class AccountService. */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Value("${path}${pathJsonToAccount}")
	private String pathJsonToAccount;
	
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Autowired
	private EventService eventService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JsonToAccount jsonToAccount;

	public AccountServiceImpl() {
		// Default empty constructor.
	}

	@Override
	public Account save(Account account) throws UniqueListException {

		List<UniqueException> uniques = new ArrayList<>();

		// It is verified that there is not exist dni
		if (accountRepository.findByDni(account.getDni()) != null) {
			uniques.add(new UniqueException("dni", account.getDni()));
		}

		// It is verified that there is not exist login
		if (accountRepository.findByLogin(account.getLogin()) != null) {
			uniques.add(new UniqueException("login", account.getLogin()));
		}

		// It is verified that there is not exist email
		if (accountRepository.findByEmail(account.getEmail()) != null) {
			uniques.add(new UniqueException("email", account.getEmail()));
		}

		if (!uniques.isEmpty()) {
			throw new UniqueListException(uniques);
		}

		account.setPassword(passwordEncoder.encode(account.getPassword()));
		Account accountNew = accountRepository.save(account);

		Object[] arguments = { account.getName() + " " + account.getSurname()};
		eventService.save("account.successCreate", account, 1, arguments);

		return accountNew;
	}

	@Override
	public Account update(Account account, boolean newPassword, boolean profile) throws UniqueListException {

		List<UniqueException> uniques = new ArrayList<>();

		// It is verified that there is not exist dni
		Account accountSearch = accountRepository.findByDni(account.getDni());
		if ((accountSearch != null) && (accountSearch.getId() != account.getId())) {
			uniques.add(new UniqueException("dni", account.getDni()));
		}

		// It is verified that there is not exist login
		accountSearch = accountRepository.findByLogin(account.getLogin());
		if ((accountSearch != null) && (accountSearch.getId() != account.getId())) {
			uniques.add(new UniqueException("login", account.getLogin()));
		}

		// It is verified that there is not exist email
		accountSearch = accountRepository.findByEmail(account.getEmail());
		if ((accountSearch != null) && (accountSearch.getId() != account.getId())) {
			uniques.add(new UniqueException("email", account.getEmail()));
		}

		if (!uniques.isEmpty()) {
			throw new UniqueListException(uniques);
		}

		if (newPassword) {
			account.setPassword(passwordEncoder.encode(account.getPassword()));
		}
		Account accountUpdate = accountRepository.update(account);

		// Save Message Event
		if (profile) {
			Object[] arguments = { account.getName() + " " + account.getSurname()};
			eventService.save("profile.success", accountUpdate, 3, arguments);
		} else {
			eventService.save("account.admin.successModify", accountUpdate, 3);
		}

		return accountUpdate;
	}

	@Override
	public void delete(Account account) {
		accountRepository.delete(account);
	}

	@Override
	public void removeToken(Account account){
		account.setToken(null);
		accountRepository.update(account);
		Object[] arguments = { account.getName() + ' ' + account.getSurname() };
		eventService.save("account.admin.successFirebase", account, 2, arguments);
	}
	
	@Override
	public void subscribe(Account account) {
		account.setActive(true);
		account.setRole(roles.ROLE_USER);
		accountRepository.update(account);
		eventService.save("account.admin.successSubscribe", account, 3);
	}

	@Override
	public void orderUp(Account account) {
		eventService.save("profile.orderUp", account, 1);
	}
	
	@Override
	public void unsubscribe(Account account) {
		account.setActive(false);
		account.setRole(roles.ROLE_EXUSER);
		account.setDateDown(new Date());
		accountRepository.update(account);
		eventService.save("account.admin.successUnsubscribe", account, 3);
	}

	@Override
	public void orderDown(Account account) {
		eventService.save("profile.orderDown", account, 1);
	}
	
	@Override
	public Account findByDni(String dni) {
		return accountRepository.findByDni(dni);
	}

	@Override
	public Account findByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

	@Override
	public Account findByLogin(String login) {
		return accountRepository.findByLogin(login);
	}

	@Override
	public Account findById(Long id) {
		return accountRepository.findById(id);
	}

	@Override
	public boolean matchPassword(Account account, String rawPassword) {
		return accountRepository.matchPassword(account, rawPassword);
	}

	@Override
	public List<Account> getUsers() {
		return accountRepository.getUsers();
	}

	@Override
	public List<Account> getUsersDirectDebit() {
		return accountRepository.getUsersDirectDebit();
	}

	@Override
	public List<Account> getAccounts() {
		return accountRepository.getAccounts();
	}

	@Override
	public List<Account> getAccountsOrderByActive() {
		return accountRepository.getAccountsOrderByActive();
	}

	@Override
	public List<AccountDTO> getAccountsDTO(List<Account> accounts) {
		List<AccountDTO> accountsDTO = new ArrayList<>();
		for (Account account : accounts) {
			accountsDTO.add(getAccountDTO(account));
		}

		return accountsDTO;
	}

	@Override
	public AccountDTO getAccountDTO(Account account) {
		AccountDTO accountDTO = null;

		if (account != null) {
			accountDTO = new AccountDTO(account.getId(), account.getLogin(), account.getDni(), account.getEmail(), account.getPhone(),
					account.getMobile(), account.getName(), account.getSurname(),account.getNickName(), account.getAddress(), account.isActive(), account.getRole(),
					account.getInstallments(), account.getDateCreate(), account.getDateDown());

			if (account.getAccountType() != null) {
				accountDTO.setAccountType(account.getAccountType().getName());
			}
			if (account.getMethodPayment() != null) {
				accountDTO.setMethodPayment(account.getMethodPayment().getName());
			}
		}

		return accountDTO;
	}

	/**
	 * Gets the name users with role=ROLE_USER an active=true.
	 *
	 * @return the name users
	 */
	@Override
	public List<String> getUsernames() {
		return accountRepository.getUsernames();

	}

	/**
	 * Save bank account.
	 *
	 * @param bankAccount the bank account
	 * @return the bank account
	 */
	@Override
	public BankAccount saveBankAccount(BankAccount bankAccount) {
		int id = 0;
		if (bankAccount.getAccount().getBankAccounts() != null) {
			id = bankAccount.getAccount().getBankAccounts().size() + 1;
		}

		String mandate = bankAccount.getAccount().getId() + "_" + bankAccount.getAccount().getDni() + "_" + id;
		bankAccount.setMandate(mandate);

		// Save Message Event
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String adminName = null;
		if (auth != null) {
			adminName = auth.getName();
		}
		Object[] arguments = { adminName, bankAccount.getAccount().getName(), bankAccount.getBank() };
		eventService.save("account.admin.successCreateBankAccount", bankAccount.getAccount(), 3, arguments);

		return bankAccountRepository.save(bankAccount);
	}

	/**
	 * Active bank account by account id.
	 *
	 * @param accountId the account id
	 * @return the bank account
	 */
	@Override
	public BankAccount activeBankAccountByAccountId(Long accountId) {
		// eventRepository.save(new Event(bankAccount.getAccount(), new Date(), 3, "prueba"));
		return bankAccountRepository.activeBankAccountByAccountId(accountId);
	}

	@Override
	public String processJson(MultipartFile file) {
		logger.info("processJson");

		try {
			byte[] bytes = file.getBytes();
			FileUtils.createFolderIfNoExist(pathJsonToAccount);
			
			String[] originalFilename = file.getOriginalFilename().split(".json");
			Path pathJson = Paths.get(pathJsonToAccount + originalFilename[0] + DateUtils.format(new Date(), DateUtils.FORMAT_FILE) + ".json");
			Files.write(pathJson, bytes);
			jsonToAccount.parser(pathJson.toString());

		} catch (Exception e) {
			logger.error("processJson: ", e);
			Object[] arguments = {};
			eventService.save("account.failUpload", null, 2, arguments);
			return "account.failUpload";
		}
		Object[] arguments = { file.getOriginalFilename() };
		eventService.save("account.successUpload", null, 2, arguments);
		return "account.successUpload";
	}
}
