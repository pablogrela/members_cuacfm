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
package org.cuacfm.members.web.report;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.report.Report;
import org.cuacfm.members.model.report.ReportDTO;
import org.cuacfm.members.model.reportservice.ReportService;
import org.cuacfm.members.web.support.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class ReportListController. */
@Controller
public class ReportListController {

	private static final Logger logger = LoggerFactory.getLogger(ReportListController.class);
	private static final String REPORT_VIEW_NAME = "report/reportlist";
	private static final String REPORT_CLOSE_VIEW_NAME = "report/reportlistclose";
	private static final String REPORT_USER_VIEW_NAME = "report/reportuserlist";

	@Autowired
	private ReportService reportService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Instantiates a new training Controller.
	 */
	public ReportListController() {
		super();
	}

	/**
	 * Show Report List.
	 *
	 * @return the string the view
	 * @throws UniqueException the unique exception
	 */

	@RequestMapping(value = "reportList")
	public String getReportListView() {
		return REPORT_VIEW_NAME;
	}

	/**
	 * Gets the report list close view.
	 *
	 * @return the report list close view
	 */
	@RequestMapping(value = "reportList/close")
	public String getReportListCloseView() {
		return REPORT_CLOSE_VIEW_NAME;
	}

	/**
	 * Gets the report user list view.
	 *
	 * @return the report user list view
	 */
	@RequestMapping(value = "reportUserList")
	public String getReportUserListView() {
		return REPORT_USER_VIEW_NAME;
	}

	/**
	 * Gets the reports.
	 *
	 * @param principal the principal
	 * @return the reports
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "reportList/")
	public ResponseEntity<List<ReportDTO>> getReports(Principal principal) {

		// List of reports
		List<ReportDTO> reportsDTO = reportService.getReportsDTO(reportService.getReportListActive());
		if (reportsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
	}

	/**
	 * Gets the reports user.
	 *
	 * @param principal the principal
	 * @return the reports user
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "reportUserList/")
	public ResponseEntity<List<ReportDTO>> getReportsUser(Principal principal) {

		Account account = accountService.findByLogin(principal.getName());

		// List of reports
		List<ReportDTO> reportsDTO = reportService.getReportsDTO(reportService.getReportListByUser(account));
		if (reportsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
	}

	/**
	 * Gets the reports close.
	 *
	 * @param principal the principal
	 * @return the reports close
	 */
	@RequestMapping(value = "reportList/close/")
	public ResponseEntity<List<ReportDTO>> getReportsClose(Principal principal) {

		// List of reports
		List<ReportDTO> reportsDTO = reportService.getReportsDTO(reportService.getReportListClose());
		if (reportsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
	}

	/**
	 * Report down.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "reportList/reportDown/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> reportDown(@PathVariable("id") Long id, RedirectAttributes ra) {

		Report report = reportService.findById(id);

		reportService.down(report);
		Object[] arguments = { report.getProgram().getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("report.down.success", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Report answer.
	 *
	 * @param principal the principal
	 * @param reportId the report id
	 * @param answer the answer
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = { "reportList/reportAnswer/{reportId}", "reportUserList/reportAnswer/{reportId}" }, method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> reportAnswer(Principal principal, @PathVariable("reportId") Long reportId,
			@RequestParam(value = "answer") String answer, RedirectAttributes ra) {

		Report report = reportService.findById(reportId);
		reportService.answer(report, accountService.findByLogin(principal.getName()), answer, null);

		Object[] arguments = { report.getProgram().getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("report.answer.success", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Report up.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "reportList/reportUp/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> reportUp(@PathVariable("id") Long id, RedirectAttributes ra) {

		Report report = reportService.findById(id);

		reportService.up(report);
		Object[] arguments = { report.getProgram().getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("report.up.sucess", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Gets the image report.
	 *
	 * @param reportId the report id
	 * @param imageName the image name
	 * @return the image report
	 */
	@RequestMapping(value = { "reportList/image/{reportId}", "reportUserList/image/{reportId}" })
	@ResponseBody
	public byte[] getImageReport(@PathVariable("reportId") Long reportId, @RequestParam(value = "imageName") String imageName) {

		Report report = reportService.findById(reportId);
		File serverFile = new File(report.getFile() + imageName);
		byte[] image = null;

		try {
			image = Files.readAllBytes(serverFile.toPath());
		} catch (IOException e) {
			logger.error("getImageReport", e);
		}

		return image;
	}

}
