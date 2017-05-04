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
package org.cuacfm.members.model.accounttypeservice;

import java.util.List;

import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttype.AccountTypeRepository;
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.util.Constants.levels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class Account Type Service. */
@Service("accountTypeService")
public class AccountTypeServiceImpl implements AccountTypeService {

	@Autowired
	private AccountTypeRepository accountTypeRepository;

	@Autowired
	private EventService eventService;
	
	@Override
	public AccountType save(AccountType accountType) throws UniqueException {
		// It is verified that there is not exist name of accountType in other accountType
		if (accountTypeRepository.findByName(accountType.getName()) != null) {
			throw new UniqueException("Name", accountType.getName());
		}
		Object[] arguments = { accountType.getName() };
		eventService.save("accountType.create.success", null, levels.MEDIUM, arguments);	
		return accountTypeRepository.save(accountType);
	}

	@Override
	public AccountType update(AccountType accountType) throws UniqueException {
		// It is verified that there is not exist name of account type in other account type
		AccountType accountTypeSearch = accountTypeRepository.findByName(accountType.getName());
		if ((accountTypeSearch != null) && (accountTypeSearch.getId() != accountType.getId())) {
			throw new UniqueException("Name", accountType.getName());
		}
		Object[] arguments = { accountType.getName() };
		eventService.save("accountType.edit.success", null, levels.MEDIUM, arguments);	
		return accountTypeRepository.update(accountType);
	}

	@Override
	public void delete(Long id) {
		accountTypeRepository.delete(id);
	}

	@Override
	public AccountType findById(Long id) {
		return accountTypeRepository.findById(id);
	}

	@Override
	public AccountType findByName(String name) {
		return accountTypeRepository.findByName(name);
	}

	@Override
	public List<AccountType> getAccountTypes() {
		return accountTypeRepository.getAccountTypes();
	}
}
