package org.cuacfm.members.model.program;

import java.util.List;

public interface ProgramRepository {
   /**
    * Save.
    *
    * @param training
    *           the program
    * @return program
    */
   public Program save(Program program);

   /**
    * Update.
    *
    * @param program
    *           the program
    * @return program
    */
   public Program update(Program program);

   /**
    * Delete.
    *
    * @param program
    *           the program
    */
   public void delete(Long id);

   /**
    * Find by id.
    *
    * @param id
    *           the id of program
    * @return the program
    */
   public Program findById(Long id);

   /**
    * Find by login.
    *
    * @param name
    *           the name of program
    * @return Program
    */
   public Program findByName(String name);

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
}
