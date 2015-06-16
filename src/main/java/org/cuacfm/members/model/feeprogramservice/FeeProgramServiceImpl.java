/**
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.model.feeprogramservice;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogram.FeeProgramRepository;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class FeeProgramServiceImpl. */
@Service("feeProgramService")
public class FeeProgramServiceImpl implements FeeProgramService {

   /** The FeeProgram repository. */
   @Autowired
   private FeeProgramRepository feeProgramRepository;

   @Inject
   private ProgramService programService;

   @Inject
   private PayProgramService payProgramService;

   /** Instantiates a new feeProgram service. */
   public FeeProgramServiceImpl() {
      // Default empty constructor.
   }

   /**
    * Save.
    *
    * @param feeProgram
    *           the fee program
    * @return the fee program
    * @throws UniqueException
    */
   @Override
   public FeeProgram save(FeeProgram feeProgram) throws UniqueException {
      // It is verified that there is not exist year of feeProgram in other
      // feeProgram
      if (feeProgramRepository.findByDate(feeProgram.getDate()) != null) {
         throw new UniqueException("Date", String.valueOf(feeProgram.getDate()));
      }
      feeProgramRepository.save(feeProgram);

      // Create payments of programs
      for (Program program : programService.getProgramListActive()) {

         Double price = feeProgram.getPrice() * program.getDuration() * program.getPeriodicity();
         payProgramService.save(new PayProgram(program, feeProgram, price));
      }

      return feeProgram;
   }

   /**
    * Update.
    *
    * @param feeProgram
    *           the fee program
    * @return the fee program
    * @throws UniqueException
    */
   @Override
   public FeeProgram update(FeeProgram feeProgram) throws UniqueException {
      // It is verified that there is not exist name of feeProgram in other
      // feeProgram
      FeeProgram feeProgramSearch = feeProgramRepository.findByDate(feeProgram.getDate());
      if ((feeProgramSearch != null) && (feeProgramSearch.getId() != feeProgram.getId())) {
            throw new UniqueException("Date", String.valueOf(feeProgram.getDate()));
      }
      return feeProgramRepository.update(feeProgram);
   }

   /**
    * Find by Name the feeProgram.
    *
    * @param name
    *           the name
    * @return FeeProgram
    */
   @Override
   public FeeProgram findByName(String name) {
      return feeProgramRepository.findByName(name);
   }

   /**
    * Find by id returns feeProgram which has this identifier.
    *
    * @param id
    *           the id
    * @return feeProgram
    */
   @Override
   public FeeProgram findById(Long id) {
      return feeProgramRepository.findById(id);
   }

   /**
    * Find by date.
    *
    * @param Date
    *           the date
    * @return the fee program
    */
   @Override
   public FeeProgram findByDate(Date date) {
      return feeProgramRepository.findByDate(date);
   }

   /**
    * Get all feePrograms.
    *
    * @return List<FeeProgram>
    */
   @Override
   public List<FeeProgram> getFeeProgramList() {
      return feeProgramRepository.getFeeProgramList();
   }
}
