package org.cuacfm.members.web.payInscription;

import javax.validation.Valid;

import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.payInscription.PayInscription;
import org.cuacfm.members.model.payInscriptionService.PayInscriptionService;
import org.cuacfm.members.web.support.DisplayDate;
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

/** The Class PayInscriptionEditController. */
@Controller
public class PayInscriptionEditController {

	/** The Constant TRAINING_VIEW_NAME. */
	private static final String TRAINING_VIEW_NAME = "payinscription/payinscriptionedit";

	/** The training service. */
	@Autowired
	private PayInscriptionService payInscriptionService;

	/** The Global variable payInscription. */
	private PayInscription payInscription;

	/**
	 * Instantiates a new training Controller.
	 */
	public PayInscriptionEditController() {
		// Default empty constructor.
	}


	/**
	 * PayInscription.
	 *
	 * @return PayInscription
	 */
	@ModelAttribute("payInscription")
	public PayInscription payInscription() {
		return payInscription;
	}
	
	/**
	 * Training.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "payInscriptionList/payInscriptionEdit")
	public String training(Model model) {
		
		if (payInscription != null) {
			PayInscriptionForm payInscriptionForm = new PayInscriptionForm();
			payInscriptionForm.setName(payInscription.getName());	
			payInscriptionForm.setYear(payInscription.getYear());
			payInscriptionForm.setPrice(payInscription.getPrice());
			payInscriptionForm.setDescription(payInscription.getDescription());
			payInscriptionForm.setDateLimit1(DisplayDate.dateToString(payInscription.getDateLimit1()));
			payInscriptionForm.setDateLimit2(DisplayDate.dateToString(payInscription.getDateLimit2()));
			model.addAttribute(payInscription);
			model.addAttribute(payInscriptionForm);
			return TRAINING_VIEW_NAME;
		}
		// If not have payInscription, redirect to payInscriptionList
		else {
			return "redirect:/payInscriptionList";
		}
	}

	
	/**
	 * Training.
	 *
	 * @param payInscriptionForm the pay inscription form
	 * @param errors the errors
	 * @param ra the ra
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "payInscriptionList/payInscriptionEdit", method = RequestMethod.POST)
	public String training(@Valid @ModelAttribute PayInscriptionForm payInscriptionForm,
			Errors errors, RedirectAttributes ra, Model model) {

		if (errors.hasErrors()) {
			return TRAINING_VIEW_NAME;
		}
	
		// Modify Training
		payInscription.setName(payInscriptionForm.getName());	
		payInscription.setYear(payInscriptionForm.getYear());
		payInscription.setPrice(payInscriptionForm.getPrice());
		payInscription.setDescription(payInscriptionForm.getDescription());
		payInscription.setDateLimit1(DisplayDate.stringToDate2(payInscriptionForm.getDateLimit1()));
		payInscription.setDateLimit2(DisplayDate.stringToDate2(payInscriptionForm.getDateLimit2()));
		int year = payInscriptionForm.getYear();
		
		try {
			payInscriptionService.update(payInscription);
		// It is verified that there is not exist year of payInscription in other payInscription
		} catch (UniqueException e) {
			errors.rejectValue("year", "payInscription.yearException",
					new Object[] { year }, "year");
		}
		
		if (errors.hasErrors()) {
			return TRAINING_VIEW_NAME;
		}
		
		MessageHelper.addWarningAttribute(ra, "payInscription.successModify", payInscriptionForm.getName());
		return "redirect:/payInscriptionList";
	}

	/**
	 * Modify PayInscription by Id.
	 *
	 * @param id the id
	 * @param ra            the redirect atributes
	 * @return the string destinity page
	 */
	@RequestMapping(value = "payInscriptionList/payInscriptionEdit/{id}", method = RequestMethod.POST)
	public String modifyTraining(@PathVariable Long id, RedirectAttributes ra) {

		payInscription = payInscriptionService.findById(id);
		return "redirect:/payInscriptionList/payInscriptionEdit";
	}
}
