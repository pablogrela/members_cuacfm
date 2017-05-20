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

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.book.Book;
import org.cuacfm.members.model.bookservice.BookService;
import org.cuacfm.members.model.element.Element;
import org.cuacfm.members.model.elementservice.ElementService;
import org.cuacfm.members.model.exceptions.DatesException;
import org.cuacfm.members.model.exceptions.UserAlreadyBookException;
import org.cuacfm.members.web.support.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class BookCreateController. */
@Controller
public class BookCreateController {

	private static final Logger logger = LoggerFactory.getLogger(BookCreateController.class);
	private static final String BOOK_VIEW_NAME = "book/bookcreate";

	@Autowired
	private BookService bookService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ElementService elementService;

	private BookForm bookForm;

	/**
	 * Instantiates a new bookController.
	 */
	public BookCreateController() {
		super();
	}

	/**
	 * Book form.
	 *
	 * @return the book form
	 */
	@ModelAttribute("bookForm")
	public BookForm bookForm() {
		return bookForm;
	}

	/**
	 * Book.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "bookList/bookCreate")
	public String book(Model model) {
		bookForm = new BookForm();
		List<Element> elements = elementService.getElementListBook();
		bookForm.setElements(elements);
		model.addAttribute(bookForm);
		model.addAttribute("bookCreate", "bookCreate");
		model.addAttribute("book", "books");
		return BOOK_VIEW_NAME;
	}

	/**
	 * Book user.
	 *
	 * @param model the model
	 * @param principal the principal
	 * @return the string
	 */
	@RequestMapping(value = "bookUserList/bookUserCreate")
	public String bookUser(Model model, Principal principal) {
		bookForm = new BookForm();

		List<Element> elements = elementService.getElementListBook();
		bookForm.setElements(elements);
		model.addAttribute(bookForm);
		model.addAttribute("bookCreate", "bookUserCreate");
		model.addAttribute("book", "book.list.user");
		return BOOK_VIEW_NAME;
	}

	/**
	 * Creates the book authorize.
	 *
	 * @param bookForm the book form
	 * @param principal the principal
	 * @param errors the errors
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "bookList/bookCreate", method = RequestMethod.POST)
	public String createBookAuthorize(@Valid @ModelAttribute BookForm bookForm, Principal principal, Errors errors, RedirectAttributes ra) {
		return createBook(bookForm, principal, errors, ra, "redirect:/bookList");
	}

	/**
	 * Creates the book user.
	 *
	 * @param bookForm the book form
	 * @param principal the principal
	 * @param errors the errors
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "bookUserList/bookUserCreate", method = RequestMethod.POST)
	public String createBookUser(@Valid @ModelAttribute BookForm bookForm, Principal principal, Errors errors, RedirectAttributes ra) {
		return createBook(bookForm, principal, errors, ra, "redirect:/bookUserList");
	}

	/**
	 * Creates the book.
	 *
	 * @param bookForm the book form
	 * @param principal the principal
	 * @param errors the errors
	 * @param ra the ra
	 * @param redirect the redirect
	 * @return the string
	 */
	private String createBook(BookForm bookForm, Principal principal, Errors errors, RedirectAttributes ra, String redirect) {

		if (errors.hasErrors()) {
			return BOOK_VIEW_NAME;
		}

		Book book;
		try {
			Account account = accountService.findByLogin(principal.getName());
			book = bookService.save(bookForm.createBook(account));
		} catch (DatesException e) {
			logger.error("createBook", e);
			if (e.getDateLimit().before(new Date())) {
				errors.rejectValue("dateStart", "book.dateStart.past", new Object[] { e.getDateTraining() }, "dateStart");
			} else {
				errors.rejectValue("dateEnd", "book.dateStart.error", new Object[] { e.getDateTraining() }, "dateEnd");
			}
			return BOOK_VIEW_NAME;
		} catch (UserAlreadyBookException e) {
			logger.error("createBook", e);
			errors.rejectValue("elementId", "book.create.error.already", new Object[] { e.getName() }, "elementId");
			return BOOK_VIEW_NAME;
		} catch (Exception e) {
			logger.error("createBook", e);
			errors.rejectValue("elementId", "book.create.error", new Object[] { e }, "elementId");
			return BOOK_VIEW_NAME;
		}

		if (book.getAnswer() != null && !book.getAnswer().isEmpty()) {
			MessageHelper.addWarningAttribute(ra, "book.create.warning", bookForm.getElement().getName());
		} else {
			MessageHelper.addSuccessAttribute(ra, "book.create.success", bookForm.getElement().getName());
		}
		return redirect;
	}

}
