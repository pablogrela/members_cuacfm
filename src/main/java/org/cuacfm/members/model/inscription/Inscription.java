package org.cuacfm.members.model.inscription;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.training.Training;

/** The Class Inscription. */
@SuppressWarnings("serial")
@Entity
public class Inscription implements Serializable {

   /** The id. */
   @Id
   @GeneratedValue
   private Long id;

   /** The account. */
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   @JoinColumn(name = "accountId")
   private Account account;

   /** The training. */
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   @JoinColumn(name = "trainingId")
   private Training training;

   /** The attend. */
   private boolean attend;

   /** The note. */
   private String note;

   /** The pass. */
   private boolean pass;

   /** The unsubscribe. */
   private boolean unsubscribe;

   /**
    * Instantiates a new inscription.
    */
   public Inscription() {
      // Default empty constructor.
   }

   /**
    * Instantiates a new training.
    *
    * @param account
    *           Account
    * @param training
    *           Training
    */
   public Inscription(Account account, Training training) {
      super();
      this.account = account;
      this.training = training;
   }

   /**
    * Gets the id.
    *
    * @return the id
    */
   public Long getId() {
      return id;
   }

   /**
    * Get the Account.
    *
    * @return Account, account of Inscription
    */
   public Account getAccount() {
      return account;
   }

   /**
    * Get the Account.
    *
    * @return training, training of Inscription
    */
   public Training getTraining() {
      return training;
   }

   /**
    * Get the Attend.
    *
    * @return boolean isAttend
    */
   public boolean isAttend() {
      return attend;
   }

   /**
    * Set the attend.
    *
    * @param attend
    *           boolean
    */
   public void setAttend(boolean attend) {
      this.attend = attend;
   }

   /**
    * Set the note.
    *
    * @return String, the new note
    */
   public String getNote() {
      return note;
   }

   /**
    * Set the note.
    *
    * @param note
    *           String
    */
   public void setNote(String note) {
      this.note = note;
   }

   /**
    * Get the pass.
    *
    * @return boolean isPass
    */
   public boolean isPass() {
      return pass;
   }

   /**
    * Set the pass.
    *
    * @param pass
    *           boolean
    */
   public void setPass(boolean pass) {
      this.pass = pass;
   }

   /**
    * Get the Unsubscribe.
    *
    * @return boolean isUnsubscribe
    */
   public boolean isUnsubscribe() {
      return unsubscribe;
   }

   /**
    * Set the Unsubscribe.
    *
    * @param unsubscribe
    *           boolean
    */
   public void setUnsubscribe(boolean unsubscribe) {
      this.unsubscribe = unsubscribe;
   }
}
