package org.cuacfm.members.model.configurationservice;

import org.cuacfm.members.model.configuration.Configuration;

/** The Interface ConfigurationService. */
public interface ConfigurationService {

   /**
    * Save.
    *
    * @param account
    *           the account
    * @return the configuration
    */
   public Configuration save(Configuration configuration);

   /**
    * Update.
    *
    * @param account
    *           the account
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
