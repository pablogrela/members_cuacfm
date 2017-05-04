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
package org.cuacfm.members.web.report;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.report.Report;
import org.cuacfm.members.model.util.Constants;
import org.springframework.web.multipart.MultipartFile;

/** The Class ReportForm. */
public class ReportForm {

	@NotNull(message = Constants.NOT_BLANK_MESSAGE)
	private Long programId;
	private List<Program> programs;

	@Max(5)
	@Min(1)
	private int dirt;

	@Max(5)
	@Min(1)
	private int tidy;

	@Max(5)
	@Min(1)
	private int configuration;

	private Boolean openDoor;
	private Boolean viewMembers;

	private MultipartFile[] photos;

	@Size(max = 500, message = Constants.MAX_CHARACTERS)
	private String location;

	@Size(max = 500, message = Constants.MAX_CHARACTERS)
	private String description;

	/**
	 * Instantiates a new report form.
	 */
	public ReportForm() {
		super();
	}

	public List<Program> getPrograms() {
		return programs;
	}

	public void setPrograms(List<Program> programs) {
		this.programs = programs;
	}

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	public Program getProgram() {
		for (Program program : programs) {
			if (program.getId() == programId) {
				return program;
			}
		}
		return null;
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

	public Boolean getOpenDoor() {
		return openDoor;
	}

	public void setOpenDoor(Boolean openDoor) {
		this.openDoor = openDoor;
	}

	public Boolean getViewMembers() {
		return viewMembers;
	}

	public void setViewMembers(Boolean viewMembers) {
		this.viewMembers = viewMembers;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public MultipartFile[] getPhotos() {
		return photos;
	}

	public void setPhotos(MultipartFile[] photos) {
		this.photos = photos;
	}

	/**
	 * Creates the report.
	 *
	 * @param account the account
	 * @return the report
	 */
	public Report createReport(Account account) {
		return new Report(account, getProgram(), getDirt(), getTidy(), getConfiguration(), getOpenDoor(), getViewMembers(), getLocation(),
				getDescription());
	}

	/**
	 * Update report.
	 *
	 * @param report the report
	 * @return the report
	 */
	public Report updateReport(Report report) {
		//		report.setName(getName());
		//		report.setDescription(getDescription());
		//		report.setPeriodicity(getPeriodicity());
		//		report.setDuration(getDuration());
		//		report.setEmail(email);
		//		report.setTwitter(twitter);
		//		report.setFacebook(facebook);
		//		report.setPodcast(podcast);
		//		report.setWeb(web);

		return report;
	}
}
