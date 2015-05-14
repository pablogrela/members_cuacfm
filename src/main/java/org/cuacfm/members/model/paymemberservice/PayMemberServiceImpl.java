package org.cuacfm.members.model.paymemberservice;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymember.PayMemberRepository;
import org.cuacfm.members.web.support.DisplayDate;
import org.cuacfm.members.web.support.CreatePdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfPTable;

/** The Class PayMemberServiceImpl. */
@Service("payMemberService")
public class PayMemberServiceImpl implements PayMemberService {

   /** The feeMember repository. */
   @Autowired
   private PayMemberRepository payMemberRepository;

   /** Instantiates a new feeMember service. */
   public PayMemberServiceImpl() {
      // Default empty constructor.
   }

   /**
    * Save an payMember into database.
    *
    * @param payMember
    *           the pay member
    * @return PayMember
    */
   @Override
   public PayMember save(PayMember payMember) {
      return payMemberRepository.save(payMember);
   }

   /**
    * Update PayMember.
    *
    * @param payMember
    *           the pay member
    * @return PayMember
    */
   @Override
   public PayMember update(PayMember payMember) {
      return payMemberRepository.update(payMember);
   }

   /**
    * Pay the PayMember.
    *
    * @param payMember
    *           the pay member
    */
   @Override
   public void pay(PayMember payMember) {
      payMember.setHasPay(true);
      payMember.setDatePay(new Date());
      payMemberRepository.update(payMember);
   }

   /**
    * Pay paypal.
    *
    * @param payMember
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
   @Override
   public void payPayPal(PayMember payMember, String idTxn, String idPayer,
         String emailPayer, String statusPay, String datePay) throws ExistTransactionIdException {

      PayMember paymentExist = payMemberRepository.findByIdTxn(idTxn);
      if (paymentExist != null) {
         if (paymentExist.getId() != payMember.getId()) {
            throw new ExistTransactionIdException(idTxn);
         }
      }

      payMember.setIdTxn(idTxn);
      payMember.setEmailPayer(emailPayer);
      payMember.setIdPayer(idPayer);
      payMember.setStatusPay(statusPay);
      payMember.setDatePay(DisplayDate.stringPaypalToDate(datePay));

      if (statusPay.contains("Completed")) {
         payMember.setHasPay(true);
      }
      payMemberRepository.update(payMember);
   }

   /**
    * Find by id returns feeMember which has this identifier.
    *
    * @param id
    *           the id
    * @return feeMember
    */
   @Override
   public PayMember findById(Long id) {
      return payMemberRepository.findById(id);
   }

   /**
    * Find by id txn.
    *
    * @param idTxn
    *           the id txn
    * @return the pay member
    */
   @Override
   public PayMember findByIdTxn(String idTxn) {
      return payMemberRepository.findByIdTxn(idTxn);
   }

   /**
    * Find by pay member ids.
    *
    * @param accountId
    *           the account id
    * @param feeMemberId
    *           the fee member id
    * @return List<PayMember>
    */
   @Override
   public List<PayMember> findByPayMemberIds(Long accountId, Long feeMemberId) {
      return payMemberRepository.findByPayMemberIds(accountId, feeMemberId);
   }

   /**
    * Get all feeMembers.
    *
    * @return List<FeeMember>
    */
   @Override
   public List<PayMember> getPayMemberList() {
      return payMemberRepository.getPayMemberList();
   }

   /**
    * Gets the pay member list by fee member id.
    *
    * @param feeMemberId
    *           the fee member id
    * @return the pay member list by fee member id
    */
   @Override
   public List<PayMember> getPayMemberListByFeeMemberId(Long feeMemberId) {
      return payMemberRepository
            .getPayMemberListByFeeMemberId(feeMemberId);
   }

   /**
    * Gets the pay member list by pay account id.
    *
    * @param accountId
    *           the fee member id
    * @return the pay member list by account id
    */
   @Override
   public List<PayMember> getPayMemberListByAccountId(Long accountId) {
      return payMemberRepository.getPayMemberListByAccountId(accountId);
   }

   /**
    * Gets the name users by fee member with role=ROLE_USER an active=true.
    *
    * @param feeMemberId
    *           the fee member id
    * @return the name users by fee member
    */
   @Override
   public List<String> getUsernamesByFeeMember(Long feeMemberId) {
      return payMemberRepository.getUsernamesByFeeMember(feeMemberId);
   }

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
    */
   @Override
   public void createPdfFeeMember(MessageSource messageSource, Long feeMemberId,
         String path, String title, String submit) {
      List<PayMember> payMembers = payMemberRepository
            .getPayMemberListByFeeMemberId(feeMemberId);
      CreatePdf pdf = new CreatePdf();
      PdfPTable table = pdf.createTablePayMembers(messageSource, submit,
            payMembers);
      pdf.createBody(path, title, table);
   }
}
