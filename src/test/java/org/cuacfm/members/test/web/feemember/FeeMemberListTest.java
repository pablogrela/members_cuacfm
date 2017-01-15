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
package org.cuacfm.members.test.web.feemember;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/** The Class FeeMemberListControllerTest.*/
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FeeMemberListTest extends WebSecurityConfigurationAware {

    /** The default session. */
    private MockHttpSession defaultSession;

    /** The account service. */
	@Inject
	private AccountService accountService;

	/** The account type service. */
	@Inject
	private AccountTypeService accountTypeService;

	/** The method payment service. */
	@Inject
	private MethodPaymentService methodPaymentService;
	
	/** The pay Inscription service. */
	@Inject
	private FeeMemberService feeMemberService;

	/** The user. */
	private Account user;
	
	/** The account type. */
	private AccountType accountType;
	
	/** The method payment. */
	private MethodPayment methodPayment;
	
	/** The pay inscription. */
	private FeeMember feeMember;
	
    /**
     * Initialize default session.
     * @throws UniqueException 
     */
    @Before
    public void initializeDefaultSession() throws UniqueException, UniqueListException {
		Account admin = new Account("admin", "55555555D", "London", "admin", "admin@udc.es", "666666666", "666666666","demo", roles.ROLE_ADMIN);
		accountService.save(admin);
        defaultSession = getDefaultSession("admin");
        
        // Create User
		user = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		accountService.save(user);
		accountType = new AccountType("Adult", false, "Fee for adults", 0);
		accountTypeService.save(accountType);
		methodPayment = new MethodPayment("cash", false, "cash");
		methodPaymentService.save(methodPayment);
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(1);
		accountService.update(user, false, true);
		
		//Create Payment
		feeMember = new FeeMember("pay of 2016",
				2016, Double.valueOf(20), DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"), "pay of 2016");
		feeMemberService.save(feeMember);
    }

	
    /**
     * Display FeeMemberListView page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayFeeMemberViewPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/feeMemberList")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send displaysFeeMemberView.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysInscriptionsTest() throws Exception {    
		
		mockMvc.perform(get("/feeMemberList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("feemember/feememberlist"))
				.andExpect(content().string(containsString("<title>Fee members</title>")));
	}	
}