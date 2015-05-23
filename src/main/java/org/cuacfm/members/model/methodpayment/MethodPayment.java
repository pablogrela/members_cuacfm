package org.cuacfm.members.model.methodpayment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class MethodPayment. */
@SuppressWarnings("serial")
@Entity
public class MethodPayment implements Serializable {

   /** The id. */
   @Id
   @GeneratedValue
   private Long id;

   /** The name. */
   @Column(unique = true)
   private String name;

   /** The direct debit. */
   private boolean directDebit;

   /** The description. */
   private String description;

   /**
    * Instantiates a new method payment.
    */
   protected MethodPayment() {
      // Default empty constructor.
   }

   /**
    * Instantiates a new method payment.
    *
    * @param name
    *           the name
    * @param directDebit
    *           the direct debit
    * @param description
    *           the description
    */
   public MethodPayment(String name, boolean directDebit, String description) {
      super();
      this.name = name;
      this.directDebit = directDebit;
      this.description = description;
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
    * Checks if is direct debit.
    *
    * @return true, if is direct debit
    */
   public boolean isDirectDebit() {
      return directDebit;
   }

   /**
    * Sets the direct debit.
    *
    * @param directDebit
    *           the new direct debit
    */
   public void setDirectDebit(boolean directDebit) {
      this.directDebit = directDebit;
   }

}
