package org.cuacfm.members.web.configuration;

import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.configuration.Configuration;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
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

/** The Class ConfigurationController. */
@Controller
public class ConfigurationController {

   /** The Constant CONFIGURATION_VIEW_NAME. */
   private static final String CONFIGURATION_VIEW_NAME = "configuration/configuration";

   /** The Constant REDIRECT_CONFIGURATION. */
   private static final String REDIRECT_CONFIGURATION = "redirect:/configuration";

   /** The ConfigurationService. */
   @Autowired
   private ConfigurationService configurationService;

   /** The AccountTypeService. */
   @Autowired
   private AccountTypeService accountTypeService;

   /** The MethodPaymentService. */
   @Autowired
   private MethodPaymentService methodPaymentService;

   /** The configuration. */
   private Configuration configuration;

   /**
    * Configuration.
    *
    * @return the configuration
    */
   @ModelAttribute("configuration")
   public Configuration configuration() {
      return configuration;
   }

   /**
    * List of AccountType.
    *
    * @return List<AccountType>
    */
   @ModelAttribute("accountTypes")
   public List<AccountType> accountTypes() {
      return accountTypeService.getAccountTypes();
   }

   /**
    * List of MethodPayment.
    *
    * @return List<MethodPayment>
    */
   @ModelAttribute("methodPayments")
   public List<MethodPayment> methodPayments() {
      return methodPaymentService.getMethodPayments();
   }

   /**
    * Display Configuration.
    *
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "configuration")
   public String displayConfiguration(Model model) {
      configuration = configurationService.getConfiguration();
      ConfigurationForm configurationForm = new ConfigurationForm();
      configurationForm.setName(configuration.getName());
      configurationForm.setEmail(configuration.getEmail());
      configurationForm.setPhone(configuration.getPhone());
      configurationForm.setFeeMember(configuration.getFeeMember());
      configurationForm.setFeeProgram(configuration.getFeeProgram());
      configurationForm.setDescriptionRule(configuration.getDescriptionRule());
      model.addAttribute(configurationForm);
      return CONFIGURATION_VIEW_NAME;
   }

   /**
    * Post configuration.
    *
    * @param configurationForm
    *           the configuration form
    * @param errors
    *           the errors
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "configuration", method = RequestMethod.POST)
   public String postConfiguration(@Valid @ModelAttribute ConfigurationForm configurationForm,
         Errors errors, RedirectAttributes ra) {

      if (errors.hasErrors()) {
         return CONFIGURATION_VIEW_NAME;
      }

      configurationService.update(configurationForm.updateConfiguration(configuration));
      MessageHelper.addWarningAttribute(ra, "configuration.successModify",
            configurationForm.getName());
      return REDIRECT_CONFIGURATION;

   }

   /**
    * Removes the account type.
    *
    * @param ra
    *           the ra
    * @param id
    *           the id
    * @return the string
    */
   @RequestMapping(value = "configuration/accountTypeDelete/{id}", method = RequestMethod.POST)
   public String removeAccountType(RedirectAttributes ra, @PathVariable Long id) {
      String name = accountTypeService.findById(id).getName();
      accountTypeService.delete(id);
      MessageHelper.addInfoAttribute(ra, "accountType.successDelete", name);
      return REDIRECT_CONFIGURATION;
   }

   /**
    * Removw method payment.
    *
    * @param ra
    *           the ra
    * @param id
    *           the id
    * @return the string
    */
   @RequestMapping(value = "configuration/methodPaymentDelete/{id}", method = RequestMethod.POST)
   public String removeMethodPayment(RedirectAttributes ra, @PathVariable Long id) {
      String name = methodPaymentService.findById(id).getName();
      methodPaymentService.delete(id);
      MessageHelper.addInfoAttribute(ra, "methodPayment.successDelete", name);
      return REDIRECT_CONFIGURATION;
   }
}
