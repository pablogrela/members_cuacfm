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
    * Gets the roles.
    *
    * @return the roles
    */
   public List<String> getRoles();

   /**
    * Gets the name users with role=ROLE_USER an active=true.
    *
    * @return the name users
    */
   public List<String> getUsernames();

}
