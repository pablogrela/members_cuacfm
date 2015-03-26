package org.cuacfm.members.web.training;

import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.exceptions.DateLimitException;
import org.cuacfm.members.model.exceptions.DateLimitExpirationException;
import org.cuacfm.members.model.exceptions.MaximumCapacityException;
import org.cuacfm.members.model.inscription.Inscription;
import org.cuacfm.members.model.inscription.InscriptionRepository;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingService.TrainingService;
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

/** The Class InscriptionListController. */
@Controller
public class InscriptionListController {

	/** The Constant TRAINING_VIEW_NAME. */
	private static final String TRAINING_VIEW_NAME = "training/inscriptionlist";

	/** The account service. */
	@Autowired
	private AccountService accountService;

	/** The TrainingTypeService. */
	@Autowired
	private TrainingService trainingService;

	@Autowired
	private InscriptionRepository inscriptionRepository;

	/** The trainingTypes. */
	public List<Inscription> inscriptions;

	/** The trainingTypes. */
	public List<Training> trainings;

	/** The training. */
	private Training training;

	/**
	 * Instantiates a new inscriptionListController Controller.
	 */
	public InscriptionListController() {
		// Default empty constructor.
	}

	/**
	 * Add training to view
	 *
	 * @return Training
	 */
	@ModelAttribute("training")
	public Training training() {
		return training;
	}

	/**
	 * Add trainings to view
	 *
	 * @return Trainings
	 */
	@ModelAttribute("trainings")
	public List<Training> trainings() {
		return trainings;
	}

	/**
	 * Show inscriptionList.
	 *
	 * @param model
	 *            the model
	 * @return string the view
	 */
	@RequestMapping(value = "trainingList/inscriptionList")
	public String getInscriptionList(Model model) {
		if (training != null) {
			inscriptions = trainingService.getInscriptionsByTrainingId(training
					.getId());
			training = trainingService.findById(training.getId());
			InscriptionsForm inscriptionsForm = new InscriptionsForm();
			inscriptionsForm.setInscriptions(inscriptions);
			model.addAttribute(inscriptionsForm);
			model.addAttribute("training", training);
			model.addAttribute("inscriptions", inscriptions);
			model.addAttribute(new FindUserForm());
			return TRAINING_VIEW_NAME;
		}
		// If not have training, redirect to trainingList
		else {
			return "redirect:/trainingList";
		}
	}

	/**
	 * Charge inscription
	 *
	 * @param @PathVariable Long trainingId
	 * @return string the view
	 */
	@RequestMapping(value = "trainingList/inscriptionList/{trainingId}", method = RequestMethod.POST)
	public String postTraining(@PathVariable Long trainingId) {
		training = trainingService.findById(trainingId);
		return "redirect:/trainingList/inscriptionList";
	}

	/**
	 * Save and Close InscriptionList
	 *
	 * @param @Valid @ModelAttribute InscriptionsForm inscriptionsForm
	 * @param RedirectAttributes
	 *            ra
	 * @return string the view
	 */
	@RequestMapping(value = "trainingList/inscriptionList/save", method = RequestMethod.POST, params = { "save" })
	public String save(
			@Valid @ModelAttribute InscriptionsForm inscriptionsForm,
			RedirectAttributes ra) throws DateLimitException {

		List<Inscription> insUpdate = inscriptionsForm.getInscriptions();

		boolean modify = false;
		int count = training.getCountPlaces();

		for (int index = 0; index < insUpdate.size(); index++) {

			if (insUpdate.get(index).getNote() != inscriptions.get(index)
					.getNote()) {
				inscriptions.get(index).setNote(insUpdate.get(index).getNote());
				modify = true;
			}
			if (insUpdate.get(index).isAttend() != inscriptions.get(index)
					.isAttend()) {
				inscriptions.get(index).setAttend(
						insUpdate.get(index).isAttend());
				modify = true;
			}
			if (insUpdate.get(index).isPass() != inscriptions.get(index)
					.isPass()) {
				inscriptions.get(index).setPass(insUpdate.get(index).isPass());
				modify = true;
			}
			if (insUpdate.get(index).isUnsubscribe() != inscriptions.get(index)
					.isUnsubscribe()) {
				inscriptions.get(index).setUnsubscribe(
						insUpdate.get(index).isUnsubscribe());
				if (insUpdate.get(index).isUnsubscribe() == true)
					count = count - 1;
				else
					count = count + 1;
				modify = true;
			}
			if (modify) {
				trainingService.updateInscription(inscriptions.get(index));
				modify = false;
			}
		}
		training.setCountPlaces(count);
		training.setClose(true);
		trainingService.update(training);
		MessageHelper.addSuccessAttribute(ra, "training.successClose",
				training.getName());
		return "redirect:/trainingList";
	}


