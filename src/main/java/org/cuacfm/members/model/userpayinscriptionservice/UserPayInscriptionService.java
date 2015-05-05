package org.cuacfm.members.model.userpayinscriptionservice;

import java.util.List;

import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.userpayinscription.UserPayInscription;

/** The Interface UserPayInscriptionService. */
public interface UserPayInscriptionService {

   /**
    * Save an userPayInscription into database.
    *
    * @param userPayInscription
    *           the user pay inscription
    * @return the training
    */
   public UserPayInscription save(UserPayInscription userPayInscription);

   /**
    * Update TraininfType.
    *
    * @param userPayInscription
    *           the user pay inscription
    * @return UserPayInscription
    */
   public UserPayInscription update(UserPayInscription userPayInscription);

   /**
    * Pay the UserPayInscription.
    *
    * @param userPayInscription
    *           the user pay inscription
    */
   public void pay(UserPayInscription userPayInscription);

   /**
    * Pay pay pal.
    *
    * @param userPayInscription
    *           the user pay inscription
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
   public void payPayPal(UserPayInscription userPayInscription, String idTxn, String idPayer,
         String emailPayer, String statusPay, String datePay) throws ExistTransactionIdException;

   /**
    * Find by id returns userPayInscription which has this identifier.
    *
    * @param id
    *           the id
    * @return userPayInscription
    */
   public UserPayInscription findById(Long id);

   /**
    * Find by id txn.
    *
    * @param idTxn
    *           the id txn
    * @return the user pay inscription
    */
   public UserPayInscription findByIdTxn(String idTxn);

   /**
    * Find by user pay inscription ids.
    *
    * @param accountId
    *           the account id
    * @param payInscriptionId
    *           the pay inscription id
    * @return List<UserPayInscription>
    */
   public List<UserPayInscription> findByUserPayInscriptionIds(Long accountId, Long payInscriptionId);

   /**
    * Get all userPayInscriptions.
    *
    * @return List<UserPayInscription>
    */
   public List<UserPayInscription> getUserPayInscriptionList();

   /**
    * Gets the user pay inscription list by pay inscription id.
    *
    * @param payInscriptionId
    *           the pay inscription id
    * @return the user pay inscription list by pay inscription id
    */
   public List<UserPayInscription> getUserPayInscriptionListByPayInscriptionId(Long payInscriptionId);

   /**
    * Gets the user pay inscription list by pay account id.
    *
    * @param accountId
    *           the pay inscription id
    * @return the user pay inscription list by account id
    */
   public List<UserPayInscription> getUserPayInscriptionListByAccountId(Long accountId);

   /**
    * Gets the name users by Pay Inscription with role=ROLE_USER an active=true.
    *
    * @param trainingId
    *           the pay inscription id
    * @return the name users by pay inscription
    */
   public List<String> getUsernamesByPayInscription(Long payInscriptionId);
}
