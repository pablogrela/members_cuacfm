package org.cuacfm.members.model.accounttypeservice;

import java.util.List;

import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.exceptions.UniqueException;

/** The Interface AccountTypeService. */
public interface AccountTypeService {

   /**
    * Save.
    *
    * @param accountType
    *           the account Type
    * @return the account type
    * @throws UniqueException
    */
   public AccountType save(AccountType accountType) throws UniqueException;

   /**
    * Update.
    *
    * @param accountType
    *           the account Type
    * @return the account type
    * @throws UniqueException
    */
   public AccountType update(AccountType accountType) throws UniqueException;

   /**
    * Delete.
    *
    * @param id
    *           the id
    */
   public void delete(Long id);

   /**
    * Find by id.
    *
    * @param id
    *           the id
    * @return the account type
    */
   public AccountType findById(Long id);

   /**
    * Find by name.
    *
    * @param name
    *           the name of AccountType
    * @return AccountType
    */
   public AccountType findByName(String name);

   /**
    * Gets the account type.
    *
    * @return the account type
    */
   public List<AccountType> getAccountTypes();

}
