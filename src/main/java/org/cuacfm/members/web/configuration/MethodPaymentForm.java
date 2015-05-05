package org.cuacfm.members.web.configuration;

import javax.validation.constraints.Size;

import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.hibernate.validator.constraints.NotBlank;

/** The Class MethodPaymentForm. */
public class MethodPaymentForm {

   /** The Constant NOT_BLANK_MESSAGE. */
   private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

   /** The Constant MAX_CHARACTERS. */
   private static final String MAX_CHARACTERS = "{max.characters}";

   /** The name. */
   @NotBlank(message = MethodPaymentForm.NOT_BLANK_MESSAGE)
   @Size(max = 30, message = MethodPaymentForm.MAX_CHARACTERS)
   private String name;

   /** The description. */
   @NotBlank(message = MethodPaymentForm.NOT_BLANK_MESSAGE)
   @Size(max = 500, message = MethodPaymentForm.MAX_CHARACTERS)
   private String description;

   /** Instantiates a new training form. */
   public MethodPaymentForm() {
      // Default empty constructor.
   }

   /**
    * Get the name.
    *
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * Set the name.
    *
    * @param name
    *           String, the new name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Get the description..
    *
    * @return the description
    */
   public String getDescription() {
      return description;
   }

   /**
    * Set the description.
    *
    * @param description
    *           String, the new description
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Creates the account type.
    *
    * @return the account type
    */
   public MethodPayment createMethodPayment() {
      return new MethodPayment(getName(), getDescription());
   }

   /**
    * Update account type.
    *
    * @param methodPayment
    *           the account type
    * @return the account type
    */
   public MethodPayment updateMethodPayment(MethodPayment methodPayment) {
      methodPayment.setName(getName());
      methodPayment.setDescription(getDescription());
      return methodPayment;
   }
}
