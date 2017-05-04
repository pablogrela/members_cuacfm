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
package org.cuacfm.members.test.web.signin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SigninControllerTest extends WebSecurityConfigurationAware {

    /** The account service. */
	@Inject
	private AccountService accountService;	
	
	/** The account type service. */
	@Inject
	private AccountTypeService accountTypeService;

	/** The method payment service. */
	@Inject
	private MethodPaymentService methodPaymentService;
	
	/** The user. */
	private Account user;
	
	/** The account type. */
	private AccountType accountType;
	
	/** The method payment. */
	private MethodPayment methodPayment;
	
    /**
     * Initialize default session.
     * @throws UniqueException 
     */
    @Before
    public void initializeDefaultSession() throws UniqueException, UniqueListException {
		accountType = new AccountType("Adult", true, "Fee for adults", 0);
		accountTypeService.save(accountType);
		methodPayment = new MethodPayment("cash", false, "cash");
		methodPaymentService.save(methodPayment);
		
        
        // Create User
		user = new Account("user", "1", "55555555C", "London", "user", "email1@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		accountService.save(user);
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(1);
		accountService.update(user, false, true);
    }
    
    /**
     * Show page sigin
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displaySignInPage() throws Exception {
        mockMvc.perform(get("/signin")).andExpect(view().name("signin/signin"));
    }
    
	/**
	 * Send null.
	 * @throws Exception the exception
	 */
	@Test
	@Ignore
	// Up and Down user only in firebase
	public void downUserTest() throws Exception {    
		user.setActive(false);
		accountService.update(user, false, true);
		
		mockMvc.perform(post("/signin").locale(Locale.ENGLISH)
				.param("username", "user")
				.param("password", "demo"))
		.andExpect(view().name("signin/signin"));
	}
}