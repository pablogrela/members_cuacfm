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
package org.cuacfm.members.web.book;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.book.Book;
import org.cuacfm.members.model.element.Element;
import org.cuacfm.members.model.util.Constants;
import org.cuacfm.members.model.util.DateUtils;
import org.hibernate.validator.constraints.NotBlank;

/** The Class BookForm. */
public class BookForm {

	@NotNull(message = Constants.NOT_BLANK_MESSAGE)
	private Long elementId;
	private List<Element> elements;

	@Size(max = 500, message = Constants.MAX_CHARACTERS)
	private String description;

	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	private String timeStart;

	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	private String dateStart;

	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	private String timeEnd;

	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	private String dateEnd;

	/**
	 * Instantiates a new book form.
	 */
	public BookForm() {
		super();
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

	public Long getElementId() {
		return elementId;
	}

	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}

	public Element getElement() {
		for (Element element : elements) {
			if (element.getId().equals(elementId)) {
				return element;
			}
		}
		return null;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	/**
	 * Creates the book.
	 *
	 * @return the book
	 */
	public Book createBook(Account account) {
		return new Book(account, getElement(), getDescription(), DateUtils.format(dateStart + " " + timeStart, DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format(dateEnd + " " + timeEnd, DateUtils.FORMAT_LOCAL_DATE));
	}
}
