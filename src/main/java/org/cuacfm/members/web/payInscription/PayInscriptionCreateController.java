package org.cuacfm.members.web.payInscription;

import javax.validation.Valid;

import org.cuacfm.members.model.payInscriptionService.PayInscriptionService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class PayInscriptionCreateController. */
@Controller
public class PayInscriptionCreateController {

	/** The Constant PAYINSCRIPTION_VIEW_NAME. */
	private static final String PAYINSCRIPTION_VIEW_NAME = "payinscription/payinscriptioncreate";

	/** The training service. */
	@Autowired
	private PayInscriptionService payInscriptionService;

	/**
	 * Instantiates a new payInscriptionController.
	 */
	public PayInscriptionCreateController() {
		// Default empty constructor.
	}

	/**
	 * Training.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "payInscriptionList/payInscriptionCreate")
	public String training(Model model) {
		model.addAttribute(new PayInscriptionForm());
		return PAYINSCRIPTION_VIEW_NAME;
	}

	/**
	 * Training.
	 *
	 * @param trainingForm
	 *            the training form
	 * @param errors
	 *            the errors
	 * @param ra
	 *            the ra
	 * @return the string
	 */
	@RequestMapping(value = "payInscriptionList/payInscriptionCreate", method = RequestMethod.POST)
	public String payInscription(
			@Valid @ModelAttribute PayInscriptionForm payInscriptionForm,
			Errors errors, RedirectAttributes ra, Model model) {
	
		// It is verified that there is not exist year of payInscription in other payInscription
		int year = payInscriptionForm.getYear();
		if (payInscriptionService.findByYear(year) != null) {		
			errors.rejectValue("year", "payInscription.yearException",
					new Object[] { year }, "year");
		}
		
		if (errors.hasErrors()) {
			return PAYINSCRIPTION_VIEW_NAME;
		}
		
		String name = payInscriptionForm.getName();
		payInscriptionService.save(payInscriptionForm.createPayInscription());
		MessageHelper.addSuccessAttribute(ra, "payInscription.successCreate", name);
		return "redirect:/payInscriptionList";
	}

}