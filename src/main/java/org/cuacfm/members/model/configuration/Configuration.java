package org.cuacfm.members.model.configuration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class Configuration. */
@SuppressWarnings("serial")
@Entity
public class Configuration implements java.io.Serializable {

   /** The id. */
   @Id
   @GeneratedValue
   private Long id;

   /** The name. */
   private String name;

   /** The Email. */
   private String email;

   /** The phone. */
   private int phone;

   /** The fee member. */
   private Double feeMember;

   /** The fee program. */
   private Double feeProgram;

   /** The description. */
   private String descriptionRule;

   /** Instantiates a new account type. */
   protected Configuration() {
      // Default empty constructor.
   }

   /**
    * Instantiates a new configuration.
    *
    * @param name
    *           the name
    * @param email
    *           the email
    * @param phone
    *           the phone
    * @param feeMember
    *           the fee member
    * @param feeProgram
    *           the fee program
    * @param descriptionRule
    *           the description rul
    */
   public Configuration(String name, String email, int phone, Double feeMember, Double feeProgram,
         String descriptionRule) {
      super();
      this.name = name;
      this.email = email;
      this.phone = phone;
      this.feeMember = feeMember;
      this.feeProgram = feeProgram;
      this.descriptionRule = descriptionRule;
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
    * Gets the email.
    *
    * @return the email
    */
   public String getEmail() {
      return email;
   }

   /**
    * Sets the email.
    *
    * @param email
    *           the new email
    */
   public void setEmail(String email) {
      this.email = email;
   }

   /**
    * Gets the phone.
    *
    * @return the phone
    */
   public int getPhone() {
      return phone;
   }

   /**
    * Sets the phone.
    *
    * @param phone
    *           the new phone
    */
   public void setPhone(int phone) {
      this.phone = phone;
   }

   /**
    * Gets the fee member.
    *
    * @return the fee member
    */
   public Double getFeeMember() {
      return feeMember;
   }

   /**
    * Sets the fee member.
    *
    * @param feeMember
    *           the new fee member
    */
   public void setFeeMember(Double feeMember) {
      this.feeMember = feeMember;
   }

   /**
    * Gets the fee program.
    *
    * @return the fee program
    */
   public Double getFeeProgram() {
      return feeProgram;
   }

   /**
    * Sets the fee program.
    *
    * @param feeProgram
    *           the new fee program
    */
   public void setFeeProgram(Double feeProgram) {
      this.feeProgram = feeProgram;
   }

   /**
    * Gets the description rule.
    *
    * @return the description rule
    */
   public String getDescriptionRule() {
      return descriptionRule;
   }

   /**
    * Sets the description rule.
    *
    * @param descriptionRule
    *           the new description rule
    */
   public void setDescriptionRule(String descriptionRule) {
      this.descriptionRule = descriptionRule;
   }

}
