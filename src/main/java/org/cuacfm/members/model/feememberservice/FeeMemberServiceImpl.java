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
package org.cuacfm.members.model.feememberservice;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feemember.FeeMemberRepository;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class FeeMemberServiceImpl. */
@Service("feeMemberService")
public class FeeMemberServiceImpl implements FeeMemberService {

   /** The FeeMember repository. */
   @Autowired
   private FeeMemberRepository feeMemberRepository;

   /** The PayMemberService. */
   @Autowired
   private PayMemberService payMemberService;

   /** The account service. */
   @Autowired
   private AccountService accountService;

   /** Instantiates a new feeMember service. */
   public FeeMemberServiceImpl() {
      // Default empty constructor.
   }

   /**
    * Save.
    *
    * @param feeMember
    *           the pay inscription
    * @return the pay inscription
    * @throws UniqueException
    */
   @Override
   public FeeMember save(FeeMember feeMember) throws UniqueException {
      // It is verified that there is not exist year of feeMember in other
      // feeMember
      if (feeMemberRepository.findByYear(feeMember.getYear()) != null) {
         throw new UniqueException("Year", String.valueOf(feeMember.getYear()));
      }

      feeMemberRepository.save(feeMember);
      int percent;

      // Create payments of users
      for (Account user : accountService.getUsers()) {
         percent = 0;
         if (user.getAccountType() != null) {
            percent = user.getAccountType().getDiscount();
         }

         if (percent != 100) {
            Double discount = (feeMember.getPrice() * percent) / 100;
            Double price = feeMember.getPrice() - discount;
            price = (price / user.getInstallments());

            // Create installments
            for (int installment = 1; installment <= user.getInstallments(); installment++) {

               int value = (installment - 1) * 12 / user.getInstallments();
               if (installment == 1) {
                  value = 1;
               }
               LocalDate dateFormat = LocalDate.now();
               dateFormat = dateFormat.withDayOfMonth(1);
               dateFormat = dateFormat.withMonth(value);
               dateFormat = dateFormat.withYear(feeMember.getYear());
               Date dateCharge = Date.from(dateFormat.atStartOfDay(ZoneId.systemDefault())
                     .toInstant());

               PayMember payMember = new PayMember(user, feeMember, price, installment,
                     user.getInstallments(), dateCharge);
               payMemberService.save(payMember);
            }
         }
      }

      return feeMember;
   }

   /**
    * Save user pay inscription.
    *
    * @param account
    *           the account
    * @param feeMember
    *           the pay inscription
    */
   @Override
   public void savePayMember(Account account, FeeMember feeMember) {

      int month = 0;
      int percent = 0;
      if (account.getAccountType() != null) {
         percent = account.getAccountType().getDiscount();
      }

      // If the same year applied the discount
      if (feeMember.getYear() == LocalDate.now().getYear()) {
         month = LocalDate.now().getMonthValue();
      }

      Double fee = feeMember.getPrice() / 12 * month;
      Double discount = (feeMember.getPrice() * percent) / 100;
      Double price = feeMember.getPrice() - discount;
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

            int value = (installment - 1) * 12 / account.getInstallments();
            if (installment == 1) {
               value = 1;
            }
            LocalDate dateFormat = LocalDate.now();
            dateFormat = dateFormat.withDayOfMonth(1);
            dateFormat = dateFormat.withMonth(value);
            dateFormat = dateFormat.withYear(feeMember.getYear());
            Date dateCharge = Date
                  .from(dateFormat.atStartOfDay(ZoneId.systemDefault()).toInstant());

            PayMember payMember = new PayMember(account, feeMember, amount, installment,
                  account.getInstallments(), dateCharge);
            payMemberService.save(payMember);
         }
      }
   }

   /**
    * Update.
    *
    * @param feeMember
    *           the pay inscription
    * @return the pay inscription
    * @throws UniqueException
    */
   @Override
   public FeeMember update(FeeMember feeMember) throws UniqueException {
      // It is verified that there is not exist name of feeMember in other
      // feeMember
      FeeMember feeMemberSearch = feeMemberRepository.findByYear(feeMember.getYear());
      if ((feeMemberSearch != null) && (feeMemberSearch.getId() != feeMember.getId())) {
         throw new UniqueException("Year", String.valueOf(feeMember.getYear()));
      }
      return feeMemberRepository.update(feeMember);
   }

   /**
    * Find by Name the feeMember.
    *
    * @param name
    *           the name
    * @return FeeMember
    */
   @Override
   public FeeMember findByName(String name) {
      return feeMemberRepository.findByName(name);
   }

   /**
    * Find by id returns feeMember which has this identifier.
    *
    * @param id
    *           the id
    * @return feeMember
    */
   @Override
   public FeeMember findById(Long id) {
      return feeMemberRepository.findById(id);
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
      return feeMemberRepository.findByYear(year);
   }

   /**
    * Get all feeMembers.
    *
    * @return List<FeeMember>
    */
   @Override
   public List<FeeMember> getFeeMemberList() {
      return feeMemberRepository.getFeeMemberList();
   }
}
