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
package org.cuacfm.members.test.model.feeprogram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class FeeProgramServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FeeProgramServiceTest extends WebSecurityConfigurationAware {

	/** The program service. */
	@Inject
	private ProgramService programService;

	/** The fee program service. */
	@Inject
	private FeeProgramService feeProgramService;

	/** The pay program service. */
	@Inject
	private PayProgramService payProgramService;

	/** The account service. */
	@Inject
	private AccountService accountService;

	/**
	 * Save and find by FeeProgram test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void saveFeeProgramTest() throws UniqueException {

		// Save
		Date date = DateUtils.format("2015-12", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// findById
		FeeProgram feeProgramSearch = feeProgramService.findById(feeProgram.getId());
		assertEquals(feeProgram, feeProgramSearch);
	}

	/**
	 * Save and find by FeeProgram test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void saveFeeProgramWithUsersTest() throws UniqueException, UniqueListException {
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);

		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.up(program);

		// Save
		Date date = DateUtils.format("2015-12", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// findById
		FeeProgram feeProgramSearch = feeProgramService.findById(feeProgram.getId());
		assertEquals(feeProgram, feeProgramSearch);

		assertEquals(payProgramService.getPayProgramListByFeeProgramId(feeProgram.getId()).size(), 1);
	}

	/**
	 * Save and find by FeeProgram test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test(expected = UniqueException.class)
	public void saveFeeProgramExceptionTest() throws UniqueException, UniqueListException {

		// Save
		Date date = DateUtils.format("2015-12", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);
		feeProgramService.save(feeProgram);
	}

	/**
	 * Save and find by FeeProgram test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void saveUserFeeProgramTest() throws UniqueException, UniqueListException {

		// Save
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);

		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.update(program);

		Date date = DateUtils.format("2015-12", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// findById

		FeeProgram feeProgramSearch = feeProgramService.findById(feeProgram.getId());
		assertEquals(feeProgram, feeProgramSearch);
	}

	/**
	 * Update.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void UpdateFeeProgramTest() throws UniqueException {

		// Save
		Date date = DateUtils.format("2015-12", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// Update
		feeProgram.setName("pay of 1016");
		feeProgram.setDate(date);
		feeProgram.setDateLimit(date);
		feeProgram.setPrice(Double.valueOf(25));
		feeProgram.setDescription("pay of 2016, tax for members");
		feeProgramService.update(feeProgram);

		// Assert
		FeeProgram feeProgramSearch = feeProgramService.findById(feeProgram.getId());
		assertEquals(feeProgram, feeProgramSearch);
		assertEquals(feeProgram.getName(), feeProgramSearch.getName());
		assertEquals(feeProgram.getDate(), feeProgramSearch.getDate());
		assertEquals(feeProgram.getDateLimit(), feeProgramSearch.getDateLimit());
		assertEquals(feeProgram.getPrice(), feeProgramSearch.getPrice());
		assertEquals(feeProgram.getDescription(), feeProgramSearch.getDescription());

		date = DateUtils.format("2016-12", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram2 = new FeeProgram("new name", Double.valueOf(25), date, date, "description");
		feeProgramService.update(feeProgram2);
	}

	/**
	 * Update Inscription Exception.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test(expected = UniqueException.class)
	public void UpdateFeeProgramExceptionTest() throws UniqueException {

		// Save
		Date date = DateUtils.format("2015-12", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);
		Date date2 = DateUtils.format("2016-12", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram2 = new FeeProgram("name2", Double.valueOf(25), date2, date2, "description");
		feeProgramService.save(feeProgram2);

		// Update
		FeeProgram feeProgram3 = new FeeProgram("name3", Double.valueOf(25), date, date, "description");
		feeProgramService.update(feeProgram3);
	}

	/**
	 * findByName test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void findByNameTest() throws UniqueException {

		// Save
		Date date = DateUtils.format("2015-12", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// findByName
		FeeProgram feeProgramSearch = feeProgramService.findByName(feeProgram.getName());
		assertEquals(feeProgram, feeProgramSearch);
		assertNull(feeProgramService.findByName("Do not exist"));
	}

	/**
	 * findByYear test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void findByDateTest() throws UniqueException {

		// Save
		Date date = DateUtils.format("2015-12", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// findByName
		FeeProgram feeProgramSearch = feeProgramService.findByDate(feeProgram.getDate());
		assertEquals(feeProgram, feeProgramSearch);
	}

	/**
	 * getFeeProgramList test.
	 *
	 * @return the fee program list test
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void getFeeProgramListTest() throws UniqueException {

		// getFeeProgramList, no FeePrograms
		List<FeeProgram> feeProgramList = feeProgramService.getFeeProgramList();
		// Assert
		assertTrue(feeProgramList.isEmpty());

		// Save
		Date date = DateUtils.format("2015-12", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);
		Date date2 = DateUtils.format("2016-12", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram2 = new FeeProgram("name", Double.valueOf(25), date2, date2, "description");
		feeProgramService.save(feeProgram2);

		// getFeeProgramList
		feeProgramList = feeProgramService.getFeeProgramList();
		// Assert
		assertEquals(feeProgramList.size(), 2);
	}
}
