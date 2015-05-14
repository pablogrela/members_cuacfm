package org.cuacfm.members.model.feemember;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class FeeMember. */
@SuppressWarnings("serial")
@Entity
public class FeeMember implements java.io.Serializable {

   /** The id. */
   @Id
   @GeneratedValue
   private Long id;

   /** The name. */
   private String name;

   /** The year. */
   private int year;

   /** The duration. */
   private Double price;

   /** The dateLimit1. */
   private Date dateLimit1;

   /** The dateLimit2. */
   private Date dateLimit2;

   /** The description. */
   private String description;

   /**
    * Instantiates a new fee member.
    */
   public FeeMember() {
      // Default empty constructor.
   }

   /**
    * Instantiates a new fee member.
    *
    * @param name
    *           the name
    * @param year
    *           the year
    * @param price
    *           the price
    * @param dateLimit1
    *           the date limit1
    * @param dateLimit2
    *           the date limit2
    * @param description
    *           the description
    */
   public FeeMember(String name, int year, Double price, Date dateLimit1, Date dateLimit2,
         String description) {
      super();
      this.name = name;
      this.year = year;
      this.price = price;
      this.dateLimit1 = dateLimit1;
      this.dateLimit2 = dateLimit2;
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
    * Sets the id.
    *
    * @param id
    *           the new id
    */
   // If necessary to probe test in Junit, because is necessary one object in
   // detach
   public void setId(Long id) {
      this.id = id;
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
    * Gets the year.
    *
    * @return the year
    */
   public int getYear() {
      return year;
   }

   /**
    * Sets the year.
    *
    * @param year
    *           the new year
    */
   public void setYear(int year) {
      this.year = year;
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
    * Gets the date limit1.
    *
    * @return the date limit1
    */
   public Date getDateLimit1() {
      return dateLimit1;
   }

   /**
    * Sets the date limit1.
    *
    * @param dateLimit1
    *           the new date limit1
    */
   public void setDateLimit1(Date dateLimit1) {
      this.dateLimit1 = dateLimit1;
   }

   /**
    * Gets the date limit2.
    *
    * @return the date limit2
    */
   public Date getDateLimit2() {
      return dateLimit2;
   }

   /**
    * Sets the date limit2.
    *
    * @param dateLimit2
    *           the new date limit2
    */
   public void setDateLimit2(Date dateLimit2) {
      this.dateLimit2 = dateLimit2;
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
