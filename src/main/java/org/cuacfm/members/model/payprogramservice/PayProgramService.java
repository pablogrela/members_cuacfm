package org.cuacfm.members.model.payprogramservice;

import java.util.List;

import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.springframework.context.MessageSource;


/** The Interface PayProgramService. */
public interface PayProgramService {

   /**
    * Save an payProgram into database.
    *
    * @param payProgram
    *           the pay program
    * @return the training
    */
   public PayProgram save(PayProgram payProgram);

   /**
    * Update TraininfType.
    *
    * @param payProgram
    *           the pay program
    * @return PayProgram
    */
   public PayProgram update(PayProgram payProgram);

   /**
    * Pay the PayProgram.
    *
    * @param payProgram
    *           the pay program
    */
   public void pay(PayProgram payProgram);

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
   public void payPayPal(PayProgram payProgram, String accountPayer, String idTxn, String idPayer,
         String emailPayer, String statusPay, String datePay) throws ExistTransactionIdException;

   /**
    * Find by id returns payProgram which has this identifier.
    *
    * @param id
    *           the id
    * @return payProgram
    */
   public PayProgram findById(Long id);

   /**
    * Find by id txn.
    *
    * @param idTxn
    *           the id txn
    * @return the pay program
    */
   public PayProgram findByIdTxn(String idTxn);

   /**
    * Find by pay program ids.
    *
    * @param programId
    *           the program id
    * @param feeProgram
    *           the fee program
    * @return the pay program
    */
   public PayProgram findByPayProgramIds(Long programId, Long feeProgramId);

   /**
    * Get all payPrograms.
    *
    * @return List<PayProgram>
    */
   public List<PayProgram> getPayProgramList();

   /**
    * Gets the pay program list by fee program id.
    *
    * @param feeProgramId
    *           the fee program id
    * @return the pay program list by fee program id
    */
   public List<PayProgram> getPayProgramListByFeeProgramId(Long feeProgramId);

   /**
    * Gets the pay program list by pay program id.
    *
    * @param programId
    *           the pay inscription id
    * @return the pay program list by program id
    */
   public List<PayProgram> getPayProgramListByProgramId(Long programId);

   /**
    * Gets the pay program list by account id.
    *
    * @param accountId
    *           the account id
    * @return the pay program list by account id
    */
   public List<PayProgram> getPayProgramListByAccountId(Long accountId);

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
    * @param submit
    *           the submit
    */
   public void createPdfFeeProgram(MessageSource messageSource, Long feeProgramId, String path,
         String title, String submit);
}
