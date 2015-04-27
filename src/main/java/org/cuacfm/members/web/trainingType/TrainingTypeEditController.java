package org.cuacfm.members.web.trainingType;

import javax.validation.Valid;

import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.trainingType.TrainingType;
import org.cuacfm.members.model.trainingTypeService.TrainingTypeService;
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

/** The Class TrainingTypeEditController. */
@Controller
public class TrainingTypeEditController {

	/** The Constant TRAINING_VIEW_NAME. */
	private static final String TRAINING_VIEW_NAME = "trainingtype/trainingtypedit";

	/** The training service. */
	@Autowired
	private TrainingTypeService trainingTypeService;

	/** The Global variable trainingType. */
	private TrainingType trainingType;

	/**
	 * Instantiates a new training Controller.
	 */
	public TrainingTypeEditController() {
		// Default empty constructor.
	}

	/**
	 * Training.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "trainingTypeList/trainingTypeEdit")
	public String training(Model model) {

		if (trainingType != null) {
			TrainingTypeForm trainingTypeForm = new TrainingTypeForm();
			trainingTypeForm.setName(trainingType.getName());
			trainingTypeForm.setRequired(trainingType.isRequired());
			trainingTypeForm.setDescription(trainingType.getDescription());
			trainingTypeForm.setPlace(trainingType.getPlace());
			trainingTypeForm.setDuration(trainingType.getDuration());
			model.addAttribute(trainingType);
			model.addAttribute(trainingTypeForm);
			return TRAINING_VIEW_NAME;
		}
		// If not have trainingType, redirect to trainingList
		else {
			return "redirect:/trainingList";
		}
	}

	/**
	 * TrainingType.
	 *
	 * @return TrainingType
	 */
	@ModelAttribute("trainingType")
	public TrainingType trainingType() {
		return trainingType;
	}

	/**
	 * Training.
	 *
	 * @param trainingFormType
	 *            the training type form
	 * @param errors
	 *            the errors
	 * @param ra
	 *            the ra
	 * @return the string
	 */

	@RequestMapping(value = "trainingTypeList/trainingTypeEdit", method = RequestMethod.POST)
	public String training(
			@Valid @ModelAttribute TrainingTypeForm trainingTypeForm,
			Errors errors, RedirectAttributes ra, Model model) {

		if (errors.hasErrors()) {
			return TRAINING_VIEW_NAME;
		}

		try {
			trainingTypeService.update(trainingTypeForm
					.updateTrainingType(trainingType));
		} catch (UniqueException e) {
			errors.rejectValue("name", "trainingType.existentName",
					new Object[] { e.getValue() }, "name");
			return TRAINING_VIEW_NAME;
		}

		MessageHelper.addWarningAttribute(ra, "trainingType.successModify",
				trainingTypeForm.getName());
		return "redirect:/trainingTypeList";
	}

	/**
	 * Modify TrainingType by Id.
	 *
	 * @param @PathVariable Long id
	 * 
	 * @param errors
	 *            the errors
	 * @param ra
	 *            the redirect atributes
	 * @return the string destinity page
	 */
	@RequestMapping(value = "trainingTypeList/trainingTypeEdit/{id}", method = RequestMethod.POST)
	public String modifyTraining(@PathVariable Long id, RedirectAttributes ra) {

		trainingType = trainingTypeService.findById(id);
		return "redirect:/trainingTypeList/trainingTypeEdit";
	}
}
