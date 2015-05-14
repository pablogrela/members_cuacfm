package org.cuacfm.members.model.payprogramservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogram.PayProgramRepository;
import org.cuacfm.members.web.support.DisplayDate;
import org.cuacfm.members.web.support.CreatePdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfPTable;

/** The Class PayProgramServiceImpl. */
@Service("payProgramService")
public class PayProgramServiceImpl implements PayProgramService {

   /** The payInscription repository. */
   @Autowired
   private PayProgramRepository payProgramRepository;

   /** The account service. */
   @Autowired
   private AccountService accountService;

   /** Instantiates a new payInscription service. */
   public PayProgramServiceImpl() {
      // Default empty constructor.
   }

   /**
    * Save an payProgram into database.
    *
    * @param payProgram
    *           the pay program
    * @return PayProgram
    */
   @Override
   public PayProgram save(PayProgram payProgram) {
      return payProgramRepository.save(payProgram);
   }

   /**
    * Update PayProgram.
    *
    * @param payProgram
    *           the pay program
    * @return PayProgram
    */
   @Override
   public PayProgram update(PayProgram payProgram) {
      return payProgramRepository.update(payProgram);
   }

   /**
    * Pay the PayProgram.
    *
    * @param payProgram
    *           the pay program
    */
   @Override
   public void pay(PayProgram payProgram) {
      payProgram.setHasPay(true);
      payProgram.setDatePay(new Date());
      payProgramRepository.update(payProgram);
   }

   @Override
   public void payPayPal(PayProgram payProgram, String accountPayer, String idTxn, String idPayer,
         String emailPayer, String statusPay, String datePay) throws ExistTransactionIdException {

      PayProgram paymentExist = payProgramRepository.findByIdTxn(idTxn);
      if (paymentExist != null) {
         if (paymentExist.getId() != payProgram.getId()) {
            throw new ExistTransactionIdException(idTxn);
         }
      }

      payProgram.setAccountPayer(accountPayer);
      payProgram.setIdTxn(idTxn);
      payProgram.setEmailPayer(emailPayer);
      payProgram.setIdPayer(idPayer);
      payProgram.setStatusPay(statusPay);
      payProgram.setDatePay(DisplayDate.stringPaypalToDate(datePay));

      if (statusPay.contains("Completed")) {
         payProgram.setHasPay(true);
      }
      payProgramRepository.update(payProgram);
   }

   /**
    * Find by id returns payInscription which has this identifier.
    *
    * @param id
    *           the id
    * @return payInscription
    */
   @Override
   public PayProgram findById(Long id) {
      return payProgramRepository.findById(id);
   }

   /**
    * Find by id txn.
    *
    * @param idTxn
    *           the id txn
    * @return the pay program
    */
   @Override
   public PayProgram findByIdTxn(String idTxn) {
      return payProgramRepository.findByIdTxn(idTxn);
   }

   /**
    * Find by pay program ids.
    *
    * @param programId
    *           the program id
    * @param feeProgram
    *           the fee program
    * @return the pay program
    */
   @Override
   public PayProgram findByPayProgramIds(Long programId, Long feeProgramId) {
      return payProgramRepository.findByPayProgramIds(programId, feeProgramId);
   }

   /**
    * Get all payInscriptions.
    *
    * @return List<FeeProgram>
    */
   @Override
   public List<PayProgram> getPayProgramList() {
      return payProgramRepository.getPayProgramList();
   }

   /**
    * Gets the pay program list by fee program id.
    *
    * @param feeProgramId
    *           the fee program id
    * @return the pay program list by fee program id
    */
   @Override
   public List<PayProgram> getPayProgramListByFeeProgramId(Long feeProgramId) {
      return payProgramRepository.getPayProgramListByFeeProgramId(feeProgramId);
   }

   /**
    * Gets the pay program list by pay program id.
    *
    * @param programId
    *           the pay inscription id
    * @return the pay program list by program id
    */
   @Override
   public List<PayProgram> getPayProgramListByProgramId(Long programId) {
      return payProgramRepository.getPayProgramListByProgramId(programId);
   }

   /**
    * Gets the pay program list by account id.
    *
    * @param accountId
    *           the account id
    * @return the pay program list by account id
    */
   @Override
   public List<PayProgram> getPayProgramListByAccountId(Long accountId) {

      Account account = accountService.findById(accountId);
      List<PayProgram> payProgramsResult = new ArrayList<PayProgram>();
      if (account.getPrograms() == null) {
         return payProgramsResult;
      }
      
      List<PayProgram> payPrograms = payProgramRepository.getPayProgramListByAccountId(accountId);
      for (PayProgram payProgram : payPrograms) {
         if (account.getPrograms().contains(payProgram.getProgram())) {
            payProgramsResult.add(payProgram);
         }
      }
      return payProgramsResult;
   }

   /**
    * Creates the pdf fee program.
    *
    * @param messageSource
    *           the message source
    * @param feeProgramId
    *           the fee program id
    * @param path
    *           the path
    * @param title
    *           the title
    */
   @Override
   public void createPdfFeeProgram(MessageSource messageSource, Long feeProgramId, String path,
         String title, String submit) {
      List<PayProgram> payPrograms = payProgramRepository.getPayProgramListByFeeProgramId(feeProgramId);
      CreatePdf pdf = new CreatePdf();
      PdfPTable table = pdf.createTablePayPrograms(messageSource, submit, payPrograms);
      pdf.createBody(path, title, table);
   }
}
