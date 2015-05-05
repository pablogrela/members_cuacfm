package org.cuacfm.members.model.accountservice;

import java.util.List;

import org.cuacfm.members.model.account.Account;
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
    */
   public Account save(Account account) throws UniqueException;

   /**
    * Update, updates an user registered into bd depending if he wants to update
    * his password or not.
    *
    * @param account
    *           the account
    * @param passwordUpdate
    *           the passwordUpdate
    * @return the account
    * @throws UniqueException
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
    * @param id
    *           the id
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
