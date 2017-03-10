/**
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.web.configuration;

import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.configuration.Configuration;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class ConfigurationController. */
@Controller
public class ConfigurationController {

   private static final String CONFIGURATION_VIEW_NAME = "configuration/configuration";
   private static final String REDIRECT_CONFIGURATION = "redirect:/configuration";

   @Autowired
   private ConfigurationService configurationService;

   @Autowired
   private AccountTypeService accountTypeService;

   @Autowired
   private MethodPaymentService methodPaymentService;

   @Autowired
   private AccountService accountService;
   
   @Autowired
   private ProgramService programService;
   
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
   
	/**
	 * Single file upload.
	 *
	 * @param file the file
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "accountList/uploadJson", method = RequestMethod.POST)
	public String singleFileUploadAccounts(@RequestParam("file") MultipartFile file, RedirectAttributes ra) {

		if (file.isEmpty()) {
			MessageHelper.addErrorAttribute(ra, "noFileUpload", "");
			return REDIRECT_CONFIGURATION;
		}

		String result = accountService.processJson(file);

		MessageHelper.addInfoAttribute(ra, result, "");
		return REDIRECT_CONFIGURATION;
	}
	
	/**
	 * Single file upload.
	 *
	 * @param file the file
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "programList/uploadJson", method = RequestMethod.POST)
	public String singleFileUploadPrograms(@RequestParam("file") MultipartFile file, RedirectAttributes ra) {

		if (file.isEmpty()) {
			MessageHelper.addErrorAttribute(ra, "noFileUpload", "");
			return REDIRECT_CONFIGURATION;
		}

		String result = programService.processJson(file);

		MessageHelper.addInfoAttribute(ra, result, "");
		return REDIRECT_CONFIGURATION;
	}
}
