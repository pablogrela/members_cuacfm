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

import javax.validation.Valid;

import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class MethodPaymentEditController. */
@Controller
public class MethodPaymentEditController {

   /** The Constant METHODPAYMENT_VIEW_NAME. */
   private static final String METHODPAYMENT_VIEW_NAME = "configuration/methodpaymentedit";

   /** The methodPaymentService. */
   @Autowired
   private MethodPaymentService methodPaymentService;

   /** The Global variable methodPayment Type. */
   private MethodPayment methodPayment;

   /** Instantiates a new methodPayment Controller. */
   public MethodPaymentEditController() {
      // Default empty constructor.
   }

   /**
    * Get page methodPaymentEdit.
    *
    * @param model
    *           the model
    * @return String to view to methodPaymentEdit
    */
   @RequestMapping(value = "configuration/methodPaymentEdit")
   public String methodPayment(Model model) {
      if (methodPayment != null) {
         MethodPaymentForm methodPaymentForm = new MethodPaymentForm();
         methodPaymentForm.setName(methodPayment.getName());
         methodPaymentForm.setDirectDebit(methodPayment.isDirectDebit());
         methodPaymentForm.setDescription(methodPayment.getDescription());
         model.addAttribute(methodPaymentForm);
         return METHODPAYMENT_VIEW_NAME;
      }
      // If not have methodPayment, redirect to configuration
      else {
         return "redirect:/configuration";
      }
   }

   /**
    * Post to create a new methodPayment.
    *
    * @param methodPaymentForm
    *           the methodPayment form
    * @param errors
    *           the errors
    * @param ra
    *           the ra
    * @return String to redirect to methodPaymentList or if fault
    *         methodPaymentEdit
    * 
    */
   @RequestMapping(value = "configuration/methodPaymentEdit", method = RequestMethod.POST)
   public String methodPaymentEdit(@Valid @ModelAttribute MethodPaymentForm methodPaymentForm,
         Errors errors, RedirectAttributes ra) {

      if (errors.hasErrors()) {
         return METHODPAYMENT_VIEW_NAME;
      }

      try {
         methodPaymentService.update(methodPaymentForm.updateMethodPayment(methodPayment));
      } catch (UniqueException e) {
         errors.rejectValue("name", "methodPayment.existentName", new Object[] { e.getValue() },
               "name");
         return METHODPAYMENT_VIEW_NAME;
      }
      MessageHelper.addWarningAttribute(ra, "methodPayment.successModify",
            methodPaymentForm.getName());
      return "redirect:/configuration";
   }

   /**
    * Charge method payment.
    *
    * @param id
    *           the id
    * @return the string
    */
   @RequestMapping(value = "configuration/methodPaymentEdit/{id}", method = RequestMethod.POST)
   public String chargeMethodPayment(@PathVariable Long id) {

      methodPayment = methodPaymentService.findById(id);
      return "redirect:/configuration/methodPaymentEdit";
   }
}
