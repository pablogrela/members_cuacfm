package org.cuacfm.members.web.configuration;

import java.util.List;

import org.cuacfm.members.model.accountType.AccountType;
import org.cuacfm.members.model.accountTypeService.AccountTypeService;
import org.cuacfm.members.model.methodPayment.MethodPayment;
import org.cuacfm.members.model.methodPaymentService.MethodPaymentService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class ConfigurationController. */
@Controller
public class ConfigurationController {

	/** The Constant CONFIGURATION_VIEW_NAME. */
	private static final String CONFIGURATION_VIEW_NAME = "configuration/configuration";

	/** The AccountTypeService. */
	@Autowired
	private AccountTypeService accountTypeService;

	/** The MethodPaymentService. */
	@Autowired
	private MethodPaymentService methodPaymentService;

	/**
	 * Instantiates a new user payments controller.
	 */
	public ConfigurationController() {
		// Default empty constructor.
	}

	/**
	 * List of AccountType.
	 *
	 * @return List<AccountType>
	 */
	@ModelAttribute("accountTypes")
	public List<AccountType> accountTypes() {
		return accountTypeService.getAccountTypes();
	}

	/**
	 * List of MethodPayment.
	 *
	 * @return List<MethodPayment>
	 */
	@ModelAttribute("methodPayments")
	public List<MethodPayment> methodPayments() {
		return methodPaymentService.getMethodPayments();
	}

	/**
	 * User payments.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "configuration")
	public String Configuration(Model model) {
		return CONFIGURATION_VIEW_NAME;
	}

	/**
	 * Removes the account type.
	 *
	 * @param ra
	 *            the ra
	 * @param id
	 *            the id
	 * @return the string
	 */
	@RequestMapping(value = "configuration/accountTypeDelete/{id}", method = RequestMethod.POST)
	public String removeAccountType(RedirectAttributes ra, @PathVariable Long id) {
		String name = accountTypeService.findById(id).getName();
		accountTypeService.delete(id);
		MessageHelper.addInfoAttribute(ra, "accountType.successDelete", name);
		return "redirect:/configuration";
	}

	/**
	 * Remov method payment.
	 *
	 * @param ra
	 *            the ra
	 * @param id
	 *            the id
	 * @return the string
	 */
	@RequestMapping(value = "configuration/methodPaymentDelete/{id}", method = RequestMethod.POST)
	public String removMethodPayment(RedirectAttributes ra,
			@PathVariable Long id) {
		String name = methodPaymentService.findById(id).getName();
		methodPaymentService.delete(id);
		MessageHelper.addInfoAttribute(ra, "methodPayment.successDelete", name);
		return "redirect:/configuration";
	}
}
