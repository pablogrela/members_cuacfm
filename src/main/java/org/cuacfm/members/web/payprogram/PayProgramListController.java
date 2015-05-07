package org.cuacfm.members.web.payprogram;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.web.support.DisplayDate;
import org.cuacfm.members.web.support.MessageHelper;
import org.cuacfm.members.web.userpayinscription.PathForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class PayProgramListController. */
@Controller
public class PayProgramListController {

   /** The Constant PAYPROGRAM_VIEW_NAME. */
   private static final String PAYPROGRAM_VIEW_NAME = "payprogram/payprogramlist";

   /** The Constant REDIRECT_PAYPROGRAM. */
   private static final String REDIRECT_PAYPROGRAM = "redirect:/feeProgramList/payProgramList";
   
   /** The Constant NOPAY. */
   private static final String NOPAY = "NOPAY";
   
   /** The Constant PAY. */
   private static final String PAY = "PAY";

   /** The message source. */
   @Autowired
   private MessageSource messageSource;

   /** The FeeProgramService. */
   @Autowired
   private FeeProgramService feeProgramService;

   /** The PayProgramService. */
   @Autowired
   private PayProgramService payProgramService;

   /** The feeProgram. */
   private FeeProgram feeProgram;

   /** The feePrograms. */
   private List<PayProgram> payPrograms;

   /**
    * Instantiates a new training Controller.
    */
   public PayProgramListController() {
      // Default empty constructor.
   }

   /**
    * Fee Program.
    *
    * @return the fee Program
    */
   @ModelAttribute("feeProgram")
   public FeeProgram feeProgram() {
      return feeProgram;
   }

   /**
    * List of PayProgram.
    *
    * @return List<PayProgram>
    */
   @ModelAttribute("payPrograms")
   public List<PayProgram> payPrograms() {
      return payPrograms;
   }

   /**
    * Pay programs.
    *
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "feeProgramList/payProgramList")
   public String payPrograms(Model model) {
      if (feeProgram != null) {

         PathForm pathform = new PathForm();
         String pathDefault = messageSource.getMessage("pathDefault", null, Locale.getDefault());
         pathform.setPath(pathDefault);
         Date date = new Date();
         String feeProgramFile = messageSource.getMessage("feeProgramFile", null,
               Locale.getDefault());
         pathform.setFile(feeProgramFile + " " + DisplayDate.dateTimeToStringSp(date));
         model.addAttribute(pathform);

         payPrograms = payProgramService.getPayProgramListByFeeProgramId(feeProgram.getId());
         model.addAttribute("payPrograms", payPrograms);
         return PAYPROGRAM_VIEW_NAME;
      } else {
         return "redirect:/feeProgramList";
      }
   }

   /**
    * View pay programs by pay fee program id.
    *
    * @param feeProgramId
    *           the pay inscription id
    * @return the string
    */
   @RequestMapping(value = "feeProgramList/payProgramList/{feeProgramId}", method = RequestMethod.POST)
   public String feeProgramList(@PathVariable Long feeProgramId) {
      feeProgram = feeProgramService.findById(feeProgramId);
      return REDIRECT_PAYPROGRAM;
   }

   /**
    * Pay bill fee program.
    *
    * @param payProgramId
    *           the pay program id
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "feeProgramList/payProgramList/pay/{payProgramId}", method = RequestMethod.POST)
   public String payBillFeeProgram(@PathVariable Long payProgramId, RedirectAttributes ra) {
      PayProgram payProgram = payProgramService.findById(payProgramId);
      payProgramService.pay(payProgram);

      MessageHelper.addSuccessAttribute(ra, "payProgram.successPay", payProgram.getProgram()
            .getName());
      return REDIRECT_PAYPROGRAM;
   }

   /**
    * Creates the pdf.
    *
    * @param pathForm
    *           the path form
    * @param errors
    *           the errors
    * @param createPdf
    *           the create pdf
    * @param ra
    *           the ra
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "feeProgramList/payProgramList", method = RequestMethod.POST, params = { "createPdf" })
   public String createPdf(@Valid @ModelAttribute PathForm pathForm, Errors errors,
         @RequestParam("createPdf") String createPdf, RedirectAttributes ra) {

      if (errors.hasErrors()) {
         return PAYPROGRAM_VIEW_NAME;
      }

      String title;
      String path = pathForm.getPath() + "/" + pathForm.getFile() + ".pdf";
      
      if (createPdf.equals(PAY)) {
         title = feeProgram.getName() + " - "
               + messageSource.getMessage("feeProgram.printPayList", null, Locale.getDefault());
         MessageHelper.addSuccessAttribute(ra, "feeProgram.successPrintPayList",
               feeProgram.getName());
      } else if (createPdf.equals(NOPAY)) {
         title = feeProgram.getName() + " - "
               + messageSource.getMessage("feeProgram.printNoPayList", null, Locale.getDefault());
         MessageHelper.addSuccessAttribute(ra, "feeProgram.successPrintNoPayList",
               feeProgram.getName());
      } else {
         title = feeProgram.getName() + " - "
               + messageSource.getMessage("feeProgram.printAllList", null, Locale.getDefault());
         MessageHelper.addSuccessAttribute(ra, "feeProgram.successPrintAllList",
               feeProgram.getName());
      }

      payProgramService.createPdfFeeProgram(messageSource, feeProgram.getId(), path, title,
            createPdf);
      return REDIRECT_PAYPROGRAM;
   }
}
