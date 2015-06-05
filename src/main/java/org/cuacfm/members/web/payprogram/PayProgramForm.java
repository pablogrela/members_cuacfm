package org.cuacfm.members.web.payprogram;

import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.util.States;
import org.cuacfm.members.model.util.States.methods;
import org.cuacfm.members.model.util.States.states;
import org.cuacfm.members.web.support.DisplayDate;

/** The Class PayProgramForm. */
public class PayProgramForm {

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

   /** The account Payer. */
   @Size(max = 30, message = PayProgramForm.MAX_CHARACTERS)
   private String accountPayer;

   /** The id payer. */
   @Size(max = 30, message = PayProgramForm.MAX_CHARACTERS)
   private String idPayer;

   /** The id txn. */
   @Size(max = 30, message = PayProgramForm.MAX_CHARACTERS)
   private String idTxn;

   /** The email payer. */
   @Size(max = 30, message = PayProgramForm.MAX_CHARACTERS)
   private String emailPayer;

   /** The status pay. */
   @Size(max = 30, message = PayProgramForm.MAX_CHARACTERS)
   private String statusPay;

   /** The date pay. */
   @Size(max = 30, message = PayProgramForm.MAX_CHARACTERS)
   private String datePay;

   /** Instantiates a new user pay inscription form. */
   public PayProgramForm() {
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
    * Gets the account payer.
    *
    * @return the account payer
    */
   public String getAccountPayer() {
      return accountPayer;
   }

   /**
    * Sets the account payer.
    *
    * @param accountPayer
    *           the new account payer
    */
   public void setAccountPayer(String accountPayer) {
      this.accountPayer = accountPayer;
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
    * Update pay program.
    *
    * @param payProgram
    *           the pay program
    * @return the pay program
    */
   public PayProgram updatePayProgram(PayProgram payProgram) {

      payProgram.setPrice(getPrice());
      payProgram.setState(states.valueOf(getState()));
      payProgram.setMethod(methods.valueOf(getMethod()));
      if (getAccountPayer() != "") {
         payProgram.setAccountPayer(getAccountPayer());
      }
      if (getIdTxn() != "") {
         payProgram.setIdTxn(getIdTxn());
      }
      if (getIdPayer() != "") {
         payProgram.setIdPayer(getIdPayer());
      }
      if (getEmailPayer() != "") {
         payProgram.setEmailPayer(getEmailPayer());
      }
      if (getIdPayer() != "") {
         payProgram.setIdPayer(getIdPayer());
      }
      if (getDatePay() != "") {
         payProgram.setDatePay(DisplayDate.stringToDateTime(getDatePay()));
      }
      return payProgram;
   }

}
