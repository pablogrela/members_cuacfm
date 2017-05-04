/**
 * Copyright © 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.test.web.bankremittance;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.bankaccount.BankAccount;
import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.bankremittanceservice.BankRemittanceService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class BankRemittanceListControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BankRemittanceListControllerTest extends WebSecurityConfigurationAware {

	/** The default session. */
	private MockHttpSession defaultSession;

	/** The fee member service. */
	@Autowired
	private FeeMemberService feeMemberService;

	/** The account service. */
	@Autowired
	private AccountService accountService;

	/** The account type service. */
	@Autowired
	private AccountTypeService accountTypeService;

	/** The method payment service. */
	@Autowired
	private MethodPaymentService methodPaymentService;

	/** The program service. */
	@Autowired
	private ProgramService programService;

	/** The fee program service. */
	@Autowired
	private FeeProgramService feeProgramService;

	/** The bank remittance service. */
	@Autowired
	private BankRemittanceService bankRemittanceService;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		Account admin = new Account("admin", "", "55555555B", "London", "admin", "admin@udc.es", "666666666", "666666666", "admin", roles.ROLE_ADMIN);
		accountService.save(admin);
		defaultSession = getDefaultSession("admin@udc.es");

		MethodPayment methodPayment = new MethodPayment("name", true, "domiciliacion");
		methodPaymentService.save(methodPayment);

		// Create User
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user1", "1", "11111111C", "London", "user11", "user1@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		account.setMethodPayment(methodPayment);
		List<BankAccount> bankAccounts = new ArrayList<BankAccount>();

		BankAccount bankAccount = new BankAccount(account, "Banco", "BSCHESMMXXX", "ES7620770024003102575766", new Date());
		accountService.saveBankAccount(bankAccount);
		bankAccounts.add(bankAccount);
		account.setBankAccounts(bankAccounts);

		accountService.update(account, false, true);
		accounts.add(account);

		Account account2 = new Account("user2", "2", "12222222C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account2);
		account2.setMethodPayment(methodPayment);
		List<BankAccount> bankAccounts2 = new ArrayList<BankAccount>();
		BankAccount bankAccount2 = new BankAccount(account2, "Banco", "BSCHESMMXXX", "ES7620770024003102575766", new Date());
		accountService.saveBankAccount(bankAccount2);
		bankAccounts2.add(bankAccount2);
		account2.setBankAccounts(bankAccounts2);
		accountService.update(account2, false, true);
		accounts.add(account2);

		Account account3 = new Account("user3", "3", "33333333C", "London", "user3", "user3@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account3);
		account3.setMethodPayment(methodPayment);

		AccountType accountType = new AccountType("Organization", true, "Organization", 0);
		accountTypeService.save(accountType);
		account3.setAccountType(accountType);

		List<BankAccount> bankAccounts3 = new ArrayList<BankAccount>();
		BankAccount bankAccount3 = new BankAccount(account3, "Banco", "BSCHESMMXXX", "ES7620770024003102575766", new Date());
		accountService.saveBankAccount(bankAccount3);
		bankAccounts3.add(bankAccount3);
		account3.setBankAccounts(bankAccounts3);

		accountService.update(account3, false, true);

		FeeMember feeMember = new FeeMember("pay of 2016", 2016, Double.valueOf(20), DateUtils.format("2016-04-05", DateUtils.FORMAT_DATE),
				DateUtils.format("2016-07-05", DateUtils.FORMAT_DATE), "pay of 2016");
		feeMemberService.save(feeMember);

		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "", "", "", "", "");
		programService.save(program);
		programService.up(program);
		program.setAccountPayer(account);
		programService.update(program);
		
		Program program2 = new Program("Pepe2", "Very interesting", Float.valueOf(1), 9, new ArrayList<Account>(), account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "", "", "", "", "");
		programService.save(program2);
		programService.up(program2);

		List<Account> accounts2 = new ArrayList<Account>();
		accounts2.add(account3);
		
		Program program3 = new Program("Pepe3", "Very interesting", Float.valueOf(1), 9, accounts2, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "", "", "", "", "");
		programService.save(program3);
		programService.up(program3);
		program3.setAccountPayer(account2);
		programService.update(program3);

		// Save
		Date date = DateUtils.format("2015-01", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);
	}

	/**
	 * Display PayProgramList page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayPayProgramEditPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/bankRemittanceList")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Displays user pay inscription edit test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displaysBankRemittance() throws Exception {

		mockMvc.perform(get("/bankRemittanceList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("bankremittance/bankremittancelist")).andExpect(model().attributeExists("bankRemittanceForm"))
				.andExpect(content().string(containsString("<title>Bank remittances</title>")));
	}

	/**
	 * Bank remittance test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createBankRemittanceTest() throws Exception {

		mockMvc.perform(post("/bankRemittanceList").locale(Locale.ENGLISH).session(defaultSession).param("dateCharge", "2015-07-05")
				.param("monthCharge", "2015-07")).andExpect(view().name("redirect:/bankRemittanceList"));
	}

	/**
	 * Pay direct debit test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payBankRemittanceTest() throws Exception {

		bankRemittanceService.createBankRemittance(new Date(), DateUtils.format("2015-01-01", DateUtils.FORMAT_DATE));
		BankRemittance bankRemittance = bankRemittanceService.getBankRemittanceList().get(0);

		mockMvc.perform(post("/bankRemittanceList/pay/" + bankRemittance.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/bankRemittanceList"));
		assertTrue(bankRemittance.getState().equals(states.PAY));

		Date date = DateUtils.format("2015-04", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);
		bankRemittanceService.createBankRemittance(new Date(), DateUtils.format("2015-04-01", DateUtils.FORMAT_DATE));

		BankRemittance bankRemittance2 = bankRemittanceService.getBankRemittanceList().get(1);

		mockMvc.perform(post("/bankRemittanceList/pay/" + bankRemittance2.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/bankRemittanceList"));
		assertTrue(bankRemittance2.getState().equals(states.PAY));
	}

	@Test
	public void managementBankRemittanceTest() throws Exception {

		bankRemittanceService.createBankRemittance(new Date(), DateUtils.format("2015-01-01", DateUtils.FORMAT_DATE));
		BankRemittance bankRemittance = bankRemittanceService.getBankRemittanceList().get(0);

		mockMvc.perform(post("/bankRemittanceList/management/" + bankRemittance.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/bankRemittanceList"));
		assertTrue(bankRemittance.getState().equals(states.MANAGEMENT));

		Date date = DateUtils.format("2015-04", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);
		bankRemittanceService.createBankRemittance(new Date(), DateUtils.format("2015-04-01", DateUtils.FORMAT_DATE));

		BankRemittance bankRemittance2 = bankRemittanceService.getBankRemittanceList().get(1);

		mockMvc.perform(post("/bankRemittanceList/management/" + bankRemittance2.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/bankRemittanceList"));
		assertTrue(bankRemittance2.getState().equals(states.MANAGEMENT));
	}

	/**
	 * Download bank remittance test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void downloadBankRemittanceTest() throws Exception {

		bankRemittanceService.createBankRemittance(new Date(), DateUtils.format("2015-01-01", DateUtils.FORMAT_DATE));
		BankRemittance bankRemittance = bankRemittanceService.getBankRemittanceList().get(0);

		mockMvc.perform(post("/bankRemittanceList/downloadBankRemittance/" + bankRemittance.getId()).locale(Locale.ENGLISH).session(defaultSession));
	}
}