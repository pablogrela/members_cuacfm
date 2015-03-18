package org.cuacfm.members.web.training;

import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingService.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class TrainingViewController. */
//@Secured("ROLE_ADMIN")
@Controller
public class TrainingViewController {

	/** The Constant TRAINING_VIEW_NAME. */
	private static final String TRAINING_VIEW_NAME = "training/trainingview";

	/** The trainingService. */
	@Autowired
	private TrainingService trainingService;

	/** The Global variable training. */
	Training training;

	/**
	 * Instantiates a new training Controller.
	 */
	public TrainingViewController() {
		// Default empty constructor.
	}

	/**
	 * Training.
	 *
	 * @param model
	 *            the model
	 * @return the string url
	 */
	@RequestMapping(value = "trainingList/trainingView")
	public String training(Model model) {
		if (training != null) {
			model.addAttribute(training);
			return TRAINING_VIEW_NAME;
		}
		// If not have training, redirect to trainingList
		else {
			return "redirect:/trainingList";
		}
	}

	/**
	 * Training.
	 *
	 * @return Training
	 */
	@ModelAttribute("training")
	public Training training() {
		return training;
	}

	/**
	 * Modify Training by Id.
	 *
	 * @param @PathVariable Long id
	 * 
	 * @param ra
	 *            the redirect atributes
	 * @return the string destinity page
	 */
	@RequestMapping(value = "trainingList/trainingView/{id}", method = RequestMethod.POST)
	public String modifyTraining(@PathVariable Long id, RedirectAttributes ra) {

		training = trainingService.findById(id);
		return "redirect:/trainingList/trainingView";
	}
}
