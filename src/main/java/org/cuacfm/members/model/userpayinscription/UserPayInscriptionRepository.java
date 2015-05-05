package org.cuacfm.members.model.userpayinscription;

import java.util.List;

/** The Interface UserPayInscriptionRepository. */
public interface UserPayInscriptionRepository {

   /**
    * Save.
    *
    * @param userPayInscription
    *           the pay inscription
    * @return the user pay inscription
    */
   public UserPayInscription save(UserPayInscription userPayInscription);

   /**
    * Update.
    *
    * @param userPayInscription
    *           the pay inscription
    * @return the user pay inscription
    */
   public UserPayInscription update(UserPayInscription userPayInscription);

   /**
    * Find by id.
    *
    * @param id
    *           the id
    * @return the user pay inscription
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
    * Gets the user pay inscription list.
    *
    * @return the user pay inscription list
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
