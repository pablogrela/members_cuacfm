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
package org.cuacfm.members.model.bankaccount;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cuacfm.members.model.account.Account;

/** The Class Account. */
@SuppressWarnings("serial")
@Entity
public class BankAccount implements Serializable {

   /** The id. */
   @Id
   @GeneratedValue
   private Long id;

   /** The account. */
   @ManyToOne(optional = true, fetch = FetchType.LAZY)
   @JoinColumn(name = "accountId")
   private Account account;

   /** The bank. */
   private String bank;

   /** The bic. */
   private String bic;

   /** The iban. */
   private String iban;

   /** The mandate. */
   private String mandate;

   /** The date created. */
   private Date dateCreated;
   
   /** The active. */
   private boolean active;

   /**
    * Instantiates a new bank account.
    */
   protected BankAccount() {
      // Default empty constructor.
   }

   /**
    * Instantiates a new bank account.
    *
    * @param account
    *           the account
    * @param bank
    *           the bank
    * @param bic
    *           the bic
    * @param iban
    *           the iban
    */
   public BankAccount(Account account, String bank, String bic, String iban) {
      super();
      this.account = account;
      this.bank = bank;
      this.bic = bic;
      this.iban = iban;
      this.dateCreated = new Date();
      this.active = true;
   }

   /**
    * Gets the id.
    *
    * @return the id
    */
   public Long getId() {
      return id;
   }

   /**
    * Gets the account.
    *
    * @return the account
    */
   public Account getAccount() {
      return account;
   }

   /**
    * Gets the bank.
    *
    * @return the bank
    */
   public String getBank() {
      return bank;
   }

   /**
    * Gets the bic.
    *
    * @return the bic
    */
   public String getBic() {
      return bic;
   }

   /**
    * Gets the iban.
    *
    * @return the iban
    */
   public String getIban() {
      return iban;
   }

   /**
    * Gets the mandate.
    *
    * @return the mandate
    */
   public String getMandate() {
      return mandate;
   }

   /**
    * Sets the mandate.
    *
    * @param mandate
    *           the new mandate
    */
   public void setMandate(String mandate) {
      this.mandate = mandate;
   }

   /**
    * Gets the date created.
    *
    * @return the date created
    */
   public Date getDateCreated() {
      return dateCreated;
   }
   
   /**
    * Checks if is active.
    *
    * @return true, if is active
    */
   public boolean isActive() {
      return active;
   }

   /**
    * Sets the active.
    *
    * @param active the new active
    */
   public void setActive(boolean active) {
      this.active = active;
   }

}
