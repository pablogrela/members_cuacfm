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

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.cuacfm.members.model.account.Account;

/** The Class Program. */
@Entity
public class Program implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String name;

	private String description;
	private Float periodicity;
	private int duration;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "UserPrograms", joinColumns = { @JoinColumn(name = "programId") }, inverseJoinColumns = { @JoinColumn(name = "accountId") })
	private List<Account> accounts;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "accountPayer")
	private Account accountPayer;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "programType")
	private ProgramType programType;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "programThematic")
	private ProgramThematic programThematic;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "programLanguage")
	private ProgramLanguage programLanguage;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "programCategory")
	private ProgramCategory programCategory;

	private String email;
	private String twitter;
	private String facebook;
	private String podcast;
	private String web;

	/** The active is a check if program if active*. */
	private boolean active;

	private Date dateCreate;
	private Date dateDown;

	/** Instantiates a new program. */
	public Program() {
		super();
	}

	/**
	 * Instantiates a new program.
	 *
	 * @param name the name
	 * @param description the description
	 * @param periodicity the periodicity
	 * @param duration the duration
	 * @param accounts the accounts
	 * @param accountPayer the account payer
	 * @param programType the program type
	 * @param programThematic the program thematic
	 * @param programLanguage the program language
	 * @param email the email
	 * @param twitter the twitter
	 * @param facebook the facebook
	 * @param podcast the podcast
	 * @param web the web
	 */
	public Program(String name, String description, Float periodicity, int duration, List<Account> accounts, Account accountPayer,
			ProgramType programType, ProgramThematic programThematic, ProgramCategory programCategory, ProgramLanguage programLanguage, String email,
			String twitter, String facebook, String podcast, String web) {
		super();
		this.name = name;
		this.description = description;
		this.periodicity = periodicity;
		this.duration = duration;
		this.accounts = accounts;
		this.accountPayer = accountPayer;
		this.programType = programType;
		this.programThematic = programThematic;
		this.programCategory = programCategory;
		this.programLanguage = programLanguage;
		this.email = email;
		this.twitter = twitter;
		this.facebook = facebook;
		this.podcast = podcast;
		this.web = web;
		this.active = false;
		this.dateCreate = new Date();
	}

	public Long getId() {
		return id;
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

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Account getAccountPayer() {
		return accountPayer;
	}

	public void setAccountPayer(Account accountPayer) {
		this.accountPayer = accountPayer;
	}

	public ProgramType getProgramType() {
		return programType;
	}

	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}

	public ProgramThematic getProgramThematic() {
		return programThematic;
	}

	public void setProgramThematic(ProgramThematic programThematic) {
		this.programThematic = programThematic;
	}

	public ProgramCategory getProgramCategory() {
		return programCategory;
	}

	public void setProgramCategory(ProgramCategory programCategory) {
		this.programCategory = programCategory;
	}

	public ProgramLanguage getProgramLanguage() {
		return programLanguage;
	}

	public void setProgramLanguage(ProgramLanguage programLanguage) {
		this.programLanguage = programLanguage;
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
		return "Program [id=" + id + ", name=" + name + ", description=" + description + ", periodicity=" + periodicity + ", duration=" + duration
				+ ", accounts=" + accounts + ", accountPayer=" + accountPayer + ", programType=" + programType + ", programThematic="
				+ programThematic + ", programLanguage=" + programLanguage + ", programCategory=" + programCategory + ", email=" + email
				+ ", twitter=" + twitter + ", facebook=" + facebook + ", podcast=" + podcast + ", web=" + web + ", active=" + active + ", dateCreate="
				+ dateCreate + ", dateDown=" + dateDown + ", getId()=" + getId() + ", getName()=" + getName() + ", getDescription()="
				+ getDescription() + ", getPeriodicity()=" + getPeriodicity() + ", getDuration()=" + getDuration() + ", getAccounts()="
				+ getAccounts() + ", getAccountPayer()=" + getAccountPayer() + ", getProgramType()=" + getProgramType() + ", getProgramThematic()="
				+ getProgramThematic() + ", getProgramCategory()=" + getProgramCategory() + ", getProgramLanguage()=" + getProgramLanguage()
				+ ", getEmail()=" + getEmail() + ", getTwitter()=" + getTwitter() + ", getFacebook()=" + getFacebook() + ", getPodcast()="
				+ getPodcast() + ", getWeb()=" + getWeb() + ", isActive()=" + isActive() + ", getDateCreate()=" + getDateCreate() + ", getDateDown()="
				+ getDateDown() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
