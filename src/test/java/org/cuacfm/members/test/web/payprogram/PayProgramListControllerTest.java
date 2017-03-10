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
package org.cuacfm.members.test.web.payprogram;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class PayProgramListControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PayProgramListControllerTest extends WebSecurityConfigurationAware {

	/** The default session. */
	private MockHttpSession defaultSession;

	/** The account service. */
	@Inject
	private AccountService accountService;

	/** The program service. */
	@Inject
	private ProgramService programService;

	/** The fee program service. */
	@Inject
	private FeeProgramService feeProgramService;

	/** The training service. */
	@Inject
	private PayProgramService payProgramService;

	/** The fee program. */
	private FeeProgram feeProgram;

	/** The pay program. */
	private PayProgram payProgram;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		Account admin = new Account("admin", "", "11111111D", "London", "admin", "admin@udc.es", "666666666", "666666666", "demo", roles.ROLE_ADMIN);
		accountService.save(admin);
		defaultSession = getDefaultSession("admin@udc.es");

		// Create User
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1","22222222C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Account account2 = new Account("user2", "2", "33333333C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account2);
		accounts.add(account2);

		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "", "", "", "", "");
		programService.save(program);
		programService.up(program);

		Program program2 = new Program("Pepe2", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "", "", "", "", "");
		programService.save(program2);
		programService.up(program2);

		// Save
		Date date = DisplayDate.stringToMonthOfYear("2015-12");
		feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);
		PayProgram payProgram2 = payProgramService.findByPayProgramIds(program2.getId(), feeProgram.getId());
		payProgramService.pay(payProgram2);

		payProgram = payProgramService.findByPayProgramIds(program.getId(), feeProgram.getId());
	}

	/**
	 * Display PayProgramList page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayInscriptionsViewPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/feeProgramList/payProgramList")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Send PayProgramList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysInscriptionsTest() throws Exception {

		mockMvc.perform(post("/feeProgramList/payProgramList/" + feeProgram.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/feeProgramList/payProgramList"));

		mockMvc.perform(get("/feeProgramList/payProgramList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("payprogram/payprogramlist"))
				.andExpect(content().string(containsString("<title>Payments pay program</title>")));
	}

	/**
	 * Send redirect FeeProgramList Because FeeProgram Is Null.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void redirectTrainingListBecauseTrainingIsNullTest() throws Exception {

		mockMvc.perform(post("/feeProgramList/payProgramList/" + Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/feeProgramList/payProgramList"));

		mockMvc.perform(get("/feeProgramList/payProgramList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/feeProgramList"));
	}

	/**
	 * Pay user pay inscription test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payPayProgramTest() throws Exception {

		// Assert no pay
		assertTrue(payProgram.getState().equals(states.NO_PAY));

		mockMvc.perform(post("/feeProgramList/payProgramList/" + feeProgram.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/feeProgramList/payProgramList"));

		mockMvc.perform(post("/feeProgramList/payProgramList/pay/" + payProgram.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/feeProgramList/payProgramList"));

		// Assert Pay
		assertTrue(payProgram.getState().equals(states.PAY));
	}

	/**
	 * Creates the pdf all.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createPdfALL() throws Exception {
		mockMvc.perform(post("/feeProgramList/payProgramList/createPdf/" + feeProgram.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.param("createPdf", "ALL"));
	}

	/**
	 * Creates the pdf pay.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createPdfPAy() throws Exception {
		mockMvc.perform(post("/feeProgramList/payProgramList/createPdf/" + feeProgram.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.param("createPdf", "PAY"));
	}

	/**
	 * Creates the pdf no pay.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createPdfNoPAy() throws Exception {
		mockMvc.perform(post("/feeProgramList/payProgramList/createPdf/" + feeProgram.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.param("createPdf", "NOPAY"));
	}
}