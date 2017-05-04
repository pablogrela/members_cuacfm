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

import javax.validation.Valid;

import org.cuacfm.members.model.report.Report;
import org.cuacfm.members.model.reportservice.ReportService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class ReportEditController. */
@Controller
public class ReportEditController {

	private static final String PROGRAM_VIEW_NAME = "report/reportedit";

	@Autowired
	private ReportService reportService;

	private Report report;
	private ReportForm reportForm;

	/**
	 * Instantiates a new report edit controller.
	 */
	public ReportEditController() {
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
	 * @return the string
	 */
	@RequestMapping(value = "reportList/reportEdit")
	public String report(Model model) {

		if (report != null) {
			reportForm = new ReportForm();
			reportForm.setProgramId(report.getProgram().getId());			
			reportForm.setDirt(report.getDirt());
			reportForm.setTidy(report.getTidy());
			reportForm.setConfiguration(report.getConfiguration());
			reportForm.setOpenDoor(report.isOpenDoor());
			reportForm.setViewMembers(report.isViewMembers());
			reportForm.setDescription(report.getDescription());
			model.addAttribute(reportForm);
			return PROGRAM_VIEW_NAME;
		}
		// If not have report, redirect to reportList
		else {
			return "redirect:/reportList";
		}
	}

	/**
	 * Report.
	 *
	 * @param reportForm the report form
	 * @param errors the errors
	 * @param ra the ra
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "reportList/reportEdit", method = RequestMethod.POST, params = { "edit" })
	public String report(@Valid @ModelAttribute ReportForm reportForm, Errors errors, RedirectAttributes ra, Model model) {

		if (errors.hasErrors()) {
			return PROGRAM_VIEW_NAME;
		}
		try {
			reportForm.updateReport(report);
			reportService.update(report);
		} catch (Exception e) {
			errors.rejectValue("name", "report.existentName", new Object[] { e }, "name");
			return PROGRAM_VIEW_NAME;
		}

		MessageHelper.addSuccessAttribute(ra, "report.successModify", reportForm.getProgram().getName());
		return "redirect:/reportList";
	}

	/**
	 * Modify report.
	 *
	 * @param id the id
	 * @return the string
	 */
	@RequestMapping(value = "reportList/reportEdit/{id}", method = RequestMethod.POST)
	public String modifyReport(@PathVariable Long id) {

		report = reportService.findById(id);
		return "redirect:/reportList/reportEdit";
	}
}
