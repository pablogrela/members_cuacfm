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
package org.cuacfm.members.web.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;

/** General error handler for the application. */
@ControllerAdvice
class ExceptionHandler {

   /** Instantiates a new exception handler. */
   public ExceptionHandler() {
      super();
   }

   /**
    * Handle exceptions thrown by handlers.
    *
    * @param exception
    *           the exception
    * @return the model and view
    */
   @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
   public ModelAndView exception(Exception exception) {
      ModelAndView modelAndView = new ModelAndView("error/general");
      modelAndView.addObject("errorMessage", Throwables.getRootCause(exception));
      return modelAndView;
   }
}