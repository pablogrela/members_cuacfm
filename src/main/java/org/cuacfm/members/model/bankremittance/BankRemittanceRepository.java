package org.cuacfm.members.model.bankremittance;

import java.util.List;

/** The Interface BankRemittanceRepository. */
public interface BankRemittanceRepository {

   /**
    * Save.
    *
    * @param bankRemittance
    *           the bank remittance
    * @return the bank remittance
    */
   public BankRemittance save(BankRemittance bankRemittance);

   /**
    * Update.
    *
    * @param bankRemittance
    *           the bank remittance
    * @return the bank remittance
    */
   public BankRemittance update(BankRemittance bankRemittance);

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
}
