package org.cuacfm.members.web.feemember;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.cuacfm.members.web.support.DisplayDate;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class FeeMemberCreateController. */
@Controller
public class FeeMemberCreateController {

   /** The Constant FEEMEMBER_VIEW_NAME. */
   private static final String FEEMEMBER_VIEW_NAME = "feemember/feemembercreate";

   /** The message source. */
   @Autowired
   private MessageSource messageSource;

   /** The ConfigurationService. */
   @Autowired
   private ConfigurationService configurationService;

   /** The fee member service. */
   @Autowired
   private FeeMemberService feeMemberService;

   /**
    * Instantiates a new fee member Controller.
    */
   public FeeMemberCreateController() {
      // Default empty constructor.
   }

   /**
    * Training.
    *
    * @param model
    *           the model
    * @return the string
    */
   @SuppressWarnings("deprecation")
   @RequestMapping(value = "feeMemberList/feeMemberCreate")
   public String feeMember(Model model) {

      FeeMemberForm feeMemberForm = new FeeMemberForm();

      String feeProgramFile = messageSource.getMessage("feeMemberName", null, Locale.getDefault());
      feeMemberForm.setName(feeProgramFile + " " + LocalDate.now().getYear());
      feeMemberForm.setPrice(configurationService.getConfiguration().getFeeMember());
      feeMemberForm.setDescription(feeProgramFile + " " + LocalDate.now().getYear());
      feeMemberForm.setYear(LocalDate.now().getYear());
      Date dateLimit = new Date();
      dateLimit.setMonth(2);
      String monthLimit = DisplayDate.monthOfYearToString(dateLimit);
      feeMemberForm.setDateLimit1(monthLimit);
      dateLimit.setMonth(8);
      monthLimit = DisplayDate.monthOfYearToString(dateLimit);
      feeMemberForm.setDateLimit2(monthLimit);
      model.addAttribute(feeMemberForm);

      return FEEMEMBER_VIEW_NAME;
   }

   /**
    * Fee member.
    *
    * @param feeMemberForm
    *           the fee member form
    * @param errors
    *           the errors
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "feeMemberList/feeMemberCreate", method = RequestMethod.POST)
   public String feeMember(@Valid @ModelAttribute FeeMemberForm feeMemberForm, Errors errors,
         RedirectAttributes ra) {

      if (errors.hasErrors()) {
         return FEEMEMBER_VIEW_NAME;
      }

      int year = feeMemberForm.getYear();
      String name = feeMemberForm.getName();
      try {
         feeMemberService.save(feeMemberForm.createFeeMember());
         // It is verified that there is not exist year of feeMember in
         // other feeMember
      } catch (UniqueException e) {
         errors.rejectValue("year", "feeMember.yearException", new Object[] { year }, "year");
      }

      if (errors.hasErrors()) {
         return FEEMEMBER_VIEW_NAME;
      }

      MessageHelper.addSuccessAttribute(ra, "feeMember.successCreate", name);
      return "redirect:/feeMemberList";
   }

}
