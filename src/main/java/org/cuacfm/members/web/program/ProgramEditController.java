package org.cuacfm.members.web.program;

import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class ProgramEditController. */
@Controller
public class ProgramEditController {

   /** The Constant PROGRAM_VIEW_NAME. */
   private static final String PROGRAM_VIEW_NAME = "program/programedit";

   /** The training service. */
   @Autowired
   private ProgramService programService;

   /** The training service. */
   @Autowired
   private AccountService accountService;

   /** The program. */
   private Program program;

   /** The usernames. */
   private List<String> usernames;

   /** The program form. */
   private ProgramForm programForm;

   /**
    * Instantiates a new programController.
    */
   public ProgramEditController() {
      // Default empty constructor.
   }

   /**
    * Accounts.
    *
    * @return the list
    */
   @ModelAttribute("programForm")
   public ProgramForm programForm() {
      return programForm;
   }

   /**
    * Usernames.
    *
    * @return the list
    */
   @ModelAttribute("usernames")
   public List<String> usernames() {
      return usernames;
   }

   /**
    * Program.
    *
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "programList/programEdit")
   public String program(Model model) {

      if (program != null) {
         usernames = accountService.getUsernames();
         model.addAttribute("usernames", usernames);

         programForm = new ProgramForm();
         programForm.setName(program.getName());
         programForm.setDescription(program.getDescription());
         programForm.setDuration(program.getDuration());
         programForm.setPeriodicity(program.getPeriodicity());
         programForm.setAccounts(program.getAccounts());
         model.addAttribute(programForm);
         return PROGRAM_VIEW_NAME;
      }
      // If not have trainingType, redirect to trainingList
      else {
         return "redirect:/programList";
      }
   }

   /**
    * Program.
    *
    * @param programForm
    *           the program form
    * @param errors
    *           the errors
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "programList/programEdit", method = RequestMethod.POST, params = { "edit" })
   public String program(@Valid @ModelAttribute ProgramForm programForm, Errors errors,
         RedirectAttributes ra) {

      if (errors.hasErrors()) {
         return PROGRAM_VIEW_NAME;
      }

      try {
         programService.update(programForm.updateProgram(program));
      } catch (UniqueException e) {
         errors.rejectValue("name", "program.existentName", new Object[] { e.getValue() }, "name");
         return PROGRAM_VIEW_NAME;
      }

      MessageHelper.addSuccessAttribute(ra, "program.successEdit", programForm.getName());
      return "redirect:/programList";
   }

   /**
    * Join account to training by trainingId.
    *
    * @param programForm
    *           the program form
    * @param errors
    *           the errors
    * @param ra
    *           the redirect atributes
    * @param model
    *           the model
    * @return the string destinity page to page trainingList
    */
   @RequestMapping(value = "programList/programEdit", method = RequestMethod.POST, params = { "addUser" })
   public String addUser(@Valid @ModelAttribute ProgramForm programForm, Errors errors) {

      Account account;
      String name = programForm.getLogin();
      Long id = Long.valueOf(0);
      if (name.contains(": ")) {
         String[] parts = programForm.getLogin().split(": ");
         id = Long.valueOf(parts[0]);
         name = parts[1].split(" - ")[0].trim();
         account = accountService.findById(id);
      } else {
         account = accountService.findByDni(name);
      }

      if (account == null) {
         errors.rejectValue("login", "program.noExistUser", new Object[] { name }, "login");
         return PROGRAM_VIEW_NAME;
      }

      // Check if account already insert
      boolean repeated = false;
      for (Account acc : programForm.getAccounts()) {
         if (acc.getId() == account.getId()) {
            repeated = true;
         }
      }

      if (repeated) {
         errors.rejectValue("login", "program.alreadyExistUser", new Object[] { name }, "login");
         return PROGRAM_VIEW_NAME;
      }

      usernames.remove(name);
      programForm.addAccount(account);
      return PROGRAM_VIEW_NAME;
   }

   /**
    * Delete Account by Id.
    *
    * @param id
    *           the id
    * @param programForm
    *           the program form
    * @param errors
    *           the errors
    * @param ra
    *           the redirect atributes
    * @param model
    *           the model
    * @return the string destinity page
    */
   @RequestMapping(value = "programList/programEdit", method = RequestMethod.POST, params = { "removeUser" })
   public String removeUser(@RequestParam("removeUser") Long id,
         @Valid @ModelAttribute ProgramForm programForm) {

      programForm.removeAccount(id);
      return PROGRAM_VIEW_NAME;
   }

   @RequestMapping(value = "programList/programEdit/{id}", method = RequestMethod.POST)
   public String modifyProgram(@PathVariable Long id) {

      program = programService.findById(id);
      return "redirect:/programList/programEdit";
   }
}
