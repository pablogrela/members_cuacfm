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
package org.cuacfm.members.test.model.bankremittance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class BankRemittanceServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BankRemittanceServiceTest extends WebSecurityConfigurationAware {

	/** The direct debit service. */
	@Autowired
	private BankRemittanceService bankRemittanceService;

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

	@Autowired
	private DirectDebitService directDebitService;

	/**
	 * Save and update user pay inscription test.
	 *
	 * @throws UniqueListException the unique exception
	 */
	@Before
	public void SaveAndUpdateDirectDebitTest() throws UniqueException, UniqueListException {
		MethodPayment methodPayment = new MethodPayment("name", true, "domiciliacion");
		methodPaymentService.save(methodPayment);
		MethodPayment methodPayment2 = new MethodPayment("name 2", false, "sin domiciliacion");
		methodPaymentService.save(methodPayment2);

		// Create User
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user1", "11111111C", "London", "user11", "user1@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
		accountService.save(account);
		account.setMethodPayment(methodPayment);
		BankAccount bankAccount = new BankAccount(account, "Banco", "BSCHESMMXXX", "ES7620770024003102575766", new Date());
		accountService.saveBankAccount(bankAccount);
		bankAccounts.add(bankAccount);
		account.setBankAccounts(bankAccounts);
		accountService.update(account, false, true);
		accounts.add(account);

		Account account2 = new Account("user2", "12222222C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account2);
		account2.setMethodPayment(methodPayment);
		List<BankAccount> bankAccounts2 = new ArrayList<BankAccount>();
		BankAccount bankAccount2 = new BankAccount(account2, "La CAIXA", "BSCHESMMXXX", "ES7620770024003102575766", new Date());
		accountService.saveBankAccount(bankAccount2);
		bankAccounts2.add(bankAccount2);
		account2.setBankAccounts(bankAccounts2);
		accountService.update(account, false, true);
		accounts.add(account2);

		Account account3 = new Account("user3", "33333333C", "London", "user3", "user3@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account3);
		accounts.add(account3);

		Account account4 = new Account("user4", "444444444C", "London", "user4", "user4@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account4);
		account4.setMethodPayment(methodPayment2);
		List<BankAccount> bankAccounts4 = new ArrayList<BankAccount>();
		bankAccounts.add(bankAccount2);
		account4.setBankAccounts(bankAccounts4);
		accountService.update(account4, false, true);
		accounts.add(account4);

		Account account5 = new Account("user5", "55555555C", "London", "user5", "user5@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account5);
		account5.setMethodPayment(methodPayment);
		BankAccount bankAccount3 = new BankAccount(account5, "La CAIXA", "BSCHESMMXXX", "ES7620770024003102575766", new Date());
		accountService.saveBankAccount(bankAccount3);
		List<BankAccount> bankAccounts5 = new ArrayList<BankAccount>();
		bankAccounts5.add(bankAccount3);
		account5.setBankAccounts(bankAccounts5);
		accountService.update(account5, false, true);
		accounts.add(account5);

		Account account6 = new Account("user6", "66666666C", "London", "user6", "user6@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account6);
		account6.setMethodPayment(methodPayment);
		BankAccount bankAccount4 = new BankAccount(account6, "La CAIXA", "BSCHESMMXXX", "ES7620770024003102575766", new Date());
		accountService.saveBankAccount(bankAccount4);
		List<BankAccount> bankAccounts6 = new ArrayList<BankAccount>();
		bankAccounts6.add(bankAccount4);
		account6.setBankAccounts(bankAccounts6);
		account6.setInstallments(6);
		accountService.update(account6, false, true);
		accounts.add(account6);

		FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20), DisplayDate.stringToDate2("2015-04-01"),
				DisplayDate.stringToDate2("2015-07-01"), "pay of 2016");
		feeMemberService.save(feeMember);

		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.up(program);

		Program program2 = new Program("Pepe2", "Very interesting", Float.valueOf(1), 9, new ArrayList<Account>(), account,
				programService.findProgramTypeById(1), programService.findProgramThematicById(1), programService.findProgramCategoryById(1),
				programService.findProgramLanguageById(1), "", "", "", "", "");
		programService.save(program2);
		programService.up(program2);

		List<Account> accounts2 = new ArrayList<Account>();
		accounts2.add(account3);
		Program program3 = new Program("Pepe3", "Very interesting", Float.valueOf(1), 9, accounts2, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program3);
		programService.up(program3);

		// Save
		Date date = DisplayDate.stringToMonthOfYear("2015-12");
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);
	}

	/*
	@Test
	public void findOpenList() throws Exception {
	  directDebitService.getDirectDebitListActive();
	  assertEquals(directDebitService.getDirectDebitListActive().size(), 0);
	  assertEquals(directDebitService.getDirectDebitListClose(LocalDate.now().getYear()).size(), 0);
	
	  directDebitService.createBankRemittance(messageSource, new Date());
	  assertEquals(directDebitService.getDirectDebitListActive().size(), 6);
	  assertEquals(directDebitService.getDirectDebitListClose(LocalDate.now().getYear()).size(), 0);
	
	  directDebitService.pay(directDebitService.getDirectDebitListActive().get(0));
	  assertEquals(directDebitService.getDirectDebitListActive().size(), 5);
	  assertEquals(directDebitService.getDirectDebitListClose(LocalDate.now().getYear()).size(), 1);
	  
	  directDebitService.createBankRemittance(messageSource, new Date());
	  assertEquals(directDebitService.getDirectDebitListActive().size(), 11);
	
	}
	
	
	@Test
	public void updateDirectDebit() throws Exception {
	
	  directDebitService.createBankRemittance(messageSource, new Date());
	  DirectDebit directDebit = directDebitService.getDirectDebitListActive().get(0);
	
	  directDebit.setActive(false);
	  directDebit.setConcept("probe");
	  directDebit.setPrice(Double.valueOf(22));
	  directDebit.setDateCharge(new Date());
	  directDebit.setDateDebit(new Date());
	  directDebit.setMandate("FNAL");
	  directDebit.setSecuence("FNAL");
	  directDebit.setMandate("mandate");
	  directDebit.setNotes("notes");
	
	  directDebitService.update(directDebit);
	  DirectDebit directDebitSearched = directDebitService.findById(directDebit.getId());
	  assertEquals(directDebit, directDebitSearched);
	  assertEquals(directDebit.getConcept(), directDebitSearched.getConcept());
	  assertEquals(directDebit.getDateCharge(), directDebitSearched.getDateCharge());
	  assertEquals(directDebit.getDateDebit(), directDebitSearched.getDateDebit());
	  assertEquals(directDebit.getMandate(), directDebitSearched.getMandate());
	  assertEquals(directDebit.getPrice(), directDebitSearched.getPrice());
	  assertEquals(directDebit.getSecuence(), directDebitSearched.getSecuence());
	  assertEquals(directDebit.isActive(), directDebitSearched.isActive());
	  assertEquals(directDebit.getNotes(), directDebitSearched.getNotes());
	}*/

	/**
	 * Find direct debit.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void findDirectDebit() throws Exception {

		bankRemittanceService.createBankRemittance(new Date(), DisplayDate.stringToDate2("2015-01-01"));
		BankRemittance bankRemittance = bankRemittanceService.getBankRemittanceList().get(0);
		bankRemittanceService.update(bankRemittance);
		DirectDebit directDebit = directDebitService.findAllByBankRemittanceId(bankRemittance.getId()).get(0);

		assertEquals(directDebit.getBankRemittance(), bankRemittance);
		// Not Null
		assertNotNull(directDebitService.findById(directDebit.getId()));

		// Null
		assertNull(directDebitService.findById(""));
	}

	/**
	 * Find bank remittance.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void findBankRemittance() throws Exception {

		bankRemittanceService.createBankRemittance(new Date(), DisplayDate.stringToDate2("2015-01-01"));
		BankRemittance bankRemittance = bankRemittanceService.getBankRemittanceList().get(0);

		// Not Null
		assertNotNull(bankRemittanceService.findById(bankRemittance.getId()));

		// Null
		assertNull(bankRemittanceService.findById(Long.valueOf(0)));
	}

	/**
	 * Bank remittance.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void bankRemittance() throws Exception {

		bankRemittanceService.createBankRemittance(new Date(), DisplayDate.stringToDate2("2015-01-01"));
		BankRemittance bankRemittance = bankRemittanceService.getBankRemittanceList().get(0);
		DirectDebit directDebit = directDebitService.findAllByBankRemittanceId(bankRemittance.getId()).get(0);
		//bankRemittanceService.updateDirectDebit(directDebit);
		directDebitService.management(directDebit, null);
		directDebitService.returnBill(directDebit, null);
		assertTrue(directDebit.getState().equals(states.RETURN_BILL));
		bankRemittanceService.managementBankRemittance(bankRemittance);

		// State of directDebit not change
		assertTrue(directDebit.getState().equals(states.RETURN_BILL));
	}

}
