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
package org.cuacfm.members.model.event;

import java.util.Date;

import org.cuacfm.members.model.account.AccountDTO;

/**
 * The Class EventDTO.
 */
public class EventDTO {

	private Long id;
	private AccountDTO account;
	private Date dateEvent;
	private int priority;
	private String description;

	/**
	 * Instantiates a new event.
	 */
	public EventDTO() {
		super();
	}

	/**
	 * Instantiates a new event.
	 *
	 * @param id the id
	 * @param accountDTO the account DTO
	 * @param dateEvent the date event
	 * @param priority the priority
	 * @param description the description
	 */
	public EventDTO(Long id, AccountDTO accountDTO, Date dateEvent, int priority, String description) {
		super();
		this.id = id;
		this.account = accountDTO;
		this.dateEvent = dateEvent;
		this.priority = priority;
		this.description = description;
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

	public Date getDateEvent() {
		return dateEvent;
	}

	public void setDateEvent(Date dateEvent) {
		this.dateEvent = dateEvent;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "EventDTO [id=" + id + ", account=" + account + ", dateEvent=" + dateEvent + ", priority=" + priority + ", description=" + description
				+ "]";
	}

}
