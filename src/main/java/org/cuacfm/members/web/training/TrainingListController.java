package org.cuacfm.members.web.training;

import java.security.Principal;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.exceptions.DateLimitExpirationException;
import org.cuacfm.members.model.exceptions.ExistInscriptionsException;
import org.cuacfm.members.model.exceptions.MaximumCapacityException;
import org.cuacfm.members.model.exceptions.UnsubscribeException;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingService.TrainingService;
import org.cuacfm.members.model.trainingType.TrainingType;
import org.cuacfm.members.model.trainingTypeService.TrainingTypeService;
import org.cuacfm.members.model.userService.UserService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class TrainingListController. */
@Controller
public class TrainingListController {

	/** The Constant TRAINING_VIEW_NAME. */
	private static final String TRAINING_VIEW_NAME = "training/traininglist";

	/** The training service. */
	@Autowired
	private AccountService accountService;

	/** The trainingTypeService. */
	@Autowired
	private TrainingTypeService trainingTypeService;

	/** The training service. */
	@Autowired
	private TrainingService trainingService;

	/** The user service. */
	@Autowired
	private UserService userService;

	/** The trainings. */
	private List<Training> trainings;

	/** The trainingTypes. */
	private List<TrainingType> trainingTypes;

	/** The trainingAccounts. */
	private List<Long> trainingAccountIds;

	/** The trainingUnsubscribes. */
	private List<Long> trainingUnsubscribeIds;

	/**
	 * Instantiates a new training Controller.
	 */
	public TrainingListController() {
		// Default empty constructor.
	}

	/**
	 * List of Training Ids trainings to which the user joined.
	 *
	 * @return List<Long> the id list of account joined
	 */
	@ModelAttribute("trainingAccountIds")
	public List<Long> trainingAccountIds() {
		return trainingAccountIds;
	}

	/**
	 * List of Training Ids unsubscribe to which the user can not join neither
	 * remove join.
	 *
	 * @return List<Long> the id list of account joined
	 */
	@ModelAttribute("trainingUnsubscribeIds")
	public List<Long> trainingUnsubscribeIds() {
		return trainingUnsubscribeIds;
	}

	/**
	 * List of TrainingType for show in form select.
	 *
	 * @return the list
	 */
	@ModelAttribute("trainingTypes")
	public List<TrainingType> trainingsType() {
		return trainingTypes;
	}

	/**
	 * List of Training for show in table.
	 *
	 * @return the list
	 */
	@ModelAttribute("trainings")
	public List<Training> trainings() {
		return trainings;
	}

	/**
	 * Get page trainingList.
	 *
	 * @param model
	 *            the model
	 * @param principal
	 *            Principal
	 * @return the string to view page trainingList
	 */
	@RequestMapping(value = "trainingList")
	public String trainings(Model model, Principal principal) {

		// List of TrainingTypes for select form
		trainingTypes = trainingTypeService.getTrainingTypeList();
		model.addAttribute("trainingTypes", trainingTypes);

		// List of Trainings for tables
		trainings = trainingService.getTrainingListOpen();
		model.addAttribute("trainings", trainings);

		// Ids trainings to which the user joined
		Account account = accountService.findByLogin(principal.getName());
		trainingAccountIds = trainingService
				.getInscriptionsIdsByAccountId(account.getId());
		model.addAttribute("trainingAccountIds", trainingAccountIds);

		// Ids trainings unsubscribe to which the user not join and remove join
		trainingUnsubscribeIds = trainingService
				.getUnsubscribeIdsByAccountId(account.getId());
		model.addAttribute("trainingUnsubscribeIds", trainingUnsubscribeIds);

		// Insert Training Type List in Select ()
		TrainingSelectForm trainingSelectForm = new TrainingSelectForm();
		trainingSelectForm.setTrainingTypes(trainingTypeService
				.getTrainingTypeList());
		model.addAttribute(trainingSelectForm);

		return TRAINING_VIEW_NAME;
	}

	/**
	 * Delete Training by Id.
	 *
	 * @param @PathVariable Long id
	 * 
	 * @param ra
	 *            the redirect atributes
	 * @return the string destinity page to page trainingList
	 */
	@RequestMapping(value = "trainingList/trainingDelete/{id}", method = RequestMethod.POST)
	public String remove(RedirectAttributes ra, @PathVariable Long id) {

		String name = trainingService.findById(id).getName();
		try {
			trainingService.delete(id);
			MessageHelper.addInfoAttribute(ra, "training.successDelete", name);
		} catch (ExistInscriptionsException e) {
			MessageHelper.addErrorAttribute(ra,
					"training.existDependenciesTrainingsException", name);
		}
		return "redirect:/trainingList";
	}

	/**
	 * Join account to training by trainingId.
	 *
	 * @param @PathVariable Long trainingId
	 * 
	 * @param ra
	 *            the redirect atributes
	 * @return the string destinity page to page trainingList
	 */
	@RequestMapping(value = "trainingList/trainingJoin/{trainingId}", method = RequestMethod.POST)
	public String join(@PathVariable Long trainingId, RedirectAttributes ra,
			Principal principal) {

		Long accountId = accountService.findByLogin(principal.getName())
				.getId();
		Training training = trainingService.findById(trainingId);

		try {
			trainingService.createInscription(accountId, trainingId);
			MessageHelper.addSuccessAttribute(ra, "training.successJoin",
					training.getName());
		} catch (MaximumCapacityException e) {
			MessageHelper.addErrorAttribute(ra, "training.maxInscriptionsException",
					training.getName());
		} catch (DateLimitExpirationException e) {
			MessageHelper
					.addErrorAttribute(ra,
							"training.dateLimitExpirationException",
							training.getName());
		}

		return "redirect:/trainingList";
	}

	/**
	 * Remove join account to training by trainingId.
	 *
	 * @param @PathVariable Long trainingId
	 * 
	 * @param ra
	 *            the redirect atributes
	 * @param principal
	 *            Principal
	 * @return the string destinity page to page trainingList
	 */
	@RequestMapping(value = "trainingList/trainingRemoveJoin/{trainingId}", method = RequestMethod.POST)
	public String removeJoin(@PathVariable Long trainingId,
			RedirectAttributes ra, Principal principal) {

		Long accountId = accountService.findByLogin(principal.getName())
				.getId();
		Training training = trainingService.findById(trainingId);

		try {
			trainingService.unsubscribeInscription(accountId, trainingId);
			MessageHelper.addErrorAttribute(ra, "training.removeJoin",
					training.getName());
		} catch (UnsubscribeException e) {
			MessageHelper.addErrorAttribute(ra,
					"training.unsubscribeInscriptionException", training.getName());
		}

		return "redirect:/trainingList";
	}
}
