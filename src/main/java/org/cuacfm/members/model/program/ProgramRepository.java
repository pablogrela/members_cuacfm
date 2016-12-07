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
package org.cuacfm.members.model.program;

import java.util.List;

/**
 * The Interface ProgramRepository.
 */
public interface ProgramRepository {

	/**
	 * Save.
	 *
	 * @param program the program
	 * @return program
	 */
	public Program save(Program program);

	/**
	 * Update.
	 *
	 * @param program the program
	 * @return program
	 */
	public Program update(Program program);

	/**
	 * Delete.
	 *
	 * @param program the program
	 */
	public void delete(Program program);

	/**
	 * Find by id.
	 *
	 * @param id the id of program
	 * @return the program
	 */
	public Program findById(Long id);

	/**
	 * Find by login.
	 *
	 * @param name the name of program
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
