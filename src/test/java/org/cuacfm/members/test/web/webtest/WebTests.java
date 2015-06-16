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
package org.cuacfm.members.test.web.webtest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.configuration.Configuration;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class WebTests. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Ignore
public class WebTests extends WebSecurityConfigurationAware {

   /** The configuration service. */
   @Inject
   private ConfigurationService configurationService;

   /** The account service. */
   @Inject
   private AccountService accountService;

   /** The driver. */
   private WebDriver driver;
   
   /** The login name string. */
   private String loginNameString = "admin";
   
   /** The user name string. */
   private String userNameString = "admin";
   
   /** The password string. */
   private String passwordString = "1234";

   /**
    * Close driver.
    */
   @org.junit.After
   public void closeDriver() {
      driver.close();
   }

   /**
    * Gets the driver.
    *
    * @return the driver
    * @throws UniqueException
    *            the unique exception
    */
   @Before
   public void getDriver() throws UniqueException {
      // Create Admin
      Account admin = new Account("admin", "55555555D", "London", "admin", "admin@udc.es",
            "666666666", "666666666", "1234", roles.ROLE_ADMIN);
      accountService.save(admin);

      // Create User
      Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666",
            "666666666", "demo", roles.ROLE_USER);
      accountService.save(user);

      driver = new FirefoxDriver();

