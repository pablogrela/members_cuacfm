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

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.bankaccount.BankAccount;
import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.bankremittanceservice.BankRemittanceService;
import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.directdebitservice.DirectDebitService;
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

/** The Class PayProgramEditControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DirectDebitListControllerTest extends WebSecurityConfigurationAware {

	/** The default session. */
	private MockHttpSession defaultSession;

	/** The fee member service. */
	@Autowired
	private FeeMemberService feeMemberService;

	/** The account service. */
	@Autowired
	private AccountService accountService;

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

	@Autowired
	private DirectDebitService directDebitService;

	private BankRemittance bankRemittance;

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
		Account account = new Account("user1", "1", "11111111C", "London", "user11", "user1@udc.es", "666666666", "666666666", "demo",
				roles.ROLE_USER);
		accountService.save(account);
		List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
		BankAccount bankAccount = new BankAccount(account, "Banco", "BSCHESMMXXX", "ES7620770024003102575766", new Date());
		accountService.saveBankAccount(bankAccount);
		account.setMethodPayment(methodPayment);
		bankAccounts.add(bankAccount);
		account.setBankAccounts(bankAccounts);
		accountService.update(account, false, true);
		accounts.add(account);

		Account account2 = new Account("user2", "2", "12222222C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo",
				roles.ROLE_USER);
		accountService.save(account2);
		account2.setMethodPayment(methodPayment);
		List<BankAccount> bankAccounts2 = new ArrayList<BankAccount>();
		BankAccount bankAccount2 = new BankAccount(account2, "Banco", "BSCHESMMXXX", "ES7620770024003102575766", new Date());
		accountService.saveBankAccount(bankAccount2);
		bankAccounts2.add(bankAccount2);
		account2.setBankAccounts(bankAccounts2);
		accountService.update(account2, false, true);
		accounts.add(account2);

		Account account3 = new Account("user3", "3", "33333333C", "London", "user3", "user3@udc.es", "666666666", "666666666", "demo",
				roles.ROLE_USER);
		accountService.save(account3);

		FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20), DateUtils.format("2015-04-05", DateUtils.FORMAT_DATE),
				DateUtils.format("2015-07-05", DateUtils.FORMAT_DATE), "pay of 2015");
		feeMemberService.save(feeMember);

		Program program = new Program("Program", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.up(program);
		program.setAccountPayer(account);
		programService.update(program);

		Program program2 = new Program("Program2", "Very interesting", Float.valueOf(1), 9, new ArrayList<Account>(), account,
				programService.findProgramTypeById(1), programService.findProgramThematicById(1), programService.findProgramCategoryById(1),
				programService.findProgramLanguageById(1), "", "", "", "", "");
		programService.save(program2);
		programService.up(program2);
		program2.setAccountPayer(account2);
		programService.update(program2);

		List<Account> accounts2 = new ArrayList<Account>();
		accounts2.add(account3);
		Program program3 = new Program("Program3", "Very interesting", Float.valueOf(1), 9, accounts2, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program3);
		programService.up(program3);
		program3.setAccountPayer(account2);
		programService.update(program3);

		// Save
		Date date = DateUtils.format("2015-01", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		bankRemittanceService.createBankRemittance(new Date(), DateUtils.format("2015-01-01", DateUtils.FORMAT_DATE));
		bankRemittance = bankRemittanceService.getBankRemittanceList().get(0);
	}

	/**
	 * Display PayProgramList page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayDirectDebitPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/bankremittance/directDebitList")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Displays bank remittance because bank remittance is null test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displaysBankRemittanceBecauseBankRemittanceIsNullTest() throws Exception {

		mockMvc.perform(get("/bankRemittanceList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("bankremittance/bankremittancelist"));
	}

	/**
	 * Displays direct debit test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displaysDirectDebitTest() throws Exception {

		mockMvc.perform(post("/bankRemittanceList/directDebitList/" + bankRemittance.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("bankremittance/directdebitlist"));

		mockMvc.perform(get("/bankRemittanceList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("bankremittance/bankremittancelist"));
	}

	/**
	 * Pay direct debit test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payCashDirectDebitTest() throws Exception {

		DirectDebit directDebit = directDebitService.findAllByBankRemittanceId(bankRemittance.getId()).get(0);

		mockMvc.perform(post("/directDebitList/cash/" + directDebit.getId()).locale(Locale.ENGLISH).session(defaultSession))
		//.andExpect(view().name("bankremittance/directdebitlist"))
		;
		assertTrue(directDebit.getState().equals(states.PAY));

	}

	/**
	 * Pay direct debit test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void returnBillDirectDebitTest() throws Exception {

		DirectDebit directDebit = directDebitService.findAllByBankRemittanceId(bankRemittance.getId()).get(0);

		mockMvc.perform(post("/directDebitList/returnBill/" + directDebit.getId()).locale(Locale.ENGLISH).session(defaultSession));
		//.andExpect(view().name("redirect:/bankRemittanceList/directDebitList"));
		assertTrue(directDebit.getState().equals(states.RETURN_BILL));

		Date date = DateUtils.format("2015-04", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("nameee", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);
		bankRemittanceService.createBankRemittance(new Date(), DateUtils.format("2015-04-01", DateUtils.FORMAT_DATE));

		BankRemittance bankRemittance2 = bankRemittanceService.getBankRemittanceList().get(1);
		DirectDebit directDebit2 = directDebitService.findAllByBankRemittanceId(bankRemittance2.getId()).get(0);

		mockMvc.perform(post("/directDebitList/returnBill/" + directDebit2.getId()).locale(Locale.ENGLISH).session(defaultSession));
		//.andExpect(view().name("redirect:/bankRemittanceList/directDebitList"));
		assertTrue(directDebit.getState().equals(states.RETURN_BILL));
	}
}