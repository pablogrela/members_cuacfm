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

import javax.validation.Valid;

import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class AccountTypeCreateController. */
@Controller
public class AccountTypeCreateController {

   /** The Constant ACCOUNTYPE_VIEW_NAME. */
   private static final String ACCOUNTYPE_VIEW_NAME = "configuration/accountypecreate";

   /** The accountTypeService. */
   @Autowired
   private AccountTypeService accountTypeService;

   /** Instantiates a new accountType Controller.*/
   public AccountTypeCreateController() {
      super();
      // Default empty constructor.
   }

   /**
    * Get page accountTypeCreate.
    *
    * @param model
    *           the model
    * @return String to view to accountTypeCreate
    */
   @RequestMapping(value = "configuration/accountTypeCreate")
   public String accountType(Model model) {
      model.addAttribute(new AccountTypeForm());
      return ACCOUNTYPE_VIEW_NAME;
   }

   /**
    * Post to create a new accountType.
    *
    * @param accountTypeForm
    *           the accountType form
    * @param errors
    *           the errors
    * @param ra
    *           the ra
    * @return String to redirect to accountTypeList or if fault
    *         accountTypeCreate
    * 
    */
   @RequestMapping(value = "configuration/accountTypeCreate", method = RequestMethod.POST)
   public String accountTypeCreate(@Valid @ModelAttribute AccountTypeForm accountTypeForm,
         Errors errors, RedirectAttributes ra) {

      if (errors.hasErrors()) {
         return ACCOUNTYPE_VIEW_NAME;
      }

      try {
         accountTypeService.save(accountTypeForm.createAccountType());
      } catch (UniqueException e) {
         errors.rejectValue("name", "accountType.existentName", new Object[] { e.getValue() },
               "name");
         return ACCOUNTYPE_VIEW_NAME;
      }
      MessageHelper.addSuccessAttribute(ra, "accountType.successCreate", accountTypeForm.getName());
      return "redirect:/configuration";
   }
}
