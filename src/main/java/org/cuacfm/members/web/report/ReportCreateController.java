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
package org.cuacfm.members.web.report;

import static org.cuacfm.members.model.util.FirebaseUtils.getEmailOfToken;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.model.report.Report;
import org.cuacfm.members.model.report.ReportDTO;
import org.cuacfm.members.model.reportservice.ReportService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/** The Class ReportCreateController. */
@Controller
public class ReportCreateController {

	private static final String INCIDENCE_VIEW_NAME = "report/reportcreate";

	@Value("${maxFiles}")
	private int maxFiles;

	@Autowired
	private ReportService reportService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ProgramService programService;

	private ReportForm reportForm;

	/**
	 * Instantiates a new reportController.
	 */
	public ReportCreateController() {
		super();
	}

	/**
	 * Report form.
	 *
	 * @return the report form
	 */
	@ModelAttribute("reportForm")
	public ReportForm reportForm() {
		return reportForm;
	}

	/**
	 * Report.
	 *
	 * @param model the model
	 * @param principal the principal
	 * @return the string
	 */
	@RequestMapping(value = "reportList/reportCreate")
	public String report(Model model, Principal principal) {

		reportForm = new ReportForm();
		Account account = accountService.findByLogin(principal.getName());

		List<Program> programs;
		if (account.getRole() == roles.ROLE_ADMIN) {
			programs = programService.getProgramList();
		} else {
			programs = programService.getProgramListActiveByUser(account);
			if (programs.size() == 1) {
				reportForm.setProgramId(programs.get(0).getId());
			}
		}
		reportForm.setPrograms(programs);
		model.addAttribute(reportForm);
		return INCIDENCE_VIEW_NAME;
	}

	/**
	 * Report.
	 *
	 * @param reportForm the report form
	 * @param principal the principal
	 * @param errors the errors
	 * @param ra the ra
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "reportList/reportCreate", method = RequestMethod.POST, params = { "create" })
	public String createReport(@Valid @ModelAttribute ReportForm reportForm, Principal principal, Errors errors, RedirectAttributes ra,
			Model model) {

		//Validate max files
		if (reportForm.getPhotos() != null && reportForm.getPhotos().length > maxFiles) {
			errors.rejectValue("photos", "report.photos.error.max", new Object[] { maxFiles }, "photos");
		}

		if (errors.hasErrors()) {
			return INCIDENCE_VIEW_NAME;
		}

		try {
			Account account = accountService.findByLogin(principal.getName());
			Report report = reportForm.createReport(account);
			reportService.save(report, reportForm.getPhotos());

		} catch (Exception e) {
			errors.rejectValue("program", "report.create.error", new Object[] { e }, "program");
			return INCIDENCE_VIEW_NAME;
		}

		MessageHelper.addSuccessAttribute(ra, "report.create.success", reportForm.getProgram().getName());
		return "redirect:/reportList";
	}

	/**
	 * Creates the report API.
	 *
	 * @param token the token
	 * @param reportJson the report json
	 * @return the response entity
	 */
	@RequestMapping(value = "api/reportList/reportCreate", method = RequestMethod.POST)
	public ResponseEntity<String> createReportAPI(@RequestParam(value = "token") String token,
			@RequestParam(value = "reportJson") String reportJson, @RequestParam(value = "photos") String photos) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			Type listType = new TypeToken<ArrayList<byte[]>>() {
			}.getType();
			List<byte[]> photosAux = new Gson().fromJson(photos, listType);

			Account account = accountService.findByEmail(email);
			ReportDTO reportDTO = new Gson().fromJson(reportJson, ReportDTO.class);

			Report report = reportService.getReport(reportDTO, account);
			report = reportService.save(report, photosAux);

			ReportDTO newReportDTO = reportService.getReportDTO(report);
			String newReportJson = new Gson().toJson(newReportDTO);
			return new ResponseEntity<>(newReportJson, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}
}
