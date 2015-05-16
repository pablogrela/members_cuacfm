package org.cuacfm.members.test.model.feeprogram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class rainingTypeServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FeeProgramServiceTest extends WebSecurityConfigurationAware {

   /** The program service. */
   @Inject
   private ProgramService programService;
   

	@Inject
	private FeeProgramService feeProgramService;
	

	@Inject
	private PayProgramService payProgramService;

	/** The account service. */
	@Inject
	private AccountService accountService;
	
	/**
	 * Save and find by FeeProgram test.
	 * @throws UniqueException 
	 */
	@Test
	public void saveFeeProgramTest() throws UniqueException {

		// Save
	   Date date = DisplayDate.stringToMonthOfYear("2015-12");
		FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
		feeProgramService.save(feeProgram);

		// findById
		FeeProgram feeProgramSearch = feeProgramService
				.findById(feeProgram.getId());
		assertEquals(feeProgram, feeProgramSearch);
	}

	/**
	 * Save and find by FeeProgram test.
	 * @throws UniqueException 
	 */
	@Test
	public void saveFeeProgramWithUsersTest() throws UniqueException {
      List<Account> accounts = new ArrayList<Account>();
      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      accounts.add(account);
      
      Program program = new Program("Pepe", Float.valueOf(1), "Very interesting", 9, accounts);
      programService.save(program);
      programService.up(program.getId());
      
		// Save
      Date date = DisplayDate.stringToMonthOfYear("2015-12");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);

		// findById
		FeeProgram feeProgramSearch = feeProgramService
				.findById(feeProgram.getId());
		assertEquals(feeProgram, feeProgramSearch);

		assertEquals(payProgramService.getPayProgramListByFeeProgramId(feeProgram.getId()).size(),1);
	}
	/**
	 * Save and find by FeeProgram test.
	 * @throws UniqueException 
	 */
	@Test(expected = UniqueException.class)
	public void saveFeeProgramExceptionTest() throws UniqueException {

		// Save
      Date date = DisplayDate.stringToMonthOfYear("2015-12");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);
      feeProgramService.save(feeProgram);
	}

	/**
	 * Save and find by FeeProgram test.
	 * @throws UniqueException 
	 */
	@Test
	public void saveUserFeeProgramTest() throws UniqueException {

		// Save
      List<Account> accounts = new ArrayList<Account>();
      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      accounts.add(account);
      
      Program program = new Program("Pepe", Float.valueOf(1), "Very interesting", 9, accounts);
      programService.save(program);
      programService.update(program);
		
      Date date = DisplayDate.stringToMonthOfYear("2015-12");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);
		

		
		// findById
		
		FeeProgram feeProgramSearch = feeProgramService
				.findById(feeProgram.getId());
		assertEquals(feeProgram, feeProgramSearch);
	}
	
	/**
	 * Update Inscription
	 * @throws UniqueException 
	 */
	@Test
	public void UpdateFeeProgramTest() throws UniqueException {

		// Save
      Date date = DisplayDate.stringToMonthOfYear("2015-12");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);

		// Update
		feeProgram.setName("pay of 1016");
		feeProgram.setDate(date);
		feeProgram.setDateLimit(date);
		feeProgram.setPrice(Double.valueOf(25));
		feeProgram.setDescription("pay of 2016, tax for members");
		feeProgramService.update(feeProgram);

		// Assert
		FeeProgram feeProgramSearch = feeProgramService
				.findById(feeProgram.getId());
		assertEquals(feeProgram, feeProgramSearch);
		assertEquals(feeProgram.getName(), feeProgramSearch.getName());
		assertEquals(feeProgram.getDate(), feeProgramSearch.getDate());
		assertEquals(feeProgram.getDateLimit(), feeProgramSearch.getDateLimit());
		assertEquals(feeProgram.getPrice(), feeProgramSearch.getPrice());
		assertEquals(feeProgram.getDescription(),
				feeProgramSearch.getDescription());
	}

	/**
	 * Update Inscription Exception
	 * @throws UniqueException 
	 */
	@Test(expected = UniqueException.class)
	public void UpdateFeeProgramExceptionTest() throws UniqueException {

		// Save
      Date date = DisplayDate.stringToMonthOfYear("2015-12");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);
      Date date2 = DisplayDate.stringToMonthOfYear("2016-12");
      FeeProgram feeProgram2 = new FeeProgram("name2", Double.valueOf(25), date2, date2, "description");
      feeProgramService.save(feeProgram2);

		// Update
      FeeProgram feeProgram3 = new FeeProgram("name3", Double.valueOf(25), date, date, "description");
		feeProgramService.update(feeProgram3);
	}

	/**
	 * findByName test.
	 * @throws UniqueException 
	 */
	@Test
	public void findByNameTest() throws UniqueException {

		// Save
      Date date = DisplayDate.stringToMonthOfYear("2015-12");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);

		// findByName
		FeeProgram feeProgramSearch = feeProgramService
				.findByName(feeProgram.getName());
		assertEquals(feeProgram, feeProgramSearch);
	}

	/**
	 * findByYear test.
	 * @throws UniqueException 
	 */
	@Test
	public void findByDateTest() throws UniqueException {

		// Save
      Date date = DisplayDate.stringToMonthOfYear("2015-12");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);

		// findByName
		FeeProgram feeProgramSearch = feeProgramService.findByDate(feeProgram.getDate());
		assertEquals(feeProgram, feeProgramSearch);
	}

	/**
	 * getFeeProgramList test.
	 * @throws UniqueException 
	 */
	@Test
	public void getFeeProgramListTest() throws UniqueException {

		// getFeeProgramList, no FeePrograms
		List<FeeProgram> feeProgramList = feeProgramService
				.getFeeProgramList();
		// Assert
		assertTrue(feeProgramList.isEmpty());

		// Save
      Date date = DisplayDate.stringToMonthOfYear("2015-12");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);
      Date date2 = DisplayDate.stringToMonthOfYear("2016-12");
      FeeProgram feeProgram2 = new FeeProgram("name", Double.valueOf(25), date2, date2, "description");
      feeProgramService.save(feeProgram2);

		// getFeeProgramList
		feeProgramList = feeProgramService.getFeeProgramList();
		// Assert
		assertEquals(feeProgramList.size(), 2);
	}
}