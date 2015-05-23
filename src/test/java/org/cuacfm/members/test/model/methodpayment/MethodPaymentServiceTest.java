package org.cuacfm.members.test.model.methodpayment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class MethodPaymentServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MethodPaymentServiceTest extends WebSecurityConfigurationAware {

   /** The Method payment service. */
   @Autowired
   private MethodPaymentService methodPaymentService;

   /**
    * Save and find method payment test.
    * 
    * @throws UniqueException
    */
   @Test
   public void saveAndFindMethodPaymentTest() throws UniqueException {
      // Save
      MethodPayment methodPayment = new MethodPayment("Cash", false, "Cash");
      methodPaymentService.save(methodPayment);

      // Assert
      MethodPayment methodPaymentSearched = methodPaymentService.findById(methodPayment.getId());
      assertEquals(methodPayment, methodPaymentSearched);
   }

   /**
    * Save method payment exception test.
    * 
    * @throws UniqueException
    */
   @Test(expected = UniqueException.class)
   public void saveMethodPaymentExceptionTest() throws UniqueException {
      // Save
      MethodPayment methodPayment = new MethodPayment("Cash", false, "Cash");
      methodPaymentService.save(methodPayment);

      MethodPayment methodPayment2 = new MethodPayment("Cash", false, "Cash");
      methodPaymentService.save(methodPayment2);
   }

   /**
    * Update method payment test.
    * 
    * @throws UniqueException
    */
   @Test
   public void updateMethodPaymentTest() throws UniqueException {
      // Save
      MethodPayment methodPayment = new MethodPayment("Cash", false, "Cash");
      methodPaymentService.save(methodPayment);

      // Update
      methodPayment.setName("Domiciled");
      methodPayment.setDescription("His current account");
      methodPayment.setDirectDebit(true);
      MethodPayment methodPaymentUpdate = methodPaymentService.update(methodPayment);

      // Assert
      assertEquals(methodPayment, methodPaymentUpdate);
      assertEquals(methodPayment.getName(), methodPaymentUpdate.getName());
      assertEquals(methodPayment.isDirectDebit(), methodPaymentUpdate.isDirectDebit());
      assertEquals(methodPayment.getDescription(), methodPaymentUpdate.getDescription());

      methodPayment.setDescription("etc");
      methodPaymentUpdate = methodPaymentService.update(methodPayment);

      MethodPayment methodPayment2 = new MethodPayment("Cash2", false, "Cash");
      methodPaymentService.update(methodPayment2);
   }

   /**
    * Update method payment exception test.
    * 
    * @throws UniqueException
    */
   @Test(expected = UniqueException.class)
   public void updateMethodPaymentExceptionTest() throws UniqueException {
      // Save
      MethodPayment methodPayment = new MethodPayment("Cash", false, "Cash");
      methodPaymentService.save(methodPayment);
      MethodPayment methodPayment2 = new MethodPayment("Domiciled", false, "His current account");
      methodPaymentService.save(methodPayment2);

      // Update
      MethodPayment methodPayment3 = new MethodPayment("Domiciled", true, "His current account");
      methodPaymentService.update(methodPayment3);
   }

   /**
    * Delete method payment test.
    * 
    * @throws UniqueException
    */
   @Test
   public void deleteMethodPaymentTest() throws UniqueException {
      // Save
      MethodPayment methodPayment = new MethodPayment("Cash", false, "Cash");
      methodPaymentService.save(methodPayment);

      // Assert
      MethodPayment methodPaymentSearched = methodPaymentService.findById(methodPayment.getId());
      assertNotNull(methodPaymentSearched);

      // Delete
      methodPaymentService.delete(methodPayment.getId());

      // Assert, no exist MethodPayment
      methodPaymentSearched = methodPaymentService.findById(methodPayment.getId());
      assertNull(methodPaymentSearched);
   }

   /**
    * Delete null method payment test.
    */
   @Test
   public void deleteNullMethodPaymentTest() {
      // Delete
      methodPaymentService.delete(Long.valueOf(0));
   }

   /**
    * Save and find by name method payment test.
    * 
    * @throws UniqueException
    */
   @Test
   public void saveAndFindByNameMethodPaymentTest() throws UniqueException {
      // Save
      MethodPayment methodPayment = new MethodPayment("Cash", false, "Cash");
      methodPaymentService.save(methodPayment);

      // Assert
      MethodPayment methodPaymentSearched = methodPaymentService
            .findByName(methodPayment.getName());
      assertEquals(methodPayment, methodPaymentSearched);
   }

   /**
    * Gets the method payments test.
    *
    * @return the method payments test
    * @throws UniqueException
    */
   @Test
   public void getMethodPaymentsTest() throws UniqueException {
      // Assert
      List<MethodPayment> methodPayments = methodPaymentService.getMethodPayments();
      assertEquals(methodPayments.size(), 0);

      // Save
      MethodPayment methodPayment = new MethodPayment("Cash", false, "Cash");
      methodPaymentService.save(methodPayment);
      MethodPayment methodPayment2 = new MethodPayment("Domiciled", false, "His current account");
      methodPaymentService.save(methodPayment2);

      // Assert
      methodPayments = methodPaymentService.getMethodPayments();
      assertEquals(methodPayments.size(), 2);
      assertTrue(methodPayments.contains(methodPayment));
      assertTrue(methodPayments.contains(methodPayment2));
   }
}
