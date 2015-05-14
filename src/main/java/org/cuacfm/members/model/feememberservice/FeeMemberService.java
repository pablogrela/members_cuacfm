package org.cuacfm.members.model.feememberservice;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feemember.FeeMember;

/** The Class FeeMemberService. */
public interface FeeMemberService {

   /**
    * Save.
    *
    * @param payInscription
    *           the pay inscription
    * @return the pay inscription
    * @throws UniqueException
    */
   public FeeMember save(FeeMember payInscription) throws UniqueException;

   /**
    * Save user pay inscription.
    *
    * @param account
    *           the account
    * @param payInscription
    *           the pay inscription
    */
   public void savePayMember(Account account, FeeMember payInscription);

   /**
    * Update.
    *
    * @param payInscription
    *           the pay inscription
    * @return the pay inscription
    * @throws UniqueException
    */
   public FeeMember update(FeeMember payInscription) throws UniqueException;

   /**
    * Find by Name the payInscription.
    *
    * @param name
    *           the name
    * @return FeeMember
    */
   public FeeMember findByName(String name);

   /**
    * Find by id returns payInscription which has this identifier.
    *
    * @param id
    *           the id
    * @return payInscription
    */
   public FeeMember findById(Long id);

   /**
    * Find by year.
    *
    * @param year
    *           the year
    * @return the pay inscription
    */
   public FeeMember findByYear(int year);

   /**
    * Get all payInscriptions.
    *
    * @return List<FeeMember>
    */
   public List<FeeMember> getFeeMemberList();
}
