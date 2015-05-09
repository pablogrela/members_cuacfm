package org.cuacfm.members.web.feeprogram;

import java.util.List;

import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/** The Class FeeProgramListController. */
@Controller
public class FeeProgramListController {

   /** The Constant FEEPROGRAM_VIEW_NAME. */
   private static final String FEEPROGRAM_VIEW_NAME = "feeprogram/feeprogramlist";

   /** The FeeProgramService. */
   @Autowired
   private FeeProgramService feeProgramService;

   /** The feePrograms. */
   private List<FeeProgram> feePrograms;

   /**
    * Instantiates a new training Controller.
    */
   public FeeProgramListController() {
      // Default empty constructor.
   }

   /**
    * List of FeeProgram.
    *
    * @return List<FeeProgram>
    */
   @ModelAttribute("feePrograms")
   public List<FeeProgram> feePrograms() {
      return feePrograms;
   }

   /**
    * Show FeeProgram List.
    *
    * @param model
    *           the model
    * @return the string the view
    */

   @RequestMapping(value = "feeProgramList")
   public String feePrograms(Model model) {
      feePrograms = feeProgramService.getFeeProgramList();
      model.addAttribute("feePrograms", feePrograms);
      return FEEPROGRAM_VIEW_NAME;
   }

}
