package org.cuacfm.members.model.directdebit;

import java.util.List;

/** The Interface DirectDebitRepository. */
public interface DirectDebitRepository {

   /**
    * Save.
    *
    * @param directDebit
    *           the direct debit
    * @return the direct debit
    */
   public DirectDebit save(DirectDebit directDebit);

   /**
    * Update.
    *
    * @param directDebit
    *           the direct debit
    * @return the direct debit
    */
   public DirectDebit update(DirectDebit directDebit);

   /**
    * Find by id.
    *
    * @param id
    *           the id
    * @return the direct debit
    */
   public DirectDebit findById(Long id);

   /**
    * Checks if is rcur or frst.
    *
    * @param accountId
    *           the account id
    * @return the boolean
    */
   public String isRcurOrFRST(Long accountId);

   /**
    * Gets the direct debit list by bank remittance id.
    *
    * @param bankRemittanceId
    *           the bank remittance id
    * @return the direct debit list by bank remittance id
    */
   public List<DirectDebit> getDirectDebitListByBankRemittanceId(Long bankRemittanceId);
}
