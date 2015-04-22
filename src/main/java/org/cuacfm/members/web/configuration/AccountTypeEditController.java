package org.cuacfm.members.web.configuration;

import javax.validation.Valid;

import org.cuacfm.members.model.accountType.AccountType;
import org.cuacfm.members.model.accountTypeService.AccountTypeService;
import org.cuacfm.members.model.exceptions.UniqueException;
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

/** The Class AccountTypeEditController. */
@Controller
public class AccountTypeEditController {

	/** The Constant TRAINING_VIEW_NAME. */
	private static final String ACCOUNTYPE_VIEW_NAME = "configuration/accountypedit";

	/** The accountTypeService. */
	@Autowired
	private AccountTypeService accountTypeService;


	/** The Global variable accountType Type. */
	private AccountType accountType;

	/**
	 * Instantiates a new accountType Controller.
	 */
	public AccountTypeEditController() {
		// Default empty constructor.
	}

	/**
	 * Get page accountTypeEdit.
	 *
	 * @param model
	 *            the model
	 * @return String to view to accountTypeEdit
	 */
	@RequestMapping(value = "configuration/accountTypeEdit")
	public String accountType(Model model) {
		if (accountType != null) {
			AccountTypeForm accountTypeForm = new AccountTypeForm();
			accountTypeForm.setName(accountType.getName());	
			accountTypeForm.setDescription(accountType.getDescription());
			accountTypeForm.setDiscount(accountType.getDiscount());
			model.addAttribute(accountTypeForm);
			return ACCOUNTYPE_VIEW_NAME;
		}
		// If not have trainingType, redirect to trainingList
		else {
			return "redirect:/configuration";
		}
	}


	/**
	 * Post to create a new accountType.
	 *
	 * @param accountTypeForm
	 *            the accountType form
	 * @param errors
	 *            the errors
	 * @param ra
	 *            the ra
	 * @return String to redirect to accountTypeList or if fault accountTypeEdit
	 * 
	 */
	@RequestMapping(value = "configuration/accountTypeEdit", method = RequestMethod.POST)
	public String accountTypeEdit(
			@Valid @ModelAttribute AccountTypeForm accountTypeForm, Errors errors,
			RedirectAttributes ra) {

		if (errors.hasErrors()) {
			return ACCOUNTYPE_VIEW_NAME;
		}

		try {
			accountTypeService.update(accountTypeForm.updateAccountType(accountType));
		} catch (UniqueException e) {
			errors.rejectValue("name", "accountType.existentName",
					new Object[] { e.getValue() }, "name");
			return ACCOUNTYPE_VIEW_NAME;
		}
		MessageHelper.addWarningAttribute(ra, "accountType.successModify",
				accountTypeForm.getName());
		return "redirect:/configuration";
	}
	


	/**
	 * Charge account type.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "configuration/accountTypeEdit/{id}", method = RequestMethod.POST)
	public String chargeAccountType(@PathVariable Long id, RedirectAttributes ra) {

		accountType = accountTypeService.findById(id);
		return "redirect:/configuration/accountTypeEdit";
	}
}
