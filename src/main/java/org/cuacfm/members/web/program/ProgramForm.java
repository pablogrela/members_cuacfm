package org.cuacfm.members.web.program;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.program.Program;
import org.hibernate.validator.constraints.NotBlank;

/** The Class ProgramForm. */
public class ProgramForm {

   /** The Constant NOT_BLANK_MESSAGE. */
   private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

   /** The Constant MAX_CHARACTERS. */
   private static final String MAX_CHARACTERS = "{max.characters}";

   /** The name. */
   @NotBlank(message = ProgramForm.NOT_BLANK_MESSAGE)
   @Size(max = 30, message = ProgramForm.MAX_CHARACTERS)
   private String name;

   /** The description. */
   @NotBlank(message = ProgramForm.NOT_BLANK_MESSAGE)
   @Size(max = 500, message = ProgramForm.MAX_CHARACTERS)
   private String description;

   /** The duration. */
   @NotNull
   @Min(0)
   private int duration;

   /** The periodicity. */
   @Max(7)
   @Min(0)
   private Float periodicity;

   /** The accounts. */
   private List<Account> accounts;

   /** The login. */
   private String login;

   /** Instantiates a new training form. */
   public ProgramForm() {
      // Default empty constructor.
   }

   /**
    * Get the name.
    *
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * Set the name.
    *
    * @param name
    *           String, the new name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Gets the periodicity.
    *
    * @return the periodicity
    */
   public Float getPeriodicity() {
      return periodicity;
   }

   /**
    * Sets the periodicity.
    *
    * @param periodicity
    *           the new periodicity
    */
   public void setPeriodicity(Float periodicity) {
      this.periodicity = periodicity;
   }

   /**
    * Gets the accounts.
    *
    * @return the accounts
    */
   public List<Account> getAccounts() {
      return accounts;
   }

   /**
    * Sets the accounts.
    *
    * @param accounts
    *           the new accounts
    */
   public void setAccounts(List<Account> accounts) {
      this.accounts = accounts;
   }

   /**
    * Adds the account.
    *
    * @param account
    *           the account
    */
   public void addAccount(Account account) {
      accounts.add(account);
   }

   /**
    * Removes the account.
    *
    * @param account
    *           the account
    */
   public void removeAccount(Long id) {
      Account accountToDelete = null;
      for (Account a : accounts) {
         if (a.getId() == id) {
            accountToDelete = a;
            break;
         }
      }
      accounts.remove(accountToDelete);
   }

   /**
    * Get the description..
    *
    * @return the description
    */
   public String getDescription() {
      return description;
   }

   /**
    * Set the description.
    *
    * @param description
    *           String, the new description
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Gets the duration.
    *
    * @return the duration
    */
   public int getDuration() {
      return duration;
   }

   /**
    * Sets the duration.
    *
    * @param duration
    *           the new duration
    */
   public void setDuration(int duration) {
      this.duration = duration;
   }

   /**
    * Gets the login.
    *
    * @return the login
    */
   public String getLogin() {
      return login;
   }

   /**
    * Sets the login.
    *
    * @param login
    *           the new login
    */
   public void setLogin(String login) {
      this.login = login;
   }

   /**
    * Creates the training Type.
    *
    * @return Program
    */
   public Program createProgram() {
      return new Program(getName(), getPeriodicity(), getDescription(), getDuration(),
            getAccounts());
   }

   /**
    * Update training type.
    *
    * @param program
    *           the training type
    * @return the training type
    */
   public Program updateProgram(Program program) {
      program.setName(getName());
      program.setPeriodicity(getPeriodicity());
      program.setDescription(getDescription());
      program.setDuration(getDuration());
      program.setAccounts(getAccounts());
      return program;
   }
}
