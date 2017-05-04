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
package org.cuacfm.members.model.inscription;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.training.Training;

/** The Class Inscription. */
@SuppressWarnings("serial")
@Entity
public class Inscription implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "accountId")
	private Account account;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "trainingId")
	private Training training;

	private boolean attend;
	private String note;
	private boolean pass;
	private boolean unsubscribe;

	/**
	 * Instantiates a new inscription.
	 */
	public Inscription() {
		super();
	}

	/**
	 * Instantiates a new inscription.
	 *
	 * @param account the account
	 * @param training the training
	 */
	public Inscription(Account account, Training training) {
		super();
		this.account = account;
		this.training = training;
	}

	public Long getId() {
		return id;
	}

	public Account getAccount() {
		return account;
	}

	public Training getTraining() {
		return training;
	}

	public boolean isAttend() {
		return attend;
	}

	public void setAttend(boolean attend) {
		this.attend = attend;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public boolean isUnsubscribe() {
		return unsubscribe;
	}

	public void setUnsubscribe(boolean unsubscribe) {
		this.unsubscribe = unsubscribe;
	}

	@Override
	public String toString() {
		return "Inscription [id=" + id + ", account=" + account + ", training=" + training + ", attend=" + attend + ", note=" + note + ", pass="
				+ pass + ", unsubscribe=" + unsubscribe + "]";
	}

}
