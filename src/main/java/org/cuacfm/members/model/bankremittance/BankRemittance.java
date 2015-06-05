package org.cuacfm.members.model.bankremittance;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.cuacfm.members.model.util.States.states;

/** The Class BankRemittance. */
@SuppressWarnings("serial")
@Entity
public class BankRemittance implements Serializable {

   /** The id. */
   @Id
   @GeneratedValue
   private Long id;

   /** The date debit. */
   private Date dateDebit;

   /** The date charge. */
   private Date dateCharge;

   /** The month charge. */
   private Date monthCharge;

   /** The State. */
   private states state;

   /**
    * Instantiates a new bank remittance.
    */
   protected BankRemittance() {
      // Default empty constructor.
   }

   /**
    * Instantiates a new bank remittance.
    *
    * @param dateCharge
    *           the date charge
    * @param monthCharge
    *           the month charge
    */
   public BankRemittance(Date dateCharge, Date monthCharge) {
      super();
      this.dateDebit = new Date();
      this.monthCharge = monthCharge;
      this.dateCharge = dateCharge;
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
    * Gets the date debit.
    *
    * @return the date debit
    */
   public Date getDateDebit() {
      return dateDebit;
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
    * Gets the month charge.
    *
    * @return the month charge
    */
   public Date getMonthCharge() {
      return monthCharge;
   }
}
