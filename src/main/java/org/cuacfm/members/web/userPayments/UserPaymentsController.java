package org.cuacfm.members.web.userPayments;

import java.security.Principal;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.payInscriptionService.PayInscriptionService;
import org.cuacfm.members.model.userPayInscription.UserPayInscription;
import org.cuacfm.members.model.userPayInscriptionService.UserPayInscriptionService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class UserPaymentsController. */
@Controller
public class UserPaymentsController {

	/** The Constant USERPAYINSCRIPTION_VIEW_NAME. */
	private static final String USERPAYINSCRIPTION_VIEW_NAME = "userpayments/userpayments";

	/** The account service. */
	@Autowired
	private AccountService accountService;

	/** The PayInscriptionService. */
	@Autowired
	private PayInscriptionService payInscriptionService;

	/** The UserPayInscriptionService. */
	@Autowired
	private UserPayInscriptionService userPayInscriptionService;

	/** The payInscriptions. */
	private List<UserPayInscription> userPayInscriptions;

	/**
	 * Instantiates a new user payments controller.
	 */
	public UserPaymentsController() {
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
	 * User payments.
	 *
	 * @param model
	 *            the model
	 * @param principal
	 *            the principal
	 * @return the string
	 */
	@RequestMapping(value = "userPayments")
	public String UserPayments(Model model, Principal principal) {
		Account account = accountService.findByLogin(principal.getName());
		userPayInscriptions = userPayInscriptionService
				.getUserPayInscriptionListByAccountId(account.getId());
		model.addAttribute("userPayInscriptions", userPayInscriptions);
		return USERPAYINSCRIPTION_VIEW_NAME;
	}

	/**
	 * View user pay inscriptions by pay inscription id.
	 *
	 * @param userPayInscriptionId
	 *            the user pay inscription id
	 * @param emailPayer
	 *            the email payer
	 * @param idPayer
	 *            the id payer
	 * @param datePay
	 *            the date pay
	 * @param statusPay
	 *            the status pay
	 * @param idTxn
	 *            the id txn
	 * @param principal
	 *            the principal
	 * @param ra
	 *            the ra
	 * @return the string
	 */
	@RequestMapping(value = "userPayments/pay/{userPayInscriptionId}", method = RequestMethod.POST)
	public String viewUserPayInscriptionsByPayInscriptionId(
			@PathVariable Long userPayInscriptionId,
			@RequestParam("payer_email") String emailPayer,
			@RequestParam("payer_id") String idPayer,
			@RequestParam("payment_date") String datePay,
			@RequestParam("payment_status") String statusPay,
			@RequestParam("txn_id") String idTxn, 
			Principal principal,
			RedirectAttributes ra) {

		// Validar que el pago, este realmente echo en paypal, con la informacion que viene en el post....

		Account account = accountService.findByLogin(principal.getName());
		UserPayInscription userPayInscription = userPayInscriptionService
				.findById(userPayInscriptionId);

		// Verified if account is equals to account of userPayAccount
		if (userPayInscription.getAccount().getId() == account.getId()) {
			try {
				userPayInscriptionService.payPayPal(userPayInscription, idTxn,
						idPayer, emailPayer, statusPay, datePay);
				MessageHelper.addSuccessAttribute(ra,
						"userPayments.successPayPayPal", userPayInscription
								.getPayInscription().getName());
			} catch (ExistTransactionIdException e) {
				MessageHelper.addErrorAttribute(ra,
						"userPayments.errorPayPayPal", userPayInscription
								.getPayInscription().getName());
			}
		}

		return "redirect:/userPayments";
	}
}
