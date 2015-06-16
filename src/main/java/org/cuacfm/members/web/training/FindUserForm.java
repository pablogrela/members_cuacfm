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
package org.cuacfm.members.web.training;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/** The Class FindUserForm. */
public class FindUserForm {

   /** The Constant NOT_BLANK_MESSAGE. */
   private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
   /** The Constant MAX_CHARACTERS. */
   private static final String MAX_CHARACTERS = "{max.characters}";

   /** The login. */
   @NotBlank(message = FindUserForm.NOT_BLANK_MESSAGE)
   @Size(max = 50, message = FindUserForm.MAX_CHARACTERS)
   private String login;

   /**
    * Instantiates a new FindUserForm.
    */
   public FindUserForm() {
      // Default empty constructor.
   }

   /**
    * Gets the login.
    *
    * @return the login
    */
   public String getLogin() {
      return login;
   }

   /**
    * Sets the login.
    *
    * @param login
    *           String, the new login
    */
   public void setLogin(String login) {
      this.login = login;
   }
}
