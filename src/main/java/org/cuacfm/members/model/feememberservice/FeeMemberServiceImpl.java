package org.cuacfm.members.model.feememberservice;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feemember.FeeMemberRepositoy;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class FeeMemberServiceImpl. */
@Service("payInscriptionService")
public class FeeMemberServiceImpl implements FeeMemberService {

   /** The FeeMember repository. */
   @Autowired
   private FeeMemberRepositoy payInscriptionRepository;

   /** The PayMemberService. */
   @Inject
   private PayMemberService userFeeMemberService;

   /** The account service. */
   @Inject
   private AccountService accountService;

   /** Instantiates a new payInscription service. */
   public FeeMemberServiceImpl() {
      // Default empty constructor.
   }

   /**
    * Save.
    *
    * @param payInscription
    *           the pay inscription
    * @return the pay inscription
    * @throws UniqueException
    */
   @Override
   public FeeMember save(FeeMember payInscription) throws UniqueException {
      // It is verified that there is not exist year of payInscription in other
      // payInscription
      if (payInscriptionRepository.findByYear(payInscription.getYear()) != null) {
         throw new UniqueException("Year", String.valueOf(payInscription.getYear()));
      }

      payInscriptionRepository.save(payInscription);
      int percent;

      // Create payments of users
      for (Account user : accountService.getUsers()) {
         if (user.getAccountType() != null) {
            percent = user.getAccountType().getDiscount();
         } else {
            percent = 0;
         }

         Double discount = (payInscription.getPrice() * percent) / 100;
         Double price = payInscription.getPrice() - discount;
         price = (price / user.getInstallments());

         // Create installments
         for (int installment = 1; installment <= user.getInstallments(); installment++) {
            PayMember userFeeMember = new PayMember(user, payInscription,
                  price, installment, user.getInstallments());
            userFeeMemberService.save(userFeeMember);
         }
      }

      return payInscription;
   }

   /**
    * Save user pay inscription.
    *
    * @param account
    *           the account
    * @param payInscription
    *           the pay inscription
    */
   @Override
   public void savePayMember(Account account, FeeMember payInscription) {

      int month = 0;
      int percent = 0;
      if (account.getAccountType() != null) {
         percent = account.getAccountType().getDiscount();
      }

      // If the same year applied the discount
      if (payInscription.getYear() == LocalDate.now().getYear()) {
         month = LocalDate.now().getMonthValue();
      }

      Double fee = payInscription.getPrice() / 12 * month;
      Double discount = (payInscription.getPrice() * percent) / 100;
      Double price = payInscription.getPrice() - discount;
      price = (price / account.getInstallments());
      Double amount = Double.valueOf(0);

      // Create installments
      for (int installment = 1; installment <= account.getInstallments(); installment++) {

         if (fee > 0) {
            amount = price - fee;
            fee = fee - price;
         } else {
            amount = price;
         }
         if (amount > 0) {
            PayMember userFeeMember = new PayMember(account, payInscription,
                  amount, installment, account.getInstallments());
            userFeeMemberService.save(userFeeMember);
         }
      }
   }

   /**
    * Update.
    *
    * @param payInscription
    *           the pay inscription
    * @return the pay inscription
    * @throws UniqueException
    */
   @Override
   public FeeMember update(FeeMember payInscription) throws UniqueException {
      // It is verified that there is not exist name of payInscription in other
      // payInscription
      FeeMember payInscriptionSearch = payInscriptionRepository.findByYear(payInscription
            .getYear());
      if (payInscriptionSearch != null) {
         if (payInscriptionSearch.getId() != payInscription.getId()) {
            throw new UniqueException("Year", String.valueOf(payInscription.getYear()));
         }
      }
      return payInscriptionRepository.update(payInscription);
   }

   /**
    * Find by Name the payInscription.
    *
    * @param name
    *           the name
    * @return FeeMember
    */
   @Override
   public FeeMember findByName(String name) {
      return payInscriptionRepository.findByName(name);
   }

   /**
    * Find by id returns payInscription which has this identifier.
    *
    * @param id
    *           the id
    * @return payInscription
    */
   @Override
   public FeeMember findById(Long id) {
      return payInscriptionRepository.findById(id);
   }

   /**
    * Find by year.
    *
    * @param year
    *           the year
    * @return the pay inscription
    */
   @Override
   public FeeMember findByYear(int year) {
      return payInscriptionRepository.findByYear(year);
   }

   /**
    * Get all payInscriptions.
    *
    * @return List<FeeMember>
    */
   @Override
   public List<FeeMember> getFeeMemberList() {
      return payInscriptionRepository.getFeeMemberList();
   }
}
