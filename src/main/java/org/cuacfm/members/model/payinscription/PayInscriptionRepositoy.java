package org.cuacfm.members.model.payinscription;

import java.util.List;

/** The Interface PayInscriptionRepository. */
public interface PayInscriptionRepositoy {

   /**
    * Save.
    *
    * @param payInscription
    *           the pay inscription
    * @return the pay inscription
    */
   public PayInscription save(PayInscription payInscription);

   /**
    * Update.
    *
    * @param payInscription
    *           the pay inscription
    * @return the pay inscription
    */
   public PayInscription update(PayInscription payInscription);

   /**
    * Find by id.
    *
    * @param id
    *           the id
    * @return the pay inscription
    */
   public PayInscription findById(Long id);

   /**
    * Find by name.
    *
    * @param name
    *           the name
    * @return the pay inscription
    */
   public PayInscription findByName(String name);

   /**
    * Find by year.
    *
    * @param year
    *           the year
    * @return the pay inscription
    */
   public PayInscription findByYear(int year);

   /**
    * Gets the pay inscription list.
    *
    * @return the pay inscription list
    */
   public List<PayInscription> getPayInscriptionList();
}
