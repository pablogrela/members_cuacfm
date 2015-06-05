package org.cuacfm.members.model.paymember;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.util.States.methods;
import org.cuacfm.members.model.util.States.states;

/** The Class PayMember. */
@SuppressWarnings("serial")
@Entity
public class PayMember implements Serializable {

   /** The id. */
   @Id
   @GeneratedValue
   private Long id;

   /** The account. */
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   @JoinColumn(name = "accountId")
   private Account account;

   /** The feeMember. */
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   @JoinColumn(name = "feeMemberId")
   private FeeMember feeMember;

   // @ManyToOne(optional = false, fetch = FetchType.LAZY)
   // @JoinColumn(name = "directDebitId")
   // private DirectDebit directDebit

   /** The price. */
   private Double price;

   /** The State. */
   private states state;

   /** The method. */
   private methods method;

   /** The installment. */
   private int installment;

   /** The installment. */
   private int installments;

   /** The id payer. */
   private String idPayer;

   /** The id txn. */
   @Column(unique = true)
   private String idTxn;

   /** The email payer. */
   private String emailPayer;

   /** The date pay. */
   private Date datePay;

   /** The date charge. */
   private Date dateCharge;

   /**
    * Instantiates a pay fee member.
    */
   public PayMember() {
      // Default empty constructor.
   }

   /**
    * Instantiates a new user fee member.
    *
    * @param account
    *           the account
    * @param feeMember
    *           the fee member
    * @param price
    *           the price
    * @param installment
    *           the installment
    * @param installments
    *           the installments
    * @param dateCharge
    *           the date charge
    */
   public PayMember(Account account, FeeMember feeMember, Double price, int installment,
         int installments, Date dateCharge) {
      super();
      this.account = account;
      this.feeMember = feeMember;
      this.price = price;
      this.installment = installment;
      this.installments = installments;
      this.dateCharge = dateCharge;
      this.state = states.NO_PAY;
      this.method = methods.NO_PAY;
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
    * Gets the fee member.
    *
    * @return the fee member
    */
   public FeeMember getFeeMember() {
      return feeMember;
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
    * Gets the method.
    *
    * @return the method
    */
   public methods getMethod() {
      return method;
   }

   /**
    * Sets the method.
    *
    * @param method
    *           the new method
    */
   public void setMethod(methods method) {
      this.method = method;
   }

   /**
    * Gets the date pay.
    *
    * @return the date pay
    */
   public Date getDatePay() {
      return datePay;
   }

   /**
    * Sets the date pay.
    *
    * @param datePay
    *           the new date pay
    */
   public void setDatePay(Date datePay) {
      this.datePay = datePay;
   }

   /**
    * Gets the date charge.
    *
    * @return the date charge
    */
   public Date getDateCharge() {
      return dateCharge;
   }

   /**
    * Sets the date charge.
    *
    * @param dateCharge
    *           the new date charge
    */
   public void setDateCharge(Date dateCharge) {
      this.dateCharge = dateCharge;
   }

}
