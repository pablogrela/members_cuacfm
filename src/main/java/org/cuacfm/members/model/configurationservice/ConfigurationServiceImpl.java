package org.cuacfm.members.model.configurationservice;

import org.cuacfm.members.model.configuration.Configuration;
import org.cuacfm.members.model.configuration.ConfigurationRepository;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class Configuration Service Impl. */
@Service("configurationService")
public class ConfigurationServiceImpl implements ConfigurationService {

   /** The Account Type Service. */
   @Autowired
   private ConfigurationRepository configurationRepository;

   /**
    * Save.
    *
    * @param configuration
    *           the configuration
    * @return the configuration
    * @throws UniqueException
    */
   @Override
   public Configuration save(Configuration configuration) {
      return configurationRepository.save(configuration);
   }

   /**
    * Update.
    *
    * @param configuration
    *           the configuration
    * @return the configuration
    * @throws UniqueException
    */
   @Override
   public Configuration update(Configuration configuration) {
      return configurationRepository.update(configuration);
   }

   /**
    * Get Configuration.
    *
    * @return the configuration
    */
   @Override
   public Configuration getConfiguration() {
      return configurationRepository.getConfiguration();
   }
}
