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
package org.cuacfm.members.model.account;

import java.util.List;

/** The Interface AccountRepository. */
public interface AccountRepository {

   /**
    * Save.
    *
    * @param account
    *           the account
    * @return the account
    */
   public Account save(Account account);

   /**
    * Update.
    *
    * @param account
    *           the account
    * @return the account
    */

   public Account update(Account account);

   /**
    * Delete.
    *
    * @param id
    *           the id
    */
   public void delete(Long id);

   /**
    * Match password.
    *
    * @param account
    *           the account
    * @param rawPassword
    *           the raw password
    * @return true, if successful
    */
   boolean matchPassword(Account account, String rawPassword);

   /**
    * Find by id.
    *
    * @param id
    *           the id of user
    * @return the account
    */
   public Account findById(Long id);

   /**
    * Find by dni.
    *
    * @param dni
    *           the dni
    * @return the account
    */
   public Account findByDni(String dni);

   /**
    * Find by email.
    *
    * @param email
    *           the email of user
    * @return the account
    */
   public Account findByEmail(String email);

   /**
    * Find by login.
    *
    * @param login
    *           the login of user
    * @return the account
    */
   public Account findByLogin(String login);

   /**
    * Gets the users.
    *
    * @return the users
    */
   public List<Account> getUsers();

   /**
    * Gets the users direct debit.
    *
    * @return the users direct debit
    */
   public List<Account> getUsersDirectDebit();

   /**
    * Gets the accounts.
    *
    * @return the accounts
    */
   public List<Account> getAccounts();

   /**
    * Gets the name users with role=ROLE_USER an active=true.
    *
    * @return the name users
    */
   public List<String> getUsernames();

}
