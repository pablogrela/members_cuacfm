package org.cuacfm.members.web.userpayinscription;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.payinscription.PayInscription;
import org.cuacfm.members.model.payinscriptionservice.PayInscriptionService;
import org.cuacfm.members.model.userpayinscription.UserPayInscription;
import org.cuacfm.members.model.userpayinscriptionservice.UserPayInscriptionService;
import org.cuacfm.members.web.support.DisplayDate;
import org.cuacfm.members.web.support.MessageHelper;
import org.cuacfm.members.web.training.FindUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class UserPayInscriptionListController. */
@Controller
public class UserPayInscriptionListController {

   /** The Constant USERPAYINSCRIPTION_VIEW_NAME. */
   private static final String USERPAYINSCRIPTION_VIEW_NAME = "userpayinscription/userpayinscriptionlist";

   /** The Constant REDIRECT_USERPAYINSCRIPTION. */
   private static final String REDIRECT_USERPAYINSCRIPTION = "redirect:/payInscriptionList/userPayInscriptionList";
   
   /** The Constant NOPAY. */
   private static final String NOPAY = "NOPAY";
   
   /** The Constant PAY. */
   private static final String PAY = "PAY";
   
   /** The message source. */
   @Autowired
   private MessageSource messageSource;

   /** The account service. */
   @Autowired
   private AccountService accountService;

   /** The PayInscriptionService. */
   @Autowired
   private PayInscriptionService payInscriptionService;

   /** The UserPayInscriptionService. */
   @Autowired
   private UserPayInscriptionService userPayInscriptionService;

   /** The pathform. */
   private PathForm pathform;

   /** The find userform. */
   private FindUserForm findUserform;

   /** The payInscription. */
   private PayInscription payInscription;

   /** The payInscriptions. */
   private static List<UserPayInscription> userPayInscriptions;

   /** The nameUsers. */
   private List<String> usernames;

   /**
    * Instantiates a new training Controller.
    */
   public UserPayInscriptionListController() {
      // Default empty constructor.
   }

   /**
    * Pay inscription.
    *
    * @return the pay inscription
    */
   @ModelAttribute("payInscription")
   public PayInscription payInscription() {
      return payInscription;
   }

   /**
    * List of UserPayInscription.
    *
    * @return List<UserPayInscription>
    */
   @ModelAttribute("userPayInscriptions")
   public List<UserPayInscription> userPayInscriptions() {
      return userPayInscriptions;
   }

   /**
    * Gets the path information.
    *
    * @param model
    *           the model
    * @return the path information
    */
   private String getPathInformation(Model model) {
      model.addAttribute(pathform);
      model.addAttribute("usernames", usernames);
      return USERPAYINSCRIPTION_VIEW_NAME;
   }

   /**
    * Gets the find user information.
    *
    * @param model
    *           the model
    * @return the find user information
    */
   private String getFindUserInformation(Model model) {
      model.addAttribute(findUserform);
      model.addAttribute("usernames", usernames);
      return USERPAYINSCRIPTION_VIEW_NAME;
   }

   /**
    * User pay inscriptions.
    *
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "payInscriptionList/userPayInscriptionList")
   public String getUserPayInscriptions(Model model) {
      if (payInscription != null) {

         pathform = new PathForm();
         String pathDefault = messageSource.getMessage("pathDefault", null, Locale.getDefault());
         pathform.setPath(pathDefault);
         Date date = new Date();
         String feeMemberFile = messageSource
               .getMessage("feeMemberFile", null, Locale.getDefault());
         pathform.setFile(feeMemberFile + " " + DisplayDate.dateTimeToStringSp(date));
         model.addAttribute(pathform);

         findUserform = new FindUserForm();
         model.addAttribute(findUserform);
         userPayInscriptions = userPayInscriptionService
               .getUserPayInscriptionListByPayInscriptionId(payInscription.getId());
         model.addAttribute("userPayInscriptions", userPayInscriptions);
         usernames = userPayInscriptionService.getUsernamesByPayInscription(payInscription.getId());
         model.addAttribute("usernames", usernames);

         return USERPAYINSCRIPTION_VIEW_NAME;
      } else {
         return "redirect:/payInscriptionList";
      }
   }

   /**
    * View user pay inscriptions by pay inscription id.
    *
    * @param payInscriptionId
    *           the pay inscription id
    * @return the string
    */
   @RequestMapping(value = "payInscriptionList/userPayInscriptionList/{payInscriptionId}", method = RequestMethod.POST)
   public String viewUserPayInscriptionsByPayInscriptionId(@PathVariable Long payInscriptionId) {
      payInscription = payInscriptionService.findById(payInscriptionId);
      return "redirect:/payInscriptionList/userPayInscriptionList";
   }

