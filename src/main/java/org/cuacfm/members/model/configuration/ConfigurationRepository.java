package org.cuacfm.members.model.configuration;

/** The Interface ConfigurationRepository. */
public interface ConfigurationRepository {

   /**
    * Save.
    *
    * @param configuration
    *           the configuration
    * @return the configuration
    */
   public Configuration save(Configuration configuration);

   /**
    * Update.
    *
    * @param configuration
    *           the configuration
    * @return the configuration
    */
   public Configuration update(Configuration configuration);

   /**
    * Get Configuration.
    *
    * @return the configuration
    */
   public Configuration getConfiguration();

}
