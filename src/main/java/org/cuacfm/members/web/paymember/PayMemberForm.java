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
package org.cuacfm.members.web.paymember;

import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.util.States;
import org.cuacfm.members.model.util.States.methods;
import org.cuacfm.members.model.util.States.states;
import org.cuacfm.members.web.support.DisplayDate;

/** The Class PayMemberForm. */
public class PayMemberForm {

   /** The Constant MAX_CHARACTERS. */
   private static final String MAX_CHARACTERS = "{max.characters}";

   /** The price. */
   @Digits(fraction = 2, integer = 2)
   @DecimalMin("0.00")
   private Double price;

   /** The state. */
   private String state;

   /** The state list. */
   private List<states> stateList;
   
   /** The method. */
   private String method;

   /** The method list. */
   private List<methods> methodList;
   /** The installment. */
   @Min(1)
   @Max(12)
   private int installment;

   /** The installments. */
   @Min(1)
   @Max(12)
   private int installments;

   /** The id payer. */
   @Size(max = 30, message = PayMemberForm.MAX_CHARACTERS)
   private String idPayer;

   /** The id txn. */
   @Size(max = 30, message = PayMemberForm.MAX_CHARACTERS)
   private String idTxn;

   /** The email payer. */
   @Size(max = 30, message = PayMemberForm.MAX_CHARACTERS)
   private String emailPayer;

   /** The status pay. */
   @Size(max = 30, message = PayMemberForm.MAX_CHARACTERS)
   private String statusPay;

   /** The date pay. */
   @Size(max = 30, message = PayMemberForm.MAX_CHARACTERS)
   private String datePay;

   /** Instantiates a new user fee member form. */
   public PayMemberForm() {
      // Default empty constructor.
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
    * Sets the price.
    *
    * @param price
    *           the new price
    */
   public void setPrice(Double price) {
      this.price = price;
   }

   /**
    * Gets the state.
    *
    * @return the state
    */
   public String getState() {
      return state;
   }

   /**
    * Sets the state.
    *
    * @param state
    *           the new state
    */
   public void setState(String state) {
      this.state = state;
   }

   /**
    * Gets the state list.
    *
    * @return the state list
    */
   public List<States.states> getStateList() {
      return stateList;
   }

   /**
    * Sets the state list.
    *
    * @param stateList
    *           the new state list
    */
   public void setStateList(List<States.states> stateList) {
      this.stateList = stateList;
   }

   /**
    * Gets the method.
    *
    * @return the method
    */
   public String getMethod() {
      return method;
   }

   /**
    * Sets the method.
    *
    * @param method
    *           the new method
    */
   public void setMethod(String method) {
      this.method = method;
   }

   /**
    * Gets the method list.
    *
    * @return the method list
    */
   public List<States.methods> getMethodList() {
      return methodList;
   }

   /**
    * Sets the method list.
    *
    * @param methodList
    *           the new method list
    */
   public void setMethodList(List<States.methods> methodList) {
      this.methodList = methodList;
   }

   /**
    * Gets the installment.
    *
    * @return the installment
    */
   public int getInstallment() {
      return installment;
   }

   /**
    * Sets the installment.
    *
    * @param installment
    *           the new installment
    */
   public void setInstallment(int installment) {
      this.installment = installment;
   }

   /**
    * Gets the installments.
    *
    * @return the installments
    */
   public int getInstallments() {
      return installments;
   }

   /**
    * Sets the installments.
    *
    * @param installments
    *           the new installments
    */
   public void setInstallments(int installments) {
      this.installments = installments;
   }

   /**
    * Gets the id payer.
    *
    * @return the id payer
    */
   public String getIdPayer() {
      return idPayer;
   }

   /**
    * Sets the id payer.
    *
    * @param idPayer
    *           the new id payer
    */
   public void setIdPayer(String idPayer) {
      this.idPayer = idPayer;
   }

   /**
    * Gets the id txn.
    *
    * @return the id txn
    */
   public String getIdTxn() {
      return idTxn;
   }

   /**
    * Sets the id txn.
    *
    * @param idTxn
    *           the new id txn
    */
   public void setIdTxn(String idTxn) {
      this.idTxn = idTxn;
   }

   /**
    * Gets the email payer.
    *
    * @return the email payer
    */
   public String getEmailPayer() {
      return emailPayer;
   }

   /**
    * Sets the email payer.
    *
    * @param emailPayer
    *           the new email payer
    */
   public void setEmailPayer(String emailPayer) {
      this.emailPayer = emailPayer;
   }

   /**
    * Gets the status pay.
    *
    * @return the status pay
    */
   public String getStatusPay() {
      return statusPay;
   }

   /**
    * Sets the status pay.
    *
    * @param statusPay
    *           the new status pay
    */
   public void setStatusPay(String statusPay) {
      this.statusPay = statusPay;
   }

   /**
    * Gets the date pay.
    *
    * @return the date pay
    */
   public String getDatePay() {
      return datePay;
   }

   /**
    * Sets the date pay.
    *
    * @param datePay
    *           the new date pay
    */
   public void setDatePay(String datePay) {
      this.datePay = datePay;
   }

   /**
    * Update user fee member.
    *
    * @param payMember
    *           the pay member
    * @return the user fee member
    */
   public PayMember updatePayMember(PayMember payMember) {

      payMember.setPrice(getPrice());
      payMember.setState(states.valueOf(getState()));
      payMember.setMethod(methods.valueOf(getMethod()));
      payMember.setInstallment(getInstallment());
      payMember.setInstallments(getInstallments());
      if (getIdTxn() != "") {
         payMember.setIdTxn(getIdTxn());
      }
      if (getIdPayer() != "") {
         payMember.setIdPayer(getIdPayer());
      }
      if (getEmailPayer() != "") {
         payMember.setEmailPayer(getEmailPayer());
      }
      if (getIdPayer() != "") {
         payMember.setIdPayer(getIdPayer());
      }
      if (getDatePay() != "") {
         payMember.setDatePay(DisplayDate.stringToDateTime(getDatePay()));
      }
      return payMember;
   }

}
