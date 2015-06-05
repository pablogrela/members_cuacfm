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
    * Instantiates a new program edit controller.
    */
   public ProgramEditController() {
      // Default empty constructor.
   }

   /**
    * Program form.
    *
    * @return the program form
    */
   @ModelAttribute("programForm")
   public ProgramForm programForm() {
      return programForm;
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
         if (program.getAccountPayer() != null) {
            programForm.setAccountPayer(program.getAccountPayer());
            programForm.setAccountPayerName(program.getAccountPayer().getName());
         }
         model.addAttribute(programForm);
         return PROGRAM_VIEW_NAME;
      }
      // If not have program, redirect to programList
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
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "programList/programEdit", method = RequestMethod.POST, params = { "edit" })
   public String program(@Valid @ModelAttribute ProgramForm programForm, Errors errors,
         RedirectAttributes ra, Model model) {

      if (errors.hasErrors()) {
         model.addAttribute("usernames", usernames);
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
    * Adds the user.
    *
    * @param programForm
    *           the program form
    * @param errors
    *           the errors
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "programList/programEdit", method = RequestMethod.POST, params = { "addUser" })
   public String addUser(@Valid @ModelAttribute ProgramForm programForm, Errors errors, Model model) {

      model.addAttribute("usernames", usernames);
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
      programForm.setLogin("");
      programForm.addAccount(account);
      return PROGRAM_VIEW_NAME;
   }

   /**
    * Removes the user.
    *
    * @param id
    *           the id
    * @param programForm
    *           the program form
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "programList/programEdit", method = RequestMethod.POST, params = { "removeUser" })
   public String removeUser(@RequestParam("removeUser") Long id,
         @Valid @ModelAttribute ProgramForm programForm, Model model) {
      model.addAttribute("usernames", usernames);
      programForm.removeAccount(id);
      return PROGRAM_VIEW_NAME;
   }

   /**
    * Adds the account payer.
    *
    * @param programForm
    *           the program form
    * @param errors
    *           the errors
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "programList/programEdit", method = RequestMethod.POST, params = { "addAccountPayer" })
   public String addAccountPayer(@Valid @ModelAttribute ProgramForm programForm, Errors errors,
         Model model) {

      model.addAttribute("usernames", usernames);
      Account account;
      String name = programForm.getAccountPayerName();
      Long id = Long.valueOf(0);
      if (name.contains(": ")) {
         String[] parts = programForm.getAccountPayerName().split(": ");
         id = Long.valueOf(parts[0]);
         name = parts[1].split(" - ")[0].trim();
         account = accountService.findById(id);
      } else {
         account = accountService.findByDni(name);
      }

      if (account == null) {
         errors.rejectValue("accountPayerName", "program.noExistUser", new Object[] { name },
               "accountPayerName");

         return PROGRAM_VIEW_NAME;
      }
      programForm.setAccountPayerName(account.getName());
      programForm.setAccountPayer(account);
      return PROGRAM_VIEW_NAME;
   }

   /**
    * Removes the account payer.
    *
    * @param programForm
    *           the program form
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "programList/programEdit", method = RequestMethod.POST, params = { "removeAccountPayer" })
   public String removeAccountPayer(@Valid @ModelAttribute ProgramForm programForm, Model model) {
      model.addAttribute("usernames", usernames);
      programForm.setAccountPayer(null);
      programForm.setAccountPayerName("");
      return PROGRAM_VIEW_NAME;
   }

   /**
    * Modify program.
    *
    * @param id
    *           the id
    * @return the string
    */
   @RequestMapping(value = "programList/programEdit/{id}", method = RequestMethod.POST)
   public String modifyProgram(@PathVariable Long id) {

      program = programService.findById(id);
      return "redirect:/programList/programEdit";
   }
}
