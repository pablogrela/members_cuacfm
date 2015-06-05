package org.cuacfm.members.web.bankremittance;

import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.bankremittanceservice.BankRemittanceService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.web.support.DisplayDate;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class BankRemittanceListController. */
@Controller
public class BankRemittanceListController {

   /** The Constant BANKREMITTANCE_VIEW_NAME. */
   private static final String BANKREMITTANCE_VIEW_NAME = "bankremittance/bankremittancelist";

   /** The Constant BANKREMITTANCE_DIRECTDEBIT. */
   private static final String REDIRECT_BANKREMITTANCE = "redirect:/bankRemittanceList";

   /** The message source. */
   @Autowired
   private MessageSource messageSource;

   /** The BankRemittanceService. */
   @Autowired
   private BankRemittanceService bankRemittanceService;

   /** The bankRemittances. */
   private List<BankRemittance> bankRemittances;

   /**
    * Instantiates a new training Controller.
    */
   public BankRemittanceListController() {
      // Default empty constructor.
   }
   
   /**
    * List of BankRemittance.
    *
    * @return List<BankRemittance>
    */
   @ModelAttribute("bankRemittances")
   public List<BankRemittance> bankRemittances() {
      return bankRemittances;
   }

   /**
    * Show BankRemittance List.
    *
    * @param model
    *           the model
    * @return the string the view
    */
   @RequestMapping(value = "bankRemittanceList")
   public String bankRemittances(Model model) {
      bankRemittances = bankRemittanceService.getBankRemittanceList();
      model.addAttribute("bankRemittances", bankRemittances);
      model.addAttribute(new BankRemittanceForm());
      return BANKREMITTANCE_VIEW_NAME;
   }

   @RequestMapping(value = "bankRemittanceList/management/{bankRemittanceId}", method = RequestMethod.POST)
   public String managementBankRemittance(@PathVariable Long bankRemittanceId, RedirectAttributes ra) throws ExistTransactionIdException {
      BankRemittance bankRemittance = bankRemittanceService.findById(bankRemittanceId);
      bankRemittanceService.managementBankRemittance(bankRemittance.getId());

      MessageHelper.addSuccessAttribute(ra, "bankRemittance.successManagement", DisplayDate.dateToString(bankRemittance.getDateCharge()));
      return REDIRECT_BANKREMITTANCE;
   }
   
   /**
    * Pay.
    *
    * @param bankRemittanceId
    *           the bank remittance id
    * @param ra
    *           the ra
    * @return the string
    * @throws ExistTransactionIdException 
    */
   @RequestMapping(value = "bankRemittanceList/pay/{bankRemittanceId}", method = RequestMethod.POST)
   public String payBankRemittance(@PathVariable Long bankRemittanceId, RedirectAttributes ra) throws ExistTransactionIdException {
      BankRemittance bankRemittance = bankRemittanceService.findById(bankRemittanceId);
      bankRemittanceService.payBankRemittance(bankRemittance.getId());

      MessageHelper.addSuccessAttribute(ra, "bankRemittance.successPay", DisplayDate.dateToString(bankRemittance.getDateCharge()));
      return REDIRECT_BANKREMITTANCE;
   }

   /**
    * Download bank remittance.
    *
    * @param bankRemittanceId
    *           the bank remittance id
    * @return the response entity
    */
   @RequestMapping(value = "bankRemittanceList/downloadBankRemittance/{bankRemittanceId}", method = RequestMethod.POST)
   public ResponseEntity<byte[]> downloadBankRemittance(@PathVariable Long bankRemittanceId) {
      return bankRemittanceService.createTxtBankRemittance(messageSource, bankRemittanceId);
   }

   /**
    * Creates the bank remittance.
    *
    * @param bankRemittanceForm
    *           the bank remittance form
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "bankRemittanceList", method = RequestMethod.POST)
   public String createBankRemittance(@Valid @ModelAttribute BankRemittanceForm bankRemittanceForm,
         RedirectAttributes ra) {

      bankRemittanceService.createBankRemittance(DisplayDate.stringToDate2(bankRemittanceForm
            .getDateCharge()), DisplayDate.stringToMonthOfYear(bankRemittanceForm
                  .getMonthCharge()));
      MessageHelper.addInfoAttribute(ra, "bankRemittance.successCreate", "success");
      return REDIRECT_BANKREMITTANCE;
   }
}
