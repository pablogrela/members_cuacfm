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
package org.cuacfm.members.test.model.directdebitservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.directdebit.DirectDebitDTO;
import org.cuacfm.members.model.directdebit.ReturnReason;
import org.cuacfm.members.model.directdebit.ReturnReasonRepository;
import org.cuacfm.members.model.directdebitservice.DirectDebitService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class DirectDebitServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DirectDebitServiceTest extends WebSecurityConfigurationAware {

	@Autowired
	private AccountService accountService;

	@Autowired
	private DirectDebitService directDebitService;

	@Autowired
	private ReturnReasonRepository returnReasonRepository;

	private Account account;
	private Account account2;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 * @throws UniqueListException the unique list exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		List<Account> accounts = new ArrayList<Account>();
		account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		account2 = new Account("user2", "2", "25555555C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account2);
		accounts.add(account2);
	}

	/**
	 * Find direct debit.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveDirectDebit() throws Exception {
		accountService.getAccounts();
		DirectDebit directDebit = new DirectDebit(account, "directDebit");
		directDebitService.save(directDebit);
		assertEquals(directDebitService.findAll().size(), 1);
		directDebitService.refresh();
		assertEquals(directDebitService.findAll().size(), 0);
	}

	/**
	 * Pay direct debit.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payDirectDebitTest() throws Exception {
		DirectDebit directDebit = new DirectDebit(account, "directDebit");
		directDebitService.save(directDebit);
		assertEquals(directDebitService.findAllByAccountId(account.getId()).size(), 1);
		directDebitService.markBankDeposit(directDebit, account);
		assertEquals(directDebitService.findAllByAccountId(account.getId()).size(), 1);
		assertEquals(directDebit.getState(), states.MANAGEMENT);
		directDebitService.management(directDebit, account);
		assertEquals(directDebitService.findAllByAccountId(account.getId()).size(), 1);
		assertEquals(directDebit.getState(), states.MANAGEMENT);
		directDebitService.cancel(directDebit, account);
		assertEquals(directDebitService.findAllByAccountId(account.getId()).size(), 1);
		assertEquals(directDebit.getState(), states.CANCEL);

		DirectDebit directDebit2 = new DirectDebit(account, "directDebit2");
		directDebitService.save(directDebit2);
		assertEquals(directDebitService.getLastDirectDebit(account.getId()).getId(), directDebit2.getId());
		assertEquals(directDebitService.getDTO(directDebitService.findAllOpenByAccountId(account.getId())).size(), 2);
		directDebitService.markBankDeposit(directDebit2, account);
		directDebitService.cancelBankDeposit(directDebit2, account);
		directDebitService.markBankDeposit(directDebit2, account);
		directDebitService.confirmBankDeposit(directDebit2, account);
		assertEquals(directDebit2.getState(), states.PAY);

		DirectDebit directDebit3 = new DirectDebit(account, "directDebit3");
		directDebitService.save(directDebit3);
		directDebitService.paypal(directDebit3, account, "11", "11", "user@tes.es", "aa", "aa");
		directDebitService.confirmPaypal(directDebit3, account);
		assertEquals(directDebit2.getState(), states.PAY);
		directDebit3.toString();
		directDebit3.getConceptShort();
		directDebitService.directDebit(directDebit3, account);
		directDebit3.setState(states.CANCEL);
		new DirectDebitDTO();
		assertEquals(directDebitService.findAllClose().size(), 3);
		assertEquals(directDebitService.findAllOpen().size(), 0);
		assertNull(directDebitService.findById("test"));

		directDebitService.markBankDeposit(directDebit3, account);
		directDebitService.cancelBankDeposit(directDebit3, account);
		directDebitService.confirmBankDeposit(directDebit3, account);
		directDebitService.confirmPaypal(directDebit3, account);

		DirectDebit directDebit4 = new DirectDebit(account, "directDebit4");
		directDebitService.save(directDebit4);
		directDebitService.paypal(directDebit4, account, directDebit.getId(), directDebit.getId(), "user@tes.es", "Completed", "aa");
		directDebit4.setReturnReason(new ReturnReason("Fail1", "Fail 1"));
	}

	/**
	 * Cash direct debit test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void cashDirectDebitTest() throws Exception {
		DirectDebit directDebit = new DirectDebit(account, "directDebit");
		directDebitService.save(directDebit);
		directDebitService.cash(directDebit, account);
		assertEquals(directDebit.getState(), states.PAY);
		directDebitService.returnBill(directDebit, account);
		assertEquals(directDebit.getState(), states.RETURN_BILL);
		directDebitService.cash(directDebit, account);
		assertEquals(directDebit.getState(), states.PAY);
		directDebitService.cancel(directDebit, account);
		directDebitService.cash(directDebit, account);
	}

	/**
	 * Bank deposit direct debit test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void bankDepositDirectDebitTest() throws Exception {
		DirectDebit directDebit = new DirectDebit(account, "directDebit");
		directDebitService.save(directDebit);
		directDebitService.markBankDeposit(directDebit, account);
		assertEquals(directDebit.getState(), states.MANAGEMENT);
		directDebitService.returnBill(directDebit, account);
		assertEquals(directDebit.getState(), states.RETURN_BILL);
		directDebitService.markBankDeposit(directDebit, account);
		assertEquals(directDebit.getState(), states.MANAGEMENT);
		directDebitService.cancel(directDebit, account);
		assertEquals(directDebit.getState(), states.CANCEL);
		directDebitService.markBankDeposit(directDebit, account);
		assertEquals(directDebit.getState(), states.CANCEL);
		directDebitService.cancelBankDeposit(directDebit, account);
		assertEquals(directDebit.getState(), states.CANCEL);
	}

	/**
	 * Direct debit return reason test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void directDebitReturnReasonTest() throws Exception {
		DirectDebit directDebit = new DirectDebit(account, "directDebit");
		directDebitService.save(directDebit);

		ReturnReason returnReason = new ReturnReason("Fail1", "Fail 1");
		returnReasonRepository.save(returnReason);
		directDebit.setReturnReason(returnReason);
		directDebitService.returnBill(directDebit, account);
		assertEquals(directDebit.getState(), states.RETURN_BILL);
		assertEquals(directDebitService.getDTO(directDebitService.findAllOpenByAccountId(account.getId())).size(), 1);
	}

	/**
	 * Direct debit test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void directDebitTest() throws Exception {
		DirectDebit directDebit = new DirectDebit(account, "directDebit");
		directDebitService.save(directDebit);
		DirectDebitDTO directDebitDTO = new DirectDebitDTO();

		directDebitDTO.setId(directDebit.getId());
		assertEquals(directDebitDTO.getId(), directDebitDTO.getId());

		directDebitDTO.setDatePay(directDebit.getDatePay());
		assertEquals(directDebitDTO.getDatePay(), directDebitDTO.getDatePay());

		directDebitDTO.setConcept(directDebit.getConcept());
		assertEquals(directDebitDTO.getConcept(), directDebitDTO.getConcept());

		directDebitDTO.setDatePay(directDebit.getDatePay());
		assertEquals(directDebitDTO.getDatePay(), directDebitDTO.getDatePay());

		directDebitDTO.setDateUpdate(directDebit.getDateUpdate());
		assertEquals(directDebitDTO.getDateUpdate(), directDebitDTO.getDateUpdate());

		directDebitDTO.setPrice(directDebit.getPrice());
		assertEquals(directDebitDTO.getPrice(), directDebitDTO.getPrice());

		directDebitDTO.setState(directDebit.getState());
		assertEquals(directDebitDTO.getState(), directDebitDTO.getState());

		directDebitDTO.setPrice(directDebit.getPrice());
		assertEquals(directDebitDTO.getPrice(), directDebitDTO.getPrice());

		directDebitDTO.setMethod(directDebit.getMethod());
		assertEquals(directDebitDTO.getMethod(), directDebitDTO.getMethod());

		directDebitDTO.setSecuence(directDebit.getSecuence());
		assertEquals(directDebitDTO.getSecuence(), directDebitDTO.getSecuence());

		directDebitDTO.setIdPayer(directDebit.getIdPayer());
		assertEquals(directDebitDTO.getIdPayer(), directDebitDTO.getIdPayer());

		directDebitDTO.setIdTxn(directDebit.getIdTxn());
		assertEquals(directDebitDTO.getIdTxn(), directDebitDTO.getIdTxn());

		directDebitDTO.setEmailPayer(directDebit.getEmailPayer());
		assertEquals(directDebitDTO.getEmailPayer(), directDebitDTO.getEmailPayer());
	}

	/**
	 * Return reason test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void returnReasonTest() throws Exception {
		ReturnReason returnReason = new ReturnReason("Fail1", "Fail 1");
		returnReasonRepository.save(returnReason);
		returnReason.setDescription("Fail 2");
		returnReasonRepository.update(returnReason);
		assertEquals(returnReason.getDescription(), "Fail 2");
		assertEquals(returnReasonRepository.findAll().size(), 1);
		assertEquals(returnReasonRepository.findById(returnReason.getId()).getId(), returnReason.getId());
		returnReason.toString();
		assertNull(returnReasonRepository.findById("test"));
		returnReasonRepository.remove(returnReason);
		assertEquals(returnReasonRepository.findAll().size(), 0);
	}
}
