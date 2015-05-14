package org.cuacfm.members.web.paymember;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.cuacfm.members.web.support.DisplayDate;
import org.cuacfm.members.web.support.MessageHelper;
import org.cuacfm.members.web.support.PathForm;
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

/** The Class PayMemberListController. */
@Controller
public class PayMemberListController {

   /** The Constant PAYMEMBER_VIEW_NAME. */
   private static final String PAYMEMBER_VIEW_NAME = "paymember/paymemberlist";

   /** The Constant REDIRECT_PAYMEMBER. */
   private static final String REDIRECT_PAYMEMBER = "redirect:/feeMemberList/payMemberList";
   
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

   /** The FeeMemberService. */
   @Autowired
   private FeeMemberService feeMemberService;

   /** The PayMemberService. */
   @Autowired
   private PayMemberService payMemberService;

   /** The pathform. */
   private PathForm pathform;

   /** The find userform. */
   private FindUserForm findUserform;

   /** The feeMember. */
   private FeeMember feeMember;

   /** The feeMembers. */
   private static List<PayMember> payMembers;

   /** The nameUsers. */
   private List<String> usernames;

   /**
    * Instantiates a new training Controller.
    */
   public PayMemberListController() {
      // Default empty constructor.
   }

   /**
    * fee member.
    *
    * @return the fee member
    */
   @ModelAttribute("feeMember")
   public FeeMember feeMember() {
      return feeMember;
   }

   /**
    * List of PayMember.
    *
    * @return List<PayMember>
    */
   @ModelAttribute("payMembers")
   public List<PayMember> payMembers() {
      return payMembers;
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
      return PAYMEMBER_VIEW_NAME;
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
      return PAYMEMBER_VIEW_NAME;
   }

   /**
    * User fee members.
    *
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "feeMemberList/payMemberList")
   public String getPayMembers(Model model) {
      if (feeMember != null) {

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
         payMembers = payMemberService
               .getPayMemberListByFeeMemberId(feeMember.getId());
         model.addAttribute("payMembers", payMembers);
         usernames = payMemberService.getUsernamesByFeeMember(feeMember.getId());
         model.addAttribute("usernames", usernames);

         return PAYMEMBER_VIEW_NAME;
      } else {
         return "redirect:/feeMemberList";
      }
   }

   /**
    * View user fee members by fee member id.
    *
    * @param feeMemberId
    *           the fee member id
    * @return the string
    */
   @RequestMapping(value = "feeMemberList/payMemberList/{feeMemberId}", method = RequestMethod.POST)
   public String viewPayMembersByFeeMemberId(@PathVariable Long feeMemberId) {
      feeMember = feeMemberService.findById(feeMemberId);
      return "redirect:/feeMemberList/payMemberList";
   }

   /**
    * Join account to PayMember.
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
   @RequestMapping(value = "feeMemberList/payMemberList", method = RequestMethod.POST)
   public String addUserToPayMemberList(@Valid @ModelAttribute FindUserForm findUserForm,
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

      // Check if account already payMember
      List<PayMember> payMembersSearched = payMemberService
            .findByPayMemberIds(account.getId(), feeMember.getId());
      if (!payMembersSearched.isEmpty()) {
         errors.rejectValue("login", "payMember.alreadyExistLogin", new Object[] { name },
               "login");
         return getPathInformation(model);
      }

      feeMemberService.savePayMember(account, feeMember);
      MessageHelper.addSuccessAttribute(ra, "payMember.successJoin", name);
      return REDIRECT_PAYMEMBER;
   }

   /**
    * Pay bill.
    *
    * @param payMemberId
    *           the user fee member id
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "feeMemberList/payMemberList/pay/{payMemberId}", method = RequestMethod.POST)
   public String pay(@PathVariable Long payMemberId, RedirectAttributes ra) {
      PayMember payMember = payMemberService
            .findById(payMemberId);
      payMemberService.pay(payMember);

      MessageHelper.addSuccessAttribute(ra, "payMember.successPay", payMember
            .getAccount().getName());
      return REDIRECT_PAYMEMBER;
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
   @RequestMapping(value = "feeMemberList/payMemberList", method = RequestMethod.POST, params = { "createPdf" })
   public String createPdf(@Valid @ModelAttribute PathForm pathForm, Errors errors,
         @RequestParam("createPdf") String createPdf, RedirectAttributes ra, Model model) {

      if (errors.hasErrors()) {
         getFindUserInformation(model);
         return PAYMEMBER_VIEW_NAME;
      }

      String title;
      String path = pathForm.getPath() + "/" + pathForm.getFile() + ".pdf";

      if (createPdf.equals(PAY)) {
         title = feeMember.getName() + " - "
               + messageSource.getMessage("feeMember.printPayList", null, Locale.getDefault());
         MessageHelper.addSuccessAttribute(ra, "feeMember.successPrintPayList",
               feeMember.getName());
      } else if (createPdf.equals(NOPAY)) {
         title = feeMember.getName()
               + " - "
               + messageSource.getMessage("feeMember.printNoPayList", null,
                     Locale.getDefault());
         MessageHelper.addSuccessAttribute(ra, "feeMember.successPrintNoPayList",
               feeMember.getName());
      } else {
         title = feeMember.getName() + " - "
               + messageSource.getMessage("feeMember.printAllList", null, Locale.getDefault());
         MessageHelper.addSuccessAttribute(ra, "feeMember.successPrintAllList",
               feeMember.getName());
      }

      payMemberService.createPdfFeeMember(messageSource, feeMember.getId(),
            path, title, createPdf);
      return REDIRECT_PAYMEMBER;
   }

}