	/**
	 * Update InscriptionList
	 *
	 * @param @Valid @ModelAttribute InscriptionsForm inscriptionsForm
	 * @param RedirectAttributes
	 *            ra
	 * @return string the view
	 */
	@RequestMapping(value = "trainingList/inscriptionList/save", method = RequestMethod.POST, params = { "update" })
	public String update(
			@Valid @ModelAttribute InscriptionsForm inscriptionsForm,
			RedirectAttributes ra) throws DateLimitException {

		System.out.println("update");
		/*
		 * if( action.equals("save") ){ System.out.println(action); } else if(
		 * action.equals("update") ){ System.out.println(action); }
		 */
		List<Inscription> insUpdate = inscriptionsForm.getInscriptions();

		boolean modify = false;
		int count = training.getCountPlaces();

		for (int index = 0; index < insUpdate.size(); index++) {

			if (insUpdate.get(index).getNote() != inscriptions.get(index)
					.getNote()) {
				inscriptions.get(index).setNote(insUpdate.get(index).getNote());
				modify = true;
			}
			if (insUpdate.get(index).isAttend() != inscriptions.get(index)
					.isAttend()) {
				inscriptions.get(index).setAttend(
						insUpdate.get(index).isAttend());
				modify = true;
			}
			if (insUpdate.get(index).isPass() != inscriptions.get(index)
					.isPass()) {
				inscriptions.get(index).setPass(insUpdate.get(index).isPass());
				modify = true;
			}
			if (insUpdate.get(index).isUnsubscribe() != inscriptions.get(index)
					.isUnsubscribe()) {
				inscriptions.get(index).setUnsubscribe(
						insUpdate.get(index).isUnsubscribe());
				if (insUpdate.get(index).isUnsubscribe() == true)
					count = count - 1;
				else
					count = count + 1;
				modify = true;
			}
			if (modify) {
				trainingService.updateInscription(inscriptions.get(index));
				modify = false;
			}
		}
		training.setCountPlaces(count);
		trainingService.update(training);
		MessageHelper.addSuccessAttribute(ra, "training.successModify",
				training.getName());
		return "redirect:/trainingList/inscriptionList";
	}

	/**
	 * Join account to training by trainingId.
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
	@RequestMapping(value = "trainingList/inscriptionList", method = RequestMethod.POST)
	public String addUserToInscriptionList(@Valid @ModelAttribute FindUserForm FindUserForm,
			Errors errors, RedirectAttributes ra, Model model) {

		// Charge database in table
		InscriptionsForm inscriptionsForm = new InscriptionsForm();
		inscriptionsForm.setInscriptions(inscriptions);
		model.addAttribute(inscriptionsForm);

		if (errors.hasErrors()) {
			return TRAINING_VIEW_NAME;
		}

		// Find by login
		String login = FindUserForm.getLogin();

		// Check if account exist
		Account account = accountService.findByLogin(login);
		if (account == null) {
			errors.rejectValue("login", "inscription.noExistLogin",
					new Object[] { login }, "login");
			return TRAINING_VIEW_NAME;
		}

		// Check if account already inscription
		Inscription inscription = trainingService.findByInscriptionIds(
				account.getId(), training.getId());
		if (inscription != null) {
			errors.rejectValue("login", "inscription.alreadyExistLogin",
					new Object[] { login }, "login");
			return TRAINING_VIEW_NAME;
		}

		try {
			trainingService
					.createInscription(account.getId(), training.getId());
			MessageHelper.addSuccessAttribute(ra, "inscription.successJoin",
					login);
		// If not have space in this training
		} catch (MaximumCapacityException e) {
			MessageHelper.addErrorAttribute(ra,
					"training.maxInscriptionsException", training.getName());
		// If Date limit was espirated
		} catch (DateLimitExpirationException e) {
			MessageHelper.addErrorAttribute(ra,
					"training.dateLimitExpirationException",
					training.getName(),
					DisplayDate.dateTimeToString(training.getDateLimit()));
		}

		return "redirect:/trainingList/inscriptionList";
	}

	/**
	 * Remove join account to training.
	 *
	 * @param @PathVariable Long accountId
	 * @param ra
	 *            the redirect atributes
	 * @param principal
	 *            Principal
	 * @return the string destinity page to page trainingList/inscriptionList
	 * @throws UnsubscribeException
	
	@RequestMapping(value = "trainingList/inscriptionList/trainingRemoveJoin/{accountId}", method = RequestMethod.POST)
	public String removeJoin(@PathVariable Long accountId,
			RedirectAttributes ra, Principal principal)
			throws UnsubscribeException {

		try {
			trainingService.unsubscribeInscription(accountId, training.getId());
			System.out.println("si");

			MessageHelper.addErrorAttribute(ra, "training.removeJoin",
					training.getName());
		} catch (UnsubscribeException e) {
			System.out.println("no");
			MessageHelper.addErrorAttribute(ra,
					"training.unsubscribeInscriptionException",
					training.getName());
		}

		return "redirect:/trainingList/inscriptionList";
	}
	 */
}
