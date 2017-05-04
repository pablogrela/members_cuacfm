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
package org.cuacfm.members.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.program.Program;

/** The Class Report. */
@Entity
public class Report implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accountId")
	private Account account;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "programId")
	private Program program;

	private int dirt;
	private int tidy;
	private int configuration;
	private boolean openDoor;
	private boolean viewMembers;
	private String location;
	private String description;
	private String file;
	private String files;
	private String answer;
	private Date dateCreate;
	private Date dateRevision;
	private boolean active;

	/** Instantiates a new report. */
	public Report() {
		super();
	}

	/**
	 * Instantiates a new report.
	 *
	 * @param account the account
	 * @param program the program
	 * @param dirt the dirt
	 * @param tidy the tidy
	 * @param configuration the configuration
	 * @param openDoor the open door
	 * @param viewMembers the view members
	 * @param description the description
	 */
	public Report(Account account, Program program, int dirt, int tidy, int configuration, boolean openDoor, boolean viewMembers, String location,
			String description) {
		super();
		this.account = account;
		this.program = program;
		this.dirt = dirt;
		this.tidy = tidy;
		this.configuration = configuration;
		this.openDoor = openDoor;
		this.viewMembers = viewMembers;
		this.location = location;
		this.description = description;
		this.dateCreate = new Date();
		// by default is no active
		this.active = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public List<String> getFiles() {
		List<String> newFiles = new ArrayList<>();
		if (files != null && !files.isEmpty()) {
			String filesaux = files.replace("[", "").replace("]", "");
			newFiles = Arrays.asList(filesaux.split(", "));
		}
		return newFiles;
	}

	public void setFiles(List<String> files) {
		this.files = files.toString();
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
		return "Report [id=" + id + ", account=" + account + ", program=" + program + ", dirt=" + dirt + ", tidy=" + tidy + ", configuration="
				+ configuration + ", openDoor=" + openDoor + ", viewMembers=" + viewMembers + ", description=" + description + ", file=" + file
				+ ", files=" + files + ", answer=" + answer + ", dateCreate=" + dateCreate + ", dateRevision=" + dateRevision + ", active=" + active
				+ "]";
	}

}
