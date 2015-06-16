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
package org.cuacfm.members.web.signin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** The Class SigninController. */
@Controller
public class SigninController {

   /** The Constant SIGNIN_VIEW_NAME. */
   private static final String SIGNIN_VIEW_NAME = "signin/signin";

   /**
    * Instantiates a new Signin controller.
    */
   public SigninController() {
      // Default empty constructor.
   }

   /**
    * Signin.
    *
    * @return the string
    */
   @RequestMapping(value = "signin")
   public String signin() {
      return SIGNIN_VIEW_NAME;
   }
}
