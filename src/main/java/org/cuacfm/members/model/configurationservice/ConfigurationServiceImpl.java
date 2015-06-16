/**
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
