package org.cuacfm.members.model.inscription;

import java.util.List;

/** The Interface InscriptionRepository */
public interface InscriptionRepository {
   /**
    * Save.
    *
    * @param inscription
    *           inscription
    */
   public void save(Inscription inscription);

   /**
    * Update.
    * 
    * @param inscription
    *           the inscription
    */
   public void update(Inscription inscription);

   /**
    * Delete.
    *
    * @param accountId
    *           the account id
    * @param trainingId
    *           the training id
    */
   public void delete(Long accountId, Long trainingId);

   /**
    * Find by accountId.
    *
    * @param accountId
    *           the id of user
    * @return the List<Inscription> pertain to user
    */
   public List<Inscription> getByAccountId(Long accountId);

   /**
    * Find by traningId.
    *
    * @param trainingId
    *           the training id
    * @return the List<Inscription> pertain to traing
    */
   public List<Inscription> getByTrainingId(Long trainingId);

   /**
    * Find by accountId and trainingId.
    *
    * @param accountId
    *           the id of user
    * @param trainingId
    *           the training id
    * @return the inscription
    */
   public Inscription findByInscriptionIds(Long accountId, Long trainingId);

   /**
    * Get all Inscriptions by trainingId.
    *
    * @param trainingId
    *           the training id
    * @return List<Inscription>
    */
   public List<Inscription> getInscriptionListByTrainingId(Long trainingId);

   /**
    * Find by accountId.
    *
    * @param accountId
    *           the id of user
    * @return the List<Inscription> pertain to user
    */
   public List<Long> getIdsByAccountId(Long accountId);

   /**
    * Get inscriptionsId by accountId with unsubscribe = true.
    *
    * @param accountId
    *           the id of account
    * @return List<Long> pertain to account
    */
   public List<Long> getUnsubscribeIdsByAccountId(Long accountId);

   /**
    * Gets the name users by inscription with role=ROLE_USER an active=true.
    *
    * @param trainingId
    *           the training id
    * @return the name users by inscription
    */
   public List<String> getUsernamesByInscription(Long trainingId);
}
