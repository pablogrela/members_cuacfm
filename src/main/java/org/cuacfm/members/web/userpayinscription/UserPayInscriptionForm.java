package org.cuacfm.members.web.userpayinscription;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.userpayinscription.UserPayInscription;
import org.cuacfm.members.web.support.DisplayDate;

/** The Class UserPayInscriptionForm. */
public class UserPayInscriptionForm {

   /** The Constant MAX_CHARACTERS. */
   private static final String MAX_CHARACTERS = "{max.characters}";

   /** The price. */
   @Digits(fraction = 2, integer = 2)
   @DecimalMin("0.00")
   private Double price;

   /** The has pay. */
   private boolean hasPay;

   /** The installment. */
   @Min(1)
   @Max(12)
   private int installment;

   /** The installments. */
   @Min(1)
   @Max(12)
   private int installments;

   /** The id payer. */
   @Size(max = 30, message = UserPayInscriptionForm.MAX_CHARACTERS)
   private String idPayer;

   /** The id txn. */
   @Size(max = 30, message = UserPayInscriptionForm.MAX_CHARACTERS)
   private String idTxn;

   /** The email payer. */
   @Size(max = 30, message = UserPayInscriptionForm.MAX_CHARACTERS)
   private String emailPayer;

   /** The status pay. */
   @Size(max = 30, message = UserPayInscriptionForm.MAX_CHARACTERS)
   private String statusPay;

   /** The date pay. */
   @Size(max = 30, message = UserPayInscriptionForm.MAX_CHARACTERS)
   private String datePay;

   /** Instantiates a new user pay inscription form. */
   public UserPayInscriptionForm() {
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
    * Checks if is checks for pay.
    *
    * @return true, if is checks for pay
    */
   public boolean getHasPay() {
      return hasPay;
   }

   /**
    * Sets the checks for pay.
    *
    * @param hasPay
    *           the new checks for pay
    */
   public void setHasPay(boolean hasPay) {
      this.hasPay = hasPay;
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
    * Update user pay inscription.
    *
    * @param userPayInscription
    *           the user pay inscription
    * @return the user pay inscription
    */
   public UserPayInscription updateUserPayInscription(UserPayInscription userPayInscription) {

      userPayInscription.setPrice(getPrice());
      userPayInscription.setHasPay(getHasPay());
      userPayInscription.setInstallment(getInstallment());
      userPayInscription.setInstallments(getInstallments());
      if (getIdTxn() != "") {
         userPayInscription.setIdTxn(getIdTxn());
      }
      if (getIdPayer() != "") {
         userPayInscription.setIdPayer(getIdPayer());
      }
      if (getEmailPayer() != "") {
         userPayInscription.setEmailPayer(getEmailPayer());
      }
      if (getStatusPay() != "") {
         userPayInscription.setStatusPay(getStatusPay());
      }
      if (getIdPayer() != "") {
         userPayInscription.setIdPayer(getIdPayer());
      }
      if (getDatePay() != "") {
         userPayInscription.setDatePay(DisplayDate.stringToDateTime(getDatePay()));
      }
      return userPayInscription;
   }

}
