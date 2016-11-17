/**
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.web.feemember;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.web.support.DisplayDate;
import org.hibernate.validator.constraints.NotBlank;

/** The Class FeeMemberForm. */
public class FeeMemberForm {

   /** The Constant NOT_BLANK_MESSAGE. */
   private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

   /** The Constant MAX_CHARACTERS. */
   private static final String MAX_CHARACTERS = "{max.characters}";

   /** The name. */
   @NotBlank(message = FeeMemberForm.NOT_BLANK_MESSAGE)
   @Size(max = 40, message = FeeMemberForm.MAX_CHARACTERS)
   private String name;

   /** The year. */
   @NotNull
   @Min(2015)
   private Integer year;

   /** The price. */
   @NotNull
   @Digits(fraction = 2, integer = 2)
   @DecimalMin("0.00")
   private Double price;

   /** The dateLimit1. */
   @NotBlank(message = FeeMemberForm.NOT_BLANK_MESSAGE)
   private String dateLimit1;

   /** The dateLimit2. */
   @NotBlank(message = FeeMemberForm.NOT_BLANK_MESSAGE)
   private String dateLimit2;

   /** The description. */
   @NotBlank(message = FeeMemberForm.NOT_BLANK_MESSAGE)
   @Size(max = 500, message = FeeMemberForm.MAX_CHARACTERS)
   private String description;

   /** Instantiates a new training form. */
   public FeeMemberForm() {
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
    * Gets the year.
    *
    * @return the year
    */
   public Integer getYear() {
      return year;
   }

   /**
    * Sets the year.
    *
    * @param year
    *           the new year
    */
   public void setYear(Integer year) {
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
   public String getDateLimit1() {
      return dateLimit1;
   }

   /**
    * Sets the date limit1.
    *
    * @param dateLimit1
    *           the new date limit1
    */
   public void setDateLimit1(String dateLimit1) {
      this.dateLimit1 = dateLimit1;
   }

   /**
    * Gets the date limit2.
    *
    * @return the date limit2
    */
   public String getDateLimit2() {
      return dateLimit2;
   }

   /**
    * Sets the date limit2.
    *
    * @param dateLimit2
    *           the new date limit2
    */
   public void setDateLimit2(String dateLimit2) {
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

   /**
    * Creates the training Type.
    *
    * @return FeeMember
    */
   public FeeMember createFeeMember() {
      return new FeeMember(getName(), getYear(), getPrice(),
            DisplayDate.stringToMonthOfYear(dateLimit1),
            DisplayDate.stringToMonthOfYear(dateLimit2), getDescription());
   }

   /**
    * Creates the fee member.
    *
    * @param feeMember
    *           the fee member
    * @return the fee member
    */
   public FeeMember updateFeeMember(FeeMember feeMember) {
      feeMember.setName(getName());
      feeMember.setYear(getYear());
      feeMember.setPrice(getPrice());
      feeMember.setDescription(getDescription());
      feeMember.setDateLimit1(DisplayDate.stringToMonthOfYear(getDateLimit1()));
      feeMember.setDateLimit2(DisplayDate.stringToMonthOfYear(getDateLimit2()));
      return feeMember;
   }
}
