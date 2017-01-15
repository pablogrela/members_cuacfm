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
package org.cuacfm.members.web.configuration;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.configuration.Configuration;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/** The Class ConfigurationForm. */
public class ConfigurationForm {

   /** The Constant NOT_BLANK_MESSAGE. */
   private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

   /** The Constant EMAIL_MESSAGE. */
   private static final String EMAIL_MESSAGE = "{email.message}";

   /** The Constant MAX_CHARACTERS. */
   private static final String MAX_CHARACTERS = "{max.characters}";

   /** The name. */
   @NotBlank(message = ConfigurationForm.NOT_BLANK_MESSAGE)
   @Size(max = 50, message = ConfigurationForm.MAX_CHARACTERS)
   private String name;

   /** The email. */
   @NotBlank(message = ConfigurationForm.NOT_BLANK_MESSAGE)
   @Email(message = ConfigurationForm.EMAIL_MESSAGE)
   @Size(max = 50, message = ConfigurationForm.MAX_CHARACTERS)
   private String email;

   /** The phone. */
   private int phone;

   @NotNull
   @Digits(fraction = 2, integer = 5)
   @DecimalMin("0.00")
   private Double feeMember;
   
   @NotNull
   @Digits(fraction = 2, integer = 5)
   @DecimalMin("0.00")
   private Double feeProgram;
   
   /** The descriptionRule. */
   @NotBlank(message = ConfigurationForm.NOT_BLANK_MESSAGE)
   @Size(max = 500, message = ConfigurationForm.MAX_CHARACTERS)
   private String descriptionRule;

   /** Instantiates a new training form. */
   public ConfigurationForm() {
      // Default empty constructor.
   }


   public String getName() {
      return name;
   }


   public void setName(String name) {
      this.name = name;
   }


   public String getEmail() {
      return email;
   }


   public void setEmail(String email) {
      this.email = email;
   }


   public int getPhone() {
      return phone;
   }


   public void setPhone(int phone) {
      this.phone = phone;
   }


   public Double getFeeMember() {
      return feeMember;
   }


   public void setFeeMember(Double feeMember) {
      this.feeMember = feeMember;
   }


   public Double getFeeProgram() {
      return feeProgram;
   }


   public void setFeeProgram(Double feeProgram) {
      this.feeProgram = feeProgram;
   }


   public String getDescriptionRule() {
      return descriptionRule;
   }


   public void setDescriptionRule(String descriptionRule) {
      this.descriptionRule = descriptionRule;
   }


   /**
    * Update account type.
    *
    * @param accountType
    *           the account type
    * @return the account type
    */
   public Configuration updateConfiguration(Configuration configuration) {
      configuration.setName(getName());
      configuration.setEmail(getEmail());
      configuration.setPhone(getPhone());
      configuration.setFeeMember(getFeeMember());
      configuration.setFeeProgram(getFeeProgram());
      configuration.setDescriptionRule(getDescriptionRule());
      return configuration;
   }
}
