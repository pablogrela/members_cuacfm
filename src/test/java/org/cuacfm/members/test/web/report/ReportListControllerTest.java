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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import org.cuacfm.members.model.report.Report;
import org.cuacfm.members.model.reportservice.ReportService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class ReportListControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReportListControllerTest extends WebSecurityConfigurationAware {

	private MockHttpSession defaultSession;
	private Account user;
	private List<Account> accounts;
	private Program program;

	@Inject
	private AccountService accountService;

	@Inject
	private ProgramService programService;

	@Inject
	private ReportService reportService;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 * @throws UniqueListException the unique list exception
	 */
	@Before
	public void initializeDefaultSessionTest() throws UniqueException, UniqueListException {
		Account admin = new Account("admin", "", "55555555A", "London", "admin", "admin@test.es", "666666666", "666666666", "admin",
				roles.ROLE_ADMIN);
		admin.addPermissions(permissions.ROLE_REPORT);
		accountService.save(admin);
		defaultSession = getDefaultSession(admin.getEmail());

		user = new Account("test", "2", "11111111H", "London", "test", "user@test.es", "666666666", "666666666", "test", roles.ROLE_USER);
		accountService.save(user);
		accounts = new ArrayList<Account>();
		accounts.add(user);
		program = new Program("Pepe", "Very interesting", Float.valueOf(1), 9, accounts, user, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
	}

	/**
	 * Display ReportList page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayReportListPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/reportList")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Send displaysReportList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysReportListTest() throws Exception {
		mockMvc.perform(get("/reportList").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("report/reportlist"));
		mockMvc.perform(get("/reportUserList").locale(Locale.ENGLISH).session(getDefaultSession(user.getEmail())))
				.andExpect(view().name("report/reportuserlist"));
	}

	/**
	 * Displays program list close.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displaysReportListCloseTest() throws Exception {
		mockMvc.perform(get("/reportList/close").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("report/reportlistclose"));
	}

	/**
	 * Send displaysReportList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysUserReportListTest() throws Exception {
		mockMvc.perform(get("/reportList").locale(Locale.ENGLISH).session(getDefaultSession(user.getEmail())))
				.andExpect(view().name("report/reportlist"));
	}

	/**
	 * Send displaysReportList.
	 * 
	 * @throws Exception the exception
	 */
		@Test
		public void reportCreateTest() throws Exception {
			CsrfToken token = new DefaultCsrfToken("headerName", "parameterName", "token");
			mockMvc.perform(get("/reportList/reportCreate").locale(Locale.ENGLISH)
					.session(defaultSession)
					.sessionAttr("parameterName", token)
					.sessionAttr("_csrf", token)
					.requestAttr(CsrfToken.class.getName(), token)
					.param("parameterName", "title")).andExpect(view().name("report/reportcreate"));
		}

	/**
	 * Report down.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void reportDownTest() throws Exception {
		Report report = new Report(user, program, 5, 5, 5, true, true, "there", "");
		List<byte[]> photosAux = null;
		reportService.save(report, photosAux);

		mockMvc.perform(post("/reportList/reportDown/" + report.getId()).locale(Locale.ENGLISH).session(defaultSession));
		assertFalse(report.isActive());
	}

	/**
	 * Report up.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void reportUpTest() throws Exception {
		Report report = new Report(user, program, 5, 5, 5, true, true, "there", "");
		List<byte[]> photosAux = null;
		reportService.save(report, photosAux);

		mockMvc.perform(post("/reportList/reportUp/" + report.getId()).locale(Locale.ENGLISH).session(defaultSession));
		assertTrue(report.isActive());
	}

	/**
	 * Display program list.
	 *
	 * @return the report list
	 * @throws Exception the exception
	 */
	@Test
	public void getReportListTest() throws Exception {
		mockMvc.perform(get("/reportList/").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(get("/reportUserList/").locale(Locale.ENGLISH).session(defaultSession));

		Report report = new Report(user, program, 1, 1, 1, true, true, "report 1", "report 1");
		List<byte[]> photosAux = null;
		reportService.save(report, photosAux);

		mockMvc.perform(get("/reportList/").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(get("/reportList/").locale(Locale.ENGLISH).session(getDefaultSession(user.getEmail())));
		mockMvc.perform(get("/reportUserList/").locale(Locale.ENGLISH).session(getDefaultSession(user.getEmail())));
	}

	/**
	 * Display program list close.
	 *
	 * @return the report list close
	 * @throws Exception the exception
	 */
	@Test
	public void getReportListCloseTest() throws Exception {
		mockMvc.perform(get("/reportList/close/").locale(Locale.ENGLISH).session(defaultSession));

		Report report = new Report(user, program, 1, 1, 1, true, true, "report 1", "report 1");
		List<byte[]> photosAux = null;
		report.setActive(false);
		reportService.save(report, photosAux);

		mockMvc.perform(get("/reportList/close/").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(get("/reportList/close/").locale(Locale.ENGLISH).session(getDefaultSession(user.getEmail())));
	}

	/**
	 * Notification.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void reportAnswerTest() throws Exception {

		Report report = new Report(user, program, 1, 1, 1, true, true, "report 1", "report 1");
		List<byte[]> photosAux = null;
		report.setActive(false);
		reportService.save(report, photosAux);

		mockMvc.perform(post("/reportList/reportAnswer/" + report.getId()).locale(Locale.ENGLISH).session(defaultSession).param("answer", "answer"));
		mockMvc.perform(post("/reportUserList/reportAnswer/" + report.getId()).locale(Locale.ENGLISH).session(defaultSession).param("answer", "answer"));
	}
	
	@Test
	public void reportImageTest() throws Exception {

		Report report = new Report(user, program, 1, 1, 1, true, true, "report 1", "report 1");
		List<byte[]> photosAux = null;
		report.setActive(false);
		reportService.save(report, photosAux);

		mockMvc.perform(post("/reportList/image/" + report.getId()).locale(Locale.ENGLISH).session(defaultSession).param("imageName", "imageName"));
		mockMvc.perform(post("/reportUserList/image/" + report.getId()).locale(Locale.ENGLISH).session(defaultSession).param("imageName", "imageName"));
	}
}