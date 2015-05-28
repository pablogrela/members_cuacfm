package org.cuacfm.members.model.payprogramservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogram.FeeProgramRepository;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogram.PayProgramRepository;
import org.cuacfm.members.web.support.CreatePdf;
import org.cuacfm.members.web.support.DisplayDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfPTable;

/** The Class PayProgramServiceImpl. */
@Service("payProgramService")
public class PayProgramServiceImpl implements PayProgramService {

   /** The Constant NOPAY. */
   private static final String NOPAY = "NOPAY";

   /** The Constant PAY. */
   private static final String PAY = "PAY";

   /** The pay program repository. */
   @Autowired
   private PayProgramRepository payProgramRepository;

   /** The fee program repository. */
   @Autowired
   private FeeProgramRepository feeProgramRepository;

   /** The account service. */
   @Autowired
   private AccountService accountService;

   /** Instantiates a new payInscription service. */
   public PayProgramServiceImpl() {
      // Default empty constructor.
   }

   /**
    * Save a payProgram into database.
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
    * @throws ExistTransactionIdException
    *            the exist transaction id exception
    */
   @Override
   public PayProgram update(PayProgram payProgram) throws ExistTransactionIdException {
      PayProgram paymentExist = payProgramRepository.findByIdTxn(payProgram.getIdTxn());
      if (paymentExist != null) {
         if (paymentExist.getId() != payProgram.getId()) {
            throw new ExistTransactionIdException(payProgram.getIdTxn());
         }
      }
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

   /**
    * Pay pay pal.
    *
    * @param payProgram
    *           the pay program
    * @param accountPayer
    *           the account payer
    * @param idTxn
    *           the id txn
    * @param idPayer
    *           the id payer
    * @param emailPayer
    *           the email payer
    * @param statusPay
    *           the status pay
    * @param datePay
    *           the date pay
    * @throws ExistTransactionIdException
    *            the exist transaction id exception
    */
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
    * Find by id returns payProgram which has this identifier.
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
    * @param feeProgramId
    *           the fee program id
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
    * Gets the pay program no pay list by direct debit.
    *
    * @return the pay program no pay list by direct debit
    */
   public List<PayProgram> getPayProgramNoPayListByDirectDebit() {
      return payProgramRepository.getPayProgramNoPayListByDirectDebit();
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
    * @param option
    *           the option
    * @return the response entity
    */
   @Override
   public ResponseEntity<byte[]> createPdfFeeProgram(MessageSource messageSource,
         Long feeProgramId, String option) {

      FeeProgram feeProgram = feeProgramRepository.findById(feeProgramId);
      List<PayProgram> payPrograms = payProgramRepository
            .getPayProgramListByFeeProgramId(feeProgramId);

      Date date = new Date();
      String fileNameFeeProgram = messageSource.getMessage("fileNameFeeProgram", null,
            Locale.getDefault())
            + DisplayDate.dateTimeToStringSp(date) + ".pdf";
      String path = System.getProperty("user.dir") + "/" + fileNameFeeProgram;

      String title;
      if (option.equals(PAY)) {
         title = feeProgram.getName() + " - "
               + messageSource.getMessage("feeProgram.printPayList", null, Locale.getDefault());
      } else if (option.equals(NOPAY)) {
         title = feeProgram.getName() + " - "
               + messageSource.getMessage("feeProgram.printNoPayList", null, Locale.getDefault());
      } else {
         title = feeProgram.getName() + " - "
               + messageSource.getMessage("feeProgram.printAllList", null, Locale.getDefault());

      }
      CreatePdf pdf = new CreatePdf();
      PdfPTable table = pdf.createTablePayPrograms(messageSource, option, payPrograms);
      pdf.createBody(path, title, table);
      return CreatePdf.viewPdf(path, fileNameFeeProgram);
   }
}
