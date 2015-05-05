package org.cuacfm.members.web.payinscription;

import java.util.List;

import org.cuacfm.members.model.payinscription.PayInscription;
import org.cuacfm.members.model.payinscriptionservice.PayInscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/** The Class PayInscriptionListController. */
@Controller
public class PayInscriptionListController {

   /** The Constant PAYINSCRIPTION_VIEW_NAME. */
   private static final String PAYINSCRIPTION_VIEW_NAME = "payinscription/payinscriptionlist";

   /** The PayInscriptionService. */
   @Autowired
   private PayInscriptionService payInscriptionService;

   /** The payInscriptions. */
   private List<PayInscription> payInscriptions;

   /**
    * Instantiates a new pay Inscription List Controller.
    */
   public PayInscriptionListController() {
      // Default empty constructor.
   }

   /**
    * List of PayInscription.
    *
    * @return List<PayInscription>
    */
   @ModelAttribute("payInscriptions")
   public List<PayInscription> payInscriptions() {
      return payInscriptions;
   }

   /**
    * Show PayInscription List.
    *
    * @param model
    *           the model
    * @return the string the view
    */

   @RequestMapping(value = "payInscriptionList")
   public String payInscriptions(Model model) {
      payInscriptions = payInscriptionService.getPayInscriptionList();
      model.addAttribute("payInscriptions", payInscriptions);
      return PAYINSCRIPTION_VIEW_NAME;
   }

}
