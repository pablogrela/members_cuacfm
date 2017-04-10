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
package org.cuacfm.members.model.programservice;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.exceptions.ExistPaymentsException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.program.ProgramCategory;
import org.cuacfm.members.model.program.ProgramDTO;
import org.cuacfm.members.model.program.ProgramLanguage;
import org.cuacfm.members.model.program.ProgramThematic;
import org.cuacfm.members.model.program.ProgramType;
import org.springframework.web.multipart.MultipartFile;

/** The Class ProgramService. */
public interface ProgramService {

	/**
	 * Save an training into database.
	 *
	 * @param program the training
	 * @return Program
	 * @throws UniqueException the unique exception
	 */
	public Program save(Program program) throws UniqueException;

	/**
	 * Update Program.
	 *
	 * @param program the program
	 * @return Program
	 * @throws UniqueException the unique exception
	 */
	public Program update(Program program) throws UniqueException;

	/**
	 * Delete.
	 *
	 * @param program the program
	 * @throws ExistPaymentsException the exist payments exception
	 */
	public void delete(Program program) throws ExistPaymentsException;

	/**
	 * Delete.
	 *
	 * @param program the program
	 * @param account the account
	 * @throws ExistPaymentsException the exist payments exception
	 */
	public void delete(Program program, Account account) throws ExistPaymentsException;

	/**
	 * Find by Name the program.
	 *
	 * @param name the name
	 * @return Program
	 */
	public Program findByName(String name);

	/**
	 * Find by id returns program which has this identifier.
	 *
	 * @param id the id
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
	 * Gets the program list active by user.
	 *
	 * @param account the account
	 * @return the program list active by user
	 */
	public List<Program> getProgramListActiveByUser(Account account);

	/**
	 * Gets the program list close.
	 *
	 * @return the program list close
	 */
	public List<Program> getProgramListClose();

	/**
	 * Gets the program list active whitout pays.
	 *
	 * @param month the month
	 * @return the program list active whitout pays
	 */
	public List<Program> getProgramListActiveWhitoutPays(Date month);

	/**
	 * Up.
	 *
	 * @param program the program
	 */
	public void up(Program program);

	/**
	 * Down.
	 *
	 * @param program the program
	 */
	public void down(Program program);

	/**
	 * Gets the programs DTO.
	 *
	 * @param programs the programs
	 * @return the programs DTO
	 */
	public List<ProgramDTO> getProgramsDTO(List<Program> programs);

	/**
	 * Gets the program DTO.
	 *
	 * @param program the program
	 * @return the program DTO
	 */
	public ProgramDTO getProgramDTO(Program program);

	/**
	 * Find program type list.
	 *
	 * @return the list
	 */
	public List<ProgramType> findProgramTypeList();

	/**
	 * Find program type by id.
	 *
	 * @param id the id
	 * @return the program type
	 */
	public ProgramType findProgramTypeById(int id);

	/**
	 * Find program type by name.
	 *
	 * @param name the name
	 * @return the program type
	 */
	public ProgramType findProgramTypeByName(String name);

	/**
	 * Find program thematic list.
	 *
	 * @return the list
	 */
	public List<ProgramThematic> findProgramThematicList();

	/**
	 * Find program thematic by id.
	 *
	 * @param id the id
	 * @return the program thematic
	 */
	public ProgramThematic findProgramThematicById(int id);

	/**
	 * Find program thematic by name.
	 *
	 * @param name the name
	 * @return the program thematic
	 */
	public ProgramThematic findProgramThematicByName(String name);

	/**
	 * Find program category list.
	 *
	 * @return the list
	 */
	public List<ProgramCategory> findProgramCategoryList();

	/**
	 * Find program category by id.
	 *
	 * @param id the id
	 * @return the program category
	 */
	public ProgramCategory findProgramCategoryById(int id);

	/**
	 * Find program category by name.
	 *
	 * @param name the name
	 * @return the program category
	 */
	public ProgramCategory findProgramCategoryByName(String name);

	/**
	 * Find program language by name.
	 *
	 * @param name the name
	 * @return the program language
	 */
	public ProgramLanguage findProgramLanguageByName(String name);

	/**
	 * Find program language list.
	 *
	 * @return the list
	 */
	public List<ProgramLanguage> findProgramLanguageList();

	/**
	 * Find program language by id.
	 *
	 * @param id the id
	 * @return the program language
	 */
	public ProgramLanguage findProgramLanguageById(int id);

	/**
	 * Process json.
	 *
	 * @param file the file
	 * @return the string
	 */
	public String processJson(MultipartFile file);
}
