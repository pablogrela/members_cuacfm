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
package org.cuacfm.members.test.model.reportservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.model.report.Report;
import org.cuacfm.members.model.report.ReportDTO;
import org.cuacfm.members.model.reportservice.ReportService;
import org.cuacfm.members.model.util.FileUtils;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/** The Class ReportServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReportServiceTest extends WebSecurityConfigurationAware {

	@Value("${path}${pathTest}")
	private String pathTest;
	
	@Inject
	private ReportService reportService;

	@Inject
	private AccountService accountService;

	@Inject
	private ProgramService programService;

	private Account account;
	private Account account2;
	private Program program;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 * @throws UniqueListException the unique list exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		List<Account> accounts = new ArrayList<Account>();
		account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		account2 = new Account("user2", "2", "25555555C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account2);
		accounts.add(account2);
		program = new Program("Pepe", "Very interesting", Float.valueOf(1), 90, accounts, account, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program);
	}

	/**
	 * Save and find report test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void saveAndFindReportTest() throws UniqueException {

		// Save
		Report report = new Report(account, program, 1, 1, 1, true, true, "report 1", "report 1");
		List<byte[]> photosAux = null;
		reportService.save(report, photosAux);

		// Assert
		Report reportSearched = reportService.findById(report.getId());
		assertEquals(report, reportSearched);
	}

	/**
	 * Save report exception test.
	 *
	 * @throws UniqueException the unique exception
	 * @throws IOException
	 */
	@Test
	public void saveReportExceptionTest() throws UniqueException, IOException {
		// Save
		Report report = new Report(account, program, 1, 1, 1, true, true, "report 1", "report 1");
		List<byte[]> photosAux = null;
		reportService.save(report, photosAux);

		Report report2 = new Report(account, program, 1, 1, 1, true, true, "report 2", "report 2");
		List<byte[]> photosAux2 = new ArrayList<>();
		reportService.save(report2, photosAux2);

		Report report3 = new Report(account, program, 1, 1, 1, true, true, "report 3", "report 3");
		List<byte[]> photosAux3 = new ArrayList<>();
		photosAux3.add(new byte[2]);
		reportService.save(report3, photosAux3);

		File file = new File("validation.txt");
		DiskFileItem fileItem = new DiskFileItem("file", "text/plain", false, file.getName(), (int) file.length(), file.getParentFile());
		List<MultipartFile> photosAux4 = new ArrayList<>();
		fileItem.getOutputStream();
		photosAux4.add(new CommonsMultipartFile(fileItem));
		MultipartFile[] multipartFiles = null;
		reportService.save(report3, multipartFiles);
		
		multipartFiles = new MultipartFile[photosAux4.size()];
		photosAux4.toArray(multipartFiles);
		reportService.save(report3, multipartFiles);
		
		List<String> lines = Arrays.asList("The first line", "The second line");
		FileUtils.createFolderIfNoExist(pathTest);
		Path file2 = Paths.get(pathTest + "file.txt");
		Files.write(file2, lines, Charset.forName("UTF-8"));
		file2.toFile().length();
		
		fileItem = new DiskFileItem("file2", "text/plain", false, file2.toFile().getName(), (int) file2.toFile().length(), file2.toFile().getParentFile());	
		fileItem.getOutputStream();
		photosAux4.clear();
		photosAux4.add(new CommonsMultipartFile(fileItem));

		multipartFiles = new MultipartFile[photosAux4.size()];
		photosAux4.toArray(multipartFiles);
		reportService.save(report3, multipartFiles);
	}

	/**
	 * Update report test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void updateReportTest() throws UniqueException {
		// Save
		Report report = new Report(account, program, 1, 1, 1, true, true, "report 1", "report 1");
		List<byte[]> photosAux = null;
		reportService.save(report, photosAux);

		// Update
		report.setAccount(account);
		report.setProgram(program);
		report.setDirt(2);
		report.setConfiguration(2);
		report.setTidy(2);
		report.setAnswer("");
		report.setDescription("");
		report.setDateCreate(new Date());
		report.setDateRevision(new Date());
		report.setOpenDoor(false);
		report.setViewMembers(false);
		report.setLocation("");
		report.setFile("");
		Report reportUpdate = reportService.update(report);

		// Assert
		assertEquals(report, reportUpdate);
		assertEquals(report.getAccount(), reportUpdate.getAccount());
		assertEquals(report.getDirt(), reportUpdate.getDirt());
		assertEquals(report.getTidy(), reportUpdate.getTidy());
		assertEquals(report.getDescription(), reportUpdate.getDescription());
		assertEquals(report.getAnswer(), reportUpdate.getAnswer());
		assertEquals(report.getDateCreate(), reportUpdate.getDateCreate());
		assertEquals(report.getDateRevision(), reportUpdate.getDateRevision());
		assertEquals(report.isOpenDoor(), reportUpdate.isOpenDoor());
		assertEquals(report.isViewMembers(), reportUpdate.isViewMembers());
		report.toString();

		ReportDTO reportDTO = reportService.getReportDTO(reportUpdate);
		reportDTO.setAccount(accountService.getAccountDTO(account));
		reportDTO.setProgram(programService.getProgramDTO(program));
		reportDTO.setDirt(2);
		reportDTO.setConfiguration(2);
		reportDTO.setTidy(2);
		reportDTO.setAnswer("");
		reportDTO.setDescription("");
		reportDTO.setDateCreate(new Date());
		reportDTO.setDateRevision(new Date());
		reportDTO.setOpenDoor(false);
		reportDTO.setViewMembers(false);
		reportDTO.setLocation("");

		// Assert
		assertEquals(report.getId(), reportUpdate.getId());
		assertEquals(reportDTO.getDirt(), reportUpdate.getDirt());
		assertEquals(reportDTO.getTidy(), reportUpdate.getTidy());
		assertEquals(reportDTO.getDescription(), reportUpdate.getDescription());
		assertEquals(reportDTO.getAnswer(), reportUpdate.getAnswer());
		assertEquals(reportDTO.isOpenDoor(), reportUpdate.isOpenDoor());
		assertEquals(reportDTO.isViewMembers(), reportUpdate.isViewMembers());
	}

	/**
	 * Gets the report test.
	 *
	 * @return the report test
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void getReportTest() throws UniqueException {
		Report report = new Report(account, program, 1, 1, 1, true, true, "report 1", "report 1");
		List<byte[]> photosAux = null;
		reportService.save(report, photosAux);
		ReportDTO reportDTO = reportService.getReportDTO(report);

		Report reportAux1 = reportService.getReport(reportDTO, null);
		Report reportAux2 = reportService.getReport(reportDTO, account);

		assertEquals(reportAux1.getId(), reportAux2.getId());
	}

	/**
	 * Report states test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void reportStatesTest() throws UniqueException {
		Report report = new Report(account, program, 1, 1, 1, true, true, "report 1", "report 1");
		List<byte[]> photosAux = null;
		reportService.save(report, photosAux);

		reportService.up(report);
		assertTrue(report.isActive());

		reportService.down(report);
		assertFalse(report.isActive());

		reportService.answer(report, account, "", null);
		assertTrue(report.getAnswer().contains("user"));

		reportService.answer(report, account, "", true);
		assertTrue(report.getAnswer().contains("user"));
		assertTrue(report.isActive());

		reportService.answer(report, account, "", false);
		assertTrue(report.getAnswer().contains("user"));
		assertFalse(report.isActive());

		reportService.answer(report, account2, "", null);
		assertTrue(report.getAnswer().contains("user2"));
	}

	/**
	 * Delete report test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void deleteReportTest() throws UniqueException {
		// Save
		Report report = new Report(account, program, 1, 1, 1, true, true, "report 1", "report 1");
		List<byte[]> photosAux = null;
		reportService.save(report, photosAux);

		// Assert
		Report reportSearched = reportService.findById(report.getId());
		assertNotNull(reportSearched);

		// Delete
		reportService.delete(report);

		// Assert, no exist Report
		reportSearched = reportService.findById(report.getId());
		assertNull(reportSearched);
	}

	/**
	 * Delete null report test.
	 */
	@Test
	public void deleteNullReportTest() {
		// Delete
		reportService.delete(null);
	}

	/**
	 * Save and find account type test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void saveAndFindByNameReportTest() throws UniqueException {
		// Save
		Report report = new Report(account, program, 1, 1, 1, true, true, "report 1", "report 1");
		List<byte[]> photosAux = null;
		reportService.save(report, photosAux);

		// Assert
		Report reportSearched = reportService.findById(report.getId());
		assertEquals(report, reportSearched);
	}

	/**
	 * Gets the reports test.
	 *
	 * @return the reports test
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void getReportsTest() throws UniqueException {
		// Assert
		List<Report> reports = reportService.getReportList();
		assertEquals(reports.size(), 0);

		// Save
		Report report = new Report(account, program, 1, 1, 1, true, true, "report 1", "report 1");
		List<byte[]> photosAux = null;
		reportService.save(report, photosAux);
		Report report2 = new Report(account2, program, 1, 1, 1, true, true, "report 1", "report 1");
		List<byte[]> photosAux2 = null;
		reportService.save(report2, photosAux2);
		Report report3 = new Report(account, program, 5, 5, 5, true, true, "report 1", "");
		List<byte[]> photosAux3 = null;
		reportService.save(report3, photosAux3);

		// Assert
		reports = reportService.getReportList();
		assertEquals(reports.size(), 3);
		assertTrue(reports.contains(report));
		assertTrue(reports.contains(report2));
		assertTrue(reports.contains(report3));

		reports = reportService.getReportListActive();
		assertEquals(reports.size(), 2);
		assertTrue(reports.contains(report));
		assertTrue(reports.contains(report2));

		reports = reportService.getReportListClose();
		assertEquals(reports.size(), 1);
		assertTrue(reports.contains(report3));

		reports = reportService.getReportListByUser(account);
		assertEquals(reports.size(), 2);
		assertTrue(reports.contains(report));
		assertTrue(reports.contains(report3));

		reports = reportService.getReportListActiveByUser(account);
		assertEquals(reports.size(), 1);
		assertTrue(reports.contains(report));

		List<ReportDTO> reportsDTO = reportService.getReportsDTO(reportService.getReportList());
		ReportDTO reportDTO = reportService.getReportDTO(report);
		assertEquals(reportDTO.getId(), reportsDTO.get(0).getId());
		ReportDTO report2DTO = reportService.getReportDTO(null);
		assertNull(report2DTO);
	}
}
