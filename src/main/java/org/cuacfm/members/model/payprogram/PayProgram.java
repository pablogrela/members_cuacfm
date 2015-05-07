package org.cuacfm.members.model.payprogram;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.program.Program;

/** The Class PayProgram. */
@SuppressWarnings("serial")
@Entity
public class PayProgram implements java.io.Serializable {

   /** The id. */
   @Id
   @GeneratedValue
   private Long id;

   /** The program. */
   @ManyToOne(optional = false, fetch = FetchType.EAGER)
   @JoinColumn(name = "programId")
   private Program program;

   /** The feeProgram. */
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   @JoinColumn(name = "feeProgramId")
   private FeeProgram feeProgram;

   /** The price. */
   private Double price;

   /** The has pay. */
   private boolean hasPay;

   /** The account payer. */
   private String accountPayer;

   /** The id payer. */
   private String idPayer;

   /** The id transaction. */
   @Column(unique = true)
   private String idTxn;

   /** The email payer. */
   private String emailPayer;

   /** The status pay. */
   private String statusPay;

   /** The date pay. */
   private Date datePay;

   /**
    * Instantiates a new pay inscription.
    */
   public PayProgram() {
      // Default empty constructor.
   }

   /**
    * Instantiates a new pay program.
    *
    * @param program
    *           the program
    * @param feeProgram
    *           the fee program
    * @param price
    *           the price
    */
   public PayProgram(Program program, FeeProgram feeProgram, Double price) {
      super();
      this.program = program;
      this.feeProgram = feeProgram;
      this.price = price;
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
    * Gets the program.
    *
    * @return the program
    */
   public Program getProgram() {
      return program;
   }

   /**
    * Gets the fee program.
    *
    * @return the fee program
    */
   public FeeProgram getFeeProgram() {
      return feeProgram;
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
   public boolean isHasPay() {
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

}
