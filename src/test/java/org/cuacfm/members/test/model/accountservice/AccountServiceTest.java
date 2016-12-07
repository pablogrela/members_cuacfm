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
package org.cuacfm.members.test.model.accountservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.bankaccount.BankAccount;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class AccountServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AccountServiceTest extends WebSecurityConfigurationAware {

   /** The account service. */
   @Autowired
   private AccountService accountService;

   /** The account type service. */
   @Autowired
   private AccountTypeService accountTypeService;

   /** The method payment service. */
   @Autowired
   private MethodPaymentService methodPaymentService;

   /**
    * Assert equal accounts.
    *
    * @param account1
    *           the account1
    * @param account2
    *           the account2
    */
   private void assertEqualAccounts(Account account1, Account account2) {
      assertEquals(account1.getName(), account2.getName());
      assertEquals(account1.getEmail(), account2.getEmail());
      assertEquals(account1.getLogin(), account2.getLogin());
      assertEquals(account1.getPassword(), account2.getPassword());
      assertEquals(account1.getRole(), account2.getRole());
   }

   /**
    * Save and find by email account test.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void saveAndFindByEmailAccountTest() throws UniqueException {
      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(account);

      Account accountSearched = accountService.findByEmail("user@udc.es");
      assertEquals(account, accountSearched);
      assertEquals(account.getName(), accountSearched.getName());
      assertEquals(account.getDni(), accountSearched.getDni());
      assertEquals(account.getAddress(), accountSearched.getAddress());
      assertEquals(account.getLogin(), accountSearched.getLogin());
      assertEquals(account.getEmail(), accountSearched.getEmail());
      assertEquals(account.getMobile(), accountSearched.getMobile());
      assertEquals(account.getPhone(), accountSearched.getPhone());
      assertEquals(account.getPassword(), accountSearched.getPassword());
      assertEquals(account.getRole(), accountSearched.getRole());
   }

   /**
    * Find by id.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void findById() throws UniqueException {
      Account account = new Account("user", "55555555C", "London", "user", "email1@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      Account accountSaved = accountService.save(account);
      Account accountSearch = accountService.findById(accountSaved.getId());

      assertEquals(accountSearch, account);
   }

   /**
    * Find by id without user.
    */
   @Test
   public void findByIDWithoutUser() {
      Account account = accountService.findById(Long.valueOf(1));
      assertEquals(null, account);
   }

   /**
    * insert Exception the same login.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test(expected = UniqueException.class)
   public void insertUserTheSameLoginException() throws UniqueException {
      Account account = new Account("user", "55555555C", "London", "user", "email1@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(account);

      Account account2 = new Account("user", "55555555B", "London", "user", "email2@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(account2);
   }

   /**
    * insert Exception the same email.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test(expected = UniqueException.class)
   public void insertUserTheSameEmailException() throws UniqueException {
      Account account = new Account("user", "55555555C", "London", "user", "email1@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(account);

      Account account2 = new Account("user", "55555555B", "London", "user2", "email1@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(account2);
   }

   /**
    * update Exception the same login.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test(expected = UniqueException.class)
   public void updateUserTheSameLoginException() throws UniqueException {
      Account account = new Account("user", "55555555C", "London", "user", "email1@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(account);

      Account account2 = new Account("user2", "55555555B", "London", "user2", "email1@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(account2);

      Account account3 = new Account("user", "55555555C", "London", "user2", "email1@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.update(account3, false, true);
   }

   /**
    * update Exception the same email.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test(expected = UniqueException.class)
   public void updateUserTheSameEmailException() throws UniqueException {
      Account account = new Account("user", "55555555C", "London", "user", "email1@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(account);

      Account account2 = new Account("user2", "55555555B", "London", "user2", "email2@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(account2);

      Account account3 = new Account("user", "55555555C", "London", "user", "email2@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.update(account3, false, true);
   }

   /**
    * Save and find by login account test.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void saveAndFindByLoginAccountTest() throws UniqueException {
      Account account = new Account("user", "55555555C", "London", "user", "email1@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(account);
      Account account2 = accountService.findByLogin("user");
      assertEqualAccounts(account, account2);
   }

   /**
    * Find by login without user.
    */
   @Test
   public void findByLoginWithoutUser() {
      Account account = accountService.findByLogin("user1");
      assertEquals(null, account);
   }

   /**
    * Find by email without user.
    */
   @Test
   public void findByEmailWithoutUser() {
      Account account = accountService.findByEmail("user@user.es");
      assertEquals(null, account);
   }

   /**
    * Update user data without password.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void updateUserDataWithoutPassword() throws UniqueException {
      Account account = new Account("user", "55555555C", "London", "user", "email1@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      Account savedAccount = accountService.save(account);
      assertEqualAccounts(account, savedAccount);
      /* changing properties */
      savedAccount.setName("user2");
      savedAccount.setEmail("user2@user2.es");
      savedAccount.setRole(roles.ROLE_ADMIN);
      savedAccount.setLogin("user2");
      /* updating */
      Account updatedAccount = accountService.update(savedAccount, false, true);
      assertEqualAccounts(updatedAccount, savedAccount);
      Account searchedAccount = accountService.findByEmail("user2@user2.es");
      assertEqualAccounts(searchedAccount, updatedAccount);
      /* check that the previous properties are not in the db */
      Account notExistentAccount = accountService.findByEmail("user@user.es");
      assertEquals(null, notExistentAccount);
      Account notExistentAccount2 = accountService.findByLogin("user1");
      assertEquals(null, notExistentAccount2);

   }

   /**
    * Update user data with password.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void updateUserDataWithPassword() throws UniqueException {
      Account account = new Account("user", "55555555C", "London", "user", "email1@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      Account savedAccount = accountService.save(account);
      assertEqualAccounts(account, savedAccount);

      /* changing properties */
      savedAccount.setEmail("user2@user2.es");
      savedAccount.setRole(roles.ROLE_ADMIN);
      savedAccount.setLogin("user2");
      savedAccount.setPassword("rawPassword2");
      savedAccount.setObservations("observations");

      /* updating */
      Account updatedAccount = accountService.update(savedAccount, true, true);

      assertEquals(updatedAccount.getEmail(), savedAccount.getEmail());
      assertEquals(updatedAccount.getLogin(), savedAccount.getLogin());
      assertEquals(updatedAccount.getRole(), savedAccount.getRole());
      assertEquals(updatedAccount.getObservations(), savedAccount.getObservations());

      /* checking that the password has changed */
      assertTrue(accountService.matchPassword(updatedAccount, "rawPassword2"));
      Account searchedAccount = accountService.findByEmail("user2@user2.es");
      assertEqualAccounts(searchedAccount, updatedAccount);

      /* check that the previous properties are not in the db */
      Account notExistentAccount = accountService.findByEmail("user@user.es");
      assertEquals(null, notExistentAccount);
      Account notExistentAccount2 = accountService.findByLogin("user1");
      assertEquals(null, notExistentAccount2);

   }

   /**
    * Match password test.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void matchPasswordTest() throws UniqueException {
      Account account = new Account("user", "55555555C", "London", "user", "email1@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);

      Account savedAccount = accountService.save(account);

      assertTrue(accountService.matchPassword(savedAccount, "demo"));
      assertFalse(accountService.matchPassword(savedAccount, "otherRawPassword"));
   }

   /**
    * Gets the users test.
    *
    * @return the users test
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void getUsersTest() throws UniqueException {
      // Save
      Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666",
            "666666666", "demo", roles.ROLE_USER);
      accountService.save(user);
      Account admin = new Account("admin", "55555555D", "London", "admin", "admin@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_ADMIN);
      accountService.save(admin);

      List<Account> users = accountService.getUsers();

      // Assert, getUsers only returns account with rol = ROLE_USER
      assertTrue(users.contains(user));
      assertFalse(users.contains(admin));
   }

   /**
    * Gets the accounts test.
    *
    * @return the accounts test
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void getAccountsTest() throws UniqueException {
      // Save
      Account user = new Account("user", "55555555C", "London", "user", "email1@udc.es", "666666666",
            "666666666", "demo", roles.ROLE_USER);
      accountService.save(user);
      Account admin = new Account("admin", "55555555D", "London", "admin", "admin@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_ADMIN);
      accountService.save(admin);

      List<Account> accounts = accountService.getAccounts();

      // Assert, getAccount return all accounts
      assertTrue(accounts.contains(user));
      assertTrue(accounts.contains(admin));
   }

   /**
    * Save test.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void SaveTest() throws UniqueException {
      // Save
      Account user = new Account("user", "55555555C", "London", "user", "email1@udc.es", "666666666",
            "666666666", "demo", roles.ROLE_USER);
      accountService.save(user);

      // Assert
      Account accountSearched = accountService.findById(user.getId());
      assertEquals(accountSearched.getAccountType(), null);
      assertEquals(accountSearched.getMethodPayment(), null);
      assertEquals(accountSearched.getInstallments(), 1);
   }

   /**
    * Unsubscribe and subscribe test.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void UnsubscribeAndSubscribeTest() throws UniqueException {
      // Save
      Account user = new Account("user", "55555555C", "London", "user", "email1@udc.es", "666666666",
            "666666666", "demo", roles.ROLE_USER);
      accountService.save(user);

      // Assert, Unsubscribe
      accountService.unsubscribe(user);
      assertEquals(user.isActive(), false);

      // Assert, Subscribe
      accountService.subscribe(user);
      assertEquals(user.isActive(), true);
   }

   /**
    * Update test.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void AddAcountTypeAndMethodPaymentToAccountTest() throws UniqueException {
      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      
      MethodPayment methodPayment = new MethodPayment("Cash", false, "cash");
      methodPaymentService.save(methodPayment);
      
      Account user = new Account("Pablo", "55555555C", "London", "pablo", "pablo@udc.es", "666666666",
            "666666666", "demo", roles.ROLE_USER);
      accountService.save(user);

      // Update
      user.setAccountType(accountType);
      user.setMethodPayment(methodPayment);
      user.setInstallments(2);
      accountService.update(user, false, true);

      // Assert
      Account accountSearched = accountService.findById(user.getId());
      assertEquals(accountSearched.getAccountType(), user.getAccountType());
      assertEquals(accountSearched.getMethodPayment(), user.getMethodPayment());
      assertEquals(accountSearched.getInstallments(), user.getInstallments());
   }

   /**
    * Bank account test.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void BankAccountTest() throws UniqueException {
      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      MethodPayment methodPayment = new MethodPayment("cash", true, "cash");
      methodPaymentService.save(methodPayment);
      Account user = new Account("user", "55555555C", "London", "user", "email1@udc.es", "666666666",
            "666666666", "demo", roles.ROLE_USER);
      accountService.save(user);

      BankAccount bankAccount = new BankAccount(user, "Santander", "BSCHESMMXXX",
            "ES7620770024003102575766");
      accountService.saveBankAccount(bankAccount);

      bankAccount.getIban();
      
      // BankAccount
      BankAccount bankAccountSearched = accountService.activeBankAccountByAccountId(user.getId());
      assertEquals(bankAccount.getId(), bankAccountSearched.getId());
      assertEquals(bankAccount.getIban(), bankAccountSearched.getIban());
      assertEquals(bankAccount.getBic(), bankAccountSearched.getBic());
      assertEquals(bankAccount.getMandate(), bankAccountSearched.getMandate());
      assertEquals(bankAccount.getBank(), bankAccountSearched.getBank());
      assertEquals(bankAccount.getDateCreated(), bankAccountSearched.getDateCreated());
      assertEquals(bankAccount.isActive(), bankAccountSearched.isActive());
      
      // New BankAccount
      BankAccount bankAccount2 = new BankAccount(user, "La Caixa", "BSCHESMMXXX",
            "ES7620770024003102575766");
      accountService.saveBankAccount(bankAccount2);
      bankAccountSearched = accountService.activeBankAccountByAccountId(user.getId());
      assertEquals(bankAccount2.getId(), bankAccountSearched.getId());
   }
}
