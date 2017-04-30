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
package org.cuacfm.members.model.reserve;

import java.util.Date;

import org.cuacfm.members.model.account.AccountDTO;
import org.cuacfm.members.model.element.ElementDTO;
import org.cuacfm.members.model.util.Constants.states;

/**
 * The Class ReportDTO.
 */
public class ReserveDTO {

	private Long id;
	private AccountDTO account;
	private ElementDTO element;
	private String description;
	private String answer;
	private Date dateCreate;
	private Date dateStart;
	private Date dateEnd;
	private Date dateRevision;
	private Date dateApproval;
	private states state;
	private boolean active;

	/**
	 * Instantiates a new reserve DTO.
	 *
	 * @param id the id
	 * @param account the account
	 * @param element the element
	 * @param description the description
	 * @param answer the answer
	 * @param dateCreate the date create
	 * @param dateStart the date start
	 * @param dateEnd the date end
	 * @param dateRevision the date revision
	 * @param dateApproval the date approval
	 * @param state the state
	 * @param active the active
	 */
	public ReserveDTO(Long id, AccountDTO account, ElementDTO element, String description, String answer, Date dateCreate, Date dateStart,
			Date dateEnd, Date dateRevision, Date dateApproval, states state, boolean active) {
		super();
		this.id = id;
		this.account = account;
		this.element = element;
		this.description = description;
		this.answer = answer;
		this.dateCreate = dateCreate;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.dateRevision = dateRevision;
		this.dateApproval = dateApproval;
		this.state = state;
		this.active = active;
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

	public ElementDTO getElement() {
		return element;
	}

	public void setElement(ElementDTO element) {
		this.element = element;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Date getDateRevision() {
		return dateRevision;
	}

	public void setDateRevision(Date dateRevision) {
		this.dateRevision = dateRevision;
	}

	public Date getDateApproval() {
		return dateApproval;
	}

	public void setDateApproval(Date dateApproval) {
		this.dateApproval = dateApproval;
	}

	public states getState() {
		return state;
	}

	public void setState(states state) {
		this.state = state;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "ReserveDTO [id=" + id + ", account=" + account + ", element=" + element + ", description=" + description + ", answer=" + answer
				+ ", dateCreate=" + dateCreate + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", dateRevision=" + dateRevision
				+ ", dateApproval=" + dateApproval + ", state=" + state + ", active=" + active + "]";
	}

}