   /**
    * Join account to UserPayInscription.
    *
    * @param findUserForm
    *           the find user form
    * @param errors
    *           the errors
    * @param ra
    *           the redirect atributes
    * @param model
    *           the model
    * @return the string destinity page to page trainingList
    */
   @RequestMapping(value = "payInscriptionList/userPayInscriptionList", method = RequestMethod.POST)
   public String addUserToUserPayInscriptionList(@Valid @ModelAttribute FindUserForm findUserForm,
         Errors errors, RedirectAttributes ra, Model model) {

      if (errors.hasErrors()) {
         return getPathInformation(model);
      }

      String name = findUserForm.getLogin();
      Long id = Long.valueOf(0);
      if (name.contains(": ")) {
         String[] parts = findUserForm.getLogin().split(": ");
         id = Long.valueOf(parts[0]);
         name = parts[1].split(" - ")[0].trim();
      }

      // Check if account exist
      Account account = accountService.findById(id);
      if (account == null) {
         errors.rejectValue("login", "inscription.noExistLogin", new Object[] { name }, "login");
         return getPathInformation(model);
      }

      // Check if account already userPayInscription
      List<UserPayInscription> userPayInscriptionsSearched = userPayInscriptionService
            .findByUserPayInscriptionIds(account.getId(), payInscription.getId());
      if (!userPayInscriptionsSearched.isEmpty()) {
         errors.rejectValue("login", "userPayInscription.alreadyExistLogin", new Object[] { name },
               "login");
         return getPathInformation(model);
      }

      payInscriptionService.saveUserPayInscription(account, payInscription);
      MessageHelper.addSuccessAttribute(ra, "userPayInscription.successJoin", name);
      return REDIRECT_USERPAYINSCRIPTION;
   }

   /**
    * Pay bill.
    *
    * @param userPayInscriptionId
    *           the user pay inscription id
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "payInscriptionList/userPayInscriptionList/pay/{userPayInscriptionId}", method = RequestMethod.POST)
   public String pay(@PathVariable Long userPayInscriptionId, RedirectAttributes ra) {
      UserPayInscription userPayInscription = userPayInscriptionService
            .findById(userPayInscriptionId);
      userPayInscriptionService.pay(userPayInscription);

      MessageHelper.addSuccessAttribute(ra, "userPayInscription.successPay", userPayInscription
            .getAccount().getName());
      return REDIRECT_USERPAYINSCRIPTION;
   }

   /**
    * GeneratePdf.
    *
    * @param pathForm
    *           the path form
    * @param errors
    *           the errors
    * @param createPdf
    *           the create pdf
    * @param ra
    *           the ra
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "payInscriptionList/userPayInscriptionList", method = RequestMethod.POST, params = { "createPdf" })
   public String createPdf(@Valid @ModelAttribute PathForm pathForm, Errors errors,
         @RequestParam("createPdf") String createPdf, RedirectAttributes ra, Model model) {

      if (errors.hasErrors()) {
         getFindUserInformation(model);
         return USERPAYINSCRIPTION_VIEW_NAME;
      }

      String title;
      String path = pathForm.getPath() + "/" + pathForm.getFile() + ".pdf";

      if (createPdf.equals(PAY)) {
         title = payInscription.getName() + " - "
               + messageSource.getMessage("payinscription.printPayList", null, Locale.getDefault());
         MessageHelper.addSuccessAttribute(ra, "payInscription.successPrintPayList",
               payInscription.getName());
      } else if (createPdf.equals(NOPAY)) {
         title = payInscription.getName()
               + " - "
               + messageSource.getMessage("payinscription.printNoPayList", null,
                     Locale.getDefault());
         MessageHelper.addSuccessAttribute(ra, "payInscription.successPrintNoPayList",
               payInscription.getName());
      } else {
         title = payInscription.getName() + " - "
               + messageSource.getMessage("payinscription.printAllList", null, Locale.getDefault());
         MessageHelper.addSuccessAttribute(ra, "payInscription.successPrintAllList",
               payInscription.getName());
      }

      userPayInscriptionService.createPdfPayInscription(messageSource, payInscription.getId(),
            path, title, createPdf);
      return REDIRECT_USERPAYINSCRIPTION;
   }

}
