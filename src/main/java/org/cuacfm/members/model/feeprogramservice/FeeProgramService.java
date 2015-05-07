package org.cuacfm.members.model.feeprogramservice;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feeprogram.FeeProgram;

/** The Class FeeProgramService. */
public interface FeeProgramService {

   /**
    * Save.
    *
    * @param feeProgram
    *           the fee program
    * @return the fee program
    * @throws UniqueException
    */
   public FeeProgram save(FeeProgram feeProgram) throws UniqueException;

   /**
    * Update.
    *
    * @param feeProgram
    *           the fee program
    * @return the fee program
    * @throws UniqueException
    */
   public FeeProgram update(FeeProgram feeProgram) throws UniqueException;

   /**
    * Find by Name the feeProgram.
    *
    * @param name
    *           the name
    * @return FeeProgram
    */
   public FeeProgram findByName(String name);

   /**
    * Find by id returns feeProgram which has this identifier.
    *
    * @param id
    *           the id
    * @return feeProgram
    */
   public FeeProgram findById(Long id);

   /**
    * Find by date.
    *
    * @param Date
    *           the date
    * @return the fee program
    */
   public FeeProgram findByDate(Date date);

   /**
    * Get all feePrograms.
    *
    * @return List<FeeProgram>
    */
   public List<FeeProgram> getFeeProgramList();

}
