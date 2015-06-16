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
package org.cuacfm.members.model.accountservice;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.bankaccount.BankAccount;
import org.cuacfm.members.model.exceptions.UniqueException;

/** The Class AccountService. */
public interface AccountService {

   /**
    * Save, saves an account into database.
    *
    * @param account
    *           the account
    * @return the account
    * @throws UniqueException
    *            the unique exception
    */
   public Account save(Account account) throws UniqueException;

   /**
    * Update, updates an user registered into bd depending if he wants to update
    * his password or not.
    *
    * @param account
    *           the account
    * @param newPassword
    *           the new password
    * @return the account
    * @throws UniqueException
    *            the unique exception
    */
   public Account update(Account account, boolean newPassword) throws UniqueException;

   /**
    * Delete.
    *
    * @param id
    *           the id
    */
   public void delete(Long id);

   /**
    * Subscribe Account.
    *
    * @param id
    *           the id
    */
   public void subscribe(Long id);

   /**
    * Unsubscribe.
    *
    * @param id
    *           the id
    */
   public void unsubscribe(Long id);

   /**
    * Find by dni.
    *
    * @param dni
    *           the dni
    * @return the account
    */
   public Account findByDni(String dni);

   /**
    * Find by email returns user which has this email.
    *
    * @param email
    *           the email
    * @return the account
    */
   public Account findByEmail(String email);

   /**
    * Find by id returns user which has this identifier.
    *
    * @param login
    *           the login
    * @return the account
    */
   public Account findByLogin(String login);

   /**
    * Find by id returns user which has this identifier.
    *
    * @param id
    *           the id
    * @return the account
    */
   public Account findById(Long id);

   /**
    * Match password check if password match with the user.
    *
    * @param account
    *           the account
    * @param rawPassword
    *           the raw password
    * @return true, if successful
    */
   public boolean matchPassword(Account account, String rawPassword);

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

   /**
    * Save bank account.
    *
    * @param bankAccount
    *           the bank account
    * @return the bank account
    */
   public BankAccount saveBankAccount(BankAccount bankAccount);
   
   
   /**
    * Active bank account by account id.
    *
    * @param accountId
    *           the account id
    * @return the bank account
    */
   public BankAccount activeBankAccountByAccountId(Long accountId);
}
