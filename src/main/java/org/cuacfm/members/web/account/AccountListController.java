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
package org.cuacfm.members.web.account;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class AccountListController. */
@Controller
public class AccountListController {

   /** The Constant ACCOUNT_VIEW_NAME. */
   private static final String ACCOUNT_VIEW_NAME = "account/accountlist";

   /** The TrainingTypeService. */
   @Autowired
   private AccountService accountService;

   /** The rol admin. */
   private roles rolAdmin = roles.ROLE_ADMIN;

   /**
    * Accounts.
    *
    * @return the list
    */
   @ModelAttribute("accounts")
   public List<Account> accounts() {
      return accountService.getAccounts();
   }

   /**
    * Rol admin.
    *
    * @return the roles
    */
   @ModelAttribute("rolAdmin")
   public roles rolAdmin() {
      return rolAdmin;
   }

   /**
    * Gets the accounts.
    *
    * @param model
    *           the model
    * @return the accounts
    */
   @RequestMapping(value = "accountList")
   public String getAccounts() {
      return ACCOUNT_VIEW_NAME;
   }

   /**
    * Unsubscribe the account.
    *
    * @param id
    *           the id
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "accountList/accountUnsubscribe/{id}", method = RequestMethod.POST)
   public String unsubscribe(@PathVariable Long id, RedirectAttributes ra) {

      Account account = accountService.findById(id);
      String name = account.getName();
      if (account.getRole() == roles.ROLE_ADMIN) {
         MessageHelper.addErrorAttribute(ra, "account.errorUnsubscribe", name);
      } else {
         accountService.unsubscribe(id);
         MessageHelper.addInfoAttribute(ra, "account.successUnsubscribe", name);
      }
      return "redirect:/accountList";
   }

   /**
    * Subscribe the account.
    *
    * @param id
    *           the id
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "accountList/accountSubscribe/{id}", method = RequestMethod.POST)
   public String subscribe(@PathVariable Long id, RedirectAttributes ra) {

      String name = accountService.findById(id).getName();
      accountService.subscribe(id);
      MessageHelper.addInfoAttribute(ra, "account.successSubscribe", name);
      return "redirect:/accountList";
   }
}
