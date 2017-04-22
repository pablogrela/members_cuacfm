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
package org.cuacfm.members.test.model.programservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.ExistPaymentsException;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProgramServiceTest extends WebSecurityConfigurationAware {

	/** The account service. */
	@Inject
	private AccountService accountService;

	/** The program service. */
	@Inject
	private ProgramService programService;

	/** The fee program service. */
	@Inject
	private FeeProgramService feeProgramService;

	/** The pay program service. */
	@Inject
	private PayProgramService payProgramService;

	/**
	 * Save and find by Program test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void saveProgramTest() throws UniqueException, UniqueListException {
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);

		// Save
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);

		// findById
		Program programSearch;

		programSearch = programService.findById(program.getId());
		assertEquals(program, programSearch);
	}

	/**
	 * Save and find by Program test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void saveAndFindUsersProgramTest() throws UniqueException, UniqueListException {
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Account account2 = new Account("user2", "2", "25555555C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo",
				roles.ROLE_USER);
		accountService.save(account2);
		accounts.add(account2);

		// Save
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 90, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");

		programService.save(program);

		// findById
		Program programSearch;

		programSearch = programService.findById(program.getId());
		assertEquals(program, programSearch);
		assertTrue(programSearch.getAccounts().contains(account));
		assertTrue(programSearch.getAccounts().contains(account2));

		//Assert by Name
		programSearch = programService.findByName(program.getName());
		assertEquals(program, programSearch);
	}

	/**
	 * Save program unique exception test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test(expected = UniqueException.class)
	public void saveProgramUniqueExceptionTest() throws UniqueException, UniqueListException {

		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);

		// Save
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.save(program);
	}

	/**
	 * Update program test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void updateProgramTest() throws UniqueException, UniqueListException {
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);

		// Save
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.update(program);

		program.setName("Pepes");
		program.setActive(true);
		program.setDescription("description");
		program.setDuration(2);
		program.setPeriodicity(Float.valueOf(2));
		program.setAccounts(accounts);
		programService.update(program);

		// findById
		Program programSearch = programService.findById(program.getId());

		// Assert
		assertEquals(program, programSearch);
		assertEquals(program.getAccounts(), programSearch.getAccounts());
		assertEquals(program.getName(), programSearch.getName());
		assertEquals(program.getDescription(), programSearch.getDescription());
		assertEquals(program.getDuration(), programSearch.getDuration());
		assertEquals(program.getPeriodicity(), programSearch.getPeriodicity());

		Program program2 = new Program("program 22", "Very interesting", Float.valueOf(1), 9, accounts, account,
				programService.findProgramTypeById(1), programService.findProgramThematicById(1), programService.findProgramCategoryById(1),
				programService.findProgramLanguageById(1), "", "", "", "", "");
		programService.update(program2);
	}

	/**
	 * Update program unique exception test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test(expected = UniqueException.class)
	public void UpdateProgramUniqueExceptionTest() throws UniqueException, UniqueListException {

		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);

		// Save
		Program program = new Program("Program", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		Program program2 = new Program("Program2", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program2);

		// Update
		Program program3 = new Program("Program2", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.update(program3);
	}

	/**
	 * Up and down program test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void upAndDownProgramTest() throws UniqueException, UniqueListException {

		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);

		// Save
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);

		// Up and assert
		programService.up(program);
		assertTrue(program.isActive());

		// Down and assert
		programService.down(program);
		assertFalse(program.isActive());
	}

	/**
	 * Delete program test.
	 *
	 * @throws UniqueException the unique exception
	 * @throws ExistPaymentsException the exist payments exception
	 */
	@Test
	public void deleteProgramTest() throws UniqueException, UniqueListException, ExistPaymentsException {

		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);

		// Save
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);

		// Delete and assert
		programService.delete(program);
		assertEquals(programService.findById(program.getId()), null);

		// Delete null
		programService.delete(new Program());
	}

	/**
	 * Delete program payments exception test.
	 *
	 * @throws UniqueException the unique exception
	 * @throws ExistPaymentsException the exist payments exception
	 */
	@Test(expected = ExistPaymentsException.class)
	public void deleteProgramPaymentsExceptionTest() throws UniqueException, UniqueListException, ExistPaymentsException {

		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);

		// Save
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.up(program);

		Date date = DateUtils.format("2015-12", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// Exception
		programService.delete(program);
	}

	/**
	 * < Pay program test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void payProgramTest() throws UniqueException, UniqueListException {

		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);

		Date date = DateUtils.format("2015-12", DateUtils.FORMAT_MONTH_YEAR);

		// Save
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.up(program);

		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// PayProgram
		PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(), feeProgram.getId());

		// Assert
		assertFalse(payProgram.getState().equals(states.PAY));

		payProgramService.pay(payProgram);

		// Assert
		assertTrue(payProgram.getState().equals(states.PAY));
	}

	/**
	 * Pay pal program test.
	 *
	 * @throws UniqueException the unique exception
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	@Test
	public void payPalProgramTest() throws UniqueException, UniqueListException, ExistTransactionIdException {

		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);

		Date date = DateUtils.format("2015-12", DateUtils.FORMAT_MONTH_YEAR);

		// Save
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.up(program);

		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// PayProgram
		PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(), feeProgram.getId());

		// Assert
		assertFalse(payProgram.getState().equals(states.PAY));
		payProgramService.payPayPal(payProgram, "accountPayer", "idTxn", "idPayer", "emailPayer", "statusPay", "12:12:12 Jun 12, 2015");
		assertFalse(payProgram.getState().equals(states.PAY));

		// Assert
		payProgramService.payPayPal(payProgram, "accountPayer", "idTxn", "idPayer", "emailPayer", "Completed", "12:12:12 Jun 12, 2015");
		assertTrue(payProgram.getState().equals(states.PAY));
		assertTrue(payProgram.getMethod().equals(methods.PAYPAL));
	}

	/**
	 * Exist transaction id exception test.
	 *
	 * @throws UniqueException the unique exception
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	@Test(expected = ExistTransactionIdException.class)
	public void existTransactionIdExceptionTest() throws UniqueException, UniqueListException, ExistTransactionIdException {

		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);

		Date date = DateUtils.format("2015-12", DateUtils.FORMAT_MONTH_YEAR);

		// Save
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

		assertEquals(programService.getProgramList().size(), 2);

		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// PayProgram
		PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(), feeProgram.getId());
		PayProgram payProgram2 = payProgramService.findByPayProgramIds(program2.getId(), feeProgram.getId());

		// Assert
		assertFalse(payProgram.getState().equals(states.PAY));

		payProgramService.payPayPal(payProgram, "accountPayer", "idTxn", "idPayer", "emailPayer", "statusPay", "12:12:12 Jun 12, 2015");
		payProgramService.payPayPal(payProgram2, "accountPayer", "idTxn", "idPayer", "emailPayer", "statusPay", "12:12:12 Jun 12, 2015");
	}
}
