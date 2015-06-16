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
import org.cuacfm.members.model.accounttype.AccountTypeRepository;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class Account Type Service. */
@Service("accountTypeService")
public class AccountTypeServiceImpl implements AccountTypeService {

   /** The Account Type Service. */
   @Autowired
   private AccountTypeRepository accountTypeRepository;

   /**
    * Save.
    *
    * @param accountType
    *           the account Type
    * @return the account type
    * @throws UniqueException
    */
   @Override
   public AccountType save(AccountType accountType) throws UniqueException {
      // It is verified that there is not exist name of accountType in other
      // accountType
      if (accountTypeRepository.findByName(accountType.getName()) != null) {
         throw new UniqueException("Name", accountType.getName());
      }
      return accountTypeRepository.save(accountType);
   }

   /**
    * Update.
    *
    * @param accountType
    *           the account Type
    * @return the account type
    * @throws UniqueException
    */
   @Override
   public AccountType update(AccountType accountType) throws UniqueException {
      // It is verified that there is not exist name of trainingType in other
      // trainingType
      AccountType accountTypeSearch = accountTypeRepository.findByName(accountType.getName());
      if ((accountTypeSearch != null) && (accountTypeSearch.getId() != accountType.getId())) {
         throw new UniqueException("Name", accountType.getName());
      }
      return accountTypeRepository.update(accountType);
   }

   /**
    * Delete.
    *
    * @param id
    *           the id
    */
   @Override
   public void delete(Long id) {
      accountTypeRepository.delete(id);
   }

   /**
    * Find by id.
    *
    * @param id
    *           the id
    * @return the account type
    */
   @Override
   public AccountType findById(Long id) {
      return accountTypeRepository.findById(id);
   }

   /**
    * Find by name.
    *
    * @param name
    *           the name of AccountType
    * @return AccountType
    */
   @Override
   public AccountType findByName(String name) {
      return accountTypeRepository.findByName(name);
   }

   /**
    * Gets the account type.
    *
    * @return the account type
    */
   @Override
   public List<AccountType> getAccountTypes() {
      return accountTypeRepository.getAccountTypes();
   }
}
