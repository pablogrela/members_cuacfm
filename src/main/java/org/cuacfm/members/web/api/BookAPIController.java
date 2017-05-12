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
package org.cuacfm.members.web.api;

import static org.cuacfm.members.model.util.FirebaseUtils.getEmailOfToken;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.permissions;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.book.Book;
import org.cuacfm.members.model.book.BookDTO;
import org.cuacfm.members.model.bookservice.BookService;
import org.cuacfm.members.model.exceptions.UserAlreadyBookException;
import org.cuacfm.members.model.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/** The Class BookListController. */
@Controller
public class BookAPIController {

	private static final Logger logger = LoggerFactory.getLogger(BookAPIController.class);

	@Autowired
	private BookService bookService;

	@Autowired
	private AccountService accountService;

	/**
	 * Gets the books API.
	 *
	 * @param token the token
	 * @return the books API
	 */
	@RequestMapping(value = "api/bookList/")
	public ResponseEntity<String> getBooksAPI(@RequestParam(value = "token") String token) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			Account account = accountService.findByEmail(email);
			if (account.getPermissions().contains(permissions.ROLE_BOOK.toString())) {
				List<BookDTO> booksDTO = bookService.getBooksDTO(bookService.getBookListActive());
				String booksJson = new Gson().toJson(booksDTO);
				return new ResponseEntity<>(booksJson, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}

	/**
	 * Gets the books API.
	 *
	 * @param token the token
	 * @return the books API
	 */
	@RequestMapping(value = "api/bookUserList/")
	public ResponseEntity<String> getBooksUserAPI(@RequestParam(value = "token") String token) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			Account account = accountService.findByEmail(email);
			List<BookDTO> booksDTO = bookService.getBooksDTO(bookService.getBookListByUser(account));
			String booksJson = new Gson().toJson(booksDTO);
			return new ResponseEntity<>(booksJson, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}

	/**
	 * Book answer API.
	 *
	 * @param bookId the book id
	 * @param token the token
	 * @param answer the answer
	 * @param manage the manage
	 * @return the response entity
	 */
	@RequestMapping(value = { "api/bookList/bookAnswer/{bookId}",
			"api/bookUserList/bookAnswer/{bookId}" }, method = RequestMethod.POST)
	public ResponseEntity<String> bookAnswerAPI(@PathVariable("bookId") Long bookId, @RequestParam(value = "token") String token,
			@RequestParam(value = "answer") String answer, @RequestParam(value = "manage") Boolean manage) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			Account account = accountService.findByEmail(email);
			Book book = bookService.findById(bookId);

			book = bookService.answer(book, account, answer, manage);

			BookDTO newBookDTO = bookService.getBookDTO(book);
			String newBookJson = new Gson().toJson(newBookDTO);
			return new ResponseEntity<>(newBookJson, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}

	/**
	 * Creates the book API.
	 *
	 * @param token the token
	 * @param bookJson the book json
	 * @return the response entity
	 */
	@RequestMapping(value = "api/bookList/bookCreate", method = RequestMethod.POST)
	public ResponseEntity<String> createBookAPI(@RequestParam(value = "token") String token,
			@RequestParam(value = "bookJson") String bookJson) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			try {
				Account account = accountService.findByEmail(email);
				Gson gson = new GsonBuilder().setDateFormat(DateUtils.FORMAT_LOCAL).create();
				BookDTO bookDTO = gson.fromJson(bookJson, BookDTO.class);
				Book book = bookService.getBook(bookDTO, account);
				book = bookService.save(book);
				BookDTO newBookDTO = bookService.getBookDTO(book);
				String newBookJson = new Gson().toJson(newBookDTO);
				return new ResponseEntity<>(newBookJson, HttpStatus.CREATED);
			} catch (UserAlreadyBookException e) {
				logger.error("createBookAPI UserAlreadyBookException", e);
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			} catch (Exception e) {
				logger.error("createBookAPI", e);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
}
