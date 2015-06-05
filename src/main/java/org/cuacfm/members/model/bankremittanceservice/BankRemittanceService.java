package org.cuacfm.members.model.bankremittanceservice;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;

/** The Interface BankRemittanceService. */
public interface BankRemittanceService {

   /**
    * Creates the bank remittance.
    *
    * @param dateCharge
    *           the date charge
    * @param monthCharge
    *           the month charge
    */
   public void createBankRemittance(Date dateCharge, Date monthCharge);

   /**
    * Update.
    *
    * @param bankRemittance
    *           the bank remittance
    * @return the bank remittance
    */
   public BankRemittance update(BankRemittance bankRemittance);

   /**
    * Pay bank remittance.
    *
    * @param bankRemittanceId
    *           the bank remittance id
    * @throws ExistTransactionIdException
    *            the exist transaction id exception
    */
   public void payBankRemittance(Long bankRemittanceId) throws ExistTransactionIdException;

   /**
    * Management bank remittance.
    *
    * @param bankRemittanceId
    *           the bank remittance id
    * @throws ExistTransactionIdException
    *            the exist transaction id exception
    */
   public void managementBankRemittance(Long bankRemittanceId) throws ExistTransactionIdException;

   /**
    * Find by id.
    *
    * @param id
    *           the id
    * @return the bank remittance
    */
   public BankRemittance findById(Long id);

   /**
    * Gets the bank remittance list.
    *
    * @return the bank remittance list
    */
   public List<BankRemittance> getBankRemittanceList();

   /**
    * Update direct debit.
    *
    * @param directDebit
    *           the direct debit
    * @return the direct debit
    */
   public DirectDebit updateDirectDebit(DirectDebit directDebit);

   /**
    * Find by direct debit id.
    *
    * @param directDebitid
    *           the direct debitid
    * @return the direct debit
    */
   public DirectDebit findByDirectDebitId(Long directDebitid);

   /**
    * Pay direct debit.
    *
    * @param directDebitId
    *           the direct debit id
    * @throws ExistTransactionIdException
    *            the exist transaction id exception
    */
   public void payDirectDebit(Long directDebitId) throws ExistTransactionIdException;

   /**
    * Return bill.
    *
    * @param directDebitId
    *           the direct debit id
    * @throws ExistTransactionIdException
    *            the exist transaction id exception
    */
   public void returnBill(Long directDebitId) throws ExistTransactionIdException;

   /**
    * Management direct debit.
    *
    * @param directDebitId
    *           the direct debit id
    * @throws ExistTransactionIdException
    *            the exist transaction id exception
    */
   public void managementDirectDebit(Long directDebitId) throws ExistTransactionIdException;

   /**
    * Gets the direct debit list by bank remittance id.
    *
    * @param bankRemittanceId
    *           the bank remittance id
    * @return the direct debit list by bank remittance id
    */
   public List<DirectDebit> getDirectDebitListByBankRemittanceId(Long bankRemittanceId);

   /**
    * create Txt Bank Remittance.
    *
    * @param messageSource
    *           the message source
    * @param bankRemittanceId
    *           the bank remittance id
    * @return the response entity
    */
   public ResponseEntity<byte[]> createTxtBankRemittance(MessageSource messageSource,
         Long bankRemittanceId);
}
