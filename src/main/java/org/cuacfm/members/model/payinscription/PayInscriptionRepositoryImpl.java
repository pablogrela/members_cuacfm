package org.cuacfm.members.model.payinscription;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class PayInscriptionRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class PayInscriptionRepositoryImpl implements PayInscriptionRepositoy {

   /** The entity manager. */
   @PersistenceContext
   private EntityManager entityManager;

   /**
    * Save.
    *
    * @param payInscription
    *           the payInscription
    * @return payInscription
    * @throws PersistenceException
    *            the persistence exception
    */
   @Override
   @Transactional
   public PayInscription save(PayInscription payInscription) {
      entityManager.persist(payInscription);
      return payInscription;
   }

   /**
    * Update.
    *
    * @param payInscription
    *           the pay inscription
    * @return payInscription
    */
   @Override
   @Transactional
   public PayInscription update(PayInscription payInscription) {
      return entityManager.merge(payInscription);
   }

   /**
    * Find by id.
    *
    * @param id
    *           the id of payInscription
    * @return payInscription
    */
   @Override
   public PayInscription findById(Long id) {
      try {
         return entityManager
               .createQuery("select a from PayInscription a where a.id = :id", PayInscription.class)
               .setParameter("id", id).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Find by name.
    *
    * @param name
    *           the name of payInscription
    * @return PayInscription
    */
   @Override
   public PayInscription findByName(String name) {
      try {
         return entityManager
               .createQuery("select a from PayInscription a where a.name = :name",
                     PayInscription.class).setParameter("name", name).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Find by year.
    *
    * @param year
    *           the year
    * @return the pay inscription
    */
   @Override
   public PayInscription findByYear(int year) {
      try {
         return entityManager
               .createQuery("select a from PayInscription a where a.year = :year",
                     PayInscription.class).setParameter("year", year).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Get all payInscriptions.
    *
    * @return List<PayInscription>
    */
   @Override
   public List<PayInscription> getPayInscriptionList() {
      return entityManager.createQuery("select a from PayInscription a order by a.name",
            PayInscription.class).getResultList();
   }
}
