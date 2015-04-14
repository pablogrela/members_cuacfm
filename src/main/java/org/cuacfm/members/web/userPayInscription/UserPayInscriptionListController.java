package org.cuacfm.members.web.userPayInscription;

import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.payInscription.PayInscription;
import org.cuacfm.members.model.payInscriptionService.PayInscriptionService;
import org.cuacfm.members.model.userPayInscription.UserPayInscription;
import org.cuacfm.members.model.userPayInscriptionService.UserPayInscriptionService;
import org.cuacfm.members.web.support.MessageHelper;
import org.cuacfm.members.web.training.FindUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class UserPayInscriptionListController. */
@Controller
public class UserPayInscriptionListController {

	/** The Constant USERPAYINSCRIPTION_VIEW_NAME. */
	private static final String USERPAYINSCRIPTION_VIEW_NAME = "userpayinscription/userpayinscriptionlist";

	/** The account service. */
	@Autowired
	private AccountService accountService;

	/** The PayInscriptionService. */
	@Autowired
	private PayInscriptionService payInscriptionService;

	/** The UserPayInscriptionService. */
	@Autowired
	private UserPayInscriptionService userPayInscriptionService;

	/** The payInscription. */
	private PayInscription payInscription;

	/** The payInscriptions. */
	private List<UserPayInscription> userPayInscriptions;

	/**
	 * Instantiates a new training Controller.
	 */
	public UserPayInscriptionListController() {
		// Default empty constructor.
	}

	/**
	 * List of UserPayInscription.
	 *
	 * @return List<UserPayInscription>
	 */
	@ModelAttribute("userPayInscriptions")
	public List<UserPayInscription> userPayInscriptions() {
		return userPayInscriptions;
	}

	/**
	 * User pay inscriptions.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "payInscriptionList/userPayInscriptionList")
	public String UserPayInscriptions(Model model) {
		if (payInscription != null) {
			model.addAttribute(new FindUserForm());
			userPayInscriptions = userPayInscriptionService
					.getUserPayInscriptionListByPayInscriptionId(payInscription
							.getId());
			model.addAttribute("userPayInscriptions", userPayInscriptions);
			return USERPAYINSCRIPTION_VIEW_NAME;
		} else {
			return "redirect:/payInscriptionList";
		}
	}

	/**
	 * View user pay inscriptions by pay inscription id.
	 *
	 * @param payInscriptionId
	 *            the pay inscription id
	 * @param ra
	 *            the ra
	 * @return the string
	 */
	@RequestMapping(value = "payInscriptionList/userPayInscriptionList/{payInscriptionId}", method = RequestMethod.POST)
	public String viewUserPayInscriptionsByPayInscriptionId(
			@PathVariable Long payInscriptionId, RedirectAttributes ra) {
		payInscription = payInscriptionService.findById(payInscriptionId);
		return "redirect:/payInscriptionList/userPayInscriptionList";
	}

	/**
	 * Join account to UserPayInscription.
	 *
	 * @param @Valid @ModelAttribute FindUserForm FindUserForm
	 * 
	 * @param errors
	 *            the errors
	 * @param ra
	 *            the redirect atributes
	 * @param model
	 *            the model
	 * @return the string destinity page to page trainingList
	 */
	@RequestMapping(value = "payInscriptionList/userPayInscriptionList", method = RequestMethod.POST)
	public String addUserToUserPayInscriptionList(
			@Valid @ModelAttribute FindUserForm FindUserForm, Errors errors,
			RedirectAttributes ra, Model model) {

		if (errors.hasErrors()) {
			return USERPAYINSCRIPTION_VIEW_NAME;
		}

		// Find by login
		String login = FindUserForm.getLogin();

		// Check if account exist
		Account account = accountService.findByLogin(login);
		if (account == null) {
			errors.rejectValue("login", "inscription.noExistLogin",
					new Object[] { login }, "login");
			return USERPAYINSCRIPTION_VIEW_NAME;
		}

		// Check if account already userPayInscription
		List<UserPayInscription> userPayInscriptions = userPayInscriptionService
				.findByUserPayInscriptionIds(account.getId(),
						payInscription.getId());
		if (!userPayInscriptions.isEmpty()) {
			errors.rejectValue("login", "userPayInscription.alreadyExistLogin",
					new Object[] { login }, "login");
			return USERPAYINSCRIPTION_VIEW_NAME;
		}

		payInscriptionService.saveUserPayInscription(account, payInscription);

		MessageHelper.addSuccessAttribute(ra, "userPayInscription.successJoin",
				login);
		return "redirect:/payInscriptionList/userPayInscriptionList";
	}

	/**
	 * Pay bill.
	 *
	 * @param userPayInscriptionId
	 *            the user pay inscription id
	 * @param ra
	 *            the ra
	 * @return the string
	 */
	@RequestMapping(value = "payInscriptionList/userPayInscriptionList/pay/{userPayInscriptionId}", method = RequestMethod.POST)
	public String pay(@PathVariable Long userPayInscriptionId,
			RedirectAttributes ra) {
		UserPayInscription userPayInscription = userPayInscriptionService
				.findById(userPayInscriptionId);
		userPayInscriptionService.pay(userPayInscription);

		MessageHelper.addSuccessAttribute(ra, "userPayInscription.successPay",
				userPayInscription.getAccount().getName());
		return "redirect:/payInscriptionList/userPayInscriptionList";
	}
}
