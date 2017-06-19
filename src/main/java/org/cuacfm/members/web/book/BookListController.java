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
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.book.Book;
import org.cuacfm.members.model.book.BookDTO;
import org.cuacfm.members.model.bookservice.BookService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class BookListController. */
@Controller
public class BookListController {

	//private static final Logger logger = LoggerFactory.getLogger(BookListController.class)
	private static final String BOOK_VIEW_NAME = "book/booklist";
	private static final String BOOK_CLOSE_VIEW_NAME = "book/booklistclose";
	private static final String BOOK_USER_VIEW_NAME = "book/bookuserlist";

	@Autowired
	private BookService bookService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Instantiates a new training Controller.
	 */
	public BookListController() {
		super();
	}

	/**
	 * Show Book List.
	 *
	 * @return the string the view
	 * @throws UniqueException the unique exception
	 */

	@RequestMapping(value = "bookList")
	public String getBookListView() {
		return BOOK_VIEW_NAME;
	}

	/**
	 * Gets the book list close view.
	 *
	 * @return the book list close view
	 */
	@RequestMapping(value = "bookList/close")
	public String getBookListCloseView() {
		return BOOK_CLOSE_VIEW_NAME;
	}

	/**
	 * Gets the book user list view.
	 *
	 * @return the book user list view
	 */
	@RequestMapping(value = "bookUserList")
	public String getBookUserListView() {
		return BOOK_USER_VIEW_NAME;
	}

	/**
	 * Gets the books.
	 *
	 * @param principal the principal
	 * @return the books
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "bookList/")
	public ResponseEntity<List<BookDTO>> getBooks(Principal principal) {

		// List of books
		List<BookDTO> booksDTO = bookService.getBooksDTO(bookService.getBookListActive());

		if (booksDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(booksDTO, HttpStatus.OK);
	}

	/**
	 * Gets the books user.
	 *
	 * @param principal the principal
	 * @return the books user
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "bookUserList/")
	public ResponseEntity<List<BookDTO>> getBooksUser(Principal principal) {

		Account account = accountService.findByLogin(principal.getName());

		// List of books
		List<BookDTO> booksDTO = bookService.getBooksDTO(bookService.getBookListByUser(account));
		if (booksDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(booksDTO, HttpStatus.OK);
	}

	/**
	 * Gets the books close.
	 *
	 * @param principal the principal
	 * @return the books close
	 */
	@RequestMapping(value = "bookList/close/")
	public ResponseEntity<List<BookDTO>> getBooksClose(Principal principal) {

		// List of books
		List<BookDTO> booksDTO = bookService.getBooksDTO(bookService.getBookListClose());
		if (booksDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(booksDTO, HttpStatus.OK);
	}

	/**
	 * Book accept.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "bookList/bookAccept/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> bookAccept(@PathVariable("id") Long id, RedirectAttributes ra) {

		Book book = bookService.findById(id);

		bookService.accept(book);
		Object[] arguments = { book.getElement().getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("book.accept.success", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Book deny.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "bookList/bookDeny/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> bookDeny(@PathVariable("id") Long id, RedirectAttributes ra) {

		Book book = bookService.findById(id);

		bookService.deny(book);
		Object[] arguments = { book.getElement().getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("book.deny.success", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Book answer.
	 *
	 * @param principal the principal
	 * @param bookId the book id
	 * @param answer the answer
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = { "bookList/bookAnswer/{bookId}", "bookUserList/bookAnswer/{bookId}" }, method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> bookAnswer(Principal principal, @PathVariable("bookId") Long bookId,
			@RequestParam(value = "answer") String answer, RedirectAttributes ra) {

		Book book = bookService.findById(bookId);
		bookService.answer(book, accountService.findByLogin(principal.getName()), answer, null);

		Object[] arguments = { book.getElement().getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("book.answer.success", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Book down.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "bookList/bookUp/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> bookUp(@PathVariable("id") Long id, RedirectAttributes ra) {

		Book book = bookService.findById(id);

		bookService.up(book);
		Object[] arguments = { book.getElement().getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("book.up.sucess", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}
}
