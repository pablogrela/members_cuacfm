package org.cuacfm.members.model.bankaccount;

/** The Interface BankAccountRepository. */
public interface BankAccountRepository {

   /**
    * Save.
    *
    * @param bankBankAccount
    *           the bankAccount
    * @return the bank bankAccount
    */
   public BankAccount save(BankAccount bankAccount);

   /**
    * Active bank account by account id.
    *
    * @param accountId
    *           the account id
    * @return the bank account
    */
   public BankAccount activeBankAccountByAccountId(Long accountId);
}
