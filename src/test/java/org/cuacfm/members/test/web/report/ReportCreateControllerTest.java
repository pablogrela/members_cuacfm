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
package org.cuacfm.members.test.web.report;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.permissions;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class ReportCreateControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReportCreateControllerTest extends WebSecurityConfigurationAware {

	private MockHttpSession defaultSession;

	@Inject
	private AccountService accountService;

	@Inject
	private ProgramService programService;

	private Program program;
	private Account admin;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 * @throws UniqueListException the unique list exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		Account user = new Account("user", "", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "user", roles.ROLE_USER);
		accountService.save(user);
		defaultSession = getDefaultSession(user.getEmail());

		admin = new Account("admin", "", "55555555A", "London", "admin", "admin@test.es", "666666666", "666666666", "admin", roles.ROLE_ADMIN);
		admin.addPermissions(permissions.ROLE_REPORT);
		accountService.save(admin);

		List<Account> accounts = new ArrayList<Account>();
		accounts.add(user);
		program = new Program("Pepe", "Very interesting", Float.valueOf(1), 90, accounts, user, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		program.setActive(true);
		programService.save(program);
	}

	/**
	 * Display ReportList page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayReportCreatePageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/reportList/reportCreate")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Gets the report create test.
	 *
	 * @return the report create test
	 * @throws Exception the exception
	 */
	@Test
	public void getReportCreateTest() throws Exception {
		mockMvc.perform(get("/reportList/reportCreate").locale(Locale.ENGLISH).session(getDefaultSession(admin.getEmail())))
				.andExpect(view().name("report/reportcreate"));
	}

	/**
	 * Gets the user report create test.
	 *
	 * @return the user report create test
	 * @throws Exception the exception
	 */
	@Test
	public void getUserReportCreateTest() throws Exception {
		mockMvc.perform(get("/reportUserList/reportUserCreate").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("report/reportcreate"));
	}

	/**
	 * Post report create test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postReportCreateTest() throws Exception {
		mockMvc.perform(get("/reportList/reportCreate").locale(Locale.ENGLISH).session(getDefaultSession(admin.getEmail())))
				.andExpect(view().name("report/reportcreate"));

		mockMvc.perform(post("/reportList/reportCreate").locale(Locale.ENGLISH).session(getDefaultSession(admin.getEmail()))
				.param("programId", program.getId().toString()).param("tidy", "1").param("dirt", "1").param("configuration", "1")
				.param("openDoor", "true").param("viewMembers", "true").param("description", "Report"))
				.andExpect(view().name("redirect:/reportList"));
	}

	/**
	 * Post report user create test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postReportUserCreateTest() throws Exception {
		mockMvc.perform(get("/reportUserList/reportUserCreate").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("report/reportcreate"));

		mockMvc.perform(post("/reportUserList/reportUserCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("programId", program.getId().toString()).param("tidy", "1").param("dirt", "1").param("configuration", "1")
				.param("openDoor", "true").param("viewMembers", "true").param("description", "Report"))
				.andExpect(view().name("redirect:/reportUserList"));
	}

	/**
	 * Post report create fail test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Ignore
	// Fails to send errors by multipart
	public void postReportCreateFailTest() throws Exception {
		mockMvc.perform(get("/reportList/reportCreate").locale(Locale.ENGLISH).session(getDefaultSession(admin.getEmail())))
				.andExpect(view().name("report/reportcreate"));

		mockMvc.perform(post("/reportList/reportCreate").locale(Locale.ENGLISH).session(getDefaultSession(admin.getEmail()))
				.param("programId", program.getId().toString()).param("tidy", "1").param("dirt", "1").param("configuration", "0")
				.param("openDoor", "true").param("viewMembers", "true").param("description", "Report"))
				.andExpect(view().name("redirect:/reportList"));
	}

	/**
	 * Post report user create no time test.
	 *
	 * @throws Exception the exception
	 */
	@Ignore
	// Fails to send errors by multipart
	@Test
	public void postReportUserCreateNoTimeTest() throws Exception {
		mockMvc.perform(get("/reportUserList/reportUserCreate").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("report/reportcreate"));

		mockMvc.perform(post("/reportUserList/reportUserCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("programId", program.getId().toString()).param("tidy", "1").param("dirt", "1").param("configuration", "1")
				.param("openDoor", "true").param("viewMembers", "true").param("description",
						"ReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReport"
								+ "ReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReport"
								+ "ReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReportReport"
								+ "ReportReportReportReportReportReportReportReportReportReportReportReport"))
				.andExpect(view().name("report/reportcreate"));
	}

}