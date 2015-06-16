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
package org.cuacfm.members.web.home;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** The Class HomeController. */
@Controller
public class HomeController {

   /**
    * Version preliminar
    * 
    * @RequestMapping(value = "/", method = RequestMethod.GET) public String
    *                       index(Principal principal) { return principal !=
    *                       null ? "home/homeSignedIn" : "home/homeNotSignedIn";
    *                       }
    **/

   /**
    * Index.
    *
    * @param principal
    *           the principal
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String index(Principal principal) {

      if (principal != null) {
         return "home/homeSignedIn";
      } else {
         return "home/homeNotSignedIn";
      }
   }
}
