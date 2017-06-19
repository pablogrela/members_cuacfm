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
package org.cuacfm.members.test.web.program;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class ProgramListControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProgramListControllerTest extends WebSecurityConfigurationAware {

	private MockHttpSession defaultSession;
	private Account user;

	@Inject
	private AccountService accountService;

	@Inject
	private ProgramService programService;

	@Inject
	private FeeProgramService feeProgramService;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 * @throws UniqueListException the unique list exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		Account admin = new Account("admin", "", "55555555A", "London", "admin", "admin@test.es", "666666666", "666666666", "admin",
				roles.ROLE_ADMIN);
		accountService.save(admin);
		defaultSession = getDefaultSession("admin@test.es");

		user = new Account("test", "2", "11111111H", "London", "test", "test@test.es", "666666666", "666666666", "test", roles.ROLE_USER);
		accountService.save(user);
	}

	/**
	 * Display ProgramList page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayProgramListPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/programList")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Send displaysProgramList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysProgramList() throws Exception {
		mockMvc.perform(get("/programList").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("program/programlist"))
				.andExpect(content().string(containsString("<title>Programs</title>")));
	}

	/**
	 * Displays program list close.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displaysProgramListClose() throws Exception {
		mockMvc.perform(get("/programList/close").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("program/programlistclose"));
	}

	/**
	 * Send displaysProgramList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysUserProgramList() throws Exception {

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		defaultSession = getDefaultSession("user@test.es");

		mockMvc.perform(get("/programList").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("program/programlist"))
				.andExpect(content().string(containsString("<title>Programs</title>")));
	}

	/**
	 * Send displaysProgramList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysProgramListWithDatabase() throws Exception {
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);

		mockMvc.perform(get("/programList").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("program/programlist"))
				.andExpect(content().string(containsString("<title>Programs</title>")));
	}

	/**
	 * Send displaysProgramList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void deleteProgramList() throws Exception {
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);

		mockMvc.perform(post("/programList/programDelete/" + program.getId()).locale(Locale.ENGLISH).session(defaultSession));
		//				.andExpect(view().name("redirect:/programList"));

		// Assert, it remove program
		assertEquals(programService.findById(program.getId()), null);
	}

	/**
	 * Delete program by user list.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteProgramByUserList() throws Exception {
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);

		defaultSession = getDefaultSession("user@test.es");
		mockMvc.perform(post("/programList/programDelete/" + program.getId()).locale(Locale.ENGLISH).session(defaultSession));
		//				.andExpect(view().name("redirect:/programList"));

		// Assert, it remove program
		assertEquals(programService.findById(program.getId()), null);
	}

	/**
	 * Delete program by diferent user list.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteProgramByDiferentUserList() throws Exception {
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);

		Account account2 = new Account("user2", "1", "555555552C", "London", "user2", "user2@test.es", "666666666", "666666666", "demo",
				roles.ROLE_USER);
		accountService.save(account2);
		defaultSession = getDefaultSession("user2@test.es");
		mockMvc.perform(post("/programList/programDelete/" + program.getId()).locale(Locale.ENGLISH).session(defaultSession));
		//				.andExpect(view().name("redirect:/programList"));

		// Assert, it remove program
		assertEquals(programService.findById(program.getId()), program);
	}

	/**
	 * Send displaysProgramList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void deleteExistPaymentsInProgramList() throws Exception {
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		programService.up(program);
		Date date = DateUtils.format("2015-12", DateUtils.FORMAT_MONTH_YEAR);
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		mockMvc.perform(post("/programList/programDelete/" + program.getId()).locale(Locale.ENGLISH).session(defaultSession));
		// .andExpect(view().name("redirect:/programList"));

		// Assert, it don´t remove program
		assertEquals(programService.findById(program.getId()), program);
	}

	/**
	 * Send displaysProgramList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void programEdit() throws Exception {
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);

		mockMvc.perform(post("/programList/programEdit/" + program.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/programList/programEdit"));
	}

	/**
	 * Send displaysProgramList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void programCreate() throws Exception {
		mockMvc.perform(post("/programList/programCreate").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("program/programcreate"));
	}

	/**
	 * Send displaysProgramList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void programDown() throws Exception {
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);

		mockMvc.perform(post("/programList/programDown/" + program.getId()).locale(Locale.ENGLISH).session(defaultSession));
		//				.andExpect(view().name("redirect:/programList"));
		assertFalse(program.isActive());
	}

	/**
	 * Send displaysProgramList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void programUp() throws Exception {
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);

		mockMvc.perform(post("/programList/programUp/" + program.getId()).locale(Locale.ENGLISH).session(defaultSession));
		//				.andExpect(view().name("redirect:/programList"));
		assertTrue(program.isActive());

	}

	/**
	 * Display program list.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayProgramList() throws Exception {
		mockMvc.perform(get("/programList/").locale(Locale.ENGLISH).session(defaultSession));
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
		mockMvc.perform(get("/programList/").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(get("/programList/").locale(Locale.ENGLISH).session(getDefaultSession("test@test.es")));
	}

	/**
	 * Display program list close.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayProgramListClose() throws Exception {
		mockMvc.perform(get("/programList/close/").locale(Locale.ENGLISH).session(defaultSession));
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		program.setActive(false);
		program.setDateDown(new Date());
		programService.save(program);
		mockMvc.perform(get("/programList/close/").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(get("/programList/close/").locale(Locale.ENGLISH).session(getDefaultSession("test@test.es")));
	}

	/**
	 * Notification.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void notification() throws Exception {
		List<Account> accounts = new ArrayList<Account>();
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		Program program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		mockMvc.perform(post("/accountList/notification/" + program.getId()).locale(Locale.ENGLISH).session(defaultSession).param("title", "title")
				.param("body", "body"));
	}
}