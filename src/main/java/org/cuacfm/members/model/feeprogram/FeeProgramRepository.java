package org.cuacfm.members.model.feeprogram;

import java.util.Date;
import java.util.List;

/** The Interface FeeProgramRepository. */
public interface FeeProgramRepository {

   /**
    * Save.
    *
    * @param feeProgram
    *           the fee program
    * @return the fee program
    */
   public FeeProgram save(FeeProgram feeProgram);

   /**
    * Update.
    *
    * @param feeProgram
    *           the fee program
    * @return the fee program
    */
   public FeeProgram update(FeeProgram feeProgram);

   /**
    * Find by id.
    *
    * @param id
    *           the id
    * @return the fee program
    */
   public FeeProgram findById(Long id);

   /**
    * Find by name.
    *
    * @param name
    *           the name
    * @return the fee program
    */
   public FeeProgram findByName(String name);

   /**
    * Find by date.
    *
    * @param Date
    *           the date
    * @return the fee program
    */
   public FeeProgram findByDate(Date date);

   /**
    * Gets the fee program list.
    *
    * @return the fee program list
    */
   public List<FeeProgram> getFeeProgramList();
}
