package org.cuacfm.members.web.bankremittance;

import java.util.List;

import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.bankremittanceservice.BankRemittanceService;
import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class DirectDebitListController. */
@Controller
public class DirectDebitListController {

   /** The Constant DIRECTDEBIT_VIEW_NAME. */
   private static final String DIRECTDEBIT_VIEW_NAME = "bankremittance/directdebitlist";

   /** The Constant REDIRECT_DIRECTDEBIT. */
   private static final String REDIRECT_DIRECTDEBIT = "redirect:/bankRemittanceList/directDebitList";

   /** The DirectDebitService. */
   @Autowired
   private BankRemittanceService bankRemittanceService;

   /** The directDebits. */
   private List<DirectDebit> directDebits;

   /** The bank remittance. */
   private BankRemittance bankRemittance;


   /**
    * Instantiates a new direct debit list controller.
    */
   public DirectDebitListController() {
      // Default empty constructor.
   }

   /**
    * List of DirectDebit.
    *
    * @return List<DirectDebit>
    */
   @ModelAttribute("directDebits")
   public List<DirectDebit> directDebits() {
      return directDebits;
   }


   /**
    * Direct debits.
    *
    * @param model the model
    * @return the string
    */
   @RequestMapping(value = "bankRemittanceList/directDebitList")
   public String directDebits(Model model) {
      if (bankRemittance != null) {
         directDebits = bankRemittanceService.getDirectDebitListByBankRemittanceId(bankRemittance
               .getId());
         model.addAttribute("directDebits", directDebits);
         model.addAttribute(new BankRemittanceForm());
         model.addAttribute("bankRemittance", bankRemittance);
         return DIRECTDEBIT_VIEW_NAME;
      } else {
         return "redirect:/bankRemittanceList";
      }

   }


   /**
    * Charge direct debit.
    *
    * @param bankRemittanceId the bank remittance id
    * @return the string
    */
   @RequestMapping(value = "bankRemittanceList/directDebitList/{bankRemittanceId}", method = RequestMethod.POST)
   public String chargeDirectDebit(@PathVariable Long bankRemittanceId) {
      bankRemittance = bankRemittanceService.findById(bankRemittanceId);
      return REDIRECT_DIRECTDEBIT;
   }


   /**
    * Pay direct debit.
    *
    * @param directDebitId the direct debit id
    * @param ra the ra
    * @return the string
    * @throws ExistTransactionIdException the exist transaction id exception
    */
   @RequestMapping(value = "bankRemittanceList/directDebitList/pay/{directDebitId}", method = RequestMethod.POST)
   public String payDirectDebit(@PathVariable Long directDebitId, RedirectAttributes ra) throws ExistTransactionIdException {
      DirectDebit directDebit = bankRemittanceService.findByDirectDebitId(directDebitId);
      bankRemittanceService.payDirectDebit(directDebit.getId());

      MessageHelper.addSuccessAttribute(ra, "directDebit.successPay", directDebit.getConcept()
            + " " + directDebit.getAccount().getName());
      return REDIRECT_DIRECTDEBIT;
   }


   /**
    * Return bill direct debit.
    *
    * @param directDebitId the direct debit id
    * @param ra the ra
    * @return the string
    * @throws ExistTransactionIdException the exist transaction id exception
    */
   @RequestMapping(value = "bankRemittanceList/directDebitList/returnBill/{directDebitId}", method = RequestMethod.POST)
   public String returnBillDirectDebit(@PathVariable Long directDebitId, RedirectAttributes ra) throws ExistTransactionIdException {
      DirectDebit directDebit = bankRemittanceService.findByDirectDebitId(directDebitId);
      bankRemittanceService.returnBill(directDebit.getId());

      MessageHelper.addSuccessAttribute(ra, "directDebit.successReturnBill",
            directDebit.getConcept() + " " + directDebit.getAccount().getName());
      return REDIRECT_DIRECTDEBIT;
   }
}
