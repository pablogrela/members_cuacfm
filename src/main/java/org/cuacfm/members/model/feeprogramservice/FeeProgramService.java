/**
 * Copyright © 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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

import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feeprogram.FeeProgram;

/** The Class FeeProgramService. */
public interface FeeProgramService {

	/**
	 * Save.
	 *
	 * @param feeProgram the fee program
	 * @return the fee program
	 * @throws UniqueException the unique exception
	 */
	public FeeProgram save(FeeProgram feeProgram) throws UniqueException;

	/**
	 * Update.
	 *
	 * @param feeProgram the fee program
	 * @return the fee program
	 * @throws UniqueException the unique exception
	 */
	public FeeProgram update(FeeProgram feeProgram) throws UniqueException;

	/**
	 * Refresh.
	 *
	 * @param feeProgram the fee program
	 * @return the fee program
	 */
	public FeeProgram refresh(FeeProgram feeProgram);

	/**
	 * Find by Name the feeProgram.
	 *
	 * @param name the name
	 * @return FeeProgram
	 */
	public FeeProgram findByName(String name);

	/**
	 * Find by id returns feeProgram which has this identifier.
	 *
	 * @param id the id
	 * @return feeProgram
	 */
	public FeeProgram findById(Long id);

	/**
	 * Find by date.
	 *
	 * @param date the date
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
