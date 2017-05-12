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
package org.cuacfm.members.web.payprogram;

import java.util.ArrayList;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.util.Constants.typeDestinataries;
import org.cuacfm.members.model.util.Constants.typePush;
import org.cuacfm.members.model.util.NotificationService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class PayProgramListController. */
@Controller
public class PayProgramListController {

	private static final String PAYPROGRAM_VIEW_NAME = "payprogram/payprogramlist";
	private static final String REDIRECT_PAYPROGRAM = "redirect:/feeProgramList/payProgramList";

	@Autowired
	private FeeProgramService feeProgramService;

	@Autowired
	private PayProgramService payProgramService;

	@Autowired
	private NotificationService notificationService;

	private FeeProgram feeProgram;
	private List<PayProgram> payPrograms;

	/** Instantiates a new training Controller. */
	public PayProgramListController() {
		super();
	}

	/**
	 * Fee Program.
	 *
	 * @return the fee Program
	 */
	@ModelAttribute("feeProgram")
	public FeeProgram feeProgram() {
		return feeProgram;
	}

	/**
	 * List of PayProgram.
	 *
	 * @return List<PayProgram>
	 */
	@ModelAttribute("payPrograms")
	public List<PayProgram> payPrograms() {
		return payPrograms;
	}

	/**
	 * Pay programs.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "feeProgramList/payProgramList")
	public String payPrograms(Model model) {
		if (feeProgram != null) {
			payPrograms = payProgramService.getPayProgramListByFeeProgramId(feeProgram.getId());
			model.addAttribute("payPrograms", payPrograms);
			return PAYPROGRAM_VIEW_NAME;
		} else {
			return "redirect:/feeProgramList";
		}
	}

	/**
	 * View pay programs by pay fee program id.
	 *
	 * @param feeProgramId the pay inscription id
	 * @return the string
	 */
	@RequestMapping(value = "feeProgramList/payProgramList/{feeProgramId}", method = RequestMethod.POST)
	public String feeProgramList(@PathVariable Long feeProgramId) {
		feeProgram = feeProgramService.findById(feeProgramId);
		return REDIRECT_PAYPROGRAM;
	}

	/**
	 * Pay bill fee program.
	 *
	 * @param payProgramId the pay program id
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "feeProgramList/payProgramList/pay/{payProgramId}", method = RequestMethod.POST)
	public String payBillFeeProgram(@PathVariable Long payProgramId, RedirectAttributes ra) {
		PayProgram payProgram = payProgramService.findById(payProgramId);
		payProgramService.pay(payProgram);

		MessageHelper.addSuccessAttribute(ra, "payProgram.successPay", payProgram.getProgram().getName());
		return REDIRECT_PAYPROGRAM;
	}

	/**
	 * Creates the pdf.
	 *
	 * @param payProgramId the pay program id
	 * @param createPdf the create pdf
	 * @return the response entity
	 */
	@RequestMapping(value = "feeProgramList/payProgramList/createPdf/{payProgramId}", method = RequestMethod.POST, params = { "createPdf" })
	public ResponseEntity<byte[]> createPdf(@PathVariable Long payProgramId, @RequestParam("createPdf") String createPdf) {
		return payProgramService.createPdfFeeProgram(payProgramId, createPdf);
	}

	/**
	 * Direct debit refresh.
	 *
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "feeProgramList/payProgramList/refresh", method = RequestMethod.POST)
	public String refresh(RedirectAttributes ra) {
		feeProgramService.refresh(feeProgram);

		MessageHelper.addErrorAttribute(ra, "feeProgram.successRefresh", "");
		return REDIRECT_PAYPROGRAM;
	}

	/**
	 * Notification.
	 *
	 * @param feeProgramId the fee program id
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "feeProgramList/notification/{feeProgramId}", method = RequestMethod.POST)
	public String notification(@PathVariable Long feeProgramId, RedirectAttributes ra) {

		FeeProgram feeProgram = feeProgramService.findById(feeProgramId);

		List<Account> accounts = new ArrayList<>();
		for (PayProgram payProgram : payProgramService.getPayProgramListByFeeProgramId(feeProgram.getId())) {
			accounts.addAll(payProgram.getProgram().getAccounts());
		}

		if (notificationService.sendNotification(typeDestinataries.ALL, accounts, feeProgram.getName(), feeProgram.getDescription(), typePush.DEFAULT,
				null)) {
			MessageHelper.addInfoAttribute(ra, "notification.send.success", feeProgram.getName());
		} else {
			MessageHelper.addWarningAttribute(ra, "notification.send.error", feeProgram.getName());
		}

		return "redirect:/feeProgramList";
	}
}
