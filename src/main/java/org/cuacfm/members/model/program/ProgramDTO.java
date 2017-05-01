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
package org.cuacfm.members.model.program;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.AccountDTO;

/**
 * The Class ProgramDTO.
 */
public class ProgramDTO {

	private Long id;
	private String name;
	private String description;
	private Float periodicity;
	private int duration;
	private List<AccountDTO> accounts;
	private AccountDTO accountPayer;
	private String programType;
	private String programThematic;
	private String programLanguage;
	private String programCategory;
	private String email;
	private String twitter;
	private String facebook;
	private String podcast;
	private String web;
	private boolean active;
	private boolean deviceToken;
	private Date dateCreate;
	private Date dateDown;

	/**
	 * Instantiates a new program DTO.
	 */
	public ProgramDTO() {
		super();
	}

	/**
	 * Instantiates a new program DTO.
	 *
	 * @param id the id
	 * @param name the name
	 * @param description the description
	 * @param periodicity the periodicity
	 * @param duration the duration
	 * @param accounts the accounts
	 * @param accountPayer the account payer
	 * @param programType the program type
	 * @param programThematic the program thematic
	 * @param programLanguage the program language
	 * @param programCategory the program category
	 * @param email the email
	 * @param twitter the twitter
	 * @param facebook the facebook
	 * @param podcast the podcast
	 * @param web the web
	 * @param active the active
	 * @param dateCreate the date create
	 * @param dateDown the date down
	 */
	public ProgramDTO(Long id, String name, String description, Float periodicity, int duration, List<AccountDTO> accounts, AccountDTO accountPayer,
			String programType, String programThematic, String programLanguage, String programCategory, String email, String twitter, String facebook,
			String podcast, String web, boolean active, Date dateCreate, Date dateDown) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.periodicity = periodicity;
		this.duration = duration;
		this.accounts = accounts;
		this.accountPayer = accountPayer;
		this.programType = programType;
		this.programThematic = programThematic;
		this.programLanguage = programLanguage;
		this.programCategory = programCategory;
		this.email = email;
		this.twitter = twitter;
		this.facebook = facebook;
		this.podcast = podcast;
		this.web = web;
		this.active = active;
		this.dateCreate = dateCreate;
		this.dateDown = dateDown;
		for (AccountDTO account : accounts) {
			if (account.isDeviceToken()) {
				this.deviceToken = true;
				break;
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(Float periodicity) {
		this.periodicity = periodicity;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public List<AccountDTO> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountDTO> accounts) {
		this.accounts = accounts;
	}

	public AccountDTO getAccountPayer() {
		return accountPayer;
	}

	public void setAccountPayer(AccountDTO accountPayer) {
		this.accountPayer = accountPayer;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public String getProgramThematic() {
		return programThematic;
	}

	public void setProgramThematic(String programThematic) {
		this.programThematic = programThematic;
	}

	public String getProgramLanguage() {
		return programLanguage;
	}

	public void setProgramLanguage(String programLanguage) {
		this.programLanguage = programLanguage;
	}

	public String getProgramCategory() {
		return programCategory;
	}

	public void setProgramCategory(String programCategory) {
		this.programCategory = programCategory;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getPodcast() {
		return podcast;
	}

	public void setPodcast(String podcast) {
		this.podcast = podcast;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(boolean deviceToken) {
		this.deviceToken = deviceToken;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateDown() {
		return dateDown;
	}

	public void setDateDown(Date dateDown) {
		this.dateDown = dateDown;
	}

	@Override
	public String toString() {
		return "ProgramDTO [id=" + id + ", name=" + name + ", description=" + description + ", periodicity=" + periodicity + ", duration=" + duration
				+ ", accounts=" + accounts + ", accountPayer=" + accountPayer + ", programType=" + programType + ", programThematic="
				+ programThematic + ", programLanguage=" + programLanguage + ", programCategory=" + programCategory + ", email=" + email
				+ ", twitter=" + twitter + ", facebook=" + facebook + ", podcast=" + podcast + ", web=" + web + ", active=" + active
				+ ", deviceToken=" + deviceToken + ", dateCreate=" + dateCreate + ", dateDown=" + dateDown + "]";
	}

}
