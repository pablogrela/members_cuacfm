/**
 * Copyright © 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.test.model.accounttypeservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class AccountTypeServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AccountTypeServiceTest extends WebSecurityConfigurationAware {

   /** The account type service. */
   @Inject
   private AccountTypeService accountTypeService;

   /**
    * Save and find account type test.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void saveAndFindAccountTypeTest() throws UniqueException {
      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);

      // Assert
      AccountType accountTypeSearched = accountTypeService.findById(accountType.getId());
      assertEquals(accountType, accountTypeSearched);
   }

   /**
    * Save and find account type test.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test(expected = UniqueException.class)
   public void saveAccountTypeExceptionTest() throws UniqueException {
      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);

      AccountType accountType2 = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType2);
   }

   /**
    * Update account type test.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void updateAccountTypeTest() throws UniqueException {
      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);

      // Update
      accountType.setName("Young");
      accountType.setDiscount(10);
      accountType.setOrganization(true);
      accountType.setDescription("Adult with discount");
      AccountType accountTypeUpdate = accountTypeService.update(accountType);

      // Assert
      assertEquals(accountType, accountTypeUpdate);
      assertEquals(accountType.getName(), accountTypeUpdate.getName());
      assertEquals(accountType.getDiscount(), accountTypeUpdate.getDiscount());
      assertEquals(accountType.isOrganization(), accountTypeUpdate.isOrganization());
      assertEquals(accountType.getDescription(), accountTypeUpdate.getDescription());

      accountType.setDiscount(20);
      accountType.setDescription("etc");
      accountTypeUpdate = accountTypeService.update(accountType);

      AccountType accountType2 = new AccountType("Adult2", false, "Fee for adults", 0);
      accountTypeService.update(accountType2);
   }

   /**
    * Update account type test.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test(expected = UniqueException.class)
   public void updateAccountTypeExceptionTest() throws UniqueException {
      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      AccountType accountType2 = new AccountType("Young", false, "Fee for youth", 20);
      accountTypeService.save(accountType2);

      // Update
      AccountType accountType3 = new AccountType("Young", false, "Fee for youth", 20);
      accountTypeService.update(accountType3);
   }

   /**
    * Delete account type test.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void deleteAccountTypeTest() throws UniqueException {
      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);

      // Assert
      AccountType accountTypeSearched = accountTypeService.findById(accountType.getId());
      assertNotNull(accountTypeSearched);

      // Delete
      accountTypeService.delete(accountType.getId());

      // Assert, no exist AccountType
      accountTypeSearched = accountTypeService.findById(accountType.getId());
      assertNull(accountTypeSearched);
   }

   /**
    * Delete account type test.
    */
   @Test
   public void deleteNullAccountTypeTest() {
      // Delete
      accountTypeService.delete(Long.valueOf(0));
   }

   /**
    * Save and find account type test.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void saveAndFindByNameAccountTypeTest() throws UniqueException {
      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);

      // Assert
      AccountType accountTypeSearched = accountTypeService.findByName(accountType.getName());
      assertEquals(accountType, accountTypeSearched);
   }

   /**
    * Gets the account types test.
    *
    * @return the account types test
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void getAccountTypesTest() throws UniqueException {
      // Assert
      List<AccountType> accountTypes = accountTypeService.getAccountTypes();
      assertEquals(accountTypes.size(), 0);

      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      AccountType accountType2 = new AccountType("Young", false, "Fee for youth", 20);
      accountTypeService.save(accountType2);

      // Assert
      accountTypes = accountTypeService.getAccountTypes();
      assertEquals(accountTypes.size(), 2);
      assertTrue(accountTypes.contains(accountType));
      assertTrue(accountTypes.contains(accountType2));
   }
}
