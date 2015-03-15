package org.cuacfm.members.web.trainingType;

import java.util.List;

import org.cuacfm.members.model.exceptions.ExistTrainingsException;
import org.cuacfm.members.model.trainingType.TrainingType;
import org.cuacfm.members.model.trainingTypeService.TrainingTypeService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class TrainingTypeListController. */
@Controller
public class TrainingTypeListController {

	/** The Constant TRAINING_VIEW_NAME. */
	private static final String TRAINING_VIEW_NAME = "trainingtype/trainingtypelist";

	/** The TrainingTypeService. */
	@Autowired
	private TrainingTypeService trainingTypeService;

	/** The trainingTypes. */
	private List<TrainingType> trainingTypes;

	/**
	 * Instantiates a new training Controller.
	 */
	public TrainingTypeListController() {
		// Default empty constructor.
	}

	/**
	 * Show TrainingType List.
	 *
	 * @param model
	 *            the model
	 * @return the string the view
	 */

	@RequestMapping(value = "trainingTypeList")
	public String trainings(Model model) {
		// List of Training Types
		trainingTypes = trainingTypeService.getTrainingTypeList();
		model.addAttribute("trainingTypes", trainingTypes);
		return TRAINING_VIEW_NAME;
	}

	/**
	 * List of TrainingType.
	 *
	 * @return List<TrainingType>
	 */
	@ModelAttribute("trainingTypes")
	public List<TrainingType> trainingTypes() {
		return trainingTypes;
	}

	/**
	 * Delete TrainingType by Id.
	 *
	 * @param @PathVariable Long id
	 * 
	 * @param ra
	 *            the redirect atributes
	 * @return the string destinity page
	 */
	@RequestMapping(value = "trainingTypeList/trainingTypeDelete/{id}", method = RequestMethod.POST)
	public String remove(@PathVariable Long id, RedirectAttributes ra) {

		String name = trainingTypeService.findById(id).getName();

		// If Exist Dependencies Trainings
		try {
			trainingTypeService.delete(id);
			MessageHelper.addInfoAttribute(ra, "trainingType.successDelete",
					name);
		} catch (ExistTrainingsException e) {
			MessageHelper.addErrorAttribute(ra,
					"trainingType.existDependenciesTrainings", name);
		}

		return "redirect:/trainingTypeList";
	}
}
