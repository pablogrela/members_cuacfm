package org.cuacfm.members.model.payprogram;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class PayProgramRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class PayProgramRepositoryImpl implements PayProgramRepository {

   /** The entity manager. */
   @PersistenceContext
   private EntityManager entityManager;

   /**
    * Save.
    *
    * @param payProgram
    *           the training type
    * @return payProgram
    */
   @Override
   @Transactional
   public PayProgram save(PayProgram payProgram) {
      entityManager.persist(payProgram);
      return payProgram;
   }

   /**
    * Update.
    *
    * @param payProgram
    *           the training type
    * @return payProgram
    */
   @Override
   @Transactional
   public PayProgram update(PayProgram payProgram) {
      return entityManager.merge(payProgram);
   }

   /**
    * Find by id.
    *
    * @param id
    *           the id of payProgram
    * @return payProgram
    */
   @Override
   public PayProgram findById(Long id) {
      try {
         return entityManager
               .createQuery("select p from PayProgram p where p.id = :id", PayProgram.class)
               .setParameter("id", id).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Find by id txn.
    *
    * @param idTxn
    *           the id txn
    * @return the pay program
    */
   @Override
   public PayProgram findByIdTxn(String idTxn) {
      try {
         return entityManager
               .createQuery("select p from PayProgram p where p.idTxn = :idTxn", PayProgram.class)
               .setParameter("idTxn", idTxn).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Find by pay program ids.
    *
    * @param programId
    *           the program id
    * @param feeProgram
    *           the fee program
    * @return the pay program
    */
   @Override
   public PayProgram findByPayProgramIds(Long programId, Long feeProgramId) {
      return entityManager
            .createQuery(
                  "select p from PayProgram p where p.program.id = :programId and p.feeProgram.id = :feeProgramId",
                  PayProgram.class).setParameter("programId", programId)
            .setParameter("feeProgramId", feeProgramId).getSingleResult();
   }

   /**
    * Get all payPrograms.
    *
    * @return List<FeeProgram>
    */
   @Override
   public List<PayProgram> getPayProgramList() {
      return entityManager.createQuery("select p from PayProgram p order by p.program.name",
            PayProgram.class).getResultList();
   }

   /**
    * Get all payPrograms.
    *
    * @return List<FeeProgram>
    */
   @Override
   public List<PayProgram> getPayProgramNoPayListByDirectDebit() {
      return entityManager
            .createQuery(
                  "select p from PayProgram p where p.hasPay = false "
                        //+ "and p.program.accounts in (select a Account from a and a.methodPayment.directDebit = true "
                        //+ "and a.iban <> '' " + "and a.bic <> '')  " 
                        + "order by p.program.name",
                  PayProgram.class).getResultList();
   }

   /**
    * Gets the pay program list by fee program id.
    *
    * @param feeProgramId
    *           the fee program id
    * @return the pay program list by fee program id
    */
   @Override
   public List<PayProgram> getPayProgramListByFeeProgramId(Long feeProgramId) {
      return entityManager
            .createQuery("select p from PayProgram p where p.feeProgram.id = :feeProgramId",
                  PayProgram.class).setParameter("feeProgramId", feeProgramId).getResultList();
   }

   /**
    * Gets the pay program list by program id.
    *
    * @param programId
    *           the pay inscription id
    * @return the pay program list by program id
    */
   @Override
   public List<PayProgram> getPayProgramListByProgramId(Long programId) {
      return entityManager
            .createQuery("select p from PayProgram p where p.program.id = :programId",
                  PayProgram.class).setParameter("programId", programId).getResultList();
   }

   /**
    * Gets the pay program list by account id.
    *
    * @param accountId
    *           the account id
    * @return the pay program list by account id
    */
   @Override
   public List<PayProgram> getPayProgramListByAccountId(Long accountId) {
      return entityManager

      // .createQuery("select p from PayProgram p where (select a from Account a where a.id = :accountId) in p.program.accounts ",
      // .createQuery("select p from PayProgram p where p.program.id in (select a.programs.id from Account a where a.id = :accountId)",
      // .createQuery("select p from PayProgram p where p.program.id in (select a.id from UserPrograms a where a.id = :accountId)",
            .createQuery("select p from PayProgram p ", PayProgram.class)
            // .setParameter("accountId", accountId)
            .getResultList();
   }
}
