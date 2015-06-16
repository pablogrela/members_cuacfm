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
