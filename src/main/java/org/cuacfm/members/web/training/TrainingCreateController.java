package org.cuacfm.members.web.training;

import javax.validation.Valid;

import org.cuacfm.members.model.exceptions.DateLimitException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.trainingService.TrainingService;
import org.cuacfm.members.model.trainingType.TrainingType;
import org.cuacfm.members.model.trainingTypeService.TrainingTypeService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class TrainingCreateController. */
@Controller
public class TrainingCreateController {

	/** The Constant TRAINING_VIEW_NAME. */
	private static final String TRAINING_VIEW_NAME = "training/trainingcreate";

	/** The trainingService. */
	@Autowired
	private TrainingService trainingService;

	/** The trainingService. */
	@Autowired
	private TrainingTypeService trainingTypeService;

	/** The Global variable training Type. */
	private TrainingType trainingType;

	/**
	 * Instantiates a new training Controller.
	 */
	public TrainingCreateController() {
		// Default empty constructor.
	}

	/**
	 * Training.
	 *
	 * @return Training
	 */
	@ModelAttribute("trainingType")
	public TrainingType trainingType() {
		return trainingType;
	}

	/**
	 * Get page trainingCreate.
	 *
	 * @param model
	 *            the model
	 * @return String to view to trainingCreate
	 */
	@RequestMapping(value = "trainingList/trainingCreate")
	public String training(Model model) {

		if (trainingType != null) {
			TrainingForm trainingForm = new TrainingForm();
			trainingForm.setName(trainingType.getName());
			trainingForm.setDescription(trainingType.getDescription());
			trainingForm.setPlace(trainingType.getPlace());
			trainingForm.setDuration(trainingType.getDuration());
			model.addAttribute(trainingForm);
			return TRAINING_VIEW_NAME;
		}
		// If not have trainingType, redirect to trainingList
		else {
			return "redirect:/trainingList";
		}
	}

	/**
	 * Add trainingType at form
	 *
	 * @param trainingSelectForm
	 *            the training select form
	 * @param ra
	 *            the ra
	 * @return String to redirect to trainingCreate
	 */
	@RequestMapping(value = "trainingList/trainingLoad", method = RequestMethod.POST)
	public String addTrainingType(
			@ModelAttribute TrainingSelectForm trainingSelectForm,
			RedirectAttributes ra) {

		trainingType = trainingTypeService.findById(trainingSelectForm
				.getTrainingTypeId());
		return "redirect:/trainingList/trainingCreate";
	}

	/**
	 * Post to create a new training.
	 *
	 * @param trainingForm
	 *            the training form
	 * @param errors
	 *            the errors
	 * @param ra
	 *            the ra
	 * @return String to redirect to trainingList or if fault trainingCreate
	 * @throws UniqueException 
	 * 
	 * @throws DateLimitException 
	 */
	@RequestMapping(value = "trainingList/trainingCreate", method = RequestMethod.POST)
	public String trainingCreate(
			@Valid @ModelAttribute TrainingForm trainingForm, Errors errors,
			RedirectAttributes ra) throws UniqueException {

		if (errors.hasErrors()) {
			return TRAINING_VIEW_NAME;
		}

		try {
			trainingService.save(trainingForm.createTraining(trainingType));
		} catch (DateLimitException  e) {
			errors.rejectValue("dateLimit", "dateLimit.message",
					new Object[] { e.getDateTraining() }, "dateTraining");
			return TRAINING_VIEW_NAME;
		}
		
		MessageHelper.addSuccessAttribute(ra, "training.successCreate",
				trainingForm.getName());
		return "redirect:/trainingList";
	}
}
