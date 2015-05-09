package org.cuacfm.members.web.feeprogram;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.web.support.DisplayDate;
import org.hibernate.validator.constraints.NotBlank;

/** The Class FeeProgramForm. */
public class FeeProgramForm {

   /** The Constant NOT_BLANK_MESSAGE. */
   private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

   /** The Constant MAX_CHARACTERS. */
   private static final String MAX_CHARACTERS = "{max.characters}";

   /** The name. */
   @NotBlank(message = FeeProgramForm.NOT_BLANK_MESSAGE)
   @Size(max = 30, message = FeeProgramForm.MAX_CHARACTERS)
   private String name;

   /** The price. */
   @NotNull
   @Digits(fraction = 2, integer = 2)
   @DecimalMin("0.00")
   private Double price;

   /** The date. */
   @NotBlank(message = FeeProgramForm.NOT_BLANK_MESSAGE)
   private String date;

   /** The dateLimit. */
   @NotBlank(message = FeeProgramForm.NOT_BLANK_MESSAGE)
   private String dateLimit;

   /** The description. */
   @NotBlank(message = FeeProgramForm.NOT_BLANK_MESSAGE)
   @Size(max = 500, message = FeeProgramForm.MAX_CHARACTERS)
   private String description;

   /** Instantiates a new training form. */
   public FeeProgramForm() {
      // Default empty constructor.
   }

   /**
    * Gets the name.
    *
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the name.
    *
    * @param name
    *           the new name
    */
   public void setName(String name) {
      this.name = name;
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
    * Gets the date.
    *
    * @return the date
    */
   public String getDate() {
      return date;
   }

   /**
    * Sets the date.
    *
    * @param date
    *           the new date
    */
   public void setDate(String date) {
      this.date = date;
   }

   /**
    * Gets the date limit.
    *
    * @return the date limit
    */
   public String getDateLimit() {
      return dateLimit;
   }

   /**
    * Sets the date limit.
    *
    * @param dateLimit
    *           the new date limit
    */
   public void setDateLimit(String dateLimit) {
      this.dateLimit = dateLimit;
   }

   /**
    * Gets the description.
    *
    * @return the description
    */
   public String getDescription() {
      return description;
   }

   /**
    * Sets the description.
    *
    * @param description
    *           the new description
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Creates the training Type.
    *
    * @return FeeProgram
    */
   public FeeProgram createFeeProgram() {
      return new FeeProgram(getName(), getPrice(), DisplayDate.stringToMonthOfYear(getDate()),
            DisplayDate.stringToMonthOfYear(getDateLimit()), getDescription());
   }

   /**
    * Update fee program.
    *
    * @param feeProgram
    *           the fee program
    * @return the program
    */
   public FeeProgram updateFeeProgram(FeeProgram feeProgram) {
      feeProgram.setName(getName());
      feeProgram.setPrice(getPrice());
      feeProgram.setDate(DisplayDate.stringToMonthOfYear(getDate()));
      feeProgram.setDateLimit(DisplayDate.stringToMonthOfYear(getDateLimit()));
      feeProgram.setDescription(getDescription());
      return feeProgram;
   }
}
