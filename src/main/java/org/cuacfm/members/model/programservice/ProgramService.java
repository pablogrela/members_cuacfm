package org.cuacfm.members.model.programservice;

import java.util.List;

import org.cuacfm.members.model.exceptions.ExistPaymentsException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.program.Program;

/** The Class ProgramService. */
public interface ProgramService {

   /**
    * Save an training into database.
    *
    * @param program
    *           the training
    * @return Program
    * @throws UniqueException
    *            the unique exception
    */
   public Program save(Program program) throws UniqueException;

   /**
    * Update Program.
    *
    * @param program
    *           the program
    * @return Program
    * @throws UniqueException
    *            the unique exception
    */
   public Program update(Program program) throws UniqueException;

   /**
    * Delete.
    *
    * @param id
    *           the id
    * @throws ExistPaymentsException
    *            the exist payments exception
    */
   public void delete(Long id) throws ExistPaymentsException;

   /**
    * Find by Name the program.
    *
    * @param name
    *           the name
    * @return Program
    */
   public Program findByName(String name);

   /**
    * Find by id returns program which has this identifier.
    *
    * @param id
    *           the id
    * @return program
    */
   public Program findById(Long id);

   /**
    * Get all programs.
    *
    * @return List<Program>
    */
   public List<Program> getProgramList();

   /**
    * Get all active programs.
    *
    * @return List<Program>
    */
   public List<Program> getProgramListActive();

   /**
    * Up.
    *
    * @param id
    *           the id
    */
   public void up(Long id);

   /**
    * Down.
    *
    * @param id
    *           the id
    */
   public void down(Long id);
}
