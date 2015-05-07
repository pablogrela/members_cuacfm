package org.cuacfm.members.model.configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class AccountTypeRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class ConfigurationRepositoryImpl implements ConfigurationRepository {

   /** The entity manager. */
   @PersistenceContext
   private EntityManager entityManager;

   /**
    * Save.
    *
    * @param configuration
    *           the configuration
    * @return the configuration
    */
   @Override
   @Transactional
   public Configuration save(Configuration configuration) {
      entityManager.persist(configuration);
      return configuration;
   }

   /**
    * Update.
    *
    * @param configuration
    *           the configuration
    * @return the configuration
    */
   @Override
   @Transactional
   public Configuration update(Configuration configuration) {
      return entityManager.merge(configuration);
   }

   /**
    * Get Configuration.
    *
    * @return the configuration
    */
   @Override
   public Configuration getConfiguration() {
      return entityManager.createQuery("select c from Configuration c",
            Configuration.class).getSingleResult();
   }
}
