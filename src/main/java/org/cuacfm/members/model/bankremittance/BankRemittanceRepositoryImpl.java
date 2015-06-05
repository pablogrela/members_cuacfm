package org.cuacfm.members.model.bankremittance;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class BankRemittanceRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class BankRemittanceRepositoryImpl implements BankRemittanceRepository {

   /** The entity manager. */
   @PersistenceContext
   private EntityManager entityManager;

   /**
    * Save.
    *
    * @param bankRemittance
    *           the bank remittance
    * @return the bank remittance
    */
   @Override
   @Transactional
   public BankRemittance save(BankRemittance bankRemittance) {
      entityManager.persist(bankRemittance);
      return bankRemittance;
   }

   /**
    * Update.
    *
    * @param bankRemittance
    *           the bank remittance
    * @return the bank remittance
    */
   @Override
   @Transactional
   public BankRemittance update(BankRemittance bankRemittance) {
      return entityManager.merge(bankRemittance);
   }

   /**
    * Find by id.
    *
    * @param id
    *           the id
    * @return the bank remittance
    */
   @Override
   public BankRemittance findById(Long id) {
      try {
         return entityManager
               .createQuery("select d from BankRemittance d where d.id = :id", BankRemittance.class)
               .setParameter("id", id).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Gets the bank remittance list.
    *
    * @return the bank remittance list
    */
   @Override
   public List<BankRemittance> getBankRemittanceList() {
      return entityManager.createQuery("select d from BankRemittance d",
            BankRemittance.class).getResultList();
   }
}
