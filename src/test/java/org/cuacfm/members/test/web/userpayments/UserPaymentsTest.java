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
package org.cuacfm.members.test.web.userpayments;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.configuration.Configuration;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.directdebitservice.DirectDebitService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class PayMemberListControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserPaymentsTest extends WebSecurityConfigurationAware {

	/** The default session. */
	private MockHttpSession defaultSession;

	/** The configuration service. */
	@Inject
	private ConfigurationService configurationService;

	/** The account service. */
	@Inject
	private AccountService accountService;

	/** The account type service. */
	@Inject
	private AccountTypeService accountTypeService;

	/** The method payment service. */
	@Inject
	private MethodPaymentService methodPaymentService;

	/** The training service. */
	@Inject
	private FeeMemberService feeMemberService;

	/** The training service. */
	@Inject
	private PayMemberService userFeeMemberService;

	/** The program service. */
	@Inject
	private ProgramService programService;

	/** The fee program service. */
	@Inject
	private FeeProgramService feeProgramService;

	/** The pay program service. */
	@Inject
	private PayProgramService payProgramService;

	@Inject
	private DirectDebitService directDebitService;

	private Account user;
	private Program program;
	private FeeProgram feeProgram;
	private AccountType accountType;
	private MethodPayment methodPayment;
	private FeeMember feeMember;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 * @throws UniqueListException the unique list exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		Configuration configuration = new Configuration("CuacFM", "cuacfm@hotmail.com", 6666666, Double.valueOf(24), Double.valueOf(25), "Rul");
		configurationService.save(configuration);

		// Create User
		user = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user);
		accountType = new AccountType("Adult", false, "Fee for adults", 0);
		accountTypeService.save(accountType);
		methodPayment = new MethodPayment("cash", false, "cash");
		methodPaymentService.save(methodPayment);
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(1);
		accountService.update(user, false, true);
		defaultSession = getDefaultSession("user@udc.es");

		// Create Payment
		feeMember = new FeeMember("pay of 2016", 2016, Double.valueOf(20), DateUtils.format("2016-04-05", DateUtils.FORMAT_DATE),
				DateUtils.format("2016-07-05", DateUtils.FORMAT_DATE), "pay of 2016");
		feeMemberService.save(feeMember);

		// Create Program and Payments
		List<Account> accounts = new ArrayList<Account>();
		List<Program> programs = new ArrayList<Program>();
		accounts.add(user);
		program = new Program("Program 1", "About program", Float.valueOf(1), 9, accounts, user, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programs.add(program);
		programService.save(program);
		programService.up(program);

		feeProgram = new FeeProgram("Fee March 2015", Double.valueOf(25), DateUtils.format("2016-03", DateUtils.FORMAT_MONTH_YEAR),
				DateUtils.format("2016-03", DateUtils.FORMAT_MONTH_YEAR), "Fee for program");
		feeProgramService.save(feeProgram);

		user.setPrograms(programs);
		accountService.update(user, false, true);

		directDebitService.save(user);
	}

	/**
	 * Display TrainingView page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayuserPaymentsViewPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/userPayments")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Send displaysTrainingView.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displayUserPaymentsTest() throws Exception {

		// Charge csrf in pay member
		PayMember userFeeMember = userFeeMemberService.findByPayMemberIds(user.getId(), feeMember.getId()).get(0);
		mockMvc.perform(post("/userPayments/payMember/" + userFeeMember.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
				.param("payment_status", "Completed").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));

		// Charge csrf in pay program
		PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(), feeProgram.getId());
		mockMvc.perform(post("/userPayments/payProgram/" + payProgram.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
				.param("payment_status", "Completed").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));

		CsrfToken token = new DefaultCsrfToken("headerName", "parameterName", "token");
		try {
			mockMvc.perform(get("/userPayments").locale(Locale.ENGLISH).session(defaultSession).sessionAttr("_csrf", token))
					.andExpect(view().name("userpayments/userpayments")).andExpect(content().string(containsString("<title>My payments</title>")));
		} catch (Exception e) {
			// prueba
		}
	}

	/**
	 * Pay user pay inscription test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payPayMemberTest() throws Exception {

		PayMember userFeeMember = userFeeMemberService.findByPayMemberIds(user.getId(), feeMember.getId()).get(0);
		// Assert no pay
		assertTrue(userFeeMember.getState().equals(states.NO_PAY));

		mockMvc.perform(post("/userPayments/payMember/" + userFeeMember.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
				.param("payment_status", "Completed").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));

		// Assert Pay
		assertTrue(userFeeMember.getState().equals(states.PAY));
	}

	/**
	 * Pay user pay inscription test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payProgressPayMemberTest() throws Exception {

		PayMember userFeeMember = userFeeMemberService.findByPayMemberIds(user.getId(), feeMember.getId()).get(0);
		// Assert no pay
		assertTrue(userFeeMember.getState().equals(states.NO_PAY));

		mockMvc.perform(post("/userPayments/payMember/" + userFeeMember.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
				.param("payment_status", "Progress").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));

		// Assert Pay
		assertTrue(userFeeMember.getState().equals(states.MANAGEMENT));
		assertEquals(userFeeMember, userFeeMemberService.findByIdTxn("txn"));
	}

	/**
	 * Pay Exist TransactionId Exception Test.
	 *
	 * @throws Exception the exception
	 */
	//	@Test
	//	public void payMemberExistTransactionIdExceptionTest() throws Exception {
	//
	//		PayMember userFeeMember = userFeeMemberService.findByPayMemberIds(user.getId(), feeMember.getId()).get(0);
	//
	//		FeeMember feeMember2 = new FeeMember("pay of 2017", 2017, Double.valueOf(20), DisplayDate.format("2017-04-05", DisplayDate.FORMAT_DATE),
	//				DisplayDate.format("2017-07-05", DisplayDate.FORMAT_DATE), "pay of 2017");
	//		feeMemberService.save(feeMember2);
	//
	//		PayMember userFeeMember2 = userFeeMemberService.findByPayMemberIds(user.getId(), feeMember2.getId()).get(0);
	//
	//		// Assert no pay
	//		assertTrue(userFeeMember.getState().equals(states.NO_PAY));
	//
	//		mockMvc.perform(post("/userPayments/payMember/" + userFeeMember.getId()).locale(Locale.ENGLISH).session(defaultSession)
	//				.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
	//				.param("payment_status", "Completed").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));
	//
	//		mockMvc.perform(post("/userPayments/payMember/" + userFeeMember.getId()).locale(Locale.ENGLISH).session(defaultSession)
	//				.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
	//				.param("payment_status", "Completed").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));
	//
	//		mockMvc.perform(post("/userPayments/payMember/" + userFeeMember2.getId()).locale(Locale.ENGLISH).session(defaultSession)
	//				.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
	//				.param("payment_status", "Completed").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));
	//
	//		// Assert Pay
	//		assertTrue(userFeeMember.getState().equals(states.PAY));
	//	}

	/**
	 * Pay Other Account PayMember Test
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payOtherAccountPayMemberTest() throws Exception {

		PayMember userFeeMember = userFeeMemberService.findByPayMemberIds(user.getId(), feeMember.getId()).get(0);
		// Assert no pay
		assertTrue(userFeeMember.getState().equals(states.NO_PAY));
		Account user2 = new Account("user2", "2", "55555555B", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user2);
		user2.setAccountType(accountType);
		user2.setMethodPayment(methodPayment);
		user2.setInstallments(1);
		accountService.update(user2, false, true);

		feeMemberService.savePayMember(user2, feeMember);
		List<PayMember> userFeeMemberProbe = userFeeMemberService.getPayMemberListByAccountId(user2.getId());

		if (userFeeMemberProbe != null && !userFeeMemberProbe.isEmpty()) {
			PayMember payMember = userFeeMemberProbe.get(0);
			mockMvc.perform(post("/userPayments/payMember/" + payMember.getId()).locale(Locale.ENGLISH).session(defaultSession)
					.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
					.param("payment_status", "Completed").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));

			// Assert Pay
			assertTrue(payMember.getState().equals(states.NO_PAY));
			//assertTrue(payMember.getMethod().equals(null));
		}
	}

	/**
	 * Pay user pay inscription test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payProgramPayProgramTest() throws Exception {

		PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(), feeProgram.getId());
		// Assert no pay
		assertTrue(payProgram.getState().equals(states.NO_PAY));

		mockMvc.perform(post("/userPayments/payProgram/" + payProgram.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
				.param("payment_status", "Completed").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));

		// Assert Pay
		assertTrue(payProgram.getState().equals(states.PAY));
		assertTrue(payProgram.getMethod().equals(methods.PAYPAL));
	}

	/**
	 * Pay user pay inscription test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payProgramProgressUserPayProgramTest() throws Exception {

		PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(), feeProgram.getId());
		// Assert no pay
		assertTrue(payProgram.getState().equals(states.NO_PAY));

		mockMvc.perform(post("/userPayments/payProgram/" + payProgram.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
				.param("payment_status", "Progress").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));

		// Assert Pay
		assertTrue(payProgram.getState().equals(states.MANAGEMENT));
		assertEquals(payProgram, payProgramService.findByIdTxn("txn"));
	}

	/**
	 * Pay Exist TransactionId Exception Test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payProgramExistTransactionIdExceptionTest() throws Exception {

		PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(), feeProgram.getId());

		FeeProgram feeProgram2 = new FeeProgram("Fee April 2015", Double.valueOf(25), DateUtils.format("2016-04", DateUtils.FORMAT_MONTH_YEAR),
				DateUtils.format("2016-04", DateUtils.FORMAT_MONTH_YEAR), "Fee for program");
		feeProgramService.save(feeProgram2);
		PayProgram payProgram2 = payProgramService.findByPayProgramIds(program.getId(), feeProgram2.getId());

		// Assert no pay
		assertTrue(payProgram.getState().equals(states.NO_PAY));

		mockMvc.perform(post("/userPayments/payProgram/" + payProgram.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
				.param("payment_status", "Completed").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));

		mockMvc.perform(post("/userPayments/payProgram/" + payProgram.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
				.param("payment_status", "Completed").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));

		mockMvc.perform(post("/userPayments/payProgram/" + payProgram2.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
				.param("payment_status", "Completed").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));

		// Assert Pay
		assertTrue(payProgram.getState().equals(states.PAY));
	}

	/**
	 * Pay Other Account PayMember Test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payProgramOtherAccountPayProgramTest() throws Exception {

		PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(), feeProgram.getId());
		// Assert no pay
		assertTrue(payProgram.getState().equals(states.NO_PAY));

		Account user2 = new Account("user2", "2", "55555555B", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user2);
		user2.setAccountType(accountType);
		user2.setMethodPayment(methodPayment);
		user2.setInstallments(1);
		accountService.update(user2, false, true);

		// Create Program and Payments
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(user2);
		Program program2 = new Program("Program 2", "About program", Float.valueOf(1), 9, accounts, user2, programService.findProgramTypeById(1),
				programService.findProgramThematicById(1), programService.findProgramCategoryById(1), programService.findProgramLanguageById(1), "",
				"", "", "", "");
		programService.save(program2);
		programService.up(program2);

		FeeProgram feeProgram2 = new FeeProgram("Fee Jun 2015", Double.valueOf(25), DateUtils.format("2016-06", DateUtils.FORMAT_MONTH_YEAR),
				DateUtils.format("2016-08", DateUtils.FORMAT_MONTH_YEAR), "Fee for program");
		feeProgramService.save(feeProgram2);

		PayProgram payProgramProbe = payProgramService.findByPayProgramIds(program2.getId(), feeProgram2.getId());

		mockMvc.perform(post("/userPayments/payProgram/" + payProgramProbe.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
				.param("payment_status", "Completed").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));

		// Assert Pay
		assertTrue(payProgram.getState().equals(states.NO_PAY));
	}

	/**
	 * Direct debit list test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void directDebitListTest() throws Exception {

		mockMvc.perform(get("userPayments/directDebitList/").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(get("userPayments/directDebitList/open").locale(Locale.ENGLISH).session(defaultSession));

		DirectDebit directDebit = directDebitService.findAll().get(0);
		// Assert no pay
		assertTrue(directDebit.getState().equals(states.NO_PAY));

		mockMvc.perform(post("/userPayments/directDebitList/paypal/" + directDebit.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf").param("payer_email", "email").param("payer_id", "id").param("payment_date", "10:10:10 Jun 10, 2015")
				.param("payment_status", "Completed").param("txn_id", "txn")).andExpect(view().name("redirect:/userPayments"));
		assertTrue(directDebit.getState().equals(states.PAY));

		DirectDebit directDebit2 = new DirectDebit(user, "directDebit");
		mockMvc.perform(post("/userPayments/directDebitList/markBankDeposit/" + directDebit2.getId()).locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(
				post("/userPayments/directDebitList/cancelBankDeposit/" + directDebit2.getId()).locale(Locale.ENGLISH).session(defaultSession));
		assertTrue(directDebit2.getState().equals(states.NO_PAY));

		mockMvc.perform(post("/userPayments/directDebitList/markBankDeposit/" + directDebit2.getId()).locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(
				post("/userPayments/directDebitList/cancelBankDeposit/" + directDebit2.getId()).locale(Locale.ENGLISH).session(defaultSession));
		assertTrue(directDebit2.getState().equals(states.NO_PAY));
	}

	/**
	 * Displaysdirect debit list test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displaysdirectDebitListTest() throws Exception {
		mockMvc.perform(get("/userPayments/directDebitList/").locale(Locale.ENGLISH).session(defaultSession));

		for (DirectDebit directDebit : directDebitService.findAll()) {
			directDebitService.updateDirectDebit(directDebit, states.CANCEL, methods.NO_PAY, new Date());
		}
		mockMvc.perform(get("/directDebitList/").locale(Locale.ENGLISH).session(defaultSession));
	}

	/**
	 * Displaysdirect debit list close test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displaysdirectDebitListCloseTest() throws Exception {
		mockMvc.perform(get("/userPayments/directDebitList/open/").locale(Locale.ENGLISH).session(defaultSession));

		for (DirectDebit directDebit : directDebitService.findAll()) {
			directDebitService.updateDirectDebit(directDebit, states.CANCEL, methods.NO_PAY, new Date());
		}
		mockMvc.perform(get("/userPayments/directDebitList/open/").locale(Locale.ENGLISH).session(defaultSession));
	}

	/**
	 * Pay bank deposit direct debit test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payBankDepositDirectDebitTest() throws Exception {
		DirectDebit directDebit = directDebitService.findAllOpenByAccountId(user.getId()).get(0);

		mockMvc.perform(post("/userPayments//directDebitList/markBankDeposit/" + directDebit.getId()).locale(Locale.ENGLISH).session(defaultSession));
		assertTrue(directDebit.getState().equals(states.MANAGEMENT));
		mockMvc.perform(
				post("/userPayments/directDebitList/cancelBankDeposit/" + directDebit.getId()).locale(Locale.ENGLISH).session(defaultSession));
		assertTrue(directDebit.getState().equals(states.NO_PAY));
		mockMvc.perform(post("/userPayments/directDebitList/markBankDeposit/" + directDebit.getId()).locale(Locale.ENGLISH).session(defaultSession));
		assertTrue(directDebit.getState().equals(states.NO_PAY));
	}
}