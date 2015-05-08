package org.cuacfm.members.web.program;

import java.security.Principal;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.ExistPaymentsException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class ProgramListController. */
@Controller
public class ProgramListController {

   /** The Constant PROGRAM_VIEW_NAME. */
   private static final String PROGRAM_VIEW_NAME = "program/programlist";

   /** The Constant REDIRECT_PROGRAM. */
   private static final String REDIRECT_PROGRAM = "redirect:/programList";

   /** The ProgramService. */
   @Autowired
   private ProgramService programService;

   /** The training service. */
   @Autowired
   private AccountService accountService;

   /** The programs. */
   private List<Program> programs;

   /**
    * Instantiates a new training Controller.
    */
   public ProgramListController() {
      // Default empty constructor.
   }

   /**
    * Show Program List.
    *
    * @param model
    *           the model
    * @param principal
    *           the principal
    * @return the string the view
    * @throws UniqueException
    *            the unique exception
    */

   @RequestMapping(value = "programList")
   public String programs(Model model, Principal principal) throws UniqueException {
      Account account = accountService.findByLogin(principal.getName());
      // List of programs
      if (account.getRole() == roles.ROLE_ADMIN) {
         programs = programService.getProgramList();
      } else {
         programs = account.getPrograms();
      }
      model.addAttribute("programs", programs);
      return PROGRAM_VIEW_NAME;
   }

   /**
    * List of Program.
    *
    * @return List<Program>
    */
   @ModelAttribute("programs")
   public List<Program> programs() {
      return programs;
   }

   /**
    * Delete Program by Id.
    *
    * @param id
    *           the id
    * @param ra
    *           the redirect atributes
    * @param principal
    *           the principal
    * @return the string destinity page
    */
   @RequestMapping(value = "programList/programDelete/{id}", method = RequestMethod.POST)
   public String remove(@PathVariable Long id, RedirectAttributes ra, Principal principal) {

      Account account = accountService.findByLogin(principal.getName());
      Program program = programService.findById(id);

      // Security, Only user can delete him program or admin
      if (program.getAccounts().contains(account) || (account.getRole() == roles.ROLE_ADMIN)) {
         try {
            programService.delete(id);
            MessageHelper.addInfoAttribute(ra, "program.successDelete", program.getName());
         } catch (ExistPaymentsException e) {
            MessageHelper.addErrorAttribute(ra, "program.existPayments", program.getName());
         }
      }
      return REDIRECT_PROGRAM;
   }

   /**
    * Program down.
    *
    * @param id
    *           the id
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "programList/programDown/{id}", method = RequestMethod.POST)
   public String programDown(@PathVariable Long id, RedirectAttributes ra) {

      String name = programService.findById(id).getName();
      programService.down(id);
      MessageHelper.addInfoAttribute(ra, "program.successDown", name);
      return REDIRECT_PROGRAM;
   }

   /**
    * Program up.
    *
    * @param id
    *           the id
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "programList/programUp/{id}", method = RequestMethod.POST)
   public String programUp(@PathVariable Long id, RedirectAttributes ra) {

      String name = programService.findById(id).getName();
      programService.up(id);
      MessageHelper.addInfoAttribute(ra, "program.successUp", name);
      return REDIRECT_PROGRAM;
   }
}
