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
package org.cuacfm.members.model.incidence;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.AccountDTO;
import org.cuacfm.members.model.program.ProgramDTO;

/**
 * The Class IncidenceDTO.
 */
public class IncidenceDTO {

	private Long id;
	private AccountDTO account;
	private ProgramDTO program;
	private int dirt;
	private int tidy;
	private int configuration;
	private boolean openDoor;
	private boolean viewMembers;
	private String description;
	private List<String> files;
	private String answer;
	private Date dateCreate;
	private Date dateRevision;
	private boolean active;

	/**
	 * Instantiates a new program DTO.
	 */
	public IncidenceDTO() {
		super();
	}

	/**
	 * Instantiates a new incidence DTO.
	 *
	 * @param id the id
	 * @param account the account
	 * @param program the program
	 * @param dirt the dirt
	 * @param tidy the tidy
	 * @param configuration the configuration
	 * @param openDoor the open door
	 * @param viewMembers the view members
	 * @param description the description
	 * @param files the files
	 * @param answer the answer
	 * @param dateCreate the date create
	 * @param dateRevision the date revision
	 * @param active the active
	 */
	public IncidenceDTO(Long id, AccountDTO account, ProgramDTO program, int dirt, int tidy, int configuration, boolean openDoor, boolean viewMembers,
			String description, List<String> files, String answer, Date dateCreate, Date dateRevision, boolean active) {
		super();
		this.id = id;
		this.account = account;
		this.program = program;
		this.dirt = dirt;
		this.tidy = tidy;
		this.configuration = configuration;
		this.openDoor = openDoor;
		this.viewMembers = viewMembers;
		this.description = description;
		this.answer = answer;
		this.dateCreate = dateCreate;
		this.dateRevision = dateRevision;
		this.active = active;
		// List of files
		// this.files = FileUtils.listFilesForFolderToListString(new File(file))
		this.files = files;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AccountDTO getAccount() {
		return account;
	}

	public void setAccount(AccountDTO account) {
		this.account = account;
	}

	public ProgramDTO getProgram() {
		return program;
	}

	public void setProgram(ProgramDTO program) {
		this.program = program;
	}

	public int getDirt() {
		return dirt;
	}

	public void setDirt(int dirt) {
		this.dirt = dirt;
	}

	public int getTidy() {
		return tidy;
	}

	public void setTidy(int tidy) {
		this.tidy = tidy;
	}

	public int getConfiguration() {
		return configuration;
	}

	public void setConfiguration(int configuration) {
		this.configuration = configuration;
	}

	public boolean isOpenDoor() {
		return openDoor;
	}

	public void setOpenDoor(boolean openDoor) {
		this.openDoor = openDoor;
	}

	public boolean isViewMembers() {
		return viewMembers;
	}

	public void setViewMembers(boolean viewMembers) {
		this.viewMembers = viewMembers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateRevision() {
		return dateRevision;
	}

	public void setDateRevision(Date dateRevision) {
		this.dateRevision = dateRevision;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "IncidenceDTO [id=" + id + ", account=" + account + ", program=" + program + ", dirt=" + dirt + ", tidy=" + tidy + ", configuration="
				+ configuration + ", openDoor=" + openDoor + ", viewMembers=" + viewMembers + ", description=" + description + ", files=" + files
				+ ", answer=" + answer + ", dateCreate=" + dateCreate + ", dateRevision=" + dateRevision + ", active=" + active + "]";
	}

}
