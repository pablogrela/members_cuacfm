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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class AccountRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class AccountRepositoryImpl implements AccountRepository {

   /** The entity manager. */
   @PersistenceContext
   private EntityManager entityManager;

   /** The password encoder. */
   @Autowired
   private PasswordEncoder passwordEncoder;

   /**
    * Save.
    *
    * @param account
    *           the account
    * @return the account
    */
   @Override
   @Transactional
   public Account save(Account account) {
      entityManager.persist(account);
      return account;
   }

   /**
    * Update.
    *
    * @param account
    *           the account
    * @return the account
    */
   @Override
   @Transactional
   public Account update(Account account) {
      return entityManager.merge(account);
   }

   /**
    * Delete.
    *
    * @param id
    *           the id
    */
   @Override
   public void delete(Long id) {
      Account account = findById(id);
      if (account != null) {
         entityManager.remove(account);
      }
   }

   /**
    * Match password.
    *
    * @param account
    *           the account
    * @param rawPassword
    *           the raw password
    * @return true, if successful
    */
   @Override
   public boolean matchPassword(Account account, String rawPassword) {
      return passwordEncoder.matches(rawPassword, account.getPassword());
   }

   /**
    * Find by id.
    *
    * @param id
    *           the id of user
    * @return the account
    */
   @Override
   public Account findById(Long id) {
      try {
         return entityManager
               .createQuery("select a from Account a where a.id = :id", Account.class)
               .setParameter("id", id).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Find by dni.
    *
    * @param dni
    *           the dni
    * @return the account
    */
   @Override
   public Account findByDni(String dni) {
      try {
         return entityManager
               .createQuery("select a from Account a where a.dni = :dni", Account.class)
               .setParameter("dni", dni).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Find by email.
    *
    * @param email
    *           the email of user
    * @return the account
    */
   @Override
   public Account findByEmail(String email) {
      try {
         return entityManager
               .createQuery("select a from Account a where a.email = :email", Account.class)
               .setParameter("email", email).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Find by login.
    *
    * @param login
    *           the login of user
    * @return the account
    */
   @Override
   public Account findByLogin(String login) {
      try {
         return entityManager
               .createQuery("select a from Account a where a.login = :login", Account.class)
               .setParameter("login", login).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Gets the users.
    *
    * @return the users
    */
   @Override
   public List<Account> getUsers() {
      return entityManager.createQuery(
            "select a from Account a where a.role <> 'ROLE_ADMIN' and a.active = true",
            Account.class).getResultList();
   }

   /**
    * Gets the users direct debit.
    *
    * @return the users direct debit
    */
   @Override
   public List<Account> getUsersDirectDebit() {
      return entityManager
            .createQuery(
                  "select a from Account a where a.role <> 'ROLE_ADMIN' and a.active = true and a.methodPayment.directDebit = true",
                  Account.class).getResultList();
   }

   /**
    * Gets the accounts.
    *
    * @return the accounts
    */
   @Override
   public List<Account> getAccounts() {
      return entityManager.createQuery("select a from Account a", Account.class).getResultList();
   }

   /**
    * Gets the name users with role=ROLE_USER an active=true.
    *
    * @return the name users
    */
   @Override
   public List<String> getUsernames() {
      // No running Concat(a.name, ' - ', a.nickname)
      List<Account> accounts = entityManager.createQuery(
            "select a from Account a " + "where a.role <> 'ROLE_ADMIN' " + "and a.active = true "
                  + "order by a.login", Account.class).getResultList();

      List<String> usernames = new ArrayList<String>();
      for (Account account : accounts) {
         if (account.getNickName() != null) {
            usernames.add(account.getId() + ": " + account.getName() + " - "
                  + account.getNickName());
         } else {
            usernames.add(account.getId() + ": " + account.getName());
         }
      }
      return usernames;
   }
}
