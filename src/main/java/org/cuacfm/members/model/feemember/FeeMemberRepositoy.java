package org.cuacfm.members.model.feemember;

import java.util.List;

/** The Interface FeeMemberRepository. */
public interface FeeMemberRepositoy {

   /**
    * Save.
    *
    * @param payInscription
    *           the fee member
    * @return the fee member
    */
   public FeeMember save(FeeMember payInscription);

   /**
    * Update.
    *
    * @param payInscription
    *           the fee member
    * @return the fee member
    */
   public FeeMember update(FeeMember payInscription);

   /**
    * Find by id.
    *
    * @param id
    *           the id
    * @return the fee member
    */
   public FeeMember findById(Long id);

   /**
    * Find by name.
    *
    * @param name
    *           the name
    * @return the fee member
    */
   public FeeMember findByName(String name);

   /**
    * Find by year.
    *
    * @param year
    *           the year
    * @return the fee member
    */
   public FeeMember findByYear(int year);

   /**
    * Gets the fee member list.
    *
    * @return the fee member list
    */
   public List<FeeMember> getFeeMemberList();
}
