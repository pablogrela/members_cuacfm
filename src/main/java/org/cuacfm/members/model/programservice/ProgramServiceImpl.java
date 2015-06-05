package org.cuacfm.members.model.programservice;

import java.util.List;

import org.cuacfm.members.model.exceptions.ExistPaymentsException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.program.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class ProgramServiceImpl. */
@Service("programService")
public class ProgramServiceImpl implements ProgramService {

   /** The program repository. */

   @Autowired
   private PayProgramService payProgramService;

   @Autowired
   private ProgramRepository programRepository;

   /** Instantiates a new program service. */
   public ProgramServiceImpl() {
      // Default empty constructor.
   }

   /**
    * Save an training into database.
    *
    * @param program
    *           the training
    * @return Program
    * @throws UniqueException
    *            the unique exception
    */
   @Override
   public Program save(Program program) throws UniqueException {
      // It is verified that there is not exist name of program in other
      // program
      if (programRepository.findByName(program.getName()) != null) {
         throw new UniqueException("Name", program.getName());
      }
      return programRepository.save(program);
   }

   /**
    * Update Program.
    *
    * @param program
    *           the program
    * @return Program
    * @throws UniqueException
    *            the unique exception
    */
   @Override
   public Program update(Program program) throws UniqueException {
      // It is verified that there is not exist name of program in other
      // program
      Program programSearch = programRepository.findByName(program.getName());
      if ((programSearch != null) && (programSearch.getId() != program.getId())) {
         throw new UniqueException("Name", program.getName());
      }
      return programRepository.update(program);

   }

   /**
    * Delete.
    *
    * @param id
    *           the id
    * @throws ExistPaymentsException
    *            the exist payments exception
    */
   @Override
   public void delete(Long id) throws ExistPaymentsException {
      // If Exist payments
      if (!payProgramService.getPayProgramListByProgramId(id).isEmpty()) {
         throw new ExistPaymentsException();
      }
      programRepository.delete(id);
   }

   /**
    * Find by Name the program.
    *
    * @param login
    *           the login
    * @return Program
    */
   @Override
   public Program findByName(String login) {
      return programRepository.findByName(login);
   }

   /**
    * Find by id returns program which has this identifier.
    *
    * @param id
    *           the id
    * @return program
    */
   @Override
   public Program findById(Long id) {
      return programRepository.findById(id);
   }

   /**
    * Get all programs.
    *
    * @return List<Program>
    */
   @Override
   public List<Program> getProgramList() {
      return programRepository.getProgramList();
   }

   /**
    * Get all active programs.
    *
    * @return List<Program>
    */
   @Override
   public List<Program> getProgramListActive() {
      return programRepository.getProgramListActive();
   }

   /**
    * Up.
    *
    * @param id
    *           the id
    */
   @Override
   public void up(Long id) {
      Program program = programRepository.findById(id);
      program.setActive(true);
      programRepository.update(program);
   }

   /**
    * Down.
    *
    * @param id
    *           the id
    */
   @Override
   public void down(Long id) {
      Program program = programRepository.findById(id);
      program.setActive(false);
      programRepository.update(program);
   }
}