      Configuration configuration = new Configuration("CuacFM", "cuacfm@org", 6666666,
            Double.valueOf(24), Double.valueOf(25), "Rul");
      configurationService.save(configuration);
   }

   /**
    * Signin web test.
    *
    * @throws InterruptedException
    *            the interrupted exception
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void signinWebTest() throws InterruptedException, UniqueException {

      driver.get("http://localhost:8080/members/signin");

      WebElement loginName = driver.findElement(By.id("login"));
      loginName.sendKeys(loginNameString);
      WebElement password = driver.findElement(By.id("password"));
      password.sendKeys(passwordString);
      // "Clickamos" en aceptar
      WebElement acceptButton = driver.findElement(By.id("acceptSignin"));
      acceptButton.click();
      // Comprobamos que está autenticado buscando su nombre en el mensaje de
      // saludo
      WebElement message = driver.findElement(By.id("nameAuthenticated"));
      assertTrue(message.getText().contains(userNameString));
   }

   /**
    * Signup web test.
    *
    * @throws InterruptedException
    *            the interrupted exception
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void signupWebTest() throws InterruptedException, UniqueException {

      driver.get("http://localhost:8080/members/signup");

      WebElement name = driver.findElement(By.id("name"));
      name.sendKeys("pepe");
      WebElement dni = driver.findElement(By.id("dni"));
      dni.sendKeys("dni");
      WebElement address = driver.findElement(By.id("address"));
      address.sendKeys("address");
      WebElement login = driver.findElement(By.id("login"));
      login.sendKeys("pepe");
      WebElement email = driver.findElement(By.id("email"));
      email.sendKeys("email@udc.es");
      WebElement phone = driver.findElement(By.id("phone"));
      phone.sendKeys("123456789");
      WebElement mobile = driver.findElement(By.id("mobile"));
      mobile.sendKeys("123456789");
      WebElement password = driver.findElement(By.id("password"));
      password.sendKeys(passwordString);
      WebElement rePassword = driver.findElement(By.id("rePassword"));
      rePassword.sendKeys(passwordString);
      WebElement rule = driver.findElement(By.id("rule"));
      rule.click();

      WebElement acceptSignup = driver.findElement(By.id("acceptSignup"));
      acceptSignup.click();
      WebElement message = driver.findElement(By.id("nameAuthenticated"));
      assertTrue(message.getText().contains("pepe"));
   }

   /**
    * Creates the fee member web test.
    *
    * @throws InterruptedException the interrupted exception
    */
   @Test
   public void createFeeMemberWebTest() throws InterruptedException {

      driver.get("http://localhost:8080/members/signin");
      WebElement loginName = driver.findElement(By.id("login"));
      loginName.sendKeys(loginNameString);
      WebElement password = driver.findElement(By.id("password"));
      password.sendKeys(passwordString);
      // "Clickamos" en aceptar
      WebElement acceptButton = driver.findElement(By.id("acceptSignin"));
      acceptButton.click();
      // Comprobamos que está autenticado buscando su nombre en el mensaje de
      // saludo
      WebElement message = driver.findElement(By.id("nameAuthenticated"));
      assertTrue(message.getText().contains(userNameString));

      driver.get(" http://localhost:8080/members/feeMemberList/feeMemberCreate");
      WebElement nameFee = driver.findElement(By.id("name"));
      nameFee.clear();
      nameFee.sendKeys("Fee of Member 2040");
      WebElement yearFee = driver.findElement(By.id("year"));
      yearFee.clear();
      yearFee.sendKeys("2040");
      WebElement priceFee = driver.findElement(By.id("price"));
      priceFee.clear();
      priceFee.sendKeys("30");
      // input type = "date", not running in Selenium
      // WebElement dateLimit1Fee = driver.findElement(By.id("dateLimit1"));
      // dateLimit1Fee.clear();
      // dateLimit1Fee.sendKeys("2040-03");
      // WebElement dateLimit2Fee = driver.findElement(By.id("dateLimit2"));
      // dateLimit2Fee.clear();
      // dateLimit2Fee.sendKeys("2040-07");
      WebElement descriptionFee = driver.findElement(By.id("description"));
      descriptionFee.clear();
      descriptionFee.sendKeys("Fee of Member 2040");

      // Create FeeMember
      WebElement create = driver.findElement(By.id("create"));
      create.click();

      driver.get(" http://localhost:8080/members/feeMemberList");
      List<WebElement> feeMembers = driver.findElements(By.id("feeMembers"));
      WebElement push = null;
      boolean found = false;
      for (WebElement row : feeMembers) {
         found = (row.findElement(By.id("feeMemberYear")).getText().equals("2040"));
         if (found) {
            push = row;
            break;
         }
      }
      push.click();

      WebElement payMemberList = driver.findElement(By.id("payMemberList"));
      payMemberList.click();

      List<WebElement> rows = driver.findElements(By.id("payMembers"));

      found = false;
      for (WebElement row : rows) {
         found = (row.findElement(By.id("payMemberName")).getText().equals("user"));
      }
      assertTrue(found);
   }

   /**
    * Creates the training type web test.
    *
    * @throws InterruptedException the interrupted exception
    */
   @Test
   public void createTrainingTypeWebTest() throws InterruptedException {

      driver.get("http://localhost:8080/members/signin");
      WebElement loginName = driver.findElement(By.id("login"));
      loginName.sendKeys(loginNameString);
      WebElement password = driver.findElement(By.id("password"));
      password.sendKeys(passwordString);
      WebElement acceptButton = driver.findElement(By.id("acceptSignin"));
      acceptButton.click();
      WebElement message = driver.findElement(By.id("nameAuthenticated"));
      assertTrue(message.getText().contains(userNameString));

      driver.get(" http://localhost:8080/members/trainingTypeList");
      WebElement createNewTrainingType = driver.findElement(By.id("createNewTrainingType"));
      createNewTrainingType.click();

      WebElement name = driver.findElement(By.id("name"));
      name.clear();
      name.sendKeys("TrainingType");
      WebElement description = driver.findElement(By.id("description"));
      description.clear();
      description.sendKeys("description");
      WebElement place = driver.findElement(By.id("place"));
      place.clear();
      place.sendKeys("place");
      WebElement duration = driver.findElement(By.id("duration"));
      duration.clear();
      duration.sendKeys("3");
      // Create FeeMember
      WebElement create = driver.findElement(By.id("create"));
      create.click();

      driver.get(" http://localhost:8080/members/trainingTypeList");
      List<WebElement> trainingTypes = driver.findElements(By.id("trainingTypes"));
      boolean found = false;
      for (WebElement row : trainingTypes) {
         if (row.findElement(By.id("trainingTypeName")).getText().equals("TrainingType")) {
            found = true;
            break;
         }
      }
      // Exist TrinigType
      assertTrue(found);

      driver.get(" http://localhost:8080/members/trainingList/trainingCreate");
      // createNewTrainingType =
      // driver.findElement(By.id("createNewTrainingType"));
      // createNewTrainingType.click();

      name = driver.findElement(By.id("name"));
      name.clear();
      name.sendKeys("TrainingType");
      description = driver.findElement(By.id("description"));
      description.clear();
      description.sendKeys("description");
      // WebElement timeLimit = driver.findElement(By.id("timeLimit"));
      // timeLimit.clear();
      // timeLimit.sendKeys("20:20");
      // WebElement dateLimit = driver.findElement(By.id("dateLimit"));
      // dateLimit.clear();
      // dateLimit.sendKeys("2040-07-10");
      // WebElement timeTraining = driver.findElement(By.id("timeTraining"));
      // timeTraining.clear();
      // timeTraining.sendKeys("20:20");
      // WebElement dateTraining = driver.findElement(By.id("dateTraining"));
      // dateTraining.clear();
      // dateTraining.sendKeys("2040-07-10");
      place = driver.findElement(By.id("place"));
      place.clear();
      place.sendKeys("place");
      duration = driver.findElement(By.id("duration"));
      duration.clear();
      duration.sendKeys("3");
      // Create training
      create = driver.findElement(By.id("create"));
      create.click();

      driver.get(" http://localhost:8080/members/trainingList");
      List<WebElement> trainings = driver.findElements(By.id("trainings"));
      found = false;
      for (WebElement row : trainings) {
         if (row.findElement(By.id("trainingName")).getText().equals("TrainingType")) {
            found = true;
            break;
         }

      }
      // Exist training
      // assertTrue(found);

      driver.get("http://localhost:8080/members/signin");
      loginName = driver.findElement(By.id("login"));
      loginName.sendKeys("user");
      password = driver.findElement(By.id("password"));
      password.sendKeys(passwordString);
      acceptButton = driver.findElement(By.id("acceptSignin"));
      acceptButton.click();
      message = driver.findElement(By.id("nameAuthenticated"));
      assertTrue(message.getText().contains("user"));

      driver.get(" http://localhost:8080/members/trainingList");
      WebElement joinTraining = driver.findElement(By.id("joinTraining"));
      joinTraining.click();

      driver.get(" http://localhost:8080/members/trainingUserList");
      List<WebElement> inscriptions = driver.findElements(By.id("inscriptions"));
      assertTrue(inscriptions.size() > 0);
      
      
      driver.get("http://localhost:8080/members/signin");
      loginName = driver.findElement(By.id("login"));
      loginName.sendKeys(loginNameString);
      password = driver.findElement(By.id("password"));
      password.sendKeys(passwordString);
      acceptButton = driver.findElement(By.id("acceptSignin"));
      acceptButton.click();
      message = driver.findElement(By.id("nameAuthenticated"));
      assertTrue(message.getText().contains(userNameString));

      driver.get(" http://localhost:8080/members/trainingList");
      createNewTrainingType = driver.findElement(By.id("viewInscriptions"));
      createNewTrainingType.click();
      WebElement note = driver.findElement(By.id("note"));
      note.sendKeys("note");
      WebElement attend = driver.findElement(By.id("attend"));
      attend.click();
      WebElement pass = driver.findElement(By.id("pass"));
      pass.click();
      WebElement unsubscribe = driver.findElement(By.id("unsubscribe"));
      unsubscribe.click();
      WebElement update = driver.findElement(By.id("update"));
      update.click();
      WebElement save = driver.findElement(By.id("save"));
      save.click();
   }
}
