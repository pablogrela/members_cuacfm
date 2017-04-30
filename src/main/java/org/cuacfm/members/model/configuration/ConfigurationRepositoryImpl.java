/**
 * Copyright © 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.model.configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class AccountTypeRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class ConfigurationRepositoryImpl implements ConfigurationRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Configuration save(Configuration configuration) {
		entityManager.persist(configuration);
		return configuration;
	}

	@Override
	@Transactional
	public Configuration update(Configuration configuration) {
		return entityManager.merge(configuration);
	}

	@Override
	public Configuration getConfiguration() {
		return entityManager.createQuery("select c from Configuration c", Configuration.class).getSingleResult();
	}
}
