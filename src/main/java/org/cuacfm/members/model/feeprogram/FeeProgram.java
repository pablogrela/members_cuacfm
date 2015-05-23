package org.cuacfm.members.model.feeprogram;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class FeeProgram. */
@SuppressWarnings("serial")
@Entity
public class FeeProgram implements Serializable {

   /** The id. */
   @Id
   @GeneratedValue
   private Long id;

   /** The name. */
   private String name;

   /** The duration. */
   private Double price;

   /** The date. */
   @Column(unique = true)
   private Date date;

   /** The dateLimit. */
   private Date dateLimit;

   /** The description. */
   private String description;

   /**
    * Instantiates a new pay inscription.
    */
   public FeeProgram() {
      // Default empty constructor.
   }

   /**
    * Instantiates a new pay inscription.
    *
    * @param name
    *           the name
    * @param price
    *           the price
    * @param date
    *           the date
    * @param dateLimit
    *           the date limit
    * @param description
    *           the description
    */
   public FeeProgram(String name, Double price, Date date, Date dateLimit, String description) {
      super();
      this.name = name;
      this.price = price;
      this.date = date;
      this.dateLimit = dateLimit;
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
   public Date getDate() {
      return date;
   }

   /**
    * Sets the date.
    *
    * @param date
    *           the new date
    */
   public void setDate(Date date) {
      this.date = date;
   }

   /**
    * Gets the date limit.
    *
    * @return the date limit
    */
   public Date getDateLimit() {
      return dateLimit;
   }

   /**
    * Sets the date limit.
    *
    * @param dateLimit
    *           the new date limit
    */
   public void setDateLimit(Date dateLimit) {
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

}
