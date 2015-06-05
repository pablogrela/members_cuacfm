package org.cuacfm.members.web.bankremittance;

import org.hibernate.validator.constraints.NotBlank;

/** The Class ChargeForm. */
public class BankRemittanceForm {

   /** The Constant NOT_BLANK_MESSAGE. */
   private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

   /** The dateCharge. */
   @NotBlank(message = BankRemittanceForm.NOT_BLANK_MESSAGE)
   private String dateCharge;

   /** The dateCharge. */
   @NotBlank(message = BankRemittanceForm.NOT_BLANK_MESSAGE)
   private String monthCharge;

   /**
    * Instantiates a new bank remittance form.
    */
   public BankRemittanceForm() {
      // Default empty constructor.
   }

   /**
    * Get the dateTrinig.
    *
    * @return Calendar with dateCharge
    */
   public String getDateCharge() {
      return dateCharge;
   }

   /**
    * Set the dateCharge.
    *
    * @param dateCharge
    *           String, Calendar
    */
   public void setDateCharge(String dateCharge) {
      this.dateCharge = dateCharge;
   }

   /**
    * Gets the month charge.
    *
    * @return the month charge
    */
   public String getMonthCharge() {
      return monthCharge;
   }

   /**
    * Sets the month charge.
    *
    * @param monthCharge
    *           the new month charge
    */
   public void setMonthCharge(String monthCharge) {
      this.monthCharge = monthCharge;
   }

}
