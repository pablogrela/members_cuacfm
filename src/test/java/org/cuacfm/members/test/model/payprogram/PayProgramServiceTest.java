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
package org.cuacfm.members.test.model.payprogram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class PayProgramServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PayProgramServiceTest extends WebSecurityConfigurationAware {

	/** The program service. */
	@Autowired
	private ProgramService programService;

	/** The fee program service. */
	@Autowired
	private FeeProgramService feeProgramService;

	/** The pay program service. */
	@Autowired
	private PayProgramService payProgramService;

	/** The account service. */
	@Autowired
	private AccountService accountService;

	/**
	 * Save and update user pay inscription test.
	 *
	 * @throws UniqueException, UniqueListException the unique exception
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	@Test
	public void SaveAndUpdatePayProgramTest() throws UniqueException, UniqueListException, UniqueListException, ExistTransactionIdException {

		// Save
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Account account2 = new Account("user2", "2", "25555555C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account2);
		accounts.add(account2);

		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.up(program);

		// Save
		Date date = DisplayDate.stringToMonthOfYear("2015-12");
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(), feeProgram.getId());

		// Update
		payProgram.setDatePay(new Date());
		payProgram.setState(states.PAY);
		payProgram.setAccountPayer("Pepe");
		payProgram.setEmailPayer("user2@udc.es");
		payProgram.setIdPayer("idPayer");
		payProgram.setIdTxn("IDTXT");
		payProgram.setPrice(Double.valueOf(30));
		payProgramService.update(payProgram);

		// Assert
		PayProgram payProgramSearch = payProgramService.findById(payProgram.getId());
		assertEquals(payProgram, payProgramSearch);
		assertEquals(payProgram.getAccountPayer(), payProgramSearch.getAccountPayer());
		assertEquals(payProgram.getDatePay(), payProgramSearch.getDatePay());
		assertEquals(payProgram.getState(), payProgramSearch.getState());
		assertEquals(payProgram.getIdPayer(), payProgramSearch.getIdPayer());
		assertEquals(payProgram.getIdTxn(), payProgramSearch.getIdTxn());
		assertEquals(payProgram.getEmailPayer(), payProgramSearch.getEmailPayer());
		assertEquals(payProgram.getPrice(), payProgramSearch.getPrice());
	}

	/**
	 * Gets the user pay inscription list by pay inscription test.
	 *
	 * @return the user pay inscription list by pay inscription test
	 * @throws UniqueException, UniqueListException the unique exception
	 */
	@Test
	public void getPayProgramListByFeeProgramTest() throws UniqueException, UniqueListException {

		// Save
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Account account2 = new Account("user2", "2", "25555555C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account2);
		accounts.add(account2);

		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.up(program);

		// Save
		Date date = DisplayDate.stringToMonthOfYear("2015-12");
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// Assert
		List<PayProgram> payPrograms = payProgramService.getPayProgramListByFeeProgramId(feeProgram.getId());
		assertEquals(payPrograms.size(), 1);
		for (PayProgram payProgram : payPrograms) {
			assertEquals(payProgram.getFeeProgram(), feeProgram);
		}

		payPrograms = payProgramService.getPayProgramListByAccountId(account.getId());
		assertEquals(payPrograms.size(), 0);

		payPrograms = payProgramService.getPayProgramListByAccountId(account2.getId());
		assertEquals(payPrograms.size(), 0);

		payPrograms = payProgramService.getPayProgramList();
		assertEquals(payPrograms.size(), 1);
		for (PayProgram payProgram : payPrograms) {
			assertEquals(payProgram.getFeeProgram(), feeProgram);
		}
	}

	/**
	 * Gets the user pay inscription list by account id test.
	 *
	 * @return the user pay inscription list by account id test
	 * @throws UniqueException, UniqueListException the unique exception
	 */
	@Test
	public void getPayProgramListByAccountIdTest() throws UniqueException, UniqueListException {

		// Save
		List<Account> accounts = new ArrayList<Account>();
		List<Program> programs = new ArrayList<Program>();

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);

		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.up(program);

		Account account2 = new Account("user2", "2", "25555555C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account2);
		accounts.add(account2);

		Program program2 = new Program("Pepe2", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program2);
		programService.up(program2);

		programs.add(program2);
		account2.setPrograms(programs);
		accountService.update(account2, false, true);

		programs.add(program);
		account.setPrograms(programs);
		accountService.update(account, false, true);

		Program program3 = new Program("Pepe3", "Very interesting", Float.valueOf(1), 9, new ArrayList<Account>(), account,
				programService.findProgramTypeById(1), programService.findProgramThematicById(1), programService.findProgramCategoryById(1),
				programService.findProgramLanguageById(1), "", "", "", "", "");
		programService.save(program3);
		programService.up(program3);

		// Save
		Date date = DisplayDate.stringToMonthOfYear("2015-12");
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// Assert
		List<PayProgram> payPrograms = payProgramService.getPayProgramListByAccountId(account.getId());
		assertEquals(payPrograms.size(), 2);

		payPrograms = payProgramService.getPayProgramListByAccountId(account2.getId());
		assertEquals(payPrograms.size(), 2);
	}

	/**
	 * Gets the user pay inscription list test.
	 *
	 * @return the user pay inscription list test
	 * @throws UniqueException, UniqueListException the unique exception
	 */
	@Test
	public void getPayProgramListTest() throws UniqueException, UniqueListException {

		// Save
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Account account2 = new Account("user2", "2", "25555555C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account2);
		accounts.add(account2);

		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.up(program);

		Program program2 = new Program("Pepe2", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program2);
		programService.up(program2);

		// Save
		Date date = DisplayDate.stringToMonthOfYear("2015-12");
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// Assert
		List<PayProgram> payPrograms = payProgramService.getPayProgramList();
		assertEquals(payPrograms.size(), 2);
		for (PayProgram payProgram : payPrograms) {
			assertEquals(payProgram.getFeeProgram(), feeProgram);
		}
	}

	/**
	 * Pay user pay inscription test.
	 *
	 * @throws UniqueException, UniqueListException the unique exception
	 */
	@Test
	public void payPayProgramTest() throws UniqueException, UniqueListException {

		// Save
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Account account2 = new Account("user2", "2", "25555555C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account2);
		accounts.add(account2);

		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.up(program);

		Program program2 = new Program("Pepe2", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program2);
		programService.up(program2);

		// Save
		Date date = DisplayDate.stringToMonthOfYear("2015-12");
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		List<PayProgram> payPrograms = payProgramService.getPayProgramListByFeeProgramId(feeProgram.getId());
		payProgramService.pay(payPrograms.get(0));

		// Assert
		assertTrue(payPrograms.get(0).getState().equals(states.PAY));
	}

	/**
	 * Pay pay pal program test.
	 *
	 * @throws UniqueException, UniqueListException the unique exception
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	@Test
	public void payPayPalProgramTest() throws UniqueException, UniqueListException, ExistTransactionIdException {

		// Save
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Account account2 = new Account("user2", "2", "25555555C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account2);
		accounts.add(account2);

		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.up(program);

		Program program2 = new Program("Pepe2", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program2);
		programService.up(program2);

		// Save
		Date date = DisplayDate.stringToMonthOfYear("2015-12");
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		List<PayProgram> payPrograms = payProgramService.getPayProgramListByFeeProgramId(feeProgram.getId());

		payProgramService.payPayPal(payPrograms.get(0), "accountPayer", "idTxn", "idPayer", "emailPayer", "statusPay", "12:12:12 Jun 12, 2015");
		assertTrue(payPrograms.get(0).getState().equals(states.MANAGEMENT));

		payProgramService.payPayPal(payPrograms.get(0), "accountPayer", "idTxn", "idPayer", "emailPayer", "Completed", "12:12:12 Jun 12, 2015");
		assertTrue(payPrograms.get(0).getState().equals(states.PAY));
	}

	/**
	 * Exist transaction id test.
	 *
	 * @throws UniqueException, UniqueListException the unique exception
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	//	@Test(expected = ExistTransactionIdException.class)
	//	public void existTransactionIdTest() throws UniqueException, UniqueListException, ExistTransactionIdException {
	//
	//		// Save
	//		List<Account> accounts = new ArrayList<Account>();
	//		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
	//		accountService.save(account);
	//		accounts.add(account);
	//		Account account2 = new Account("user2", "25555555C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
	//		accountService.save(account2);
	//		accounts.add(account2);
	//
	//		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
	//				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
	//				"", "", "", "");
	//		programService.save(program);
	//		programService.up(program);
	//
	//		Program program2 = new Program("Pepe2", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
	//				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
	//				"", "", "", "");
	//		programService.save(program2);
	//		programService.up(program2);
	//
	//		// Save
	//		Date date = DisplayDate.stringToMonthOfYear("2015-12");
	//		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
	//		feeProgramService.save(feeProgram);
	//
	//		PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(), feeProgram.getId());
	//		payProgram.setIdTxn("a");
	//		payProgramService.update(payProgram);
	//		payProgramService.update(payProgram);
	//
	//		PayProgram payProgram2 = new PayProgram(program2, feeProgram, Double.valueOf(25));
	//		payProgram2.setIdTxn("a");
	//		payProgramService.update(payProgram2);
	//	}

	/**
	 * Gets the pay inscription list test.
	 *
	 * @return the pay inscription list test
	 * @throws UniqueException, UniqueListException the unique exception
	 */
	@Test
	public void getFeeProgramListTest() throws UniqueException, UniqueListException {

		// getFeeProgramList, no FeePrograms
		List<FeeProgram> feeProgramList = feeProgramService.getFeeProgramList();
		// Assert
		assertTrue(feeProgramList.isEmpty());

		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Account account2 = new Account("user2", "2", "25555555C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account2);
		accounts.add(account2);

		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.up(program);

		Program program2 = new Program("Pepe2", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program2);
		programService.up(program2);

		// Save
		Date date = DisplayDate.stringToMonthOfYear("2015-12");
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// getFeeProgramList
		feeProgramList = feeProgramService.getFeeProgramList();
		// Assert
		assertEquals(feeProgramList.size(), 1);
	}
}
