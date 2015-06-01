package org.cuacfm.members.web.account;

import javax.validation.constraints.Size;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.bankaccount.BankAccount;
import org.hibernate.validator.constraints.NotBlank;

/** The Class BankAccountForm. */
public class BankAccountForm {

   /** The Constant NOT_BLANK_MESSAGE. */
   private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

   /** The Constant MAX_CHARACTERS. */
   private static final String MAX_CHARACTERS = "{max.characters}";

   /** The bank. */
   @NotBlank(message = BankAccountForm.NOT_BLANK_MESSAGE)
   @Size(max = 30, message = BankAccountForm.MAX_CHARACTERS)
   private String bank;

   /** The bic. */
   @NotBlank(message = BankAccountForm.NOT_BLANK_MESSAGE)
   @Size(max = 11, message = BankAccountForm.MAX_CHARACTERS)
   private String bic;

   /** The iban. */
   @NotBlank(message = BankAccountForm.NOT_BLANK_MESSAGE)
   @Size(max = 34, message = BankAccountForm.MAX_CHARACTERS)
   private String iban;

   /**
    * Gets the bank.
    *
    * @return the bank
    */
   public String getBank() {
      return bank;
   }

   /**
    * Sets the bank.
    *
    * @param bank
    *           the new bank
    */
   public void setBank(String bank) {
      this.bank = bank;
   }

   /**
    * Gets the bic.
    *
    * @return the bic
    */
   public String getBic() {
      return bic;
   }

   /**
    * Sets the bic.
    *
    * @param bic
    *           the new bic
    */
   public void setBic(String bic) {
      this.bic = bic;
   }

   /**
    * Gets the iban.
    *
    * @return the iban
    */
   public String getIban() {
      return iban;
   }

   /**
    * Sets the iban.
    *
    * @param iban
    *           the new iban
    */
   public void setIban(String iban) {
      this.iban = iban;
   }

   /**
    * Creates the bank account.
    *
    * @param account
    *           the account
    * @return the bank account
    */
   public BankAccount createBankAccount(Account account) {
      return new BankAccount(account, getBank(), getBic(), getIban());
   }

}