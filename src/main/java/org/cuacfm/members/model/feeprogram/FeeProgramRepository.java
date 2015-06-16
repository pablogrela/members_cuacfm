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
