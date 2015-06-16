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
package org.cuacfm.members.model.directdebit;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.util.States.states;

/** The Class DirectDebit. */
@SuppressWarnings("serial")
@Entity
public class DirectDebit implements Serializable {

   /** The id. */
   @Id
   @GeneratedValue
   private Long id;

   /** The account. */
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   @JoinColumn(name = "accountId")
   private Account account;

   /** The bank remittance. */
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   @JoinColumn(name = "bankRemittanceId")
   private BankRemittance bankRemittance;

   /** The pay members. */
   @ManyToMany(fetch = FetchType.LAZY)
   @JoinTable(name = "DirectDebitPayMembers", joinColumns = { @JoinColumn(name = "directDebitId") }, inverseJoinColumns = { @JoinColumn(name = "payMemberId") })
   // @OneToMany(fetch = FetchType.LAZY, mappedBy = "directDebit")
   private List<PayMember> payMembers;

   /** The pay programs. */
   @ManyToMany(fetch = FetchType.LAZY)
   @JoinTable(name = "DirectDebitPayPrograms", joinColumns = { @JoinColumn(name = "directDebitId") }, inverseJoinColumns = { @JoinColumn(name = "payProgramId") })
   // @OneToMany(fetch = FetchType.LAZY, mappedBy = "directDebit")
   private List<PayProgram> payPrograms;

   /** The price. */
   private Double price;

   /** The concept. */
   private String concept;

   /** The mandate. */
   private String mandate;

   /** The secuence debtor. */
   private String secuence;

   /** The state. */
   private states state;

   /** Instantiates a direct debit. */
   protected DirectDebit() {
      // Default empty constructor.
   }

   /**
    * Instantiates a new direct debit.
    *
    * @param account
    *           the account
    * @param bankRemittance
    *           the bank remittance
    * @param payMembers
    *           the pay members
    * @param payPrograms
    *           the pay programs
    * @param price
    *           the price
    * @param concept
    *           the concept
    * @param mandate
    *           the mandate
    * @param secuence
    *           the secuence
    */
   public DirectDebit(Account account, BankRemittance bankRemittance, List<PayMember> payMembers,
         List<PayProgram> payPrograms, Double price, String concept, String mandate, String secuence) {
      super();
      this.account = account;
      this.bankRemittance = bankRemittance;
      this.payMembers = payMembers;
      this.payPrograms = payPrograms;
      this.price = price;
      this.concept = concept;
      this.mandate = mandate;
      this.secuence = secuence;
      this.state = states.NO_PAY;
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
    * Gets the bank remittance.
    *
    * @return the bank remittance
    */
   public BankRemittance getBankRemittance() {
      return bankRemittance;
   }

   /**
    * Gets the pay members.
    *
    * @return the pay members
    */
   public List<PayMember> getPayMembers() {
      return payMembers;
   }

   /**
    * Gets the pay programs.
    *
    * @return the pay programs
    */
   public List<PayProgram> getPayPrograms() {
      return payPrograms;
   }

   /**
    * Gets the price.
    *
    * @return the price
    */
   public Double getPrice() {
      return price;
   }

   /**
    * Gets the concept.
    *
    * @return the concept
    */
   public String getConcept() {
      return concept;
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
    * Gets the secuence.
    *
    * @return the secuence
    */
   public String getSecuence() {
      return secuence;
   }

   /**
    * Gets the state.
    *
    * @return the state
    */
   public states getState() {
      return state;
   }

   /**
    * Sets the state.
    *
    * @param state
    *           the new state
    */
   public void setState(states state) {
      this.state = state;
   }

}
