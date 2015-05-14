package org.cuacfm.members.model.paymemberservice;

import java.util.List;

import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.paymember.PayMember;
import org.springframework.context.MessageSource;

/** The Interface PayMemberService. */
public interface PayMemberService {

   /**
    * Save an Fee Member into database.
    *
    * @param paymember
    *           the pay member
    * @return the training
    */
   public PayMember save(PayMember paymember);

   /**
    * Update pay member.
    *
    * @param paymember
    *           the pay member
    * @return PayMember
    */
   public PayMember update(PayMember paymember);

   /**
    * Pay the PayMember.
    *
    * @param paymember
    *           the pay member
    */
   public void pay(PayMember paymember);

   /**
    * Pay pay pal.
    *
    * @param paymember
    *           the pay member
    * @param idTxn
    *           the id txn
    * @param idPayer
    *           the id payer
    * @param emailPayer
    *           the email payer
    * @param statusPay
    *           the status pay
    * @param datePay
    *           the date pay
    * @throws ExistTransactionIdException
    *            the exist transaction id exception
    */
   public void payPayPal(PayMember paymember, String idTxn, String idPayer,
         String emailPayer, String statusPay, String datePay) throws ExistTransactionIdException;

   /**
    * Find by id returns paymember which has this identifier.
    *
    * @param id
    *           the id
    * @return paymember
    */
   public PayMember findById(Long id);

   /**
    * Find by id txn.
    *
    * @param idTxn
    *           the id txn
    * @return the pay member
    */
   public PayMember findByIdTxn(String idTxn);

   /**
    * Find by pay member ids.
    *
    * @param accountId
    *           the account id
    * @param feeMemberId
    *           the fee member id
    * @return List<PayMember>
    */
   public List<PayMember> findByPayMemberIds(Long accountId, Long feeMemberId);

   /**
    * Get all paymembers.
    *
    * @return List<PayMember>
    */
   public List<PayMember> getPayMemberList();

   /**
    * Gets the pay member list by fee member id.
    *
    * @param feeMemberId
    *           the fee member id
    * @return the pay member list by fee member id
    */
   public List<PayMember> getPayMemberListByFeeMemberId(Long feeMemberId);

   /**
    * Gets the pay member list by pay account id.
    *
    * @param accountId
    *           the fee member id
    * @return the pay member list by account id
    */
   public List<PayMember> getPayMemberListByAccountId(Long accountId);

   /**
    * Gets the name users by fee member with role=ROLE_USER an active=true.
    *
    * @param feeMemberId
    *           the fee member id
    * @return the name users by fee member
    */
   public List<String> getUsernamesByFeeMember(Long feeMemberId);

   /**
    * Creates the pdf fee member.
    *
    * @param messageSource
    *           the message source
    * @param feeMemberId
    *           the fee member id
    * @param path
    *           the path
    * @param title
    *           the title
    * @param submit
    *           the submit
    */
   public void createPdfFeeMember(MessageSource messageSource, Long feeMemberId,
         String path, String title, String submit);
}
