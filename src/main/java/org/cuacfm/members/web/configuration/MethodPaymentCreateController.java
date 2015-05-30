package org.cuacfm.members.web.configuration;

import javax.validation.Valid;

import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class MethodPaymentCreateController. */
@Controller
public class MethodPaymentCreateController {

   /** The Constant METHODPAYMENT_VIEW_NAME. */
   private static final String METHODPAYMENT_VIEW_NAME = "configuration/methodpaymentcreate";

   /** The methodPaymentService. */
   @Autowired
   private MethodPaymentService methodPaymentService;

   /**
    * Instantiates a new methodPayment Controller.
    */
   public MethodPaymentCreateController() {
      // Default empty constructor.
   }

   /**
    * Get page methodPaymentCreate.
    *
    * @param model
    *           the model
    * @return String to view to methodPaymentCreate
    */
   @RequestMapping(value = "configuration/methodPaymentCreate")
   public String methodPayment(Model model) {
      model.addAttribute(new MethodPaymentForm());
      return METHODPAYMENT_VIEW_NAME;
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
    * @return String to redirect to configuration or if fault
    *         methodPaymentCreate
    * 
    */
   @RequestMapping(value = "configuration/methodPaymentCreate", method = RequestMethod.POST)
   public String methodPaymentCreate(@Valid @ModelAttribute MethodPaymentForm methodPaymentForm,
         Errors errors, RedirectAttributes ra) {

      if (errors.hasErrors()) {
         return METHODPAYMENT_VIEW_NAME;
      }

      try {
         methodPaymentService.save(methodPaymentForm.createMethodPayment());
      } catch (UniqueException e) {
         errors.rejectValue("name", "methodPayment.existentName", new Object[] { e.getValue() },
               "name");
         return METHODPAYMENT_VIEW_NAME;
      }
      MessageHelper.addSuccessAttribute(ra, "methodPayment.successCreate",
            methodPaymentForm.getName());
      return "redirect:/configuration";
   }
}
